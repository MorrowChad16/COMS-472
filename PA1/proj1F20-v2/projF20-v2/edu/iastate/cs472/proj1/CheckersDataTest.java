package edu.iastate.cs472.proj1;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckersDataTest {
    static final int
            EMPTY = 0,
            RED = 1,
            RED_KING = 2,
            BLACK = 3,
            BLACK_KING = 4;

    @Test
    public void testSetUpGame(){
        CheckersData testClass = new CheckersData();
        testClass.setUpGame();

        //check middle section is empty
        for(int i = 3; i < 5; i++){
            for(int j = 0; j < 8; j++){
                assertEquals(EMPTY, testClass.board[i][j]);
            }
        }

        //check white board boxes are empty
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(i % 2 != j % 2) {
                    assertEquals(EMPTY, testClass.board[i][j]);
                }
            }
        }

        //check red's are in the starting position
        assertEquals(BLACK, testClass.board[0][0]);
        assertEquals(BLACK, testClass.board[0][2]);
        assertEquals(BLACK, testClass.board[0][4]);
        assertEquals(BLACK, testClass.board[0][6]);
        assertEquals(BLACK, testClass.board[1][1]);
        assertEquals(BLACK, testClass.board[1][3]);
        assertEquals(BLACK, testClass.board[1][5]);
        assertEquals(BLACK, testClass.board[1][7]);
        assertEquals(BLACK, testClass.board[2][0]);
        assertEquals(BLACK, testClass.board[2][2]);
        assertEquals(BLACK, testClass.board[2][4]);
        assertEquals(BLACK, testClass.board[2][6]);

        //check black's are in the starting position
        assertEquals(RED, testClass.board[5][1]);
        assertEquals(RED, testClass.board[5][3]);
        assertEquals(RED, testClass.board[5][5]);
        assertEquals(RED, testClass.board[5][7]);
        assertEquals(RED, testClass.board[6][0]);
        assertEquals(RED, testClass.board[6][2]);
        assertEquals(RED, testClass.board[6][4]);
        assertEquals(RED, testClass.board[6][6]);
        assertEquals(RED, testClass.board[7][1]);
        assertEquals(RED, testClass.board[7][3]);
        assertEquals(RED, testClass.board[7][5]);
        assertEquals(RED, testClass.board[7][7]);
    }

    @Test
    public void testSimpleMoveBlack(){
        CheckersData testClass = new CheckersData();
        CheckersMove[] testArray = new CheckersMove[4];
        testArray[0] = new CheckersMove(4, 3, 5, 2);
        testArray[1] = new CheckersMove(4, 3, 5, 4);
        testArray[2] = new CheckersMove(4, 4, 5, 3);
        testArray[3] = new CheckersMove(4, 4, 5, 5);

        //set up current player
        int player = BLACK;

        //set up pieces
        //RED piece by itself
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                if(i == 4 && j == 4) {
                    testClass.board[i][j] = player;
                } else if(i == 4 && j == 3) {
                    testClass.board[i][j] = player;
                } else {
                    testClass.board[i][j] = EMPTY;
                }
            }
        }

        List<CheckersMove> list = Arrays.asList(testArray);

//        for(int i = 0; i < testClass.getLegalMoves(player).length; i++){
//            System.out.println(testClass.getLegalMoves(player)[i].toString());
//        }

        assertEquals(testClass.getLegalMoves(player).length, testArray.length);
        assertTrue(list.contains(testArray[0]));
        assertTrue(list.contains(testArray[1]));
        assertTrue(list.contains(testArray[2]));
        assertTrue(list.contains(testArray[3]));
    }

    @Test
    public void testSimpleMoveBlackRight(){
        CheckersData testClass = new CheckersData();
        CheckersMove[] testArray = new CheckersMove[1];
        testArray[0] = new CheckersMove(4, 4, 5, 5);

        //set up current player
        int player = BLACK;

        //set up pieces
        //RED piece by itself
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                if(i == 4 && j == 4) {
                    testClass.board[i][j] = player;
                } else if(i == 5 && j == 3) {
                    testClass.board[i][j] = RED;
                } else if(i == 6 && j == 2) {
                    testClass.board[i][j] = RED;
                } else {
                    testClass.board[i][j] = EMPTY;
                }
            }
        }

        List<CheckersMove> list = Arrays.asList(testArray);

