public class rbNode {
    private rbNode parent;
    private rbNode left;
    private rbNode right;
    private boolean color;// ifBlack - true, ifRed - false
    private int theID;
    private int m; //count

    public rbNode(){
        parent = rbTree.getNil();
        left = rbTree.getNil();
        right = rbTree.getNil();
        theID = 0;
        m = 0;
    }

    public rbNode getParent() {
        return parent;
    }
    public void setParent(rbNode parent) {
        this.parent = parent;
    }
    public rbNode getLeft() {
        return left;
    }
    public void setLeft(rbNode left) {
        this.left = left;
    }
    public rbNode getRight() {
        return right;
    }
    public void setRight(rbNode right) {
        this.right = right;
    }
    public boolean getColor() {
        return color;
    }
    public void setColor(boolean color) {
        this.color = color;
    }
    public int getID() {
        return theID;
    }
    public int getCount() {
        return m;
    }
    public void setData(int id, int count) {
        if (count > 0) {
            this.theID = id;
            this.m = count;
        }
    }
}