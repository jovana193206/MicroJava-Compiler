// generated with ast extension for cup
// version 0.8
// 22/7/2019 15:47:31


package rs.ac.bg.etf.pp1.ast;

public class InitLst extends InitList {

    private InitListStart InitListStart;
    private ExprList ExprList;

    public InitLst (InitListStart InitListStart, ExprList ExprList) {
        this.InitListStart=InitListStart;
        if(InitListStart!=null) InitListStart.setParent(this);
        this.ExprList=ExprList;
        if(ExprList!=null) ExprList.setParent(this);
    }

    public InitListStart getInitListStart() {
        return InitListStart;
    }

    public void setInitListStart(InitListStart InitListStart) {
        this.InitListStart=InitListStart;
    }

    public ExprList getExprList() {
        return ExprList;
    }

    public void setExprList(ExprList ExprList) {
        this.ExprList=ExprList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(InitListStart!=null) InitListStart.accept(visitor);
        if(ExprList!=null) ExprList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(InitListStart!=null) InitListStart.traverseTopDown(visitor);
        if(ExprList!=null) ExprList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(InitListStart!=null) InitListStart.traverseBottomUp(visitor);
        if(ExprList!=null) ExprList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("InitLst(\n");

        if(InitListStart!=null)
            buffer.append(InitListStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ExprList!=null)
            buffer.append(ExprList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InitLst]");
        return buffer.toString();
    }
}
