package edu.iastate.cs472.proj1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AlphaBetaSearch {
    private CheckersData board;
    // Initial values of
    // Aplha and Beta
    static double alpha = Double.NEGATIVE_INFINITY;
    static double beta = Double.POSITIVE_INFINITY;
    static int depth = 10;

    static final int
            RED = 1,
            RED_KING = 2,
            BLACK = 3,
            BLACK_KING = 4;

    // An instance of this class will be created in the Checkers.Board
    // It would be better to keep the default constructor.

    public void setCheckersData(CheckersData board) {
        this.board = board;
    }

    private int utility(int[][] board) {
        int evaluationValue = 0;
        int kingValue = 4;
        int humanValue = 1;
        int otherHalf = 0;
        int oneFromKing = 0;

        //-1: red human
        //-3: red king
        //+1: black human
        //+3: black king
        //+1/-1: on enemies half of board and human
        //+2/-2: on enemies second-to-last row and human
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j] == BLACK){
                    evaluationValue += humanValue;
                    if(i < 4 && i > 1){
                        evaluationValue += otherHalf;
                    } else if(i == 1){
                        evaluationValue += oneFromKing;
                    }
                } else if(board[i][j] == BLACK_KING){
                    evaluationValue -= kingValue;
                } else if(board[i][j] == RED){
                    evaluationValue += humanValue;
                    if(i > 3 && i < 6){
                        evaluationValue -= otherHalf;
                    } else if(i == 6){
                        evaluationValue -= oneFromKing;
                    }
                } else if(board[i][j] == RED_KING){
                    evaluationValue += kingValue;
                }
            }
        }

        return evaluationValue;
    }

    // Returns optimal value for
    // current player(Initially called
    // for root and maximizer)
    private double minimax(CheckersData board, int depth, Boolean maximizingPlayer, double alpha, double beta) {
        //reached our depth limit so return the eval function
        if(depth == 0){
            return utility(board.board);
        }

        CheckersMove[] legalMoves;

        //clone of board for running future moves on
        CheckersData cloneBoard;
        double best;

        //if we're the maximizing player (BLACK)
        if (maximizingPlayer) {
            legalMoves = board.getLegalMoves(BLACK);
            if(legalMoves == null){
                return utility(board.board);
            }

            best = Double.NEGATIVE_INFINITY;

            for (CheckersMove legalMove : legalMoves) {
                if(legalMove != null){
                    cloneBoard = new CheckersData();
                    setCheckersData(cloneBoard);
                    cloneBoard.makeMove(legalMove);

                    best = Math.max(best, minimax(cloneBoard, depth - 1, false, alpha, beta));
                    if (best >= beta)
                        return best;
                    alpha = Math.max(alpha, best);
                }
            }
        } else { //if we're the minimizing player (RED)\
            legalMoves = board.getLegalMoves(RED);
            if(legalMoves == null){
                return utility(board.board);
            }

            best = Double.POSITIVE_INFINITY;

            for (CheckersMove legalMove : legalMoves) {
                if(legalMove != null) {
                    cloneBoard = new CheckersData();
                    setCheckersData(cloneBoard);
                    cloneBoard.makeMove(legalMove);

                    best = Math.min(best, minimax(cloneBoard, depth - 1, true, alpha, beta));
                    if (best <= alpha)
                        return best;
                    beta = Math.min(beta, best);
                }
            }
        }
        return best;
    }

    /**
     *  You need to implement the Alpha-Beta pruning algorithm here to
     * find the best move at current stage.
     * The input parameter legalMoves contains all the possible moves.
     * It contains four integers:  fromRow, fromCol, toRow, toCol
     * which represents a move from (fromRow, fromCol) to (toRow, toCol).
     * It also provides a utility method `isJump` to see whether this
     * move is a jump or a simple move.
     *
     * @param legalMoves All the legal moves for the agent at current step.
     */
    public CheckersMove makeMove(CheckersMove[] legalMoves) {
        // The checker board state can be obtained from this.board,
        // which is a int 2D array. The numbers in the `board` are
        // defined as
        // 0 - empty square,
        // 1 - red man
        // 2 - red king
        // 3 - black man
        // 4 - black king
        System.out.println(board);
        System.out.println();

        if(legalMoves == null){
            return null;
        }

        List<Double> evals = new ArrayList<>();
        List<CheckersMove> legalMovesList = Arrays.asList(legalMoves);

        //run through future moves and add eval value to array
        for(CheckersMove legalMove : legalMoves) {
            if (legalMove != null) {
                CheckersData cloneBoard;
                cloneBoard = new CheckersData();
                setCheckersData(cloneBoard);
                cloneBoard.makeMove(legalMove);
                evals.add(minimax(cloneBoard, depth, false, alpha, beta));
            }
        }


        System.out.println(evals);

        double maxValue = Double.NEGATIVE_INFINITY;
        //find the maximum value from our searching
        for (Double eval : evals) {
            if (eval >= maxValue) {
                maxValue = eval;
            }
        }

        System.out.println("Max Value: " + maxValue);

        List<CheckersMove> bestMoves = new ArrayList<>();
        //trim the list down to only the best moves
        for(int i = 0; i < evals.size(); i++){
            if(evals.get(i) == maxValue){
//                return legalMovesList.get(i);
                bestMoves.add(legalMovesList.get(i));
            }
        }

        System.out.println(bestMoves);

//        return legalMoves[0];

        Random rand = new Random();
        //if we have the same max value possibility then return any one of them
        // Todo: return the move for the current state
        return bestMoves.get(rand.nextInt(bestMoves.size()));
    }
}