//        for(int i = 0; i < testClass.getLegalMoves(player).length; i++){
//            System.out.println(testClass.getLegalMoves(player)[i].toString());
//        }

        assertEquals(testClass.getLegalMoves(player).length, testArray.length);
        assertTrue(list.contains(testArray[0]));
    }

    @Test
    public void testSimpleMoveBlackLeft(){
        CheckersData testClass = new CheckersData();
        CheckersMove[] testArray = new CheckersMove[1];
        testArray[0] = new CheckersMove(4, 4, 5, 3);

        //set up current player
        int player = BLACK;

        //set up pieces
        //RED piece by itself
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                if(i == 4 && j == 4) {
                    testClass.board[i][j] = player;
                } else if(i == 5 && j == 5) {
                    testClass.board[i][j] = RED;
                } else if(i == 6 && j == 6) {
                    testClass.board[i][j] = RED;
                } else {
                    testClass.board[i][j] = EMPTY;
                }
            }
        }

        List<CheckersMove> list = Arrays.asList(testArray);

//        for(int i = 0; i < testClass.getLegalMoves(player).length; i++){
//            System.out.println(testClass.getLegalMoves(player)[i].toString());
//        }

        assertEquals(testClass.getLegalMoves(player).length, testArray.length);
        assertTrue(list.contains(testArray[0]));
    }

    @Test
    public void testMoveBlockedBlack(){
        CheckersData testClass = new CheckersData();

        //set up current player
        int player = BLACK;

        //set up pieces
        //RED piece by itself
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                if(i == 4 && j == 4) {
                    testClass.board[i][j] = player;
                } else if(i == 5 && j == 3) {
                    testClass.board[i][j] = RED;
                } else if(i == 6 && j == 2) {
                    testClass.board[i][j] = RED;
                } else if(i == 5 && j == 5) {
                    testClass.board[i][j] = RED;
                }else if(i == 6 && j == 6) {
                    testClass.board[i][j] = RED;
                } else {
                    testClass.board[i][j] = EMPTY;
                }
            }
        }

//        for(int i = 0; i < testClass.getLegalMoves(player).length; i++){
//            System.out.println(testClass.getLegalMoves(player)[i].toString());
//        }

        assertNull(testClass.getLegalMoves(player));
    }

    @Test
    public void testSimpleMoveRed(){
        CheckersData testClass = new CheckersData();
        CheckersMove[] testArray = new CheckersMove[2];
        testArray[0] = new CheckersMove(4, 4, 3, 3);
        testArray[1] = new CheckersMove(4, 4, 3, 5);

        //set up current player
        int player = RED;

        //set up pieces
        //RED piece by itself
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                if(i == 4 && j == 4) {
                    testClass.board[i][j] = player;
                } else {
                    testClass.board[i][j] = EMPTY;
                }
            }
        }

        List<CheckersMove> list = Arrays.asList(testArray);

//        for(int i = 0; i < testClass.getLegalMoves(player).length; i++){
//            System.out.println(testClass.getLegalMoves(player)[i].toString());
//        }

        assertEquals(testClass.getLegalMoves(player).length, testArray.length);
        assertTrue(list.contains(testArray[0]));
        assertTrue(list.contains(testArray[1]));
    }

    @Test
    public void testSimpleMoveRedRight(){
        CheckersData testClass = new CheckersData();
        CheckersMove[] testArray = new CheckersMove[1];
        testArray[0] = new CheckersMove(4, 4, 3, 5);

        //set up current player
        int player = RED;

        //set up pieces
        //RED piece by itself
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                if(i == 4 && j == 4) {
                    testClass.board[i][j] = player;
                } else if(i == 3 && j == 3) {
                    testClass.board[i][j] = BLACK;
                } else if(i == 2 && j == 2) {
                    testClass.board[i][j] = BLACK;
                } else {
                    testClass.board[i][j] = EMPTY;
                }
            }
        }

        List<CheckersMove> list = Arrays.asList(testArray);

//        for(int i = 0; i < testClass.getLegalMoves(player).length; i++){
//            System.out.println(testClass.getLegalMoves(player)[i].toString());
//        }

        assertEquals(testClass.getLegalMoves(player).length, testArray.length);
        assertTrue(list.contains(testArray[0]));
    }

    @Test
    public void testSimpleMoveRedLeft(){
        CheckersData testClass = new CheckersData();
        CheckersMove[] testArray = new CheckersMove[1];
        testArray[0] = new CheckersMove(4, 4, 3, 3);

        //set up current player
        int player = RED;

        //set up pieces
        //RED piece by itself
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                if(i == 4 && j == 4) {
                    testClass.board[i][j] = player;
                } else if(i == 3 && j == 5) {
                    testClass.board[i][j] = BLACK;
                } else if(i == 2 && j == 6) {
                    testClass.board[i][j] = BLACK;
                } else {
                    testClass.board[i][j] = EMPTY;
                }
            }
        }

        List<CheckersMove> list = Arrays.asList(testArray);

