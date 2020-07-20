// generated with ast extension for cup
// version 0.8
// 22/7/2019 15:47:28


package rs.ac.bg.etf.pp1.ast;

public class GlobalVarIdents extends GlobalVarIdentList {

    private GlobalVarIdentList GlobalVarIdentList;
    private GlVarIdentListPart GlVarIdentListPart;

    public GlobalVarIdents (GlobalVarIdentList GlobalVarIdentList, GlVarIdentListPart GlVarIdentListPart) {
        this.GlobalVarIdentList=GlobalVarIdentList;
        if(GlobalVarIdentList!=null) GlobalVarIdentList.setParent(this);
        this.GlVarIdentListPart=GlVarIdentListPart;
        if(GlVarIdentListPart!=null) GlVarIdentListPart.setParent(this);
    }

    public GlobalVarIdentList getGlobalVarIdentList() {
        return GlobalVarIdentList;
    }

    public void setGlobalVarIdentList(GlobalVarIdentList GlobalVarIdentList) {
        this.GlobalVarIdentList=GlobalVarIdentList;
    }

    public GlVarIdentListPart getGlVarIdentListPart() {
        return GlVarIdentListPart;
    }

    public void setGlVarIdentListPart(GlVarIdentListPart GlVarIdentListPart) {
        this.GlVarIdentListPart=GlVarIdentListPart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(GlobalVarIdentList!=null) GlobalVarIdentList.accept(visitor);
        if(GlVarIdentListPart!=null) GlVarIdentListPart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(GlobalVarIdentList!=null) GlobalVarIdentList.traverseTopDown(visitor);
        if(GlVarIdentListPart!=null) GlVarIdentListPart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(GlobalVarIdentList!=null) GlobalVarIdentList.traverseBottomUp(visitor);
        if(GlVarIdentListPart!=null) GlVarIdentListPart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobalVarIdents(\n");

        if(GlobalVarIdentList!=null)
            buffer.append(GlobalVarIdentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(GlVarIdentListPart!=null)
            buffer.append(GlVarIdentListPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobalVarIdents]");
        return buffer.toString();
    }
}
