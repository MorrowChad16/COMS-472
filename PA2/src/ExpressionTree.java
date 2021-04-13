// Java program to construct an expression tree
import java.util.ArrayList;
import java.util.Stack;

/**
 * @author Chad Morrow
 */
// Java program for expression tree
class Node {
    String value;
    Node left, right, parent;
    String nodeCNF;

    Node(String item) {
        value = item;
        left = right = parent = null;
        nodeCNF = "";
    }

    @Override
    public String toString() {
        return nodeCNF;
    }
}

/**
 * @author Chad Morrow
 */
class ExpressionTree {
    // Utility function to do inorder traversal
    void inorder(Node t) {
        if (t != null) {
            inorder(t.left);
            System.out.print(t.value + " ");
            inorder(t.right);
        }
    }

    /**
     * @param sentence the sentence to construct a tree with
     * @return the root node of the constructed tree
     */
    // Returns root of constructed tree for given
    // postfix expression
    public Node constructTree(ComplexSentence sentence) {
        Stack<Node> st = new Stack<>();
        Node root, parent;

        /**
         * set up stack with all the values in postfix format
         */
        for(int i = 0; i < sentence.getSentence().size(); i++){
            st.push(new Node(sentence.getSentence().get(i)));
        }

        /**
         * assign the top of the stack as the root
         */
        parent = root = st.pop();

        if(root.value.equals("~")){
            root.right = st.pop();
            root.right.parent = root;
            root.right.nodeCNF = root.right.value;
            root.nodeCNF = "~" + root.right.nodeCNF;
            return root;
        }

        /**
         * Traverse through every character of
         * input expression starting at the end
         */
        while (!st.empty()) {
            /**
             * if the top of the stack is an operator then simply add it to the tree at the first open position
             * right to left
             */
            if(Converter.isOperatorStrict(st.peek().value)){
                /** if the parent has no available open spots then reset it as the root*/
                if(parent.right != null && parent.left != null){
                    parent = root;
                }

                /** assign the values to the correct child location*/
                if(parent.right == null){
                    parent.right = new Node(st.pop().value);
                    parent.right.parent = parent;
                    parent = parent.right;
                } else {
                    parent.left = new Node(st.pop().value);
                    parent.left.parent = parent;
                    parent = parent.left;
                }
            } else { /** if the top of the stack is not an operator then check if it is
             a negation symbol otherwise add it to the tree*/
                if(parent.right == null){
                    parent.right = new Node(st.pop().value);
                    parent.right.parent = parent;

                    /** deals with negation*/
                    if(parent.right.value.equals("~")){
                        parent.right.right = new Node(st.pop().value);
                        parent.right.right.parent = parent.right;
                        /** if the value after the negation is an operator assign the parent to it*/
                        if(Converter.isOperatorStrict(parent.right.right.value)){
                            parent = parent.right.right;
                        } else {
                            //leaf node, so the CNF is just the nodes value
                            parent.right.right.nodeCNF = parent.right.right.value;
                            //negation node, so the CNF is the negation of it's only child
                            parent.right.nodeCNF = parent.right.value + parent.right.right.nodeCNF;
                        }
                    } else { /** leaf node, so the CNF is just the nodes value*/
                        parent.right.nodeCNF = parent.right.value;
                    }
                } else {
                    parent.left = new Node(st.pop().value);
                    parent.left.parent = parent;
                    /** deals with negation*/
                    if(parent.left.value.equals("~")){
                        parent.left.left = new Node(st.pop().value);
                        parent.left.left.parent = parent.left;
                        /** if the value after the negation is an operator assign the parent to it*/
                        if(Converter.isOperatorStrict(parent.left.left.value)){
                            parent = parent.left.left;
                        } else {
                            //leaf node, so the CNF is just the nodes value
                            parent.left.left.nodeCNF = parent.left.left.value;
                            //negation node, so the CNF is the negation of it's only child
                            parent.left.nodeCNF = parent.left.value + parent.left.left.nodeCNF;
                        }
                    } else { /** leaf node, so the CNF is just the nodes value*/
                        parent.left.nodeCNF = parent.left.value;
                    }
                }
            }
        }

        return root;
    }

