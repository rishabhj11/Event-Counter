public class rbTree {
    private rbNode root;
    private int inRangeCount = 0;
    /*Created a sentinel node, nil, that would act as leaves*/
    private static rbNode nil = new rbNode();

    public static rbNode getNil() {
        return nil;
    }

    /*****Initialize tree with a root*****/
    public rbTree() {
        root = new rbNode();
        root.setParent(nil);
        root.setLeft(nil);
        root.setRight(nil);
        root.setData(0, 0);
        nil.setColor(true);
    }

    public rbNode getRoot() {
        return root;
    }
    public void setRoot(rbNode root) {
        this.root = root;
    }

    /***************************Construct Initial Tree***********************************/
    public rbNode buildInitialRBTree(int[] arrID, int[] arrCount, int start, int end, int height, int maxHeight) {
        if (start > end) {
            return nil;
        }
        int mid = (start + end) / 2;
        rbNode node = new rbNode();
        height += 1;
        if (height == maxHeight)
            node.setColor(false);
        else
            node.setColor(true);
        node.setData(arrID[mid],arrCount[mid]);
        node.setLeft(buildInitialRBTree(arrID, arrCount, start, mid - 1, height, maxHeight));
        if(node.getLeft() != null){
            node.getLeft().setParent(node);
        }
        node.setRight(buildInitialRBTree(arrID, arrCount, mid + 1, end, height, maxHeight));
        if(node.getRight() != null){
            node.getRight().setParent(node);
        }
        return node;
    }

    /*****************Insert new nodes****************/
    public void rbInsert(int id, int count){
        rbNode parent = nil;
        rbNode node = root;

        boolean isLeft = false;
        while(!node.equals(nil))
        {
            parent = node;
            if(node.getID()>= id)
            {
                isLeft = true;
                node = node.getLeft();
            }
            else{
                if(node.getID() == getNil().getID())
                {
                    parent = nil;
                }
                isLeft = false;
                node = node.getRight();
            }
        }
        node = new rbNode();
        node.setLeft(nil);
        node.setRight(nil);
        node.setData(id, count);
        node.setColor(false);
        node.setParent(parent);

        if(!parent.equals(nil)){
            if(isLeft) {
                parent.setLeft(node);
            }
            else {
                parent.setRight(node);
            }

        }
        else
        {
            root = node;
        }
        /***Prints for the increase function***/
        System.out.println(node.getCount());
        rbInsertFixUp(node);
    }

    /*Coloring the new node red may cause violation of the red black
    properties. Calling rbInsertFixUp to fix up any such violations*/
    private void rbInsertFixUp(rbNode node){
        while(!node.getParent().getColor()){
            rbNode parent = node.getParent();
            if(!(parent.equals(parent.getParent().getLeft()))){
                rbNode pLeft = parent.getParent().getLeft();
                if(!pLeft.getColor()){
                    parent.getParent().setColor(false);
                    pLeft.setColor(true);
                    parent.setColor(true);
                    node = parent.getParent();
                }
                else {
                    if(node.equals(parent.getLeft())){
                        node = node.getParent();
                        rightRotate(node);
                    }
                    node.getParent().getParent().setColor(false);
                    node.getParent().setColor(true);
                    leftRotate(node.getParent().getParent());
                }
            }
            else {
                rbNode pRight = parent.getParent().getRight();
                if(!pRight.getColor()){
                    parent.getParent().setColor(false);
                    pRight.setColor(true);
                    parent.setColor(true);
                    node = parent.getParent();
                }
                else {
                    if(node.equals(parent.getRight())){
                        node = node.getParent();
                        leftRotate(node);
                    }
                    node.getParent().setColor(true);
                    node.getParent().getParent().setColor(false);
                    rightRotate(node.getParent().getParent());
                }
            }
        }
        getRoot().setColor(true);
    }

    /***To find the node with the least ID***/
    public rbNode findMin(rbNode root){
        rbNode node = root;
        if(!node.equals(nil)){
            while(!node.getLeft().equals(nil)){
                node = node.getLeft();
            }
        }
        return node;
    }

    /***To find the node with the most ID***/
    public rbNode findMax(rbNode root){
        rbNode node = root;
        if(!node.equals(nil)){
            while(!node.getRight().equals(nil)) {
                node = node.getRight();
            }
        }
        return node;
    }

    public void leftRotate(rbNode node){
        rbNode right = node.getRight();
        node.setRight(right.getLeft());
        if(!right.getLeft().equals(nil))
        {
            right.getLeft().setParent(node);
        }
        right.setParent(node.getParent());
        if(node.getParent().equals(nil))
        {
            root = right;
        }
        else if(node.equals(node.getParent().getLeft()))
        {
            node.getParent().setLeft(right);
        }
        else
        {
            node.getParent().setRight(right);
        }
        right.setLeft(node);
        node.setParent(right);
    }

    public void rightRotate(rbNode node){
        rbNode left = node.getLeft();
        node.setLeft(left.getRight());
        if(!left.getRight().equals(nil))
        {
            left.getRight().setParent(node);
        }
        left.setParent(node.getParent());
        if(node.getParent().equals(nil))
        {
            root = left;
        }
        else if(node.equals(node.getParent().getRight()))
        {
            node.getParent().setRight(left);
        }
        else
        {
            node.getParent().setLeft(left);
        }
        left.setRight(node);
        node.setParent(left);
    }

    /********************Remove Nodes********************/
    public void rbDelete(int theID){
        rbDelete(findNode(theID));
    }

    public void rbDelete(rbNode node){
        if(!node.equals(nil)){
            rbNode n = node;
            rbNode replace;
            boolean isBlack = n.getColor();
            if(node.getLeft().equals(nil))
            {
                replace = node.getRight();
                rbTransplant(node,node.getRight());
            }
            else if(node.getRight().equals(nil))
            {
                replace = node.getLeft();
                rbTransplant(node,node.getLeft());
            }
            else
            {
                n = findMin(node.getRight());
                isBlack = n.getColor();
                replace = n.getRight();
                if(n.getParent().equals(node))
                {
                    replace.setParent(n);
                }
                else
                {
                    rbTransplant(n,n.getRight());
                    n.setRight(node.getRight());
                    n.getRight().setParent(n);
                }
                rbTransplant(node,n);
                n.setLeft(node.getLeft());
                n.getLeft().setParent(n);
                n.setColor(node.getColor());
            }
            if(isBlack)
                deleteFixUp(replace);
        }
    }

    /*If the deleted node was black, it may cause red black tree violations,
    here we call deletefixup to remedy that*/
    public void deleteFixUp(rbNode node){
        while(!node.equals(root)&& node.getColor())
        {
            if(!(node.equals(node.getParent().getLeft())))
            {
                rbNode pLeft=node.getParent().getLeft();
                if(!pLeft.getColor())
                {
                    pLeft.setColor(true);
                    node.getParent().setColor(false);
                    rightRotate(node.getParent());
                    pLeft = node.getParent().getLeft();
                }
                if(pLeft.getRight().getColor() && pLeft.getLeft().getColor())
                {
                    pLeft.setColor(false);
                    node = node.getParent();
                }
                else
                {
                    if(pLeft.getLeft().getColor()){
                        pLeft.getLeft().setColor(true);
                        pLeft.setColor(false);
                        leftRotate(pLeft);
                        pLeft=node.getParent().getLeft();
                    }
                    pLeft.setColor(node.getParent().getColor());
                    node.getParent().setColor(true);
                    pLeft.getLeft().setColor(true);
                    rightRotate(node.getParent());
                    node = root;
                }
            }

            else
            {
                rbNode pRight = node.getParent().getRight();
                if(!pRight.getColor())
                {
                    node.getParent().setColor(false);
                    pRight.setColor(true);
                    leftRotate(node.getParent());
                    pRight = node.getParent().getRight();

                }
                if(pRight.getLeft().getColor() && pRight.getRight().getColor())
                {
                    pRight.setColor(false);
                    node = node.getParent();
                }
                else
                {
                    if(pRight.getRight().getColor())
                    {
                        pRight.getLeft().setColor(true);
                        pRight.setColor(false);
                        rightRotate(pRight);
                        pRight=node.getParent().getRight();
                    }
                    pRight.setColor(node.getParent().getColor());
                    node.getParent().setColor(true);
                    pRight.getRight().setColor(true);
                    leftRotate(node.getParent());
                    node = root;
                }
            }
        }
        node.setColor(true);
    }

    /***Search function that returns the node for the given ID*/
    public rbNode findNode(int id){
        rbNode node = root;
        if(node.getID()>0){
            while(node.getID()!= id && !node.equals(nil)){
                if(node.getID() > id)
                    node = node.getLeft();
                else node = node.getRight();
            }
        }
        return node;
    }

    private void rbTransplant(rbNode parent, rbNode child){
        if(parent.getParent().equals(nil))
            root = child;
        else if(parent.equals(parent.getParent().getLeft()))
            parent.getParent().setLeft(child);
        else parent.getParent().setRight(child);
        child.setParent(parent.getParent());
    }

    /*The inRange function will run in O(log n + k)
    where k is no of nodes in the range*/
    public void inRange(int k1, int k2) {
        if(k2 >= k1) {
            inRangeCount = 0;
            inRange(root, k1, k2);
            System.out.println(inRangeCount);
        }
    }

    private void inRange(rbNode node, int k1, int k2) {
        if (node.equals(nil)) {
            return;
        }
        if (k1 < node.getID()) {
            inRange(node.getLeft(), k1, k2);
        }
        if (k1 <= node.getID() && k2 >= node.getID()) {
            inRangeCount += node.getCount();
            //System.out.print(node.getID() + " ");
        }
        if (k2 > node.getID()) {
            inRange(node.getRight(), k1, k2);
        }
    }

    public void increase(int theID, int m) {
        rbNode node = findNode(theID);
        /*If node is present, increase count*/
        if(node != nil) {
            node.setData(theID, node.getCount() + m);
            System.out.println(node.getCount());
        }
        /*If node not present, insert node*/
        else {
            rbInsert(theID, m);
        }
    }

    public void reduce(int theID, int m) {
        rbNode node = findNode(theID);
        if(node != nil) {
            /*If node is present, decrease count*/
            if(node.getCount() - m > 0) {
                node.setData(theID, node.getCount() - m);
                System.out.println(node.getCount());
            }
            /*If node count goes down to zero, delete node*/
            else {
                rbDelete(theID);
                System.out.println(0);
            }
        }
        else
            System.out.println(0);
    }

    /*Returns count for the given ID, returns 0 if ID not present*/
    public void count(int theID){
        rbNode node = findNode(theID);
        if(node != nil)
            System.out.println(node.getCount());
        else
            System.out.println(0);
    }

    public void next(int theID){
        rbNode next;
        /*Initial two conditions check if ID is beyond extremities*/
        if(theID >= findMax(root).getID()){
            System.out.println("0 0");
        }
        else if (theID < (next = findMin(root)).getID()){
            System.out.println(next.getID()+" "+next.getCount());
        }

        //System.out.println("looking for next of "+next.getID());
        else {
            next = findNode(theID);
            rbNode node;
            /*If node is present, call successor function*/
            if(next != nil) {
                node = findNext(next);
            }
            /*If node not present, find the next closest*/
            else{
                node = searchNextClosest(theID);
            }
            System.out.println(node.getID() + " " + node.getCount());
        }
    }

    /*finds the successor if the node is present*/
    private rbNode findNext(rbNode node){
        if(!node.getRight().equals(nil)){
            return findMin(node.getRight());
        }
        rbNode next = node.getParent();
        while(!next.equals(nil) && node.equals(next.getRight())){
            node = next;
            next = next.getParent();
        }
        return next;
    }

    /*Finds the difference with each node and returns
    the one with the minimum difference*/
    private rbNode searchNextClosest(int id){
        rbNode node = root;
        int diff = Integer.MAX_VALUE;
        if(node.getID()>0){
            while(!node.equals(nil)){
                if(node.getID() > id) {
                    if((node.getID() - id)<diff)
                        diff = node.getID() - id;
                    node = node.getLeft();
                }
                else {
                    node = node.getRight();
                }
            }
        }
        return findNode(id + diff);
    }

    public void previous(int theID){
        rbNode last;
        /*Initial two conditions check if ID is beyond extremities*/
        if(theID <= findMin(root).getID()){
            System.out.println("0 0");
        }
        else if (theID > (last = findMax(root)).getID()){
            System.out.println(last.getID()+" "+last.getCount());
        }

        else {
            last = findNode(theID);
            rbNode node;
            /*If node is present, call predecessor function*/
            if(last != nil) {
                node = findPrevious(last);
            }
            /*If node not present, find the last closest*/
            else{
                node = searchLastClosest(theID);
            }
            System.out.println(node.getID() + " " + node.getCount());
        }
    }

    /*finds the predecessor if the node is present*/
    public rbNode findPrevious(rbNode node){
        rbNode last = nil;
        if(!node.equals(nil)){
            if(!node.getLeft().equals(nil))
                last = findMax(node.getLeft());
            else{
                last = node.getParent();
                while(!last.equals(nil) && !last.getLeft().equals(nil) && last.getLeft().getID() == node.getID()){
                    node = last;
                    last = last.getParent();
                }
            }
        }
        return last;
    }

    /*Finds the difference with each node and returns
    the one with the minimum difference*/
    private rbNode searchLastClosest(int id){
        rbNode node = root;
        int diff = Integer.MAX_VALUE;
        if(node.getID()>0){
            while(!node.equals(nil)){
                if(node.getID() > id) {
                    node = node.getLeft();
                }
                else {
                    if((id - node.getID()) < diff)
                        diff = id - node.getID();
                    node = node.getRight();
                }
            }
        }
        return findNode(id - diff);
    }

    public void print(rbNode node){ //In-Order
        if(!node.equals(nil)){
            print(node.getLeft());
            System.out.println(node.getID()+" "+node.getCount()+" "+node.getColor()+" "+node.getLeft().getID()+" "+node.getRight().getID()+" "+node.getParent().getID());
            print(node.getRight());
        }else{
            System.out.print(" ");
        }
    }
}