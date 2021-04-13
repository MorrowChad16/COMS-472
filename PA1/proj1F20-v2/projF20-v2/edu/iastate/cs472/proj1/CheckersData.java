package edu.iastate.cs472.proj1;

import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * An object of this class holds data about a game of checkers.
 * It knows what kind of piece is on each square of the checkerboard.
 * Note that RED moves "up" the board (i.e. row number decreases)
 * while BLACK moves "down" the board (i.e. row number increases).
 * Methods are provided to return lists of available legal moves.
 */
public class CheckersData {

  /*  The following constants represent the possible contents of a square
      on the board.  The constants RED and BLACK also represent players
      in the game. */

    static final int
            EMPTY = 0,
            RED = 1,
            RED_KING = 2,
            BLACK = 3,
            BLACK_KING = 4;


    int[][] board;  // board[r][c] is the contents of row r, column c.

    /**
     * Constructor.  Create the board and set it up for a new game.
     */
    CheckersData() {
        board = new int[8][8];
        setUpGame();
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < board.length; i++) {
            int[] row = board[i];
            sb.append(8 - i).append(" ");
            for (int n : row) {
                if (n == 0) {
                    sb.append(" ");
                } else if (n == 1) {
                    sb.append(ANSI_RED + "R" + ANSI_RESET);
                } else if (n == 2) {
                    sb.append(ANSI_RED + "K" + ANSI_RESET);
                } else if (n == 3) {
                    sb.append(ANSI_YELLOW + "B" + ANSI_RESET);
                } else if (n == 4) {
                    sb.append(ANSI_YELLOW + "K" + ANSI_RESET);
                }
                sb.append(" ");
            }
            sb.append(System.lineSeparator());
        }
        sb.append("  a b c d e f g h");