    /**
     * @param node the root node of the expression tree
     * @return the root node of the CNF tree
     */
    public Node constructCNFTree(Node node){
        if(node.right != null){
            if(node.right.nodeCNF.isEmpty()){
                constructCNFTree(node.right);
            } else if(node.left.nodeCNF.isEmpty() && !node.value.equals("~")){
                constructCNFTree(node.left);
            } else {
                if(node.value.equals("&&")){
                    node.nodeCNF = evalAnd(node.left.nodeCNF, node.right.nodeCNF);
                } else if(node.value.equals("||")){
                    node.nodeCNF = evalOr(node.left.nodeCNF, node.right.nodeCNF);
                } else if(node.value.equals("~")){
                    node.nodeCNF = evalNegation(node.right.nodeCNF);
                } else if(node.value.equals("=>")){
                    node.nodeCNF = evalImplies(node.left.nodeCNF, node.right.nodeCNF);
                } else if(node.value.equals("<=>")){
                    node.nodeCNF = evalIff(node.left.nodeCNF, node.right.nodeCNF);
                }

                if(node.parent == null){
                    return node;
                }

                constructCNFTree(node.parent);
            }
        } else if(node.left != null){
            if(node.left.nodeCNF.isEmpty()){
                constructCNFTree(node.left);
            } else if(node.right == null && !node.value.equals("~")) {
                constructCNFTree(node.right);
            } else {
                if(node.value.equals("&&")){
                    node.nodeCNF = evalAnd(node.left.nodeCNF, node.right.nodeCNF);
                } else if(node.value.equals("||")){
                    node.nodeCNF = evalOr(node.left.nodeCNF, node.right.nodeCNF);
                } else if(node.value.equals("~")){
                    node.nodeCNF = evalNegation(node.left.nodeCNF);
                } else if(node.value.equals("=>")){
                    node.nodeCNF = evalImplies(node.left.nodeCNF, node.right.nodeCNF);
                } else if(node.value.equals("<=>")){
                    node.nodeCNF = evalIff(node.left.nodeCNF, node.right.nodeCNF);
                }

                if(node.parent == null){
                    return node;
                }

                constructCNFTree(node.parent);
            }
        } else {
            node.nodeCNF = node.value;
        }

        return node;
    }

    /**
     * @param equationLeft left child
     * @param equationRight right child
     * @return the AND constructed string
     */
    public String evalAnd(String equationLeft, String equationRight){
        return equationLeft + " && " + equationRight;
    }

    /**
     * @param equationLeft left child
     * @param equationRight right child
     * @return the OR constructed string
     */
    public String evalOr(String equationLeft, String equationRight){
        return equationLeft + " || " + equationRight;
    }

    /**
     * @param equation the current equation
     * @return the new negated equation
     */
    public String evalNegation(String equation){
        StringBuilder newSentence = new StringBuilder();
        String[] vals = equation.split(" ");

        for(String val : vals){
            if(val.equals("&&")){
                newSentence.append(" || ");
            } else if(val.equals("||")){
                newSentence.append(" && ");
            } else {
                if(val.contains("~")){
                    newSentence.append(val.substring(1));
                } else {
                    newSentence.append("~").append(val);
                }
            }
        }

        return newSentence.toString();
    }

    /**
     * @param equationLeft left child
     * @param equationRight right child
     * @return the new CNF implies equation
     */
    public String evalImplies(String equationLeft, String equationRight){
        String newSentence = "";

        equationLeft = evalNegation(equationLeft);
        String[] splitLeft = equationLeft.split(" ");
        String[] splitRight = equationRight.split(" ");

        for (String l : splitLeft) {
            if(!Converter.isOperatorStrict(l)){
                for (String r : splitRight) {
                    if(!Converter.isOperatorStrict(r)){
                        newSentence += " && ( " + l + " || " + r + " )";
                    }
                }
            }
        }

        return newSentence.substring(4);
    }

