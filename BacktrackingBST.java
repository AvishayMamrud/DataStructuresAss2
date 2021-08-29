public class BacktrackingBST implements Backtrack, ADTSet<BacktrackingBST.Node> {
    private Stack stack;
    private Stack redoStack;
    BacktrackingBST.Node root = null;

    // Do not change the constructor's signature
    public BacktrackingBST(Stack stack, Stack redoStack) {
        this.stack = stack;
        this.redoStack = redoStack;
    }

    public Node getRoot() {
        return root;
    }

    public Node search(int x) {
        if(root!=null)
            return root.Search(x);
        return null;
    }

    public void insert(BacktrackingBST.Node z) {
    	if(search(z.key)==null) {
    		redoStack.clear();
    		Node inserted;
	        if(root==null) {
	            root = new BacktrackingBST.Node(z.getKey(),z.getValue());
	            inserted = root;
	        } else {
	            inserted = root.insert(z);
	        }
	        stack.push(new Capsule("del", inserted, null));
    	}
    }

    public void delete(Node x) {
        Node n = search(x.key);
        if (n != null) {
        	redoStack.clear();
            if (root == n & root.left == null & root.right == null) {
                root = null;
                stack.push(new Capsule("ins", n, null));//a leaf was deleted
                //if node is deleted than backtrack should insert it back
            }else if(root==n){
                root = n.delete(stack, true);
            } else {
            	n.delete(stack, true);
            }
        }
    }

    public Node minimum() {
        if(root!=null)
            return root.minimum();
        return null;
    }

    public Node maximum() {
        if(root!=null)
            return root.maximum();
        return null;
    }

    public Node successor(Node x) {
        Node theNode = search(x.key);
        if(theNode!=null) {
            return theNode.successor();
        }
        return null;
    }

    public Node predecessor(Node x) {
        Node theNode = search(x.key);
        if(theNode!=null) {
            return theNode.predecessor();
        }
        return null;
    }

    @Override
    public void backtrack() {
        if(!stack.isEmpty()){
            Node toDel, backToPlace, backOriginal, BTreplace;
            Capsule myCapsule = (Capsule)stack.pop();
            String action = myCapsule.ActionToDo;
            if(action.equals("del")) {
                toDel = myCapsule.nodeToAct;
                if(toDel.key!=root.key) {
                    toDel.delete(redoStack, true);
                }else if(root.right!=null | root.left!=null){
                    root = toDel.delete(redoStack, true);
                }else{
                    redoStack.push(new Capsule("ins", toDel, null));
                    root = null;
                }
            }else {
            	backToPlace = myCapsule.replaceCopy;//if we need to insert node back to its place
                backOriginal= myCapsule.nodeToAct;//the node to insert
                if(backToPlace!=null)
                	BTreplace = search(backToPlace.key);
                else BTreplace = null;

                //parent pointer setup
                if(backOriginal.parent!=null){
                	if(backOriginal.key>backOriginal.parent.key)//where to insert the node
                		backOriginal.parent.right=backOriginal;
                	else backOriginal.parent.left=backOriginal;
                }else root=backOriginal;//if the root was deleted
                
                //children's parent pointer setup
                if(backToPlace==null){//leaf was deleted
                	//this case is handled by telling the parent to point the leaf
                }else if(backOriginal.left!=null & backOriginal.right!=null){
                	backOriginal.right.parent = backOriginal;
                	backOriginal.left.parent = backOriginal;
                	if(backOriginal.right.key==backToPlace.key){//backToPlace is the right of backOriginal
                		BTreplace.parent=backOriginal;
                		BTreplace.left=null;
                	}else{
                		BTreplace.parent = backToPlace.parent;
                		BTreplace.left = null;
                		BTreplace.right = backToPlace.right;
               		 	backToPlace.parent.left=BTreplace;
               		 	if(backToPlace.right!=null) 
               		 		backToPlace.right.parent=BTreplace;
                	}
                }else{//if backOriginal has only one son
                	if(backOriginal.right!=null)
                		backOriginal.right.parent = backOriginal;
                	if(backOriginal.left!=null)
                		backOriginal.left.parent = backOriginal;
                }
                redoStack.push(new Capsule("del", backOriginal, null));
            }
            System.out.println("backtracking performed");
        }
    }

    @Override
    public void retrack() {
        if(!redoStack.isEmpty()){
            Node toDel, backToPlace, backOriginal, BTreplace;
            Capsule myCapsule = (Capsule)redoStack.pop();
            String action = myCapsule.ActionToDo;
            if(action.equals("del")) {
                toDel = myCapsule.nodeToAct;
                if(toDel.key!=root.key) {
                    toDel.delete(stack, true);
                }else if(root.right!=null | root.left!=null){
                    root = toDel.delete(stack, true);
                }else{
                    stack.push(new Capsule("ins", toDel, null));
                    root = null;
                }
            }else {
                backToPlace = myCapsule.replaceCopy;//if we need to insert node back to its place
                backOriginal= myCapsule.nodeToAct;//the node to insert
                if(backToPlace!=null)
                	BTreplace = search(backToPlace.key);
                else BTreplace = null;

                //parent pointer setup
                if(backOriginal.parent!=null){
                	if(backOriginal.key>backOriginal.parent.key)//where to insert the node
                		backOriginal.parent.right=backOriginal;
                	else backOriginal.parent.left=backOriginal;
                }else root=backOriginal;//if the root was deleted
                
                //children parent pointer setup
                if(backToPlace==null){//leaf was deleted
                	//this case is handled by telling the parent to point the leaf
                }else if(backOriginal.left!=null & backOriginal.right!=null){
                	backOriginal.right.parent = backOriginal;
                	backOriginal.left.parent = backOriginal;
                	if(backOriginal.right.key==backToPlace.key){//backToPlace is the right of backOriginal
                		BTreplace.parent=backOriginal;
                		BTreplace.left=null;
                	}else{
                		BTreplace.parent = backToPlace.parent;
                		BTreplace.left = null;
                		BTreplace.right = backToPlace.right;
               		 	backToPlace.parent.left=BTreplace;
               		 	if(backToPlace.right!=null) 
               		 		backToPlace.right.parent=BTreplace;
                	}
                }else{//if backOriginal has only one son
                	if(backOriginal.right!=null)
                		backOriginal.right.parent = backOriginal;
                	if(backOriginal.left!=null)
                		backOriginal.left.parent = backOriginal;
                }
                stack.push(new Capsule("del", backOriginal, null));
            }
        }
    }

    public void printPreOrder(){
        if(root!=null) {
            System.out.println(root.printPreOrder());
        }
    }

    @Override
    public void print() {
        if (root != null) {
            root.print();
        }
    }
    
    public String toString() {
        if (root!=null)
            return root.toString();
        else
            return "Empty Tree";
    }
    
    private static class Capsule{//this Capsule holds two nodes and a string
    	private Node nodeToAct;
    	private Node replaceCopy;
    	private String ActionToDo;
    	
    	public Capsule(String ActionToDo, Node nodeToAct, Node replaceCopy) {
    		this.ActionToDo = ActionToDo;
    		this.nodeToAct = nodeToAct;
    		this.replaceCopy = replaceCopy;
    	}
    }

    public static class Node{
        //These fields are public for grading purposes. By coding conventions and best practice they should be private.
        public BacktrackingBST.Node left;
        public BacktrackingBST.Node right;

        private BacktrackingBST.Node parent;
        private int key;
        private Object value;

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public Node getParent() {
            return parent;
        }
        
        public Node minimum() {
            if(left!=null)
                return left.minimum();
            return this;
        }

        public Node maximum() {
            if(right!=null)
                return right.maximum();
            return this;
        }

        public Node successor() {
            Node myNode,n=this;
            if(right!=null) {
                return right.minimum();
            }else {
                myNode = parent;
                while(myNode!=null){
                    if(myNode.left == n) {
                        return myNode;
                    }else {
                        n=myNode;
                        myNode = myNode.parent;
                    }
                }
                return null;
            }
        }

        public Node predecessor() {
            Node myNode,n=this;
            if(left!=null) {
                return left.maximum();
            }else {
                myNode = parent;
                while(myNode!=null){
                    if(myNode.right == n) {
                        return myNode;
                    }else {
                        n=myNode;
                        myNode = myNode.parent;
                    }
                }
                return null;
            }
        }

        public Node delete(Stack stack, boolean record) {
            Node backOriginal,backToPlace,onlyChild,minim,nodeToReturn = null;
            if(left==null | right==null) {//delete leaf
                if (left!=null)
                    backToPlace = left.duplicate();
                else if(right!=null) 
                	backToPlace = right.duplicate();
                else backToPlace = null;
                backOriginal = this;
                if(right==null)
                    onlyChild = left;
                else onlyChild = right;
                if(parent!=null){
                	if(onlyChild!=null)
                		onlyChild.parent=parent;
                    if (parent.left!=null && parent.left.key == this.key)
                        parent.left = onlyChild;
                    else parent.right = onlyChild;
                }else{
                    onlyChild.parent=null;
                    nodeToReturn =  onlyChild;
                }
                
            }else{//has two children
                minim = right.minimum();
                backToPlace = minim.duplicate();
                backOriginal = this;
                if(parent!=null && parent.right == this)
                	parent.right = minim;
                else if(parent!=null) {
                	parent.left = minim;
                }
                if(right==minim) {
                	minim.parent = parent;
                	minim.left = left;
                	left.parent = minim;
                }else{
                	if(minim.right!=null) 
                		minim.right.parent = minim.parent;
                	minim.parent.left = minim.right;//even if minim.right is null
                    minim.parent = parent;
                    minim.right = right;
                    minim.left = left;
                    left.parent = minim;
                    right.parent = minim;
                }
                nodeToReturn = minim;
            }
            if(record){
                //if node is deleted than backtrack should insert it back
                stack.push(new Capsule("ins", backOriginal, backToPlace));
            }
            return nodeToReturn;
        }

        public Node Search(int x) {
            if(key==x) 
                return this;
            else if(key<x & right!=null) 
                return right.Search(x);
            else if(left!=null)
                return left.Search(x);
            return null;
        }

        public Node insert(BacktrackingBST.Node z) {
            if(key<z.getKey()) {
                if(right==null) {
                    right = new BacktrackingBST.Node(z.getKey(),z.getValue());
                    right.parent = this;
                    return right;
                }else {
                    return right.insert(z);
                }
            }else if(key>z.getKey()) {
                if(left==null) {
                    left = new BacktrackingBST.Node(z.getKey(),z.getValue());
                    left.parent = this;
                    return left;
                }else {
                    return left.insert(z);
                }
            }
            return null;

        }

        public String printPreOrder() {
            String str = ""+key;
            if(left!=null)
                str = str + " " + left.printPreOrder() ;
            if(right!=null)
                str = str + " " + right.printPreOrder() ;
            return str;
        }
        
        public void print() {
            System.out.println(printPreOrder());
        }
        
        private Node duplicate(){//assistance function to duplicate a node
            Node copy =new Node(this.key,this.value);
            copy.left=left;
            copy.right=right;
            copy.parent=parent;
            return copy;
        }
        
        public String toString() {
        	String str = toStringHelper(this,"0");
        	return str;
        }
        
        //assistance function for toString
        private String toStringHelper(Node curr, String indent) {
        	String indentation = "";
        	String LorR = "";
        	for(int i = 0; i<indent.length()-1;i=i+1) {
        		if(indent.charAt(i)=='1') {
        			indentation = indentation + "|   ";
        		}else {
        			indentation = indentation + "    ";
        		}
        	}//takes care of the indentation and "|" when required
        	if(indent.charAt(indent.length()-1)=='1') {
        		LorR = "|-- ";
        	}else {
        		LorR = "\\-- ";
        	}//takes care of the last-turn mark
        	String str = indentation + LorR + curr.key + "\n";
        	if(curr.left!=null) {
        		str = str + curr.toStringHelper(curr.left,indent + "1");//1 signs a turn right
        	}
        	if(curr.right!=null) {
        		str = str + curr.toStringHelper(curr.right, indent + "0");//0 signs a turn left
        	}   
        	return str;
        }
    }
}