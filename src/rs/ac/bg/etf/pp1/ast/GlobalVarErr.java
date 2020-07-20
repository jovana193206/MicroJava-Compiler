// generated with ast extension for cup
// version 0.8
// 22/7/2019 15:47:28


package rs.ac.bg.etf.pp1.ast;

public class GlobalVarErr extends GlobalVarDecl {

    private GlobalVarError GlobalVarError;

    public GlobalVarErr (GlobalVarError GlobalVarError) {
        this.GlobalVarError=GlobalVarError;
        if(GlobalVarError!=null) GlobalVarError.setParent(this);
    }

    public GlobalVarError getGlobalVarError() {
        return GlobalVarError;
    }

    public void setGlobalVarError(GlobalVarError GlobalVarError) {
        this.GlobalVarError=GlobalVarError;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(GlobalVarError!=null) GlobalVarError.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(GlobalVarError!=null) GlobalVarError.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(GlobalVarError!=null) GlobalVarError.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobalVarErr(\n");

        if(GlobalVarError!=null)
            buffer.append(GlobalVarError.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobalVarErr]");
        return buffer.toString();
    }
}