        return sb.toString();
    }

    //TODO flip red and black again

    /**
     * Set up the board with checkers in position for the beginning
     * of a game.  Note that checkers can only be found in squares
     * that satisfy  row % 2 == col % 2.  At the start of the game,
     * all such squares in the first three rows contain black squares
     * and all such squares in the last three rows contain red squares.
     */
    void setUpGame() {
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(i < 3 && (i % 2 == j % 2)){
                    board[i][j] = BLACK;
                } else if (i > 4 && (i % 2 == j % 2)) {
                    board[i][j] = RED;
                } else  {
                    board[i][j] = EMPTY;
                }
            }
        }
    }


    /**
     * Return the contents of the square in the specified row and column.
     */
    int pieceAt(int row, int col) {
        return board[row][col];
    }


    /**
     * Make the specified move.  It is assumed that move
     * is non-null and that the move it represents is legal.
     * @return  true if the piece becomes a king, otherwise false
     */
    boolean makeMove(CheckersMove move) {
        return makeMove(move.fromRow, move.fromCol, move.toRow, move.toCol);
    }

    /**
     * Make the move from (fromRow,fromCol) to (toRow,toCol).  It is
     * assumed that this move is legal.  If the move is a jump, the
     * jumped piece is removed from the board.  If a piece moves to
     * the last row on the opponent's side of the board, the
     * piece becomes a king.
     *
     * @param fromRow row index of the from square
     * @param fromCol column index of the from square
     * @param toRow   row index of the to square
     * @param toCol   column index of the to square
     * @return        true if the piece becomes a king, otherwise false
     */
    boolean makeMove(int fromRow, int fromCol, int toRow, int toCol) {
        // You need to take care of the following situations:
        // 1. move the piece from (fromRow,fromCol) to (toRow,toCol)
        // 2. if this move is a jump, remove the captured piece
        // 3. if the piece moves into the kings row on the opponent's side of the board, crowned it as a king
        boolean turnedToKing = false;

        //move piece
        if(pieceAt(fromRow, fromCol) == BLACK){
            board[toRow][toCol] = BLACK;
            //check if BLACK has reached RED's starting row, making it a BLACK_KING
            if(toRow == 7) {
                board[toRow][toCol] = BLACK_KING;
                turnedToKing = true;
            }
        } else if(pieceAt(fromRow, fromCol) == BLACK_KING){
            board[toRow][toCol] = BLACK_KING;
        } else if(pieceAt(fromRow, fromCol) == RED){
            board[toRow][toCol] = RED;
            //check if RED has reached BLACK's starting row, making it a RED_KING
            if(toRow == 0) {
                board[toRow][toCol] = RED_KING;
                turnedToKing = true;
            }
        } else if(pieceAt(fromRow, fromCol) == RED_KING){
            board[toRow][toCol] = RED_KING;
        }

        board[fromRow][fromCol] = EMPTY;

        //check if that move was a jump
        if((Math.abs(toRow - fromRow) == 2) && (Math.abs(toCol - fromCol) == 2)){
            int removeRow;
            int removeCol;

            //moving down the board so we set the jumped row up one from old position
            if(toRow - fromRow == 2){
                removeRow = fromRow + 1;
            } else { //moving up the board so we set the jumped row down one from old position
                removeRow = fromRow - 1;
            }

            //moving right on the board so we set the jumped col right one from old position
            if(toCol - fromCol == 2){
                removeCol = fromCol + 1;
            } else { //moving left on the board so we set the jumped col left one from old position
                removeCol = fromCol - 1;
            }

            board[removeRow][removeCol] = EMPTY;
        }

        return turnedToKing;
    }

    /**
     * Return an array containing all the legal CheckersMoves
     * for the specified player on the current board.  If the player
     * has no legal moves, null is returned.  The value of player
     * should be one of the constants RED or BLACK; if not, null
     * is returned.  If the returned value is non-null, it consists
     * entirely of jump moves or entirely of regular moves, since
     * if the player can jump, only jumps are legal moves.
     *
     * @param player color of the player, RED or BLACK
     */
    CheckersMove[] getLegalMoves(int player) {
        CheckersMove[] validMoves = new CheckersMove[1000];
        ArrayList<CheckersMove> validMovesList = new ArrayList<>();
        int size = 0;
        boolean isJumpsOnly = false;

        int[] validPieces = new int[2];
        if(player == BLACK){
            validPieces[0] = BLACK;
            validPieces[1] = BLACK_KING;
        } else if (player == RED){
            validPieces[0] = RED;
            validPieces[1] = RED_KING;
        }

        CheckersMove temp;
        //check which player you are evaluating
        //red
        //loop through every square on the board
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                //if the current square is occupied by red then check the possible moves for that piece
                if(pieceAt(i, j) == validPieces[0] || pieceAt(i, j) == validPieces[1]){
                    if(player == BLACK){
                        /*Basic Moves*/
                        //check for the boundaries of the board
                        if(!isJumpsOnly) {
                            if((temp = checkMoveDownLeft(i, j)) != null){
                                validMoves[size] = temp;
                                size++;
                            }

                            if((temp = checkMoveDownRight(i, j)) != null){
                                validMoves[size] = temp;
                                size++;
                            }

                            //handle extra king cases
                            if(pieceAt(i, j) == BLACK_KING){
                                if((temp = checkMoveUpLeft(i, j)) != null){
                                    validMoves[size] = temp;
                                    size++;
                                }

                                if((temp = checkMoveUpRight(i, j)) != null){
                                    validMoves[size] = temp;
                                    size++;
                                }
                            }
                        }

                        CheckersMove[] jumpMoves;
                        if((jumpMoves = getLegalJumpsFrom(player, i, j)) != null){
                            if(size > 0 && !isJumpsOnly){
                                size = 0;
                            }
                            isJumpsOnly = true;
                            validMoves = new CheckersMove[1000];
                            int index = 0;
                            for (CheckersMove jumpMove : jumpMoves) {
                                if (jumpMove != null) {
                                    validMoves[index] = jumpMove;
                                    index++;
                                    size++;
                                }
                            }
                        }
                    } else if(player == RED) {
                        /*Basic Moves*/
                        //check for the boundaries of the board
                        if(!isJumpsOnly) {
                            if((temp = checkMoveUpLeft(i, j)) != null){
                                validMoves[size] = temp;
                                size++;
                            }

                            if((temp = checkMoveUpRight(i, j)) != null){
                                validMoves[size] = temp;
                                size++;
                            }

                            //handle extra king cases
                            if(pieceAt(i, j) == RED_KING){
                                if((temp = checkMoveDownLeft(i, j)) != null){
                                    validMoves[size] = temp;
                                    size++;
                                }

                                if((temp = checkMoveDownRight(i, j)) != null){
                                    validMoves[size] = temp;
                                    size++;
                                }
                            }
                        }

                        CheckersMove[] jumpMoves;
                        if((jumpMoves = getLegalJumpsFrom(player, i, j)) != null){
                            if(size > 0 && !isJumpsOnly){
                                size = 0;
                            }
                            isJumpsOnly = true;
                            validMoves = new CheckersMove[1000];

                            for (CheckersMove jumpMove : jumpMoves) {
                                    validMovesList.add(jumpMove);
                                    size++;
                            }
                        }
                    } else {
                        return null;
                    }
                }
            }
        }

        if(size == 0){
            return null;
        }

        if(validMovesList.size() != 0){
            for(int i = 0; i < validMovesList.size(); i++){
                validMoves[i] = validMovesList.get(i);
            }
        }

        return trimMoves(validMoves, size);
    }

    /**
     * Return a list of the legal jumps that the specified player can
     * make starting from the specified row and column.  If no such
     * jumps are possible, null is returned.  The logic is similar
     * to the logic of the getLegalMoves() method.
     *
     * @param player The player of the current jump, either RED or BLACK.
     * @param row    row index of the start square.
     * @param col    col index of the start square.
     */
    CheckersMove[] getLegalJumpsFrom(int player, int row, int col) {
        CheckersMove[] legalJumpMoves = new CheckersMove[1000];
        int size = 0;
        CheckersMove temp;
        int[] redPieces = new int[2];
        redPieces[0] = RED;
        redPieces[1] = RED_KING;
        int[] blackPieces = new int[2];
        blackPieces[0] = BLACK;
        blackPieces[1] = BLACK_KING;

        if(player == BLACK){
            /*Jumps*/
            //check for the boundaries of the board
            //if the the next corner piece is occupied then check if you can jump
            if((temp = checkJumpMoveDownLeft(row, col, redPieces)) != null){
                legalJumpMoves[size] = temp;
                size++;
            }

            //check for the boundaries of the board
            //if the the next corner piece is occupied then check if you can jump
            if((temp = checkJumpMoveDownRight(row, col, redPieces)) != null){
                legalJumpMoves[size] = temp;
                size++;
            }

            //handle extra king cases
            if(pieceAt(row, col) == BLACK_KING){
                //check for the boundaries of the board
                //if the the next corner piece is occupied then check if you can jump
                if((temp = checkJumpMoveUpLeft(row, col, redPieces)) != null){
                    legalJumpMoves[size] = temp;
                    size++;
                }

                //check for the boundaries of the board
                //if the the next corner piece is occupied then check if you can jump
                if((temp = checkJumpMoveUpRight(row, col, redPieces)) != null){
                    legalJumpMoves[size] = temp;
                    size++;
                }
            }
        } else if (player == RED) {
            /*Jumps*/
            //check for the boundaries of the board
            //if the the next corner piece is occupied then check if you can jump
            if((temp = checkJumpMoveUpLeft(row, col, blackPieces)) != null){
                legalJumpMoves[size] = temp;
                size++;
            }

            //check for the boundaries of the board
            //if the the next corner piece is occupied then check if you can jump
            if((temp = checkJumpMoveUpRight(row, col, blackPieces)) != null){
                legalJumpMoves[size] = temp;
                size++;
            }

            //handle extra king cases
            if(pieceAt(row, col) == RED_KING){
                //check for the boundaries of the board
                //if the the next corner piece is occupied then check if you can jump
                if((temp = checkJumpMoveDownLeft(row, col, blackPieces)) != null){
                    legalJumpMoves[size] = temp;
                    size++;
                }

                //check for the boundaries of the board
                //if the the next corner piece is occupied then check if you can jump
                if((temp = checkJumpMoveDownRight(row, col, blackPieces)) != null){
                    legalJumpMoves[size] = temp;
                    size++;
                }
            }
        } else {
            return null;
        }

//        System.out.println("Piece at (6, 4): " + pieceAt(6, 4));
//        System.out.println("Piece at (5, 3): " + pieceAt(5, 3));
//        System.out.println("Piece at (4, 2): " + pieceAt(4, 2));
//        System.out.println(size);
        if(size == 0){
            return null;
        }

        return trimMoves(legalJumpMoves, size);
    }

    private CheckersMove checkMoveDownLeft(int row, int col){
        if ((row + 1 < 8) && (col - 1 >= 0) && pieceAt(row + 1, col - 1) == EMPTY) {
            return new CheckersMove(row, col, row + 1, col - 1);
        }
        return null;
    }

    private CheckersMove checkMoveDownRight(int row, int col){
        if ((row + 1 < 8) && (col + 1 < 8) && pieceAt(row + 1, col + 1) == EMPTY) {
            return new CheckersMove(row, col, row + 1, col + 1);
        }
        return null;
    }

    private CheckersMove checkMoveUpLeft(int row, int col){
        if ((row - 1 >= 0) && (col - 1 >= 0) && pieceAt(row - 1, col - 1) == EMPTY) {
            return new CheckersMove(row, col, row - 1, col - 1);
        }
        return null;
    }

    private CheckersMove checkMoveUpRight(int row, int col){
        if ((row - 1 >= 0) && (col + 1 < 8) && pieceAt(row - 1, col + 1) == EMPTY) {
            return  new CheckersMove(row, col, row - 1, col + 1);
        }
        return null;
    }

    private CheckersMove checkJumpMoveDownLeft(int row, int col, int[] jumpColor){
        if((row + 1 < 8) && (col - 1 >= 0) && (pieceAt(row + 1, col - 1) == jumpColor[0] || pieceAt(row + 1, col - 1) == jumpColor[1])){
            //if the box two corners away is open then you can jump your opponent
            if((row + 2 < 8) && (col - 2 >= 0) && pieceAt(row + 2, col - 2) == EMPTY){
                //if the validMoves array contains non-jump moves then clear it
                return new CheckersMove(row, col, row + 2, col - 2);
            }
        }
        return null;
    }

    private CheckersMove checkJumpMoveDownRight(int row, int col, int[] jumpColor){
        if((row + 1 < 8) && (col + 1 < 8) && (pieceAt(row + 1, col + 1) == jumpColor[0] || pieceAt(row + 1, col + 1) == jumpColor[1])){
            //if the box two corners away is open then you can jump your opponent
            if((row + 2 < 8) && (col + 2 < 8) && pieceAt(row + 2, col + 2) == EMPTY){
                //if the validMoves array contains non-jump moves then clear it
                return new CheckersMove(row, col, row + 2, col + 2);
            }
        }
        return null;
    }

    private CheckersMove checkJumpMoveUpLeft(int row, int col, int[] jumpColor){
        if((row - 1 >= 0) && (col - 1 >= 0) && (pieceAt(row - 1, col - 1) == jumpColor[0] || pieceAt(row - 1, col - 1) == jumpColor[1])){
            //if the box two corners away is open then you can jump your opponent
            if((row - 2 >= 0) && (col - 2 >= 0) && pieceAt(row - 2, col - 2) == EMPTY){
                //if the validMoves array contains non-jump moves then clear it
                return new CheckersMove(row, col, row - 2, col - 2);
            }
        }
        return null;
    }

    private CheckersMove checkJumpMoveUpRight(int row, int col, int[] jumpColor){
        if((row - 1 >= 0) && (col + 1 < 8) && (pieceAt(row - 1, col + 1) == jumpColor[0] || pieceAt(row - 1, col + 1) == jumpColor[1])){
            //if the box two corners away is open then you can jump your opponent
            if((row - 2 >= 0) && (col + 2 < 8) && pieceAt(row - 2, col + 2) == EMPTY){
                //if the validMoves array contains non-jump moves then clear it
                return new CheckersMove(row, col, row - 2, col + 2);
            }
        }
        return null;
    }

    private CheckersMove[] trimMoves(CheckersMove[] orig, int size){
        CheckersMove[] trimmedMoves = new CheckersMove[size];

        for(int i = 0; i < size; i++){
            trimmedMoves[i] = orig[i];
        }

        return trimmedMoves;
    }
}