// generated with ast extension for cup
// version 0.8
// 22/7/2019 15:47:30


package rs.ac.bg.etf.pp1.ast;

public class NewArrayInit extends DesignatorStatement {

    private DesignAssign DesignAssign;
    private InitList InitList;

    public NewArrayInit (DesignAssign DesignAssign, InitList InitList) {
        this.DesignAssign=DesignAssign;
        if(DesignAssign!=null) DesignAssign.setParent(this);
        this.InitList=InitList;
        if(InitList!=null) InitList.setParent(this);
    }

    public DesignAssign getDesignAssign() {
        return DesignAssign;
    }

    public void setDesignAssign(DesignAssign DesignAssign) {
        this.DesignAssign=DesignAssign;
    }

    public InitList getInitList() {
        return InitList;
    }

    public void setInitList(InitList InitList) {
        this.InitList=InitList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignAssign!=null) DesignAssign.accept(visitor);
        if(InitList!=null) InitList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignAssign!=null) DesignAssign.traverseTopDown(visitor);
        if(InitList!=null) InitList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignAssign!=null) DesignAssign.traverseBottomUp(visitor);
        if(InitList!=null) InitList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NewArrayInit(\n");

        if(DesignAssign!=null)
            buffer.append(DesignAssign.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(InitList!=null)
            buffer.append(InitList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NewArrayInit]");
        return buffer.toString();
    }
}