//        for(int i = 0; i < testClass.getLegalMoves(player).length; i++){
//            System.out.println(testClass.getLegalMoves(player)[i].toString());
//        }

        assertEquals(testClass.getLegalMoves(player).length, testArray.length);
        assertTrue(list.contains(testArray[0]));
    }

    @Test
    public void testMoveBlockedRed(){
        CheckersData testClass = new CheckersData();

        //set up current player
        int player = RED;

        //set up pieces
        //RED piece by itself
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                if(i == 4 && j == 4) {
                    testClass.board[i][j] = player;
                } else if(i == 3 && j == 3) {
                    testClass.board[i][j] = BLACK;
                } else if(i == 2 && j == 2) {
                    testClass.board[i][j] = BLACK;
                } else if(i == 3 && j == 5) {
                    testClass.board[i][j] = BLACK;
                } else if(i == 2 && j == 6) {
                    testClass.board[i][j] = BLACK;
                } else {
                    testClass.board[i][j] = EMPTY;
                }
            }
        }

//        for(int i = 0; i < testClass.getLegalMoves(player).length; i++){
//            System.out.println(testClass.getLegalMoves(player)[i].toString());
//        }

        assertNull(testClass.getLegalMoves(player));
    }

    @Test
    public void testJumpMoveBlack(){
        CheckersData testClass = new CheckersData();
        CheckersMove[] testArray = new CheckersMove[4];
        testArray[0] = new CheckersMove(2, 3, 4, 1);
        testArray[1] = new CheckersMove(2, 3, 4, 5);
        testArray[2] = new CheckersMove(4, 4, 6, 2);
        testArray[3] = new CheckersMove(4, 4, 6, 6);


        //set up current player
        int player = BLACK;

        //set up pieces
        //RED piece by itself
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                if(i == 4 && j == 4) {
                    testClass.board[i][j] = player;
                } else if(i == 2 && j == 3) {
                    testClass.board[i][j] = player;
                } else if(i == 5 && j == 3) {
                    testClass.board[i][j] = RED;
                } else if(i == 5 && j == 5) {
                    testClass.board[i][j] = RED;
                } else if(i == 3 && j == 2) {
                    testClass.board[i][j] = RED;
                } else if(i == 3 && j == 4) {
                    testClass.board[i][j] = RED;
                } else {
                    testClass.board[i][j] = EMPTY;
                }
            }
        }

        List<CheckersMove> list = Arrays.asList(testArray);

//        for(int i = 0; i < testClass.getLegalMoves(player).length; i++){
//            System.out.println(testClass.getLegalMoves(player)[i].toString());
//        }

        assertEquals(testArray.length, testClass.getLegalMoves(player).length);
        assertTrue(list.contains(testArray[0]));
        assertTrue(list.contains(testArray[1]));
        assertTrue(list.contains(testArray[2]));
        assertTrue(list.contains(testArray[3]));
    }

    @Test
    public void testJumpMoveRed(){
        CheckersData testClass = new CheckersData();
        CheckersMove[] testArray = new CheckersMove[2];
        testArray[0] = new CheckersMove(4, 4, 2, 2);
        testArray[1] = new CheckersMove(4, 4, 2, 6);

        //set up current player
        int player = RED;

        //set up pieces
        //RED piece by itself
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                if(i == 4 && j == 4) {
                    testClass.board[i][j] = player;
                } else if(i == 3 && j == 3) {
                    testClass.board[i][j] = BLACK;
                } else if(i == 3 && j == 5) {
                    testClass.board[i][j] = BLACK;
                } else {
                    testClass.board[i][j] = EMPTY;
                }
            }
        }

        List<CheckersMove> list = Arrays.asList(testArray);

//        for(int i = 0; i < testClass.getLegalMoves(player).length; i++){
//            System.out.println(testClass.getLegalMoves(player)[i].toString());
//        }

        assertEquals(testClass.getLegalMoves(player).length, testArray.length);
        assertTrue(list.contains(testArray[0]));
        assertTrue(list.contains(testArray[1]));
    }

    @Test
    public void testSimpleThenJumpBlack(){
        CheckersData testClass = new CheckersData();
        CheckersMove[] testArray = new CheckersMove[1];
        testArray[0] = new CheckersMove(4, 4, 6, 6);

        //set up current player
        int player = BLACK;

        //set up pieces
        //RED piece by itself
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                if(i == 4 && j == 4) {
                    testClass.board[i][j] = player;
                } else if(i == 5 && j == 5) {
                    testClass.board[i][j] = RED;
                } else {
                    testClass.board[i][j] = EMPTY;
                }
            }
        }

        List<CheckersMove> list = Arrays.asList(testArray);

//        for(int i = 0; i < testClass.getLegalMoves(player).length; i++){
//            System.out.println(testClass.getLegalMoves(player)[i].toString());
//        }

        assertEquals(testClass.getLegalMoves(player).length, testArray.length);
        assertTrue(list.contains(testArray[0]));
    }

    @Test
    public void testSimpleThenJumpRed(){
        CheckersData testClass = new CheckersData();
        CheckersMove[] testArray = new CheckersMove[1];
        testArray[0] = new CheckersMove(4, 4, 2, 2);

        //set up current player
        int player = RED;

        //set up pieces
        //RED piece by itself
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                if(i == 4 && j == 4) {
                    testClass.board[i][j] = player;
                } else if(i == 3 && j == 3) {
                    testClass.board[i][j] = BLACK;
                } else {
                    testClass.board[i][j] = EMPTY;
                }
            }
        }

        List<CheckersMove> list = Arrays.asList(testArray);

//        for(int i = 0; i < testClass.getLegalMoves(player).length; i++){
//            System.out.println(testClass.getLegalMoves(player)[i].toString());
//        }

        assertEquals(testClass.getLegalMoves(player).length, testArray.length);
        assertTrue(list.contains(testArray[0]));
    }

    @Test
    public void testEdgeOfGameBlackRight(){
        CheckersData testClass = new CheckersData();
        CheckersMove[] testArray = new CheckersMove[1];
        testArray[0] = new CheckersMove(2, 7, 3, 6);

        //set up current player
        int player = BLACK;

        //set up pieces
        //RED piece by itself
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                if(i == 2 && j == 7) {
                    testClass.board[i][j] = player;
                } else {
                    testClass.board[i][j] = EMPTY;
                }
            }
        }

        List<CheckersMove> list = Arrays.asList(testArray);

//        for(int i = 0; i < testClass.getLegalMoves(player).length; i++){
//            System.out.println(testClass.getLegalMoves(player)[i].toString());
//        }

        assertEquals(testClass.getLegalMoves(player).length, testArray.length);
        assertTrue(list.contains(testArray[0]));
    }

    @Test
    public void testEdgeOfGameBlackLeft(){
        CheckersData testClass = new CheckersData();
        CheckersMove[] testArray = new CheckersMove[1];
        testArray[0] = new CheckersMove(4, 0, 5, 1);

        //set up current player
        int player = BLACK;

        //set up pieces
        //RED piece by itself
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                if(i == 4 && j == 0) {
                    testClass.board[i][j] = player;
                } else {
                    testClass.board[i][j] = EMPTY;
                }
            }
        }

        List<CheckersMove> list = Arrays.asList(testArray);

//        for(int i = 0; i < testClass.getLegalMoves(player).length; i++){
//            System.out.println(testClass.getLegalMoves(player)[i].toString());
//        }

        assertEquals(testClass.getLegalMoves(player).length, testArray.length);
        assertTrue(list.contains(testArray[0]));
    }

    @Test
    public void testEdgeOfGameRedRight(){
        CheckersData testClass = new CheckersData();
        CheckersMove[] testArray = new CheckersMove[1];
        testArray[0] = new CheckersMove(5, 7, 4, 6);

        //set up current player
        int player = RED;

        //set up pieces
        //RED piece by itself
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                if(i == 5 && j == 7) {
                    testClass.board[i][j] = player;
                } else {
                    testClass.board[i][j] = EMPTY;
                }
            }
        }

        List<CheckersMove> list = Arrays.asList(testArray);

//        for(int i = 0; i < testClass.getLegalMoves(player).length; i++){
//            System.out.println(testClass.getLegalMoves(player)[i].toString());
//        }

        assertEquals(testClass.getLegalMoves(player).length, testArray.length);
        assertTrue(list.contains(testArray[0]));
    }

    @Test
    public void testEdgeOfGameRedLeft(){
        CheckersData testClass = new CheckersData();
        CheckersMove[] testArray = new CheckersMove[1];
        testArray[0] = new CheckersMove(5, 0, 4, 1);

        //set up current player
        int player = RED;

        //set up pieces
        //RED piece by itself
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                if(i == 5 && j == 0) {
                    testClass.board[i][j] = player;
                } else {
                    testClass.board[i][j] = EMPTY;
                }
            }
        }

        List<CheckersMove> list = Arrays.asList(testArray);

//        for(int i = 0; i < testClass.getLegalMoves(player).length; i++){
//            System.out.println(testClass.getLegalMoves(player)[i].toString());
//        }

        assertEquals(testClass.getLegalMoves(player).length, testArray.length);
        assertTrue(list.contains(testArray[0]));
    }

    @Test
    public void testSimpleMoveBlackKing(){
        CheckersData testClass = new CheckersData();
        CheckersMove[] testArray = new CheckersMove[4];
        testArray[0] = new CheckersMove(4, 4, 5, 3);
        testArray[1] = new CheckersMove(4, 4, 5, 5);
        testArray[2] = new CheckersMove(4, 4, 3, 3);
        testArray[3] = new CheckersMove(4, 4, 3, 5);

        //set up pieces
        //RED piece by itself
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                if(i == 4 && j == 4) {
                    testClass.board[i][j] = BLACK_KING;
                } else {
                    testClass.board[i][j] = EMPTY;
                }
            }
        }

        List<CheckersMove> list = Arrays.asList(testArray);

//        for(int i = 0; i < testClass.getLegalMoves(player).length; i++){
//            System.out.println(testClass.getLegalMoves(player)[i].toString());
//        }

        assertEquals(testClass.getLegalMoves(BLACK).length, testArray.length);
        assertTrue(list.contains(testArray[0]));
        assertTrue(list.contains(testArray[1]));
        assertTrue(list.contains(testArray[2]));
        assertTrue(list.contains(testArray[3]));
    }

    @Test
    public void testSimpleMoveRedKing(){
        CheckersData testClass = new CheckersData();
        CheckersMove[] testArray = new CheckersMove[4];
        testArray[0] = new CheckersMove(4, 4, 3, 3);
        testArray[1] = new CheckersMove(4, 4, 3, 5);
        testArray[2] = new CheckersMove(4, 4, 5, 3);
        testArray[3] = new CheckersMove(4, 4, 5, 5);

        //set up pieces
        //RED piece by itself
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                if(i == 4 && j == 4) {
                    testClass.board[i][j] = RED_KING;
                } else {
                    testClass.board[i][j] = EMPTY;
                }
            }
        }

        List<CheckersMove> list = Arrays.asList(testArray);

//        for(int i = 0; i < testClass.getLegalMoves(player).length; i++){
//            System.out.println(testClass.getLegalMoves(player)[i].toString());
//        }

        assertEquals(testClass.getLegalMoves(RED).length, testArray.length);
        assertTrue(list.contains(testArray[0]));
        assertTrue(list.contains(testArray[1]));
        assertTrue(list.contains(testArray[2]));
        assertTrue(list.contains(testArray[3]));
    }

    @Test
    public void testMakeMoveBlackOverRedLeft(){
        CheckersData testClass = new CheckersData();

        testClass.board[4][4] = BLACK;
        testClass.board[5][3] = RED;
        testClass.board[6][2] = EMPTY;

        testClass.makeMove(4, 4, 6, 2);

        assertEquals(EMPTY, testClass.board[4][4]);
        assertEquals(EMPTY, testClass.board[5][3]);
        assertEquals(BLACK, testClass.board[6][2]);
    }

    @Test
    public void testMakeMoveBlackOverRedRight(){
        CheckersData testClass = new CheckersData();

        testClass.board[4][4] = BLACK;
        testClass.board[5][5] = RED;
        testClass.board[6][6] = EMPTY;

        testClass.makeMove(4, 4, 6, 6);

        assertEquals(EMPTY, testClass.board[4][4]);
        assertEquals(EMPTY, testClass.board[5][5]);
        assertEquals(BLACK, testClass.board[6][6]);
    }

    @Test
    public void testMakeMoveRedOverBlackRight(){
        CheckersData testClass = new CheckersData();

        testClass.board[4][4] = RED;
        testClass.board[5][5] = BLACK;
        testClass.board[6][6] = EMPTY;

        testClass.makeMove(4, 4, 6, 6);

        assertEquals(EMPTY, testClass.board[4][4]);
        assertEquals(EMPTY, testClass.board[5][5]);
        assertEquals(RED, testClass.board[6][6]);
    }

    @Test
    public void testMakeMoveRedOverBlackLeft(){
        CheckersData testClass = new CheckersData();

        testClass.board[4][4] = RED;
        testClass.board[5][3] = BLACK;
        testClass.board[6][2] = EMPTY;

        testClass.makeMove(4, 4, 6, 2);

        assertEquals(EMPTY, testClass.board[4][4]);
        assertEquals(EMPTY, testClass.board[5][3]);
        assertEquals(RED, testClass.board[6][2]);
    }

    @Test
    public void testMakeMoveTurnToKingBlackRight(){
        CheckersData testClass = new CheckersData();

        testClass.board[5][4] = BLACK;
        testClass.board[6][5] = RED;
        testClass.board[7][6] = EMPTY;

        testClass.makeMove(5, 4, 7, 6);

//        for(int i = 0; i < testClass.getLegalMoves(player).length; i++){
//            System.out.println(testClass.getLegalMovaes(player)[i].toString());
//        }

        assertEquals(EMPTY, testClass.board[5][4]);
        assertEquals(EMPTY, testClass.board[6][5]);
        assertEquals(BLACK_KING, testClass.board[7][6]);
    }

    @Test
    public void testMakeMoveTurnToKingBlackLeft(){
        CheckersData testClass = new CheckersData();

        testClass.board[5][4] = BLACK;
        testClass.board[6][3] = RED;
        testClass.board[7][2] = EMPTY;

        testClass.makeMove(5, 4, 7, 2);

//        for(int i = 0; i < testClass.getLegalMoves(player).length; i++){
//            System.out.println(testClass.getLegalMovaes(player)[i].toString());
//        }

        assertEquals(EMPTY, testClass.board[5][4]);
        assertEquals(EMPTY, testClass.board[6][3]);
        assertEquals(BLACK_KING, testClass.board[7][2]);
    }

    @Test
    public void testMakeMoveTurnToKingRedLeft(){
        CheckersData testClass = new CheckersData();

        testClass.board[2][4] = RED;
        testClass.board[1][3] = BLACK;
        testClass.board[0][2] = EMPTY;

        testClass.makeMove(2, 4, 0, 2);

//        for(int i = 0; i < testClass.getLegalMoves(player).length; i++){
//            System.out.println(testClass.getLegalMovaes(player)[i].toString());
//        }

        assertEquals(EMPTY, testClass.board[2][4]);
        assertEquals(EMPTY, testClass.board[1][3]);
        assertEquals(RED_KING, testClass.board[0][2]);
    }

    @Test
    public void testMakeMoveTurnToKingRedRight(){
        CheckersData testClass = new CheckersData();

        testClass.board[2][4] = RED;
        testClass.board[1][5] = BLACK;
        testClass.board[0][6] = EMPTY;

        testClass.makeMove(2, 4, 0, 6);


        assertEquals(EMPTY, testClass.board[2][4]);
        assertEquals(EMPTY, testClass.board[1][5]);
        assertEquals(RED_KING, testClass.board[0][6]);
    }

    @Test
    public void testMakeMoveRedJumpBack(){
        CheckersData testClass = new CheckersData();

        //set up pieces
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                if(i == 4 && j == 6) {
                    testClass.board[i][j] = RED;
                } else if(i == 3 && j == 5) {
                    testClass.board[i][j] = BLACK;
                } else if(i == 1 && j == 3) {
                    testClass.board[i][j] = BLACK;
                } else if(i == 0 && j == 2) {
                    testClass.board[i][j] = BLACK;
                } else if(i == 1 && j == 5) {
                    testClass.board[i][j] = BLACK;
                } else if(i == 0 && j == 6) {
                    testClass.board[i][j] = BLACK;
                } else if(i == 3 && j == 3) {
                    testClass.board[i][j] = BLACK;
                } else {
                    testClass.board[i][j] = EMPTY;
                }
            }
        }

        assertEquals(new CheckersMove(4, 6, 2, 4).toString(), testClass.getLegalMoves(RED)[0].toString());
        testClass.makeMove(testClass.getLegalMoves(RED)[0]);
        assertNull(testClass.getLegalMoves(RED));
    }
}