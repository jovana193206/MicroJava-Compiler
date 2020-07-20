// generated with ast extension for cup
// version 0.8
// 22/7/2019 15:47:30


package rs.ac.bg.etf.pp1.ast;

public class DesignAssignStmt extends DesignatorStatement {

    private DesignAssign DesignAssign;

    public DesignAssignStmt (DesignAssign DesignAssign) {
        this.DesignAssign=DesignAssign;
        if(DesignAssign!=null) DesignAssign.setParent(this);
    }

    public DesignAssign getDesignAssign() {
        return DesignAssign;
    }

    public void setDesignAssign(DesignAssign DesignAssign) {
        this.DesignAssign=DesignAssign;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignAssign!=null) DesignAssign.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignAssign!=null) DesignAssign.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignAssign!=null) DesignAssign.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignAssignStmt(\n");

        if(DesignAssign!=null)
            buffer.append(DesignAssign.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignAssignStmt]");
        return buffer.toString();
    }
}
