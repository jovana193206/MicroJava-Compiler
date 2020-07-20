package rs.ac.bg.etf.pp1;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.ActualParam;
import rs.ac.bg.etf.pp1.ast.ActualParams;
import rs.ac.bg.etf.pp1.ast.ArrayDesignator;
import rs.ac.bg.etf.pp1.ast.ArrayName;
import rs.ac.bg.etf.pp1.ast.ArrayVarIdent;
import rs.ac.bg.etf.pp1.ast.BinaryCondFact;
import rs.ac.bg.etf.pp1.ast.BoolConst;
import rs.ac.bg.etf.pp1.ast.BreakStmt;
import rs.ac.bg.etf.pp1.ast.CharConst;
import rs.ac.bg.etf.pp1.ast.CondTerms;
import rs.ac.bg.etf.pp1.ast.Conditions;
import rs.ac.bg.etf.pp1.ast.Const;
import rs.ac.bg.etf.pp1.ast.ConstDeclaration;
import rs.ac.bg.etf.pp1.ast.ConstInit;
import rs.ac.bg.etf.pp1.ast.ConstType;
import rs.ac.bg.etf.pp1.ast.ContinueStmt;
import rs.ac.bg.etf.pp1.ast.DesignAssign;
import rs.ac.bg.etf.pp1.ast.DesignAssignErrBeg;
import rs.ac.bg.etf.pp1.ast.DesignCall;
import rs.ac.bg.etf.pp1.ast.DesignDec;
import rs.ac.bg.etf.pp1.ast.DesignFact;
import rs.ac.bg.etf.pp1.ast.DesignInc;
import rs.ac.bg.etf.pp1.ast.Designator;
import rs.ac.bg.etf.pp1.ast.DesignatorAssign;
import rs.ac.bg.etf.pp1.ast.DesignatorStmt;
import rs.ac.bg.etf.pp1.ast.DesignatorStmtErr;
import rs.ac.bg.etf.pp1.ast.EnumDecl;
import rs.ac.bg.etf.pp1.ast.EnumName;
import rs.ac.bg.etf.pp1.ast.Factors;
import rs.ac.bg.etf.pp1.ast.For;
import rs.ac.bg.etf.pp1.ast.ForCond;
import rs.ac.bg.etf.pp1.ast.ForDesStatement;
import rs.ac.bg.etf.pp1.ast.ForDesignAssign;
import rs.ac.bg.etf.pp1.ast.ForDesignCall;
import rs.ac.bg.etf.pp1.ast.ForDesignDec;
import rs.ac.bg.etf.pp1.ast.ForDesignInc;
import rs.ac.bg.etf.pp1.ast.FormParams;
import rs.ac.bg.etf.pp1.ast.FormPars;
import rs.ac.bg.etf.pp1.ast.FuncCall;
import rs.ac.bg.etf.pp1.ast.GlobalVar;
import rs.ac.bg.etf.pp1.ast.IfCond;
import rs.ac.bg.etf.pp1.ast.InitElem;
import rs.ac.bg.etf.pp1.ast.InitEnumElem;
import rs.ac.bg.etf.pp1.ast.InitList;
import rs.ac.bg.etf.pp1.ast.InitListStart;
import rs.ac.bg.etf.pp1.ast.InitLst;
import rs.ac.bg.etf.pp1.ast.MatchedForStmt;
import rs.ac.bg.etf.pp1.ast.MatchedIfElse;
import rs.ac.bg.etf.pp1.ast.MethodCalled;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MethodReturnTypeT;
import rs.ac.bg.etf.pp1.ast.MethodReturnVoid;
import rs.ac.bg.etf.pp1.ast.MethodTypeName;
import rs.ac.bg.etf.pp1.ast.NegativeExpr;
import rs.ac.bg.etf.pp1.ast.NewArray;
import rs.ac.bg.etf.pp1.ast.NewFact;
import rs.ac.bg.etf.pp1.ast.NewFactArray;
import rs.ac.bg.etf.pp1.ast.NoForCondition;
import rs.ac.bg.etf.pp1.ast.NoFormParam;
import rs.ac.bg.etf.pp1.ast.NoInitEnumElem;
import rs.ac.bg.etf.pp1.ast.NonArrayDesignator;
import rs.ac.bg.etf.pp1.ast.NonArrayVarIdent;
import rs.ac.bg.etf.pp1.ast.NumConst;
import rs.ac.bg.etf.pp1.ast.ParenFact;
import rs.ac.bg.etf.pp1.ast.PositiveExpr;
import rs.ac.bg.etf.pp1.ast.PrintStmt;
import rs.ac.bg.etf.pp1.ast.ProgName;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.ast.ReadStmt;
import rs.ac.bg.etf.pp1.ast.RelopEqual;
import rs.ac.bg.etf.pp1.ast.RelopNotEqual;
import rs.ac.bg.etf.pp1.ast.ReturnExpr;
import rs.ac.bg.etf.pp1.ast.ReturnNoExpr;
import rs.ac.bg.etf.pp1.ast.SingleCondTerm;
import rs.ac.bg.etf.pp1.ast.SingleCondition;
import rs.ac.bg.etf.pp1.ast.SingleFactor;
import rs.ac.bg.etf.pp1.ast.SingleTerm;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.Terms;
import rs.ac.bg.etf.pp1.ast.Type;
import rs.ac.bg.etf.pp1.ast.UnaryCondFact;
import rs.ac.bg.etf.pp1.ast.UnmatchedForStmt;
import rs.ac.bg.etf.pp1.ast.UnmatchedIf;
import rs.ac.bg.etf.pp1.ast.UnmatchedIfElse;
import rs.ac.bg.etf.pp1.ast.VarDecl;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticAnalyzer extends VisitorAdaptor {

	boolean errorDetected = false;
	Obj currentMethod = null;
	boolean isFormParam = false;
	int formParsIndex = 0;
	boolean returnFound = false;
	int nVars;
	boolean programFound = false;
	Struct currentType = Tab.noType;	//current var declaration type (poslednji procitan Tip)
	boolean forActive = false;
	Struct currentEnum = null;
	Obj lastEnumConst = null;
	Obj lastDesignator = null;
	Stack<MethodCall> funcCalls = new Stack<MethodCall>();
	int initListSize = 0, newArraySize = 0;
	
	int numOfClasses = 0, numOfMethods = 0, numOfGlobalVars = 0;
	int numOfGlobalConsts = 0, numOfGlobalArrays = 0, numOfLocalVarsMain = 0;
	int numOfStmtsMain = 0, numOfFuncCallsMain = 0;
	
	
	/*Obj currentClass = null;
	boolean classDefinitionOpen = false;
	*/
	
	

	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	//=========================================================================================================================
	//Obrada Programa
	public void visit(Program program) {		
		nVars = Tab.currentScope.getnVars();
		numOfGlobalVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgName().obj);
		Obj mainMeth = Tab.find("main");
		if(mainMeth == Tab.noObj) {
			report_error("Greska na " + program.getLine() + ": program mora sadrzati void main() metodu", null);
			errorDetected = true;
		}
		Tab.closeScope();
	}

	public void visit(ProgName progName) {
		if(programFound) {
			report_error("Greska na " + progName.getLine() + ": kljucna rec program pronadjena vise puta", null);
			errorDetected = true;
		}
		progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		Tab.openScope();
		//Predeclared function len
	}
	
	
	//=========================================================================================================================
	//Obrada deklaracija globalnih i lokalnih promenljivih
	public void visit(GlobalVar globalVar) {
		currentType = Tab.noType;
	}
	
	public void visit (VarDecl varDecl) {
		currentType = Tab.noType;
	}
	
	public void visit(NonArrayVarIdent nonArrayVar) {
		/*if(classDefinitionOpen) {
			report_info("Deklarisano polje  "+ nonArrayVar.getVarName() + " klase " + currentClass.getName(), nonArrayVar);
			Obj fieldNode = Tab.insert(Obj.Fld, nonArrayVar.getVarName(), currentType);
			return;
		}*/
		if(Tab.currentScope.findSymbol(nonArrayVar.getVarName()) != null) {
			report_error("Greska na " + nonArrayVar.getLine() + ": " + nonArrayVar.getVarName() + " vec deklarisano", null);
			errorDetected = true;
			return;
		}
		//report_info("Deklarisana promenljiva "+ nonArrayVar.getVarName(), nonArrayVar);
		Obj varNode = Tab.insert(Obj.Var, nonArrayVar.getVarName(), currentType);
		//Ako je ovaj nonArrayVar zapravo formalni argument funkcije treba mu dodeliti redni broj
		if(currentMethod != null && isFormParam) {
			varNode.setFpPos(++formParsIndex);
			currentMethod.setLevel(currentMethod.getLevel() + 1);
		}
	}
	
	public void visit(ArrayVarIdent arrayVar) {
		/*if(classDefinitionOpen) {
			report_info("Deklarisano nizovno polje "+ arrayVar.getArrayName() + " klase " + currentClass.getName(), arrayVar);
			Obj fieldNode = Tab.insert(Obj.Fld, arrayVar.getArrayName(), currentType);
			return;
		}*/
		if(Tab.currentScope.findSymbol(arrayVar.getArrayName()) != null) {
			report_error("Greska na " + arrayVar.getLine() + ": " + arrayVar.getArrayName() + " vec deklarisano", null);
			errorDetected = true;
			return;
		}
		//report_info("Deklarisana nizovna promenljiva "+ arrayVar.getArrayName(), arrayVar);
		Obj varNode = Tab.insert(Obj.Var, arrayVar.getArrayName(), new Struct(Struct.Array, currentType));
		//Ako je ovaj arrayVar zapravo formalni argument funkcije treba mu dodeliti redni broj
		if(currentMethod != null && isFormParam) {
			varNode.setFpPos(++formParsIndex);
			currentMethod.setLevel(currentMethod.getLevel() + 1);
		}
		if(varNode.getLevel() == 0) { 	//Ako je globalni niz
			numOfGlobalArrays++;
		}
	}
	
	//==========================================================================================================================
	//Obrada deklaracija konstanti
	public void visit(ConstDeclaration constDecl) {
		currentType = Tab.noType;
	}
	
	public void visit(ConstType constType) {
		Struct type = constType.getType().struct;
		if((type != Tab.intType) && (type != Tab.charType) && (type != TabExtended.boolType)) {
			report_error("Greska na " + constType.getLine() + "(" + constType.getType().getTypeName()
							+ "): nepodrzan tip konstante", null);
			errorDetected = true;
			currentType = Tab.noType;
		}
		else {
			currentType = type;
		}
	}
	
	public void visit(ConstInit constInit) {
		if(currentType != Tab.noType) {
			if(Tab.currentScope.findSymbol(constInit.getConstName()) != null) {
				report_error("Greska na " + constInit.getLine() + ": " + constInit.getConstName() + " vec deklarisano", null);
				errorDetected = true;
				return;
			}
			if(!currentType.equals(constInit.getConstant().obj.getType())) {
				report_error("Greska na " + constInit.getLine() + "(" + constInit.getConstName() + ")"
						+ ": nekompatibilni tipovi", null);
				errorDetected = true;
				return;
			}
			//report_info("Deklarisana konstanta "+ constInit.getConstName(), constInit);
			int initVal = constInit.getConstant().obj.getAdr();
			Obj newConst = Tab.insert(Obj.Con, constInit.getConstName(), constInit.getConstant().obj.getType());
			newConst.setAdr(initVal);
			if(newConst.getLevel() == 0) { 	//Ako je globalna konstanta
				numOfGlobalConsts++;
			}
		}
	}
	
	public void visit(NumConst constant) {
		constant.obj = new Obj(Obj.Con, "initVal", TabExtended.intType);
		constant.obj.setAdr(constant.getVal());
	}
	
	public void visit(CharConst constant) {
		constant.obj = new Obj(Obj.Con, "initVal", TabExtended.charType);
		constant.obj.setAdr(constant.getVal());
	}
	
	public void visit(BoolConst constant) {
		constant.obj = new Obj(Obj.Con, "initVal", TabExtended.boolType);
		int val = constant.getVal();
		if(val != 0) val = 1;
		constant.obj.setAdr(val);
	}
	
	//=========================================================================================================================
	//Obrada Tipa
	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());
		if (typeNode == Tab.noObj) {
			report_error("Greska na " + type.getLine() + ": " + type.getTypeName() + " nije nadjeno", null);
			errorDetected = true;
			type.struct = Tab.noType;
		} 
		else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
			} 
			else {
				report_error("Greska na " + type.getLine() + ": " + type.getTypeName() + " ne predstavlja tip ", null);
				errorDetected = true;
				type.struct = Tab.noType;
			}
		}
		currentType = type.struct;
	}
	
	//========================================================================================================================
	//Obrada deklaracije enuma
	public void visit(EnumDecl enumDecl) {
		Tab.chainLocalSymbols(enumDecl.getEnumName().obj.getType());
		Tab.closeScope();
		currentEnum = null;
		lastEnumConst = null;
	}
	
	public void visit(EnumName enumName) {
		if(Tab.currentScope.findSymbol(enumName.getName()) != null) {
			report_error("Greska na " + enumName.getLine() + ": " + enumName.getName() + " vec deklarisano", null);
			errorDetected = true;
			return;
		}
		currentEnum = new Struct(Struct.Enum);
		enumName.obj = Tab.insert(Obj.Type, enumName.getName(), currentEnum);
		//report_info("Deklarisano nabrajanje "+ enumName.getName(), enumName);
		Tab.openScope();
		lastEnumConst = null;
	}
	
	public void visit(InitEnumElem elem) {
		Collection<Obj> elemCol = Tab.currentScope.values();
		Set<Obj> elems = new HashSet<Obj> (elemCol);
		for(Obj obj : elems) {
			if(obj.getName().equals(elem.getEnumElemName())) {
				report_error("Greska na " + elem.getLine() + "(" + elem.getEnumElemName() + "): "
						+ elem.getEnumElemName() + " vec postoji u enum-u", null);
				errorDetected = true;
				return;
			}
			if(obj.getAdr() == elem.getInitVal()) {
				report_error("Greska na " + elem.getLine() + "(" + elem.getEnumElemName() + "): "
						+ elem.getInitVal() + "vec postoji u enum-u", null);
				errorDetected = true;
				return;
			}
		}
		lastEnumConst = Tab.insert(Obj.Con, elem.getEnumElemName(), currentEnum);
		lastEnumConst.setAdr(elem.getInitVal());
	}
	
	public void visit(NoInitEnumElem elem) {
		if(Tab.currentScope.findSymbol(elem.getEnumElemName()) != null) {
			report_error("Greska na " + elem.getLine() + "(" + elem.getEnumElemName() + "): "
					+ elem.getEnumElemName() + " vec postoji u enum-u", null);
			errorDetected = true;
			return;
		}
		int initVal = (lastEnumConst == null) ? 0 : (lastEnumConst.getAdr() + 1);
		lastEnumConst = Tab.insert(Obj.Con, elem.getEnumElemName(), currentEnum);
		lastEnumConst.setAdr(initVal);
	}
	
	//========================================================================================================================
	//Obrada deklaracije funkcija
	public void visit(MethodDecl methodDecl) {
		if (!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("Greska na " + methodDecl.getLine() + ": funkcija " + currentMethod.getName() + " nema return iskaz!", null);
			errorDetected = true;
		}
		
		if(currentMethod.getName().equals("main")) {
			numOfLocalVarsMain = Tab.currentScope.getnVars();
		}
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		//currentMethod.setLevel(formParsIndex);
		returnFound = false;
		currentMethod = null;
		formParsIndex = 0;
		numOfMethods++;
	}

	public void visit(MethodTypeName methodTypeName) {
		if(Tab.currentScope.findSymbol(methodTypeName.getMethName()) != null) {
			report_error("Greska na " + methodTypeName.getLine() + ": " + methodTypeName.getMethName() + " vec postoji", null);
			errorDetected = true;
			return;
		}
		currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethName(), methodTypeName.getMethodReturnType().struct);
		methodTypeName.obj = currentMethod;
		isFormParam = true;
		Tab.openScope();
		/*if(classDefinitionOpen == true) {
			Obj thisRef = Tab.insert(Obj.Var, "this", currentClass.getType());
		}*/
		//report_info("Obradjuje se funkcija " + methodTypeName.getMethName(), methodTypeName);
	}
	
	public void visit(FormParams formPars) {
		isFormParam = false;
	}
	
	public void visit(NoFormParam formPars) {
		isFormParam = false;
	}
	
	public void visit(MethodReturnTypeT mrt) {
		mrt.struct = mrt.getType().struct;
	}
	
	public void visit(MethodReturnVoid mrv) {
		mrv.struct = Tab.noType;
	}
	
	public void visit(ReturnExpr returnExpr){
		if(currentMethod == null) {
			report_error("Greska na " + returnExpr.getLine() + ": return pronadjen van tela funkcije ", null);
			errorDetected = true;
			if(currentMethod != null && currentMethod.getName().equals("main")) {
				numOfStmtsMain++;
			}
			return;
		}
		returnFound = true;
		Struct currMethType = currentMethod.getType();
		if (!currMethType.compatibleWith(returnExpr.getExpr().obj.getType())) {
			report_error("Greska na " + returnExpr.getLine() + " : " + "tip izraza u return ne slaze se sa tipom povratne vrednosti funkcije " + currentMethod.getName(), null);
			errorDetected = true;
		}
		if(currentMethod != null && currentMethod.getName().equals("main")) {
			numOfStmtsMain++;
		}
	}
	
	public void visit(ReturnNoExpr returnNoExpr) {
		if(currentMethod == null) {
			report_error("Greska na " + returnNoExpr.getLine() + ": return pronadjen van tela funkcije ", null);
			errorDetected = true;
			if(currentMethod != null && currentMethod.getName().equals("main")) {
				numOfStmtsMain++;
			}
			return;
		}
		if(currentMethod.getType() != Tab.noType) {
			report_error("Greska na " + returnNoExpr.getLine() + " : " + "funkcija " + currentMethod.getName() + " treba da ima povratnu vrednost", null);
			errorDetected = true;
		}
		if(currentMethod != null && currentMethod.getName().equals("main")) {
			numOfStmtsMain++;
		}
	}
	
	
	//=========================================================================================================================
	// Statements: print, break, continue, read

	public void visit(PrintStmt printStmt){
		Struct exprType = printStmt.getExpr().obj.getType();
		if((exprType != Tab.intType) && (exprType != Tab.charType) && (exprType != TabExtended.boolType))  {
			report_error("Greska na " + printStmt.getLine() + ": izraz unutar print funkcije mora biti tipa int, char ili bool", null);
			errorDetected = true;
		}
		if(currentMethod != null && currentMethod.getName().equals("main")) {
			numOfStmtsMain++;
		}
	}
	
	public void visit(MatchedForStmt matchedFor) {
		forActive = false;
		if((matchedFor.getForCondition() instanceof ForCond) && (matchedFor.getForCondition().struct != TabExtended.boolType)) {
			report_error("Greska na " + matchedFor.getLine() + ": uslov mora biti tipa bool", null);
			errorDetected = true;
		}
		if(currentMethod != null && currentMethod.getName().equals("main")) {
			numOfStmtsMain++;
		}
	}
	
	public void visit(UnmatchedForStmt unmatchedFor) {
		forActive = false;
		if((unmatchedFor.getForCondition() instanceof ForCond) && (unmatchedFor.getForCondition().struct != TabExtended.boolType)) {
			report_error("Greska na " + unmatchedFor.getLine() + ": uslov mora biti tipa bool", null);
			errorDetected = true;
		}
		if(currentMethod != null && currentMethod.getName().equals("main")) {
			numOfStmtsMain++;
		}
	}
	
	public void visit(For forBeg) {
		forActive = true;
	}
	
	public void visit(BreakStmt breakStmt) {
		if(!forActive) {
			report_error("Greska na " + breakStmt.getLine() + ": break se ne sme naci van for petlje", null);
			errorDetected = true;
		}
		if(currentMethod != null && currentMethod.getName().equals("main")) {
			numOfStmtsMain++;
		}
	}
	
	public void visit(ContinueStmt continueStmt) {
		if(!forActive) {
			report_error("Greska na " + continueStmt.getLine() + ": continue se ne sme naci van for petlje", null);
			errorDetected = true;
		}
		if(currentMethod != null && currentMethod.getName().equals("main")) {
			numOfStmtsMain++;
		}
	}

	public void visit(ReadStmt readStmt) {
		Struct desType = readStmt.getDesignator().obj.getType();
		int desObjKind = readStmt.getDesignator().obj.getKind();
		if(desObjKind != Obj.Var && desObjKind != Obj.Fld && desObjKind != Obj.Elem) {
			report_error("Greska na " + readStmt.getLine() + ": argument funkcije read mora biti promenljiva ili element niza", null);
			errorDetected = true;
			if(currentMethod != null && currentMethod.getName().equals("main")) {
				numOfStmtsMain++;
			}
			return;
		}
		if((desType != TabExtended.boolType) && (desType != Tab.intType) && (desType != Tab.charType)) {
			report_error("Greska na " + readStmt.getLine() + ": argument funkcije read mora biti int, char ili bool tipa", null);
			errorDetected = true;
			if(currentMethod != null && currentMethod.getName().equals("main")) {
				numOfStmtsMain++;
			}
			return;
		}
		if(currentMethod != null && currentMethod.getName().equals("main")) {
			numOfStmtsMain++;
		}
	}
	
	public void visit(MatchedIfElse matchedIfElse) {
		if(matchedIfElse.getIfCondition().struct != TabExtended.boolType) {
			report_error("Greska na " + matchedIfElse.getLine() + ": uslov mora biti tipa bool", null);
			errorDetected = true;
		}
		if(currentMethod != null && currentMethod.getName().equals("main")) {
			numOfStmtsMain++;
		}
	}
	
	public void visit(UnmatchedIf unmatchedIf) {
		if(unmatchedIf.getIfCondition().struct != TabExtended.boolType) {
			report_error("Greska na " + unmatchedIf.getLine() + ": uslov mora biti tipa bool", null);
			errorDetected = true;
		}
		if(currentMethod != null && currentMethod.getName().equals("main")) {
			numOfStmtsMain++;
		}
	}
	
	public void visit(UnmatchedIfElse unmatchedIfElse) {
		if(unmatchedIfElse.getIfCondition().struct != TabExtended.boolType) {
			report_error("Greska na " + unmatchedIfElse.getLine() + ": uslov mora biti tipa bool", null);
			errorDetected = true;
		}
		if(currentMethod != null && currentMethod.getName().equals("main")) {
			numOfStmtsMain++;
		}
	}
	
	
	//=========================================================================================================================
	//Obrada poziva funkcija

	public void visit(FuncCall funcCall){
		funcCall.obj = new Obj(Obj.Con, "funcCalled", funcCall.getMethodCalled().struct);
		MethodCall calledMethod = funcCalls.pop();
	}
	
	public void visit(DesignCall designCall) {
		MethodCall calledMethod = funcCalls.pop();
	}
	
	public void visit(ForDesignCall designCall) {
		MethodCall calledMethod = funcCalls.pop();
	}
	
	public void visit(MethodCalled mCalled) {
		if(mCalled.getDesignator().obj.getKind() != Obj.Meth) {
			report_error("Greska na " + mCalled.getLine() + "(" + mCalled.getDesignator().obj.getName() + "):" 
						+ " nije funkcija", null);
			errorDetected = true;
			funcCalls.push(new MethodCall(Tab.noObj));
			mCalled.struct = Tab.noType;
			return;
		}
		//report_info("Obrada poziva funkcije " + mCalled.getDesignator().obj.getName(), mCalled);
		mCalled.struct = mCalled.getDesignator().obj.getType();
		funcCalls.push(new MethodCall(mCalled.getDesignator().obj));
		if(currentMethod != null && currentMethod.getName().equals("main")) { 	//Ako iz main-a pozivamo neku fju
			numOfFuncCallsMain++;
		}
	}
	
	public void visit(ActualParam actParam) {   //funkcije sa jednim parametrom
		MethodCall methCall = funcCalls.peek();
		if(methCall.calledMethod == null || methCall.calledMethod == Tab.noObj) {
			report_error("Greska na " + actParam.getLine() + ": stvarni argumenti moraju biti deo poziva funkcije", null);
			errorDetected = true;
			return;
		}
		if(methCall.calledMethod.getName().equals("chr")) {
			if(methCall.parsDetected != 0) {
				report_error("Greska na " + actParam.getLine() + ": metoda chr prima tacno 1 argument", null);
				errorDetected = true;
				return;
			}
			if(actParam.getExpr().obj.getType() != Tab.intType) {
				report_error("Greska na " + actParam.getLine() + ": argument funkcije chr mora biti tipa int", null);
				errorDetected = true;
			}
			return;
		}
		if(methCall.calledMethod.getName().equals("ord")) {
			if(methCall.parsDetected != 0) {
				report_error("Greska na " + actParam.getLine() + ": metoda ord prima tacno 1 argument", null);
				errorDetected = true;
				return;
			}
			if(actParam.getExpr().obj.getType() != Tab.charType) {
				report_error("Greska na " + actParam.getLine() + ": argument funkcije ord mora biti tipa char", null);
				errorDetected = true;
			}
			return;
		}
		if(methCall.calledMethod.getName().equals("len")) {
			if(methCall.parsDetected != 0) {
				report_error("Greska na " + actParam.getLine() + ": metoda len prima tacno 1 argument", null);
				errorDetected = true;
				return;
			}
			if(actParam.getExpr().obj.getType().getKind() != Struct.Array) {
				report_error("Greska na " + actParam.getLine() + ": argument funkcije len mora biti niz", null);
				errorDetected = true;
			}
			return;
		}
		
		if(methCall.parsDetected == methCall.parsExpected) { 
			report_error("Greska na " + actParam.getLine() + ": broj formalnih i stvarnih argumenata se ne poklapa", null);
			errorDetected = true;
			return;
		}
		Struct actType = actParam.getExpr().obj.getType();
		Struct formType = methCall.formTypes[methCall.parsDetected++];
		if(!(actType.assignableTo(formType) || (actType.getKind() == Struct.Enum && formType == Tab.intType) )) {
			report_error("Greska na " + actParam.getLine() + ": formalni i stvarni argument na poziciji " + 
							(methCall.parsDetected - 1) + " nisu kompatibilni", null);
			errorDetected = true;
			return;
		}
	}
	
	public void visit(ActualParams actParam) {
		MethodCall methCall = funcCalls.peek();
		if(methCall.calledMethod == null || methCall.calledMethod == Tab.noObj) {
			report_error("Greska na " + actParam.getLine() + " stvarni argumenti moraju biti deo poziva funkcije", null);
			errorDetected = true;
			return;
		}
		if(methCall.parsDetected == methCall.parsExpected) { 
			report_error("Greska na " + actParam.getLine() + ": broj formalnih i stvarnih argumenata se ne poklapa", null);
			errorDetected = true;
			return;
		}
		Struct actType = actParam.getExpr().obj.getType();
		Struct formType = methCall.formTypes[methCall.parsDetected++];
		if(!(actType.assignableTo(formType) || (actType.getKind() == Struct.Enum && formType == Tab.intType) )) {
			report_error("Greska na " + actParam.getLine() + ": formalni i stvarni argument na poziciji " 
							+ (methCall.parsDetected - 1) + " nisu kompatibilni", null);
			errorDetected = true;
			return;
		}
	}
	//=========================================================================================================================
	//Obrada DesignatorStatements-a
	
	public void visit(DesignInc designInc) {
		Obj designator = designInc.getDesignator().obj;
		if(designator != null) {
			if(!(designator.getKind() == Obj.Var || designator.getKind() == Obj.Elem || designator.getKind() == Obj.Fld)) {
				report_error("Greska na " + designInc.getLine() + ": " + designator.getName() + 
						" mora biti promenljiva, element niza ili polje klase", null);
				errorDetected = true;
				return;
			}
			if(!(designator.getType() == Tab.intType)) {
				report_error("Greska na " + designInc.getLine() + ": " + designator.getName() + " mora biti tipa int", null);
				errorDetected = true;
			}
		}
	}
	
	public void visit(ForDesignInc designInc) {
		Obj designator = designInc.getDesignator().obj;
		if(designator != null) {
			if(!(designator.getKind() == Obj.Var || designator.getKind() == Obj.Elem || designator.getKind() == Obj.Fld)) {
				report_error("Greska na " + designInc.getLine() + ": " + designator.getName() + 
						" mora biti promenljiva, element niza ili polje klase", null);
				errorDetected = true;
				return;
			}
			if(!(designator.getType() == Tab.intType)) {
				report_error("Greska na " + designInc.getLine() + ": " + designator.getName() + " mora biti tipa int", null);
				errorDetected = true;
			}
		}
	}
	
	public void visit(DesignDec designDec) {
		Obj designator = designDec.getDesignator().obj;
		if(designator != null) {
			if(!(designator.getKind() == Obj.Var || designator.getKind() == Obj.Elem || designator.getKind() == Obj.Fld)) {
				report_error("Greska na " + designDec.getLine() + ": " + designator.getName() + 
						" mora biti promenljiva, element niza ili polje klase", null);
				errorDetected = true;
				return;
			}
			if(!(designator.getType() == Tab.intType)) {
				report_error("Greska na " + designDec.getLine() + ": " + designator.getName() + " mora biti tipa int", null);
				errorDetected = true;
			}
		}
	}
	
	public void visit(ForDesignDec designDec) {
		Obj designator = designDec.getDesignator().obj;
		if(designator != null) {
			if(!(designator.getKind() == Obj.Var || designator.getKind() == Obj.Elem || designator.getKind() == Obj.Fld)) {
				report_error("Greska na " + designDec.getLine() + ": " + designator.getName() + 
						" mora biti promenljiva, element niza ili polje klase", null);
				errorDetected = true;
				return;
			}
			if(!(designator.getType() == Tab.intType)) {
				report_error("Greska na " + designDec.getLine() + ": " + designator.getName() + " mora biti tipa int", null);
				errorDetected = true;
			}
		}
	}
	
	public void visit(DesignAssign designAssign) {
		Obj designator = designAssign.getDesignator().obj;
		if(designator != null) {
			if(!(designator.getKind() == Obj.Var || designator.getKind() == Obj.Elem || designator.getKind() == Obj.Fld)) {
				report_error("Greska na " + designAssign.getLine() + ": " + designator.getName() + 
							" mora biti promenljiva, element niza ili polje klase", null);
				errorDetected = true;
				return;
			}
			Struct assignExprType = designAssign.getDesignatorAssignment().struct;
			Struct dstType = designAssign.getDesignator().obj.getType();
			if(!(assignExprType.assignableTo(dstType) || (assignExprType.getKind() == Struct.Enum && dstType == Tab.intType) )) {
				report_error("Greska na " + designAssign.getLine() + ": nekompatibilni tipovi u dodeli vrednosti", null);
				errorDetected = true;
			}
		}
	}
	
	public void visit(DesignatorAssign designAssign) {
		designAssign.struct = designAssign.getExpr().obj.getType();
	}
	
	public void visit(DesignAssignErrBeg designAssignErr) {
		designAssignErr.struct = Tab.noType;
	}
	
	public void visit(ForDesignAssign designAssign) {
		Obj designator = designAssign.getDesignator().obj;
		if(designator != null) {
			if(!(designator.getKind() == Obj.Var || designator.getKind() == Obj.Elem || designator.getKind() == Obj.Fld)) {
				report_error("Greska na " + designAssign.getLine() + ": " + designator.getName() + 
							" mora biti promenljiva, element niza ili polje klase", null);
				errorDetected = true;
				return;
			}
			Struct assignExprType = designAssign.getExpr().obj.getType();
			Struct dstType = designAssign.getDesignator().obj.getType();
			if(!(assignExprType.assignableTo(dstType) || (assignExprType.getKind() == Struct.Enum && dstType == Tab.intType) )) {
				report_error("Greska na " + designAssign.getLine() + ": nekompatibilni tipovi u dodeli vrednosti", null);
				errorDetected = true;
			}
		}
	}
	
	public void visit(DesignatorStmt desStmt) {
		if(currentMethod != null && currentMethod.getName().equals("main")) {
			numOfStmtsMain++;
		}
	}
	
	public void visit(DesignatorStmtErr desStmt) {
		if(currentMethod != null && currentMethod.getName().equals("main")) {
			numOfStmtsMain++;
		}
	}
	
	public void visit(ForDesStatement desStmt) {
		if(currentMethod != null && currentMethod.getName().equals("main")) {
			numOfStmtsMain++;
		}
	}
	//=========================================================================================================================
	//Obrada izraza
	
	public void visit(NewFactArray fact) {
		fact.obj = fact.getNewArray().obj;
	}
	
	public void visit(NewArray fact) {
		if(fact.getExpr().obj.getType() != Tab.intType && fact.getExpr().obj.getType().getKind() != Struct.Enum) {
			report_error("Greska na " + fact.getLine() + ": izraz u [] mora biti tipa int ", null);
			errorDetected = true;
			fact.obj = new Obj(Obj.Con, "newArray", Tab.noType);
			return;
		}
		fact.obj = new Obj(Obj.Con, "newArray", new Struct(Struct.Array, fact.getType().struct));
		newArraySize = fact.getExpr().obj.getAdr();
	}
	
	public void visit(InitLst initList) {
		if(initListSize != newArraySize) {
			report_error("Greska na " + initList.getLine() + ": broj elemenata u inicijalizatorskoj listi mora odgovarati duzini niza! ", null);
			errorDetected = true;
		}
		initListSize = 0;
	}
	
	public void visit(DesignFact fact) {
		/*Struct desType = fact.getDesignator().obj.getType();
		//Ako je u pitanju promenljiva tipa enum, tretiraj je u izrazu kao int
		if(desType.getKind() == Struct.Enum) desType = Tab.intType; 
		fact.struct = desType;*/
		if(fact.getDesignator().obj != null) {
			fact.obj = fact.getDesignator().obj;
		}
		else fact.obj = Tab.noObj;
	}
	
	public void visit(Const fact) {
		fact.obj = fact.getConstant().obj;
	}
	
	//A nivo nema klase i interfejse, ovo ne bi trebalo da se javi u stablu
	public void visit(NewFact fact) {
		if(fact.getType().struct.getKind() != Struct.Class) {
			report_error("Greska na " + fact.getLine() + ": posle new se mora naci tip klase ", null);
			errorDetected = true;
			fact.obj = Tab.noObj;
			return;
		}
		fact.obj = new Obj(Obj.Con, "newObject", fact.getType().struct);
	}
	
	public void visit(ParenFact fact) {
		fact.obj = fact.getExpr().obj;
	}
	
	public void visit(SingleFactor term) {
		term.obj = term.getFactor().obj;
	}
	
	public void visit(Factors term) {
		Struct fe = term.getTerm().obj.getType();
		Struct f = term.getFactor().obj.getType();
		if((fe.compatibleWith(f) && fe == Tab.intType && f == Tab.intType) ||
				(fe == Tab.intType && f.getKind() == Struct.Enum) ||
				(fe.getKind() == Struct.Enum && f== Tab.intType) ||
				(fe.getKind() == Struct.Enum && f.getKind() == Struct.Enum) ) {
			term.obj = new Obj(Obj.Con, "factors", fe);
			term.obj.setAdr(term.getTerm().obj.getAdr() * term.getFactor().obj.getAdr());
		}
		else {
			report_error("Greska na " + term.getLine() + ": nekompatibilni tipovi u izrazu za mnozenje", null);
			errorDetected = true;
			term.obj = Tab.noObj;
		}
	}
	
	public void visit(SingleTerm terms) {
		terms.obj = terms.getTerm().obj;
	}
	
	public void visit(Terms terms) {
		Struct te = terms.getTermList().obj.getType();
		Struct t = terms.getTerm().obj.getType();
		if((te.compatibleWith(t) && te == Tab.intType && t == Tab.intType) ||
				(te == Tab.intType && t.getKind() == Struct.Enum) ||
				(te.getKind() == Struct.Enum && t== Tab.intType) ||
				(te.getKind() == Struct.Enum && t.getKind() == Struct.Enum) ) {
			terms.obj = new Obj(Obj.Con, "factors", te);
			terms.obj.setAdr(terms.getTermList().obj.getAdr() + terms.getTerm().obj.getAdr());
		}
		else {
			report_error("Greska na " + terms.getLine() + ": nekompatibilni tipovi u izrazu za sabiranje", null);
			errorDetected = true;
			terms.obj = Tab.noObj;
		}
	}
	
	public void visit(InitElem initElem) {
		initElem.obj = initElem.getExpr().obj;
		Struct exprType = initElem.obj.getType();
		initListSize++;
		// + provera tipa izraza da li se slaze sa currentType
		if(!(currentType.compatibleWith(exprType) || (currentType == Tab.intType && exprType.getKind() == Struct.Enum))) {
			report_error("Greska na " + initElem.getLine() + ": tip elementa inicijalizatorske liste nije kompatibilan sa tipom elemenata niza", null);
			errorDetected = true;
		}
	}
	
	public void visit(PositiveExpr expr) {
		expr.obj = expr.getTermList().obj;
	}
	
	public void visit(NegativeExpr expr) {
		if(expr.getTermList().obj.getType() != Tab.intType && expr.getTermList().obj.getType().getKind() != Struct.Enum) {
			report_error("Greska na " + expr.getLine() + ": negativan izraz mora biti tipa int", null);
			errorDetected = true;
			expr.obj = Tab.noObj;
			return;
		}
		expr.obj = new Obj(Obj.Con, "expression", Tab.intType);
		expr.obj.setAdr(-expr.getTermList().obj.getAdr());
	}
	
	public void visit(BinaryCondFact condFact) {
		Struct left = condFact.getExpr().obj.getType();
		Struct right = condFact.getExpr1().obj.getType();
		if(!left.compatibleWith(right)) {
			report_error("Greska na " + condFact.getLine() + ": nekompatibilni tipovi u uslovnom izrazu", null);
			errorDetected = true;
			condFact.struct = Tab.noType;
			return;
		}
		if((left.isRefType() || right.isRefType()) && !((condFact.getRelop() instanceof RelopNotEqual) || (condFact.getRelop() instanceof RelopEqual))) {
			report_error("Greska na " + condFact.getLine() + ": nedozvoljeni operator za tipove u izrazu", null);
			errorDetected = true;
			condFact.struct = Tab.noType; 
			return;
		}
		condFact.struct = TabExtended.boolType;  //Rezultat Relop je ili true(1) ili false(0) => uvek je boolType
	}
	
	public void visit(UnaryCondFact condFact) {
		//It must be a boolean variable
		Obj var = condFact.getExpr().obj;
		if(var.getKind() != Obj.Var && var.getType() != TabExtended.boolType) {
			report_error("Greska na " + condFact.getLine() + ": unarni uslov mora biti bool promenljiva", null);
			errorDetected = true;
			condFact.struct = Tab.noType; 
			return;
		}
		condFact.struct = var.getType();
	}
	
	public void visit(SingleCondTerm condTerm) {
		condTerm.struct = condTerm.getCondFact().struct;
	}
	
	public void visit(CondTerms condTerm) {
		if(condTerm.getCondTerm().struct == Tab.noType || condTerm.getCondFact().struct == Tab.noType) {
			condTerm.struct = Tab.noType;
		}
		else condTerm.struct = TabExtended.boolType;   //Rezultat logickog I je ili true(1) ili false(0) => uvek je boolType
	}
	
	public void visit(SingleCondition cond) {
		cond.struct = cond.getCondTerm().struct;
	}
	
	public void visit(Conditions cond) {
		if(cond.getCondition().struct == Tab.noType || cond.getCondTerm().struct == Tab.noType) {
			cond.struct = Tab.noType;
		}
		else cond.struct = TabExtended.boolType;   //Rezultat logickog ILI je ili true(1) ili false(0) => uvek je boolType
	}
	
	public void visit(NoForCondition forCond) {
		forCond.struct = Tab.noType;
	}
	
	public void visit(ForCond forCond) {
		forCond.struct = forCond.getCondition().struct;
	}
	
	public void visit(IfCond ifCond) {
		ifCond.struct = ifCond.getCondition().struct;
	}
	
	
	//=========================================================================================================================
	//Obrada designatora
	public void visit(Designator designator) { 
		if(lastDesignator != null && lastDesignator.getKind() == Obj.Type) {
			report_error("Greska na " + designator.getLine()+ "(" + lastDesignator.getName() + 
						"): designator ne moze biti Tip ", null);
			errorDetected = true;
			designator.obj = Tab.noObj;
			lastDesignator = null;
			return;
		}
		designator.obj = lastDesignator;
		lastDesignator = null;
		if(designator.obj != null) {
			report_info("Pretraga na " + designator.getLine() + "(" + designator.obj.getName() + "), nadjeno "
					+ TabExtended.objToString(designator.obj), null);
		}
		else {
			designator.obj = Tab.noObj;
		}
	}
	
	public void visit(NonArrayDesignator designator){
		if(lastDesignator != null) {
			if(lastDesignator.getType().getKind() == Struct.Enum && lastDesignator.getKind() == Obj.Type) {  //enum
				Obj enumConst = lastDesignator.getType().getMembersTable().searchKey(designator.getName());
				if(enumConst == null) {
					report_error("Greska na " + designator.getLine() + ": " + designator.getName() + 
							" nije konstanta iz enuma " + lastDesignator.getName(), null);
					errorDetected = true;
					designator.obj = null;
					lastDesignator = null;
					return;
				}
				designator.obj = enumConst;
				lastDesignator = designator.obj;
			}
		}
		else {
			Obj obj = Tab.find(designator.getName());
			if (obj == Tab.noObj) { 
				report_error("Greska na " + designator.getLine() + " : "+ designator.getName() +" nije deklarisano! ", null);
				errorDetected = true;
				designator.obj = Tab.noObj;
				lastDesignator = null;
				return;
			}
			designator.obj = obj;
			lastDesignator = designator.obj;
		}
	}
	
	public void visit(ArrayDesignator arrayDesign) {
		if(arrayDesign.getExpr().obj.getType() != Tab.intType && arrayDesign.getExpr().obj.getType().getKind() != Struct.Enum) {
			report_error("Greska na " + arrayDesign.getLine() + ": izraz u [] mora biti int tipa ", null);
			errorDetected = true;
			return;
		}
		Obj arrObj  = arrayDesign.getArrayName().obj;
		arrayDesign.obj = new Obj(Obj.Elem, "element niza " + arrObj.getName(), arrObj.getType().getElemType());
		lastDesignator = arrayDesign.obj;
	}
	
	public void visit(ArrayName arrName) {
		Obj obj = Tab.find(arrName.getArrName());
		if(obj == Tab.noObj) {
			report_error("Greska na " + arrName.getLine()+ " : "+arrName.getArrName() +" nije deklarisano! ", null);
			errorDetected = true;
			return;
		}
		if(obj.getType().getKind() != Struct.Array) {
			report_error("Greska na " + arrName.getLine() + ": " + arrName.getArrName() + " nije niz", arrName);
			errorDetected = true;
			return;
		}
		arrName.obj = obj;
	}
	
	//=========================================================================================================================
	
	public boolean passed() {
		return !errorDetected;
	}
	
	//=========================================================================================================================
		/*
		//Obrada definicije klase
		public void visit(ClassDecl classDecl) {
			Tab.chainLocalSymbols(currentClass.getType());  //Uvezivanje u members
			Tab.closeScope();
			
			classDefinitionOpen = false;
			currentClass = null;
		}
		
		public void visit(ClassName className) {
			currentClass = Tab.insert(Obj.Type, className.getName(), Tab.nullType);  //nullType = new Struct(Struct.Class);
			className.obj = currentClass;
			classDefinitionOpen = true;
			Tab.openScope();
			report_info("Obradjuje se definicija klase " + className.getName(), className);
		}
		
		public void visit(ClassExtend classExtend) {
			//U scope-u izvedene klase definisem nasledjena polja i metode
			Collection<Obj> superMembers = classExtend.getType().struct.getMembers().symbols();
			for(Obj member : superMembers) {
				Obj extendedMember = Tab.insert(Obj.Fld, member.getName(), member.getType());  
			}
		}*/
		
}

