// generated with ast extension for cup
// version 0.8
// 22/7/2019 15:47:30


package rs.ac.bg.etf.pp1.ast;

public class MatchedForStmt extends Matched {

    private For For;
    private ForDesStmt1 ForDesStmt1;
    private ForCondition ForCondition;
    private ForDesStmt2 ForDesStmt2;
    private Matched Matched;

    public MatchedForStmt (For For, ForDesStmt1 ForDesStmt1, ForCondition ForCondition, ForDesStmt2 ForDesStmt2, Matched Matched) {
        this.For=For;
        if(For!=null) For.setParent(this);
        this.ForDesStmt1=ForDesStmt1;
        if(ForDesStmt1!=null) ForDesStmt1.setParent(this);
        this.ForCondition=ForCondition;
        if(ForCondition!=null) ForCondition.setParent(this);
        this.ForDesStmt2=ForDesStmt2;
        if(ForDesStmt2!=null) ForDesStmt2.setParent(this);
        this.Matched=Matched;
        if(Matched!=null) Matched.setParent(this);
    }

    public For getFor() {
        return For;
    }

    public void setFor(For For) {
        this.For=For;
    }

    public ForDesStmt1 getForDesStmt1() {
        return ForDesStmt1;
    }

    public void setForDesStmt1(ForDesStmt1 ForDesStmt1) {
        this.ForDesStmt1=ForDesStmt1;
    }

    public ForCondition getForCondition() {
        return ForCondition;
    }

    public void setForCondition(ForCondition ForCondition) {
        this.ForCondition=ForCondition;
    }

    public ForDesStmt2 getForDesStmt2() {
        return ForDesStmt2;
    }

    public void setForDesStmt2(ForDesStmt2 ForDesStmt2) {
        this.ForDesStmt2=ForDesStmt2;
    }

    public Matched getMatched() {
        return Matched;
    }

    public void setMatched(Matched Matched) {
        this.Matched=Matched;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(For!=null) For.accept(visitor);
        if(ForDesStmt1!=null) ForDesStmt1.accept(visitor);
        if(ForCondition!=null) ForCondition.accept(visitor);
        if(ForDesStmt2!=null) ForDesStmt2.accept(visitor);
        if(Matched!=null) Matched.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(For!=null) For.traverseTopDown(visitor);
        if(ForDesStmt1!=null) ForDesStmt1.traverseTopDown(visitor);
        if(ForCondition!=null) ForCondition.traverseTopDown(visitor);
        if(ForDesStmt2!=null) ForDesStmt2.traverseTopDown(visitor);
        if(Matched!=null) Matched.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(For!=null) For.traverseBottomUp(visitor);
        if(ForDesStmt1!=null) ForDesStmt1.traverseBottomUp(visitor);
        if(ForCondition!=null) ForCondition.traverseBottomUp(visitor);
        if(ForDesStmt2!=null) ForDesStmt2.traverseBottomUp(visitor);
        if(Matched!=null) Matched.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MatchedForStmt(\n");

        if(For!=null)
            buffer.append(For.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForDesStmt1!=null)
            buffer.append(ForDesStmt1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForCondition!=null)
            buffer.append(ForCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForDesStmt2!=null)
            buffer.append(ForDesStmt2.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Matched!=null)
            buffer.append(Matched.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MatchedForStmt]");
        return buffer.toString();
    }
}