    /**
     * @param equationA left child
     * @param equationB right child
     * @return the new CNF iff equation
     */
    public String evalIff(String equationA, String equationB){
        String newSentence = "";

        newSentence = "( " + evalImplies(equationA, equationB) + " )" + " && " + "( " + evalImplies(equationB, equationA) + " )";

        return newSentence;
    }

    /**
     * @param kbCommand the knowledge base command
     * @return the knowledge base command split into components
     */
    public String[] splitKB(String kbCommand){
        //trims off parentheses
        for(int i = 0; i < kbCommand.length(); i++){
            if(kbCommand.charAt(i) == '('){
                kbCommand = kbCommand.substring(0, kbCommand.indexOf('(')) +
                        kbCommand.substring(kbCommand.indexOf('(') + 1);
            } else if(kbCommand.charAt(i) == ')') {
                kbCommand = kbCommand.substring(0, kbCommand.indexOf(')')) +
                        kbCommand.substring(kbCommand.indexOf(')') + 1);
            }
        }

        //splits the clauses based on the and symbol
        String[] newKBCommand = kbCommand.trim().split("&&");

        //trim excess whitespace off of split clauses
        for(int i = 0; i < newKBCommand.length; i++){
            newKBCommand[i] = newKBCommand[i].trim();
        }

        return newKBCommand;
    }

    /**
     * @param statement the statement to refute
     * @param kb the knowledge base components
     * @return whether we were able to refute the statement or not
     */
    public boolean refute(String statement, ArrayList<String> kb){
        boolean isChanged = false;
        String currentSentence = statement;

        while(true) {
            String initSentence = currentSentence;
            //split statement into separate pieces
            String negateCurrentStatement[] = evalNegation(currentSentence).split("\\|\\|");
            for (int i = 0; i < negateCurrentStatement.length; i++) {
                negateCurrentStatement[i] = negateCurrentStatement[i].trim();
            }

            //split the kb clauses into separate pieces
            ArrayList<ArrayList<String>> kbComponents = new ArrayList<>();
            for (String el : kb) {
                ArrayList<String> temp = new ArrayList<>();
                String[] tempSplit = el.split("\\|\\|");

                for (String splits : tempSplit) {
                    temp.add(splits.trim());
                }

                kbComponents.add(temp);
            }

            //check if the knowledge base contains a clause with only one variable, causing it to go true right away
            for (ArrayList<String> clause : kbComponents) {
                for (String negateStatement : negateCurrentStatement) {
                    if (clause.contains(negateStatement) && clause.size() == 1) {
                        System.out.println(currentSentence);
                        System.out.println(clause.get(0));
                        System.out.println("--------------------");
                        System.out.println("empty clause\n");
                        return true;
                    }
                }
            }

            //loop through the knowledge base
            for (int i = 0; i < kbComponents.size() && !isChanged; i++) {
                for (int j = 0; j < negateCurrentStatement.length && !isChanged; j++) {
                    //if the knowledge base clause contains the negation of our prove statement then we can use it to refute
                    if (kbComponents.get(i).contains(negateCurrentStatement[j])) {
                        System.out.println(currentSentence);

                        String temp = "";
                        //add all the non-negateStatement variables to the new sentence
                        for (String s : kbComponents.get(i)) {
                            temp += s + " || ";
                        }

                        System.out.println(temp.substring(0, temp.length() - 4));

                        currentSentence = "";

                        //add all the non-negateStatement variables to the new sentence
                        for (String s : kbComponents.get(i)) {
                            if (!s.equals(negateCurrentStatement[j])) {
                                currentSentence += s + " || ";
                            }
                        }

                        //add all the non-negateStatement variables to the new sentence
                        for (String s : negateCurrentStatement) {
                            if (!s.equals(negateCurrentStatement[j])) {
                                currentSentence += s + " || ";
                            }
                        }

                        currentSentence = currentSentence.substring(0, currentSentence.length() - 4);

                        System.out.println("--------------------");
                        System.out.println(currentSentence + "\n");
                        isChanged = true;
                    }
                }
            }

            if (currentSentence.equals(initSentence)) {
                System.out.println("No new clauses are added.\n");
                //if we got through the knowledge base without trimming anything then we can't refute it
                return false;
            }
            isChanged = false;
        }
    }
}

