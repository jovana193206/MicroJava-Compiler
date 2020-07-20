// generated with ast extension for cup
// version 0.8
// 22/7/2019 15:47:31


package rs.ac.bg.etf.pp1.ast;

public class Subdesignators extends SubdesignatorList {

    private SubdesignatorList SubdesignatorList;
    private Subdesignator Subdesignator;

    public Subdesignators (SubdesignatorList SubdesignatorList, Subdesignator Subdesignator) {
        this.SubdesignatorList=SubdesignatorList;
        if(SubdesignatorList!=null) SubdesignatorList.setParent(this);
        this.Subdesignator=Subdesignator;
        if(Subdesignator!=null) Subdesignator.setParent(this);
    }

    public SubdesignatorList getSubdesignatorList() {
        return SubdesignatorList;
    }

    public void setSubdesignatorList(SubdesignatorList SubdesignatorList) {
        this.SubdesignatorList=SubdesignatorList;
    }

    public Subdesignator getSubdesignator() {
        return Subdesignator;
    }

    public void setSubdesignator(Subdesignator Subdesignator) {
        this.Subdesignator=Subdesignator;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SubdesignatorList!=null) SubdesignatorList.accept(visitor);
        if(Subdesignator!=null) Subdesignator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SubdesignatorList!=null) SubdesignatorList.traverseTopDown(visitor);
        if(Subdesignator!=null) Subdesignator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SubdesignatorList!=null) SubdesignatorList.traverseBottomUp(visitor);
        if(Subdesignator!=null) Subdesignator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Subdesignators(\n");

        if(SubdesignatorList!=null)
            buffer.append(SubdesignatorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Subdesignator!=null)
            buffer.append(Subdesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Subdesignators]");
        return buffer.toString();
    }
}
