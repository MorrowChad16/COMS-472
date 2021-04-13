import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    private static ArrayList<ComplexSentence> knowledgeBase;
    private static ArrayList<ComplexSentence> proveStatements;
    private static final int KB_SENTENCE = 0;
    private static final int PS_SENTENCE = 1;

    /**
     * @author Chad Morrow
     * @param args
     */
    public static void main(String[] args) {
        knowledgeBase = new ArrayList<>();
        proveStatements = new ArrayList<>();

        iterateInput();
        System.out.println();

        for(int i = 0; i < knowledgeBase.size(); i++){
            knowledgeBase.set(i, Converter.infixToPostfix(knowledgeBase.get(i)));
            System.out.println(knowledgeBase.get(i));
        }
        System.out.println();

        for(int i = 0; i < proveStatements.size(); i++){
            proveStatements.set(i, Converter.infixToPostfix(proveStatements.get(i)));
            System.out.println(proveStatements.get(i));
        }
        System.out.println();

        ExpressionTree expressionTree = new ExpressionTree();

        ArrayList<String> knowledgeBaseRoots = new ArrayList<>();
        ArrayList<String> proveStatementRoots = new ArrayList<>();

        System.out.println("Knowledge base in clauses:\n");
        int i = 0;
        for (ComplexSentence sentence : knowledgeBase) {
            Node node = expressionTree.constructTree(sentence);

            //if the top of the expression tree already has a CNF value then just pull it
            if(!node.nodeCNF.isEmpty()){
                knowledgeBaseRoots.add(node.nodeCNF);
            } else { //otherwise construct an expression tree, pull the CNF value, and split that value into separate clauses
                knowledgeBaseRoots.addAll(Arrays.asList(expressionTree.splitKB(expressionTree.constructCNFTree(node).nodeCNF)));
            }
            i++;
        }

        /**
         * print the knowledge base clauses
         */
        for(String el : knowledgeBaseRoots){
            System.out.println(el);
            System.out.println();
        }

        i = 0;
        for (ComplexSentence sentence : proveStatements) {
            Node node = expressionTree.constructTree(sentence);

            //if the top of the expression tree already has a CNF value then just pull it
            if(!node.nodeCNF.isEmpty()){
                proveStatementRoots.add(node.nodeCNF);
            } else { //otherwise construct an expression tree, pull the CNF value, and split that value into separate clauses
                proveStatementRoots.addAll(Arrays.asList(expressionTree.splitKB(expressionTree.constructCNFTree(node).nodeCNF)));
            }

            System.out.println("****************");
            System.out.println("Goal sentence " + (i + 1) + ":\n");
            System.out.println(proveStatementRoots.get(i));
            System.out.println("****************\n");
            System.out.println("Negated goal in clauses:\n");
            System.out.println(expressionTree.evalNegation(proveStatementRoots.get(i)) + "\n");
            System.out.println("Proof by refutation:\n");
            if(expressionTree.refute(expressionTree.evalNegation(proveStatementRoots.get(i)), knowledgeBaseRoots)){
                System.out.println("The KB entails " + proveStatementRoots.get(i) + ".");
            } else {
                System.out.println("The KB does not entail " + proveStatementRoots.get(i) + ".");
            }
            i++;
        }
    }

    /**
     * iterate through the input from the text file
     */
    private static void iterateInput(){
        boolean isReadingKnowledgeBase = false;
        boolean isReadingProveStatements = false;

        String[] kb = readInput().split("\\r?\\n");
        for(String sentence : kb){
            if(sentence.contains("Knowledge Base:")){
                isReadingKnowledgeBase = true;
                isReadingProveStatements = false;
            } else if(sentence.contains("Prove the following sentences by refutation:")){
                isReadingKnowledgeBase = false;
                isReadingProveStatements = true;
            } else {
                if(isReadingKnowledgeBase && !sentence.isEmpty()) {
                    knowledgeBase.add(evalSentence(sentence));
                } else if(isReadingProveStatements && !sentence.isEmpty()){
                    proveStatements.add(evalSentence(sentence));
                }
            }
        }
    }

    /**
     * parse the "kb.txt" input
     * MUST FOLLOW THE SAME FORMAT AS THE KB.TXT FILE I HAVE WRITTEN
     */
    private static String readInput() {
        String kbString = "";
        File kbFile = new File("kb.txt");
        if(kbFile.canRead()){
            try {
                FileInputStream fileInputStream = new FileInputStream(kbFile);

                int c;
                while((c = fileInputStream.read()) != -1){
                    kbString += (char) c;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return kbString;
    }


    /**
     * @param sentence the text sentence we want to evaluate
     * @return the new complex sentence containing the string sentence components
     */
    private static ComplexSentence evalSentence(String sentence){
        ComplexSentence complexSentence = new ComplexSentence();
        String[] sentenceParsed = sentence.split(" ");

        for(String section : sentenceParsed){
            //if the statement has a not in front of it separate it
            if(section.charAt(0) == '~'){
                complexSentence.addToSentence(section.substring(0,1));
                complexSentence.addToSentence(section.substring(1));
            } else {
                complexSentence.addToSentence(section);
            }

        }

        System.out.println(complexSentence);
        return complexSentence;
    }
}
