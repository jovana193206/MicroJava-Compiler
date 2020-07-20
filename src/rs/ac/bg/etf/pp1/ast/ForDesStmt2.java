// generated with ast extension for cup
// version 0.8
// 22/7/2019 15:47:30


package rs.ac.bg.etf.pp1.ast;

public class ForDesStmt2 implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private ForDesStatement ForDesStatement;

    public ForDesStmt2 (ForDesStatement ForDesStatement) {
        this.ForDesStatement=ForDesStatement;
        if(ForDesStatement!=null) ForDesStatement.setParent(this);
    }

    public ForDesStatement getForDesStatement() {
        return ForDesStatement;
    }

    public void setForDesStatement(ForDesStatement ForDesStatement) {
        this.ForDesStatement=ForDesStatement;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForDesStatement!=null) ForDesStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForDesStatement!=null) ForDesStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForDesStatement!=null) ForDesStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForDesStmt2(\n");

        if(ForDesStatement!=null)
            buffer.append(ForDesStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForDesStmt2]");
        return buffer.toString();
    }
}
