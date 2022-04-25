package dominoes;

/**
 * ComputerPlayer class extends Player.
 * Contains methods and contructor for computer player.
 * @author Sam Cox
 * @version date 2/14/20
 */
public class ComputerPlayer extends Player{

    private boolean sidePlaced;

    /**
     * ComputerPlayer constructor calls super constructor with
     * a reference to the boneyard and a boolean to determine
     * what version of the game is being played.
     * @param boneyard reference to boneyard
     * @param csl true for console false for GUI
     */
    public ComputerPlayer(Boneyard boneyard, boolean csl){
        super(boneyard, csl);
    }

    /**
     * Makes the first legal move the computer can make.
     * Pulls from boneyard if it cant make a move and returns
     * false if no move was made.
     * @param board reference to board
     * @return true or false if move was made
     */
    protected boolean makeMove(Board board){
        Dominoe left = board.getLeft();
        Dominoe right = board.getRight();

        for(Dominoe d: super.getHand()){
            if(checkMatch(d,left,right,board)) {
                super.getHand().remove(d);
                return true;
            }
        }

        while(true){
            Dominoe newDominoe = super.getBoneyard().getDominoe();
            if(newDominoe == null){
                return false;
            }else{
                super.getHand().add(newDominoe);
                if(checkMatch(newDominoe,left,right,board)){
                    super.getHand().remove(newDominoe);
                    return true;
                }
            }
        }
    }

    /**
     * Returns the side the computer placed a dominoe on.
     * @return true for left false for right
     */
    protected boolean getSidePlaced(){
        return this.sidePlaced;
    }

    /**
     * Checks if both side of a dominoe in the computers hand matches
     * either edge of the board.
     * @param inHand dominoe in hand
     * @param onBoardLeft dominoe on left side of board
     * @param onBoardRight dominoe on right side of board
     * @param board reference to board
     * @return return true or false if values on the dominoes match or not
     */
    private boolean checkMatch(Dominoe inHand, Dominoe onBoardLeft,
                               Dominoe onBoardRight, Board board){
        if(compareDominoes(inHand,onBoardLeft.getLeftVal(),true)){
            super.checkWhereToPlace(inHand,onBoardLeft);
            board.placeOnLeft(inHand);
            this.sidePlaced = true;
            if(super.getConsoleStatus()){
                System.out.println("Computer plays [" + inHand.getLeftVal() +
                        ", " + inHand.getRightVal() + "] at left.");
            }
            return true;
        }else if(compareDominoes(inHand,onBoardRight.getRightVal(),
                false)){
            super.checkWhereToPlace(inHand,onBoardRight);
            board.placeOnRight(inHand);
            this.sidePlaced = false;
            if(super.getConsoleStatus()){
                System.out.println("Computer plays [" + inHand.getLeftVal() +
                        ", " + inHand.getRightVal() + "] at right.");
            }
            return true;
        }
        return false;
    }

    /**
     * Compares integer values of the dominoes and filps the dominoe if
     * necessary to make a move.
     * @param inHand dominoe in hand
     * @param onBoard value of the dominoe on the board
     * @param onLeft what side of the board is being checked
     * @return true or false if matching
     */
    private boolean compareDominoes(Dominoe inHand, int onBoard,
                                    boolean onLeft){
        if(inHand.getLeftVal() == onBoard || inHand.getLeftVal() == 0
           || onBoard == 0){
            if(onLeft){
                inHand.rotateDominoe();
            }
            return true;
        }else if(inHand.getRightVal() == onBoard || inHand.getRightVal() == 0){
            if(!onLeft){
                inHand.rotateDominoe();
            }
            return true;
        }
        return false;
    }
}
