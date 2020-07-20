package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class MethodCall {
	
	public Struct formTypes[];
	public int parsDetected, parsExpected;
	public Obj calledMethod;
	
	public MethodCall(Obj meth) {
		calledMethod = meth;
		if(calledMethod != Tab.noObj) {
			parsExpected = calledMethod.getLevel();
			formTypes = new Struct[parsExpected];
			for(Obj obj : meth.getLocalSymbols()) {
				int fpPos = obj.getFpPos();
				if(fpPos != 0) {  //if fpPos == 0 it is not a formal parameter
					formTypes[fpPos-1] = obj.getType();
				}
			}
			
		}
		else {
			parsExpected = 0;
			formTypes = null;
		}
		parsDetected = 0;
	}

}
