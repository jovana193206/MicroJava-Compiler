// generated with ast extension for cup
// version 0.8
// 22/7/2019 15:47:29


package rs.ac.bg.etf.pp1.ast;

public class InitEnumElem extends EnumElem {

    private String enumElemName;
    private Assignop Assignop;
    private Integer initVal;

    public InitEnumElem (String enumElemName, Assignop Assignop, Integer initVal) {
        this.enumElemName=enumElemName;
        this.Assignop=Assignop;
        if(Assignop!=null) Assignop.setParent(this);
        this.initVal=initVal;
    }

    public String getEnumElemName() {
        return enumElemName;
    }

    public void setEnumElemName(String enumElemName) {
        this.enumElemName=enumElemName;
    }

    public Assignop getAssignop() {
        return Assignop;
    }

    public void setAssignop(Assignop Assignop) {
        this.Assignop=Assignop;
    }

    public Integer getInitVal() {
        return initVal;
    }

    public void setInitVal(Integer initVal) {
        this.initVal=initVal;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Assignop!=null) Assignop.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Assignop!=null) Assignop.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Assignop!=null) Assignop.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("InitEnumElem(\n");

        buffer.append(" "+tab+enumElemName);
        buffer.append("\n");

        if(Assignop!=null)
            buffer.append(Assignop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+initVal);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InitEnumElem]");
        return buffer.toString();
    }
}
