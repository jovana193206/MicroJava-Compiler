package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;

public class TabExtended extends Tab {
	
	public static final Struct boolType = new Struct(Struct.Bool);
	
	// pozovi metodu Tab.init() pa posle samo dodaj  currentScope.addToLocals(new Obj(Obj.Type, "bool", intType));
	//Tab extended sluzi samo za pozivanje init() metode sve ostalo se rai sa Tab klasom

	public static void init() {
		Tab.init();
		Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", boolType));
	}
	
	public static String objToString(Obj objToVisit) {
		StringBuilder output = new StringBuilder(); 
		
		switch (objToVisit.getKind()) {
		case Obj.Con:  output.append("Con "); break;
		case Obj.Var:  output.append("Var "); break;
		case Obj.Type: output.append("Type "); break;
		case Obj.Meth: output.append("Meth "); break;
		case Obj.Fld:  output.append("Fld "); break;
		case Obj.Prog: output.append("Prog "); break;
		}
		
		output.append(objToVisit.getName());
		output.append(": ");
		
		Struct structToVisit = objToVisit.getType();
		
		output.append(TabExtended.structToString(structToVisit));
		
		output.append(", ");
		output.append(objToVisit.getAdr());
		output.append(", ");
		output.append(objToVisit.getLevel() + " ");
		
		return output.toString();
	}
	
	public static String structToString(Struct structToVisit) {
		StringBuilder output = new StringBuilder();
		
		switch (structToVisit.getKind()) {
		case Struct.None:
			output.append("notype");
			break;
		case Struct.Int:
			output.append("int");
			break;
		case Struct.Char:
			output.append("char");
			break;
		case Struct.Bool:
			output.append("bool");
			break;
		case Struct.Array:
			output.append("Arr of ");
			
			switch (structToVisit.getElemType().getKind()) {
			case Struct.None:
				output.append("notype");
				break;
			case Struct.Int:
				output.append("int");
				break;
			case Struct.Char:
				output.append("char");
				break;
			case Struct.Class:
				output.append("Class");
				break;
			}
			break;
		case Struct.Enum:
			output.append("int");
			break;
		}
		
		return output.toString();
	}

}
