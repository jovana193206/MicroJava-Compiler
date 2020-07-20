package rs.ac.bg.etf.pp1;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.AddopMinus;
import rs.ac.bg.etf.pp1.ast.AddopPlus;
import rs.ac.bg.etf.pp1.ast.ArrayName;
import rs.ac.bg.etf.pp1.ast.BinaryCondFact;
import rs.ac.bg.etf.pp1.ast.BreakStmt;
import rs.ac.bg.etf.pp1.ast.CondTerms;
import rs.ac.bg.etf.pp1.ast.Conditions;
import rs.ac.bg.etf.pp1.ast.Const;
import rs.ac.bg.etf.pp1.ast.ContinueStmt;
import rs.ac.bg.etf.pp1.ast.DesignAssign;
import rs.ac.bg.etf.pp1.ast.DesignCall;
import rs.ac.bg.etf.pp1.ast.DesignDec;
import rs.ac.bg.etf.pp1.ast.DesignInc;
import rs.ac.bg.etf.pp1.ast.Designator;
import rs.ac.bg.etf.pp1.ast.Factors;
import rs.ac.bg.etf.pp1.ast.ForCond;
import rs.ac.bg.etf.pp1.ast.ForCondition;
import rs.ac.bg.etf.pp1.ast.ForDesStmt1;
import rs.ac.bg.etf.pp1.ast.ForDesStmt2;
import rs.ac.bg.etf.pp1.ast.ForDesignAssign;
import rs.ac.bg.etf.pp1.ast.ForDesignCall;
import rs.ac.bg.etf.pp1.ast.ForDesignDec;
import rs.ac.bg.etf.pp1.ast.ForDesignInc;
import rs.ac.bg.etf.pp1.ast.FormalParamDecl;
import rs.ac.bg.etf.pp1.ast.FuncCall;
import rs.ac.bg.etf.pp1.ast.IfCond;
import rs.ac.bg.etf.pp1.ast.IfCondition;
import rs.ac.bg.etf.pp1.ast.InitElem;
import rs.ac.bg.etf.pp1.ast.InitListStart;
import rs.ac.bg.etf.pp1.ast.MatchedForStmt;
import rs.ac.bg.etf.pp1.ast.MatchedIfElse;
import rs.ac.bg.etf.pp1.ast.MatchedThen;
import rs.ac.bg.etf.pp1.ast.MethodCalled;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MethodTypeName;
import rs.ac.bg.etf.pp1.ast.MulopDivision;
import rs.ac.bg.etf.pp1.ast.MulopMod;
import rs.ac.bg.etf.pp1.ast.MulopMultiplication;
import rs.ac.bg.etf.pp1.ast.NegativeExpr;
import rs.ac.bg.etf.pp1.ast.NewArray;
import rs.ac.bg.etf.pp1.ast.NewArrayInit;
import rs.ac.bg.etf.pp1.ast.NewFactArray;
import rs.ac.bg.etf.pp1.ast.NoForCondition;
import rs.ac.bg.etf.pp1.ast.NoPrintParam;
import rs.ac.bg.etf.pp1.ast.PrintParam;
import rs.ac.bg.etf.pp1.ast.PrintStmt;
import rs.ac.bg.etf.pp1.ast.ProgName;
import rs.ac.bg.etf.pp1.ast.ReadStmt;
import rs.ac.bg.etf.pp1.ast.RelopEqual;
import rs.ac.bg.etf.pp1.ast.RelopGreater;
import rs.ac.bg.etf.pp1.ast.RelopGreaterEq;
import rs.ac.bg.etf.pp1.ast.RelopLess;
import rs.ac.bg.etf.pp1.ast.RelopLessEq;
import rs.ac.bg.etf.pp1.ast.RelopNotEqual;
import rs.ac.bg.etf.pp1.ast.ReturnExpr;
import rs.ac.bg.etf.pp1.ast.ReturnNoExpr;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.Terms;
import rs.ac.bg.etf.pp1.ast.UnaryCondFact;
import rs.ac.bg.etf.pp1.ast.UnmatchedForStmt;
import rs.ac.bg.etf.pp1.ast.UnmatchedIfElse;
import rs.ac.bg.etf.pp1.ast.UnmatchedThen;
import rs.ac.bg.etf.pp1.ast.VarDecl;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	
	private int varCount;
	private int paramCnt;
	private int mainPc;
	
	private Stack<Integer> addOpCodes = new Stack<Integer>();
	private Stack<Integer> mulOpCodes = new Stack<Integer>();
	private int relOpCode = 0;
	
	private int forCondAdr = 0, forFirsStatementPatchAdr = 0;
	//Must be stacks because we save them at the top and use them at the bottom, inner loops would overwrite them otherwise
	private Stack<Integer> forDesStmtm2Adr = new Stack<Integer>(), endForPatchAdr = new Stack<Integer>();
	private Stack<Integer> breakAdr = new Stack<Integer>();
	
	private Stack<Integer> ifStartAdr = new Stack<Integer>(), ifElseAdr  = new Stack<Integer>();
	
	private int printWidth = -1;
	
	private int newArraySize = 0;
	
	
	public int getMainPc() {
		return mainPc;
	}
	
	
