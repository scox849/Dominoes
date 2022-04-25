package dominoes;

import java.util.*;


/**
 * Board class contains methods and constructor
 * for the board.
 * @author Sam Cox
 * @version date 2/14/20
 */
public class Board {

    private List<Dominoe> board;

    /**
     * Board constructor initializes linked
     * list used to hold the dominoes on the board.
     */
    public Board(){

        board = new LinkedList<>();
    }

    /**
     * Places dominoe on the left side of the board.
     * @param dominoe to be placed
     */
    protected void placeOnLeft(Dominoe dominoe){
        this.board.add(0,dominoe);
    }

    /**
     * Places dominoe on the right side of the board.
     * @param dominoe to be placed
     */
    protected void placeOnRight(Dominoe dominoe){

        this.board.add(dominoe);
    }

    /**
     * Returns a reference to the dominoe on the left side of the board.
     * @return dominoe on left
     */
    protected Dominoe getLeft(){
        return this.board.get(0);
    }

    /**
     * Returns a reference to the dominoe on the right side of the board.
     * @return dominoe on right
     */
    protected Dominoe getRight(){

        return this.board.get(this.board.size() - 1);
    }

    /**
     * Prints the board to the console in the proper format.
     * Used for console version only.
     */
    protected void printBoard(){

        if(board.size() > 0){
            Dominoe firstDom = board.get(0);
            if(!firstDom.getPosition()){
                System.out.print("   ");
            }
            for (Dominoe dominoe : this.board) {
                if(dominoe.getPosition())
                {
                    System.out.print("[" + dominoe.getLeftVal() + ", "
                            + dominoe.getRightVal() + "] ");
                }

            }
            System.out.println();
            if(firstDom.getPosition()){
                System.out.print("   ");
            }
            for (Dominoe dominoe : this.board) {
                if(!dominoe.getPosition())
                {
                    System.out.print("[" + dominoe.getLeftVal() + ", "
                            + dominoe.getRightVal() + "] ");
                }

            }
        }
    }

    /**
     * Returns a reference to the board.
     * @return the board.
     */
    protected List<Dominoe> getBoard(){
        return this.board;
    }
}
