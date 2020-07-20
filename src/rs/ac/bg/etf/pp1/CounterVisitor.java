package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.ArrayVarIdent;
import rs.ac.bg.etf.pp1.ast.FormalParam;
import rs.ac.bg.etf.pp1.ast.FormalParamDecl;
import rs.ac.bg.etf.pp1.ast.NonArrayVarIdent;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {
	
	protected int count;
	
	public int getCount() {
		return count;
	}
	
	public static class FormParamCounter extends CounterVisitor {

		@Override
		public void visit(FormalParam formParam) {
			count++;
		}		
	}
	
	public static class VarCounter extends CounterVisitor { 
		//Broji sve VarIdent -> rezultat je broj formalnih parametara + broj lokalnih promenljivih metode
		
		@Override
		public void visit(NonArrayVarIdent VarDecl) {
			count++;
		}
		
		@Override
		public void visit(ArrayVarIdent VarDecl) {
			count++;
		}
	}
}
