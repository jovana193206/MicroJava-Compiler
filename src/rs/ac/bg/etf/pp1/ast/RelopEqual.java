// generated with ast extension for cup
// version 0.8
// 22/7/2019 15:47:31


package rs.ac.bg.etf.pp1.ast;

public class RelopEqual extends Relop {

    public RelopEqual () {
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
        buffer.append("RelopEqual(\n");

        buffer.append(tab);
        buffer.append(") [RelopEqual]");
        return buffer.toString();
    }
}
