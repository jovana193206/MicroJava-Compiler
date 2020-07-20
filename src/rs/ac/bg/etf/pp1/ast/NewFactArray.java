// generated with ast extension for cup
// version 0.8
// 22/7/2019 15:47:31


package rs.ac.bg.etf.pp1.ast;

public class NewFactArray extends Factor {

    private NewArray NewArray;

    public NewFactArray (NewArray NewArray) {
        this.NewArray=NewArray;
        if(NewArray!=null) NewArray.setParent(this);
    }

    public NewArray getNewArray() {
        return NewArray;
    }

    public void setNewArray(NewArray NewArray) {
        this.NewArray=NewArray;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NewArray!=null) NewArray.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NewArray!=null) NewArray.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NewArray!=null) NewArray.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NewFactArray(\n");

        if(NewArray!=null)
            buffer.append(NewArray.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NewFactArray]");
        return buffer.toString();
    }
}
