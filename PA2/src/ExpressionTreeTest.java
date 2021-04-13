import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionTreeTest {
    @Test
    void testConstructingTree(){
        ExpressionTree expressionTree = new ExpressionTree();

        /**
         * create sentence
         */
        ComplexSentence complexSentence = new ComplexSentence();
        complexSentence.addToSentence("P");
        complexSentence.addToSentence("Q");
        complexSentence.addToSentence("~");
        complexSentence.addToSentence("&&");
        complexSentence.addToSentence("~");
        complexSentence.addToSentence("R");
        complexSentence.addToSentence("||");
        complexSentence.addToSentence("S");
        complexSentence.addToSentence("T");
        complexSentence.addToSentence("~");
        complexSentence.addToSentence("&&");
        complexSentence.addToSentence("=>");

        Node root = expressionTree.constructTree(complexSentence);

        assertEquals(new Node("=>").value, root.value);
        assertEquals(new Node("&&").value, root.right.value);
        assertEquals(new Node("~").value, root.right.right.value);
        assertEquals(new Node("T").value, root.right.right.right.value);
        assertEquals(new Node("S").value, root.right.left.value);
        assertEquals(new Node("||").value, root.left.value);
        assertEquals(new Node("R").value, root.left.right.value);
        assertEquals(new Node("~").value, root.left.left.value);
        assertEquals(new Node("&&").value, root.left.left.left.value);
        assertEquals(new Node("~").value, root.left.left.left.right.value);
        assertEquals(new Node("Q").value, root.left.left.left.right.right.value);
        assertEquals(new Node("P").value, root.left.left.left.left.value);
    }

    @Test
    void testCNFNegation(){
        ExpressionTree expressionTree = new ExpressionTree();

        ComplexSentence complexSentence = new ComplexSentence();
        complexSentence.addToSentence("P");
        complexSentence.addToSentence("&&");
        complexSentence.addToSentence("~");
        complexSentence.addToSentence("Q");

        assertEquals("~P || Q", expressionTree.evalNegation("P && ~Q"));
        assertEquals("S && ~T", expressionTree.evalAnd("S", "~T"));
        assertEquals("~P || Q || R", expressionTree.evalOr("~P || Q", "R"));
        assertEquals("( P || S ) && ( P || ~T ) && ( ~Q || S ) && ( ~Q || ~T ) && ( ~R || S ) && ( ~R || ~T )",
                expressionTree.evalImplies("~P || Q || R", "S && ~T"));
        System.out.println(expressionTree.evalIff("~P || Q || R", "S && ~T"));
    }
}