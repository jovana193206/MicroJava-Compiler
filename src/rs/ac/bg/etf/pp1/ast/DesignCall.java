// generated with ast extension for cup
// version 0.8
// 22/7/2019 15:47:30


package rs.ac.bg.etf.pp1.ast;

public class DesignCall extends DesignatorStatement {

    private MethodCalled MethodCalled;
    private ActPars ActPars;

    public DesignCall (MethodCalled MethodCalled, ActPars ActPars) {
        this.MethodCalled=MethodCalled;
        if(MethodCalled!=null) MethodCalled.setParent(this);
        this.ActPars=ActPars;
        if(ActPars!=null) ActPars.setParent(this);
    }

    public MethodCalled getMethodCalled() {
        return MethodCalled;
    }

    public void setMethodCalled(MethodCalled MethodCalled) {
        this.MethodCalled=MethodCalled;
    }

    public ActPars getActPars() {
        return ActPars;
    }

    public void setActPars(ActPars ActPars) {
        this.ActPars=ActPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodCalled!=null) MethodCalled.accept(visitor);
        if(ActPars!=null) ActPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodCalled!=null) MethodCalled.traverseTopDown(visitor);
        if(ActPars!=null) ActPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodCalled!=null) MethodCalled.traverseBottomUp(visitor);
        if(ActPars!=null) ActPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignCall(\n");

        if(MethodCalled!=null)
            buffer.append(MethodCalled.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActPars!=null)
            buffer.append(ActPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignCall]");
        return buffer.toString();
    }
}
