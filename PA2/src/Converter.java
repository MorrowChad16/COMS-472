
/* Java implementation to convert
 infix expression to postfix*/
// Note that here we use Stack class for Stack operations

import java.util.ArrayList;
import java.util.Stack;

/**
 * @author Chad Morrow
 */
class Converter {
    // A utility function to return
    // precedence of a given operator
    // Higher returned value means
    // higher precedence
    static int Prec(String ch) {
        switch (ch) {
            case "~":
                return 5;
            case "&&":
                return 4;
            case "||":
                return 3;
            case "=>":
                return 2;
            case "<=>":
                return 1;
        }
        return -1;
    }

    /**
     * @param s the string to view
     * @return whether the string contains an operator
     */
    public static boolean isOperator(String s){
        return !s.equals("~") && !s.equals("&&") && !s.equals("||") && !s.equals("=>")
                && !s.equals("<=>") && !s.equals("(") && !s.equals(")");
    }

    /**
     * @param s the string to view
     * @return whether the string contains an operator MINUS ~
     */
    public static boolean isOperatorStrict(String s){
        return s.equals("&&") || s.equals("||") || s.equals("=>")
                || s.equals("<=>") || s.equals("(") || s.equals(")");
    }

    /**
     * @param exp to convert from infix to postfix
     * @return the postfix form of the sentence
     */
    // The main method that converts
    // given infix expression
    // to postfix expression.
    public static ComplexSentence infixToPostfix(ComplexSentence exp) {
        // initializing empty String for result
        ComplexSentence result = new ComplexSentence();

        // initializing empty stack
        Stack<String> stack = new Stack<>();

        /** an operator is encountered*/
        for (int i = 0; i < exp.getSentence().size(); i++) {
            String c = exp.getSentence().get(i);

            // If the scanned character is an
            // operand, add it to output.
            if (isOperator(c))
                result.getSentence().add(c);

                // If the scanned character is an '(',
                // push it to the stack.
            else if (c.equals("("))
                stack.push(c);

                //  If the scanned character is a ')',
                // pop and output from the stack
                // until an '(' is encountered.
            else if (c.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    result.getSentence().add(stack.pop());
                }
                stack.pop();
            } else /** an operator is encountered*/ {
                while (!stack.isEmpty() && Prec(c) <= Prec(stack.peek())) {
                    result.getSentence().add(stack.pop());
                }
                stack.push(c);
            }
        }

        // pop all the operators from the stack
        while (!stack.isEmpty()){
            if(stack.peek().equals("(")) {
                System.out.println("Invalid Expression");
                return null;
            }
            result.getSentence().add(stack.pop());
        }
        return result;
    }
}