//===============================================================================================================================
//Create predeclared functions (ord, chr, len)
	
	@Override
	public void visit(ProgName pName) {
		//Generate code for ord function
		Obj ordFunc = Tab.find("ord");
		ordFunc.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);  //1 formalni parametar i 0 lokalnih promenljivih
		Code.put(Code.load_n + 0); //Stavi na stek vrednost lokalne promenljive
		Code.put(Code.exit);
		Code.put(Code.return_);
		//Generate code for chr function
		Obj chrFunc = Tab.find("chr");
		chrFunc.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);  //1 formalni parametar i 0 lokalnih promenljivih
		Code.put(Code.load_n + 0); //Stavi na stek vrednost lokalne promenljive
		Code.put(Code.exit);
		Code.put(Code.return_);
		//Generate code for len function - int len(int arrayAdr)
		Obj lenFunc = Tab.find("len");
		lenFunc.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);  //1 formalni parametar i 0 lokalnih promenljivih
		Code.put(Code.load_n + 0); //Stavi na stek vrednost lokalne promenljive - adresa niza
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
//===============================================================================================================================
//Method declarations
	
	@Override
	public void visit(MethodTypeName MethodTypeName) {
		if ("main".equalsIgnoreCase(MethodTypeName.getMethName())) {
			mainPc = Code.pc;
		}
		MethodTypeName.obj.setAdr(Code.pc);
		
		// Collect arguments and local variables.
		SyntaxNode methodNode = MethodTypeName.getParent();
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);
		
		int fpCount = fpCnt.getCount(), varCount = varCnt.getCount();
		
		// Generate the entry.
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(varCnt.getCount());
	}
	
	
	@Override
	public void visit(VarDecl VarDecl) {
		varCount++;
	}

	@Override
	public void visit(FormalParamDecl FormalParam) {
		paramCnt++;
	}	
	
	
	@Override
	public void visit(MethodDecl MethodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(ReturnExpr ReturnExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(ReturnNoExpr ReturnNoExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
//===============================================================================================================================
//DesignatorStatements
	
	@Override
	public void visit(DesignInc desInc) {
		Code.load(desInc.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(desInc.getDesignator().obj);
	}
	
	@Override
	public void visit(ForDesignInc desInc) {
		Code.load(desInc.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(desInc.getDesignator().obj);
	}
	
	@Override
	public void visit(DesignDec desDec) {
		Code.load(desDec.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(desDec.getDesignator().obj);
	}
	
	@Override
	public void visit(ForDesignDec desDec) {
		Code.load(desDec.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(desDec.getDesignator().obj);
	}
	
	@Override
	public void visit(DesignAssign Assignment) {
		Code.store(Assignment.getDesignator().obj);
	}
	
	@Override
	public void visit(ForDesignAssign Assignment) {
		Code.store(Assignment.getDesignator().obj);
	}
//===============================================================================================================================
	
	//Odnosi se na pojavu konstante kao faktora u izrazu
	@Override
	public void visit(Const Const) {
		//Code.load(new Obj(Obj.Con, "$", Const.struct, Const.getN1(), 0));   iz sablona
		Code.load(Const.getConstant().obj);
	}
	
	@Override
	public void visit(NewFactArray newArray) {
		//pobrini se za inicijalizatorsku listu
	}
	
	@Override
	public void visit(NewArray newArray) {
		Code.put(Code.newarray);
		if(newArray.getType().struct == Tab.charType || newArray.getType().struct == TabExtended.boolType) {
			Code.put(0);		//0
		}
		else {
			Code.put(1);
		}
		newArraySize = newArray.getExpr().obj.getAdr();
		//Put the array adr on the stack one more time, so that initiList can have it
		if(newArray.getParent().getParent().getParent().getParent().getParent().getParent().getParent().getClass() 
				== NewArrayInit.class) {
			Code.put(Code.dup);
		}
	}
	
	@Override
	public void visit(InitListStart initStart) {
		//Put index for array[newArraySize - 1] initialization (adr is already on the stack)
		Code.loadConst(newArraySize - 1);
		//Put  adr of the array and indexes on the stack to be used for initialization for array[0..length - 2]
		for(int i  = newArraySize - 2; i >= 0; i--) {
			Code.put(Code.dup2);
			Code.put(Code.pop);
			Code.loadConst(i);
		}
	}
	
	@Override
	public void visit(InitElem init) {
		//In this moment we have adr, ind, val on the ExprStack and we should call astore to initialize adr[ind] = val
		Code.put(Code.astore);
	}
	
//==================================================================================================================================
//Obrada Designatora
	
	@Override
	public void visit(Designator Designator) {
		SyntaxNode parent = Designator.getParent();
		if (DesignAssign.class != parent.getClass() && ForDesignAssign.class != parent.getClass()
				&& MethodCalled.class != parent.getClass()
				&& DesignInc.class != parent.getClass() && DesignDec.class != parent.getClass()
				&& ForDesignInc.class != parent.getClass() && ForDesignDec.class != parent.getClass()
				&& ReadStmt.class != parent.getClass()) {
			Code.load(Designator.obj);
		}
	}
	
	@Override
	public void visit(ArrayName arrName) {
		Code.load(arrName.obj);
	}
//===================================================================================================================================	
//Function calls	
	@Override
	public void visit(FuncCall FuncCall) {
		Obj functionObj = FuncCall.getMethodCalled().getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc; 
		Code.put(Code.call);
		Code.put2(offset);
	}
	
	@Override
	public void visit(DesignCall FuncCall) {
		Obj functionObj = FuncCall.getMethodCalled().getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc; 
		Code.put(Code.call);
		Code.put2(offset);
		//Ako je pozvana funkcija imala povratnu vrednost treba je skinuti sa steka
		if(FuncCall.getMethodCalled().getDesignator().obj.getType() != Tab.noType) {
			Code.put(Code.pop);
		}
	}
	
	@Override
	public void visit(ForDesignCall FuncCall) {
		Obj functionObj = FuncCall.getMethodCalled().getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc; 
		Code.put(Code.call);
		Code.put2(offset);
		//Ako je pozvana funkcija imala povratnu vrednost treba je skinuti sa steka
		if(FuncCall.getMethodCalled().getDesignator().obj.getType() != Tab.noType) {
			Code.put(Code.pop);
		}
	}
	
//===============================================================================================================================
//Print, Read	
	
	@Override
	public void visit(PrintStmt PrintStmt) {
		Struct pType = PrintStmt.getExpr().obj.getType();
		if(pType == Tab.intType || pType == TabExtended.boolType) {
			if(printWidth > 0) {
				Code.loadConst(printWidth);
			}
			else {
				Code.loadConst(5);
			}
			Code.put(Code.print);
		}
		else { 	//charType
			if(printWidth > 0) {
				Code.loadConst(printWidth);
			}
			else {
				Code.loadConst(5);
			}
			Code.put(Code.bprint);
		}
		printWidth = -1;
	}
	
	@Override
	public void visit(PrintParam printW) {
		printWidth = printW.getN1();
	}
	
	@Override
	public void visit(NoPrintParam printW) {
		printWidth = -1;
	}
	
	@Override
	public void visit(ReadStmt readStmt) {
		Struct desType = readStmt.getDesignator().obj.getType();
		if(desType == Tab.intType || desType == TabExtended.boolType) {
			Code.put(Code.read);
		}
		else { 	//charType
			Code.put(Code.bread);
		}
		Code.store(readStmt.getDesignator().obj);
	}
//===============================================================================================================================
//Expression obrada
	
	@Override
	public void visit(NegativeExpr negExpr) {
		Code.put(Code.neg);
	}
	
	@Override
	public void visit(Terms AddExpr) {
		Code.put(addOpCodes.pop());
	}
	
	@Override
	public void visit(AddopPlus plus) {
		addOpCodes.push(Code.add);
	}
	
	@Override
	public void visit(AddopMinus minus) {
		addOpCodes.push(Code.sub);
	}
	
	@Override
	public void visit(Factors MulExpr) {
		Code.put(mulOpCodes.pop());
	}
	
	@Override
	public void visit(MulopMultiplication multi) {
		mulOpCodes.push(Code.mul);
	}
	
	@Override
	public void visit(MulopDivision div) {
		mulOpCodes.push(Code.div);
	}
	
	@Override
	public void visit(MulopMod mod) {
		mulOpCodes.push(Code.rem);
	}
//===============================================================================================================================
//Condition obrada
	
	@Override
	public void visit(RelopEqual relopEq) {
		relOpCode = Code.eq;
	}
	
	@Override
	public void visit(RelopNotEqual relopNotEq) {
		relOpCode = Code.ne;
	}
	
	@Override
	public void visit(RelopGreater relopGt) {
		relOpCode = Code.gt;
	}
	
	@Override
	public void visit(RelopGreaterEq relopGe) {
		relOpCode = Code.ge;
	}
	
	@Override
	public void visit(RelopLess relopLt) {
		relOpCode = Code.lt;
	}
	
	@Override
	public void visit(RelopLessEq relopLe) {
		relOpCode = Code.le;
	}
	
	@Override
	public void visit(UnaryCondFact condFact) {
		//It is a boolean variable which was paired with Designator production and it was loaded in that moment
		//Therefore, no further action is required
	}
	
	@Override
	public void visit(BinaryCondFact condFact) {
		Code.putFalseJump(relOpCode, Code.pc + 7);
		//If the jump was not executed => the condition is true (1) and we put it on the expr stack
		Code.loadConst(1);
		//The result is already on the stack, we ned to skip the load of the opposite result
		Code.putJump(Code.pc + 4);
		//If the jump was executed we end up here => the condition is false (0) and we put it on the expr stack
		Code.loadConst(0);
	}
	
	@Override
	public void visit(CondTerms condTerm) {
		//Check CondFact == 1
		Code.loadConst(1);
		Code.putFalseJump(Code.eq, Code.pc + 11);
		//CondFact is true, check CondTerm == 1
		Code.loadConst(1);
		Code.putFalseJump(Code.eq, Code.pc + 7);
		//CondFact is true and CondTerm is true = Condition is true (1), load the result on the ExprStack
		Code.loadConst(1);
		//The result is on the stack => skip the rest and return
		Code.putJump(Code.pc + 4);
		//The condition is false (0), load the result on the ExprStack
		Code.loadConst(0);
	}
	
	@Override
	public void visit(Conditions cond) {
		//Check CondTerm != 1 (CondTerm == 0)
		Code.loadConst(1);
		Code.putFalseJump(Code.ne, Code.pc + 11);
		//CondTerm == 0, check Condition == 0
		Code.loadConst(1);
		Code.putFalseJump(Code.ne, Code.pc + 7);
		//CondTerm is false and Condition is false = The whole Condition is false (0), load the result on the ExprStack
		Code.loadConst(0);
		//The result is on the stack => skip the rest and return
		Code.putJump(Code.pc + 4);
		//The condition is true (1), load the result on the ExprStack
		Code.loadConst(1);
	}
//===============================================================================================================================
//If Statement
	
	@Override
	public void visit(IfCond cond) {
		//Check the condition and set the jump
		Code.loadConst(1);
		Code.putFalseJump(Code.eq, 0);
		//Remember the adr where to patch later
		ifStartAdr.push(Code.pc - 2);
	}
	
	@Override
	public void visit(MatchedThen then) {
		//We have reached the end of the then branch, else branch should be skipped
		Code.putJump(0);
		ifElseAdr.push(Code.pc-2);
		//Patch the jump address for when the if condition is false and we should go to the else branch
		Code.fixup(ifStartAdr.pop());
	}
	
	@Override
	public void visit(UnmatchedThen then) {
		//Patch the jump address for when the if condition is false and we should skip the then branch
		Code.fixup(ifStartAdr.pop());
	}
	
	@Override
	public void visit(UnmatchedIfElse ifElse) {
		//Patch the jump address for when we have done the then branch and should skip to the end of if statement
		Code.fixup(ifElseAdr.pop());
	}
	
	@Override
	public void visit(MatchedIfElse ifElse) {
		//Patch the jump address for when we have done the then branch and should skip to the end of if statement
		Code.fixup(ifElseAdr.pop());
	}
//===============================================================================================================================
//For Statement
	
	@Override
	public void visit(ForDesStmt1 forDesStmt1) {
		//Remember the address where the condition check begins, after each iteration we should jump there
		forCondAdr = Code.pc;
	}
	
	@Override
	public void visit(NoForCondition cond) {
		//If there is no condition we should enter the for loop => condition is true (1)
		Code.loadConst(1);
	}
	
	@Override
	public void visit(ForCond forCond) {
		//Check the condition and jump to the first statement of for loop if it is true (skip the ForDesStmt2)
		Code.loadConst(0);
		Code.putFalseJump(Code.eq, 0);
		forFirsStatementPatchAdr = Code.pc - 2;
		//The condition is false, we should jump to the end of for loop
		breakAdr.push(Code.pc);
		Code.putJump(0);
		endForPatchAdr.push(Code.pc - 2);
		//Remember the address of ForDesStmt2, in the end of each iteration we should jump there
		forDesStmtm2Adr.push(Code.pc);
	}
	
	@Override
	public void visit(ForDesStmt2 forDesStmt2) {
		//After executing this statement we should return to check the for condition for the next iteration
		Code.putJump(forCondAdr);
		//Now the pc containes the address of the first statement in for loop, we should patch it
		Code.fixup(forFirsStatementPatchAdr);
	}
	
	@Override
	public void visit(UnmatchedForStmt forStmt) {
		//We have reached the last statement in for loop and should now execute ForDesStmt2
		Code.putJump(forDesStmtm2Adr.pop());
		//Patch the end of for loop
		Code.fixup(endForPatchAdr.pop());
		//Delete the breakAdr for this for loop from the stack
		breakAdr.pop();
	}
	
	@Override
	public void visit(MatchedForStmt forStmt) {
		//We have reached the last statement in for loop and should now execute ForDesStmt2
		Code.putJump(forDesStmtm2Adr.pop());
		//Patch the end of for loop
		Code.fixup(endForPatchAdr.pop());
	}
	
	@Override
	public void visit(BreakStmt breakStmt) {
		//Exit the loop
		Code.putJump(breakAdr.peek());
	}
	
	@Override
	public void visit(ContinueStmt continueStmt) {
		//Jump to the ForDesStmt2 (proceed to the next iteration)
		Code.putJump(forDesStmtm2Adr.peek());
	}
	
}


