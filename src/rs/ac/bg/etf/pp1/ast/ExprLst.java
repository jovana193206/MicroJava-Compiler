// generated with ast extension for cup
// version 0.8
// 22/7/2019 15:47:31


package rs.ac.bg.etf.pp1.ast;

public class ExprLst extends ExprList {

    private ExprList ExprList;
    private InitElem InitElem;

    public ExprLst (ExprList ExprList, InitElem InitElem) {
        this.ExprList=ExprList;
        if(ExprList!=null) ExprList.setParent(this);
        this.InitElem=InitElem;
        if(InitElem!=null) InitElem.setParent(this);
    }

    public ExprList getExprList() {
        return ExprList;
    }

    public void setExprList(ExprList ExprList) {
        this.ExprList=ExprList;
    }

    public InitElem getInitElem() {
        return InitElem;
    }

    public void setInitElem(InitElem InitElem) {
        this.InitElem=InitElem;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExprList!=null) ExprList.accept(visitor);
        if(InitElem!=null) InitElem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprList!=null) ExprList.traverseTopDown(visitor);
        if(InitElem!=null) InitElem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprList!=null) ExprList.traverseBottomUp(visitor);
        if(InitElem!=null) InitElem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExprLst(\n");

        if(ExprList!=null)
            buffer.append(ExprList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(InitElem!=null)
            buffer.append(InitElem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExprLst]");
        return buffer.toString();
    }
}
