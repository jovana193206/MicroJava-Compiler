// generated with ast extension for cup
// version 0.8
// 22/7/2019 15:47:28


package rs.ac.bg.etf.pp1.ast;

public class GlobalVarDeclaration extends GlobalVarDecl {

    private Type Type;
    private GlobalVarIdentList GlobalVarIdentList;

    public GlobalVarDeclaration (Type Type, GlobalVarIdentList GlobalVarIdentList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.GlobalVarIdentList=GlobalVarIdentList;
        if(GlobalVarIdentList!=null) GlobalVarIdentList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public GlobalVarIdentList getGlobalVarIdentList() {
        return GlobalVarIdentList;
    }

    public void setGlobalVarIdentList(GlobalVarIdentList GlobalVarIdentList) {
        this.GlobalVarIdentList=GlobalVarIdentList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(GlobalVarIdentList!=null) GlobalVarIdentList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(GlobalVarIdentList!=null) GlobalVarIdentList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(GlobalVarIdentList!=null) GlobalVarIdentList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobalVarDeclaration(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(GlobalVarIdentList!=null)
            buffer.append(GlobalVarIdentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobalVarDeclaration]");
        return buffer.toString();
    }
}
