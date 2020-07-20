// generated with ast extension for cup
// version 0.8
// 22/7/2019 15:47:29


package rs.ac.bg.etf.pp1.ast;

public class UnmatchedIf extends Unmatched {

    private IfCondition IfCondition;
    private UnmatchedThen UnmatchedThen;

    public UnmatchedIf (IfCondition IfCondition, UnmatchedThen UnmatchedThen) {
        this.IfCondition=IfCondition;
        if(IfCondition!=null) IfCondition.setParent(this);
        this.UnmatchedThen=UnmatchedThen;
        if(UnmatchedThen!=null) UnmatchedThen.setParent(this);
    }

    public IfCondition getIfCondition() {
        return IfCondition;
    }

    public void setIfCondition(IfCondition IfCondition) {
        this.IfCondition=IfCondition;
    }

    public UnmatchedThen getUnmatchedThen() {
        return UnmatchedThen;
    }

    public void setUnmatchedThen(UnmatchedThen UnmatchedThen) {
        this.UnmatchedThen=UnmatchedThen;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IfCondition!=null) IfCondition.accept(visitor);
        if(UnmatchedThen!=null) UnmatchedThen.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IfCondition!=null) IfCondition.traverseTopDown(visitor);
        if(UnmatchedThen!=null) UnmatchedThen.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IfCondition!=null) IfCondition.traverseBottomUp(visitor);
        if(UnmatchedThen!=null) UnmatchedThen.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("UnmatchedIf(\n");

        if(IfCondition!=null)
            buffer.append(IfCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(UnmatchedThen!=null)
            buffer.append(UnmatchedThen.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [UnmatchedIf]");
        return buffer.toString();
    }
}
