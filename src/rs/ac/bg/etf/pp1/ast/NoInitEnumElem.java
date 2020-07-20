// generated with ast extension for cup
// version 0.8
// 22/7/2019 15:47:29


package rs.ac.bg.etf.pp1.ast;

public class NoInitEnumElem extends EnumElem {

    private String enumElemName;

    public NoInitEnumElem (String enumElemName) {
        this.enumElemName=enumElemName;
    }

    public String getEnumElemName() {
        return enumElemName;
    }

    public void setEnumElemName(String enumElemName) {
        this.enumElemName=enumElemName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NoInitEnumElem(\n");

        buffer.append(" "+tab+enumElemName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NoInitEnumElem]");
        return buffer.toString();
    }
}
