package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.visitors.SymbolTableVisitor;

public class MJCodeGenTest {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void main(String[] args) throws Exception {
		
		Logger log = Logger.getLogger(MJSemanticTest.class);
		
		Reader br = null;
		try {
			File sourceCode = new File("test/test302.mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();  //pocetak parsiranja
	        
	        Program prog = (Program)(s.value); 
	        //Zbog bool tipa zove se TabExtended.init(); umesto Tab.init();
	        TabExtended.init();
			// ispis sintaksnog stabla
			log.info(prog.toString(""));
			log.info("===================================");

			// ispis prepoznatih programskih konstrukcija
			SemanticAnalyzer semanticCheck = new SemanticAnalyzer();
			prog.traverseBottomUp(semanticCheck); 

			//log.info(" Deklarisanih promenljivih ima = " + semanticCheck.nVars);
			
			log.info("==============SINTAKSNA ANALIZA===============");
			log.info("" + semanticCheck.numOfClasses + "\t classes ");
			log.info("" + semanticCheck.numOfMethods + "\t methods in the program ");
			log.info("" + semanticCheck.numOfGlobalVars + "\t global variables ");
			log.info("" + semanticCheck.numOfGlobalConsts + "\t global constants ");
			log.info("" + semanticCheck.numOfGlobalArrays + "\t global arrays ");
			log.info("" + semanticCheck.numOfLocalVarsMain + "\t local variables in main ");
			log.info("" + semanticCheck.numOfStmtsMain + "\t statements in main ");
			log.info("" + semanticCheck.numOfFuncCallsMain + "\t function calls in main ");
			
			SymbolTableVisitor stv = new Dump();
			Tab.dump(stv);
			
			if(!p.errorDetected && semanticCheck.passed()){
				File objFile = new File("test/test302.obj");
				log.info("Generating bytecode file: " + objFile.getAbsolutePath());
	        	if (objFile.exists())
	        		objFile.delete();
	        	
				// Code generation...
				CodeGenerator codeGenerator = new CodeGenerator();
				prog.traverseBottomUp(codeGenerator); 
				Code.dataSize = semanticCheck.nVars;
	        	Code.mainPc = codeGenerator.getMainPc();
	        	Code.write(new FileOutputStream(objFile));
				log.info("Parsiranje uspesno zavrseno!");
			}else{
				log.error("Parsiranje NIJE uspesno zavrseno!");
			}
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}
	}
	
}
