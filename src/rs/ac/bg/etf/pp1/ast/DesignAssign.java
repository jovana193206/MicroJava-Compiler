// generated with ast extension for cup
// version 0.8
// 22/7/2019 15:47:30


package rs.ac.bg.etf.pp1.ast;

public class DesignAssign implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Designator Designator;
    private DesignatorAssignment DesignatorAssignment;

    public DesignAssign (Designator Designator, DesignatorAssignment DesignatorAssignment) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.DesignatorAssignment=DesignatorAssignment;
        if(DesignatorAssignment!=null) DesignatorAssignment.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public DesignatorAssignment getDesignatorAssignment() {
        return DesignatorAssignment;
    }

    public void setDesignatorAssignment(DesignatorAssignment DesignatorAssignment) {
        this.DesignatorAssignment=DesignatorAssignment;
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
        if(Designator!=null) Designator.accept(visitor);
        if(DesignatorAssignment!=null) DesignatorAssignment.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(DesignatorAssignment!=null) DesignatorAssignment.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(DesignatorAssignment!=null) DesignatorAssignment.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignAssign(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorAssignment!=null)
            buffer.append(DesignatorAssignment.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignAssign]");
        return buffer.toString();
    }
}
