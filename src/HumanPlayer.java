package dominoes;

/**
 * HumanPlayer class extends Player.
 * Contains methods and contructor for human player.
 * @author Sam Cox
 * @version date 2/14/20
 */
public class HumanPlayer extends Player{


    /**
     * HumanPlayer constructor calls super constructor with
     * a reference to the boneyard and a boolean to determine
     * what version of the game is being played.
     * @param boneyard reference to boneyard
     * @param csl true for console false for GUI
     */
    public HumanPlayer(Boneyard boneyard, boolean csl) {

        super(boneyard, csl);
    }

    /**
     * Gets a dominoe from a provided index and plays it on
     * the given side of the board if it is legal.
     * Also flips the dominoe if specified to do so.
     * @param indexInHand index of the dominoe in the hand
     * @param side side of the board dominoe will be played on
     * @param flip true or false to flip the dominoe
     * @param board reference to the board
     * @return true or false if move was made or not
     */
    protected boolean pickDominoe(int indexInHand, String side, boolean flip,
                               Board board){
        Dominoe toBePlayed = super.getHand().get(indexInHand);
        if(flip){
            toBePlayed.rotateDominoe();
        }
        if(side.equals("l")){
            boolean left = true;
            if(isLegal(toBePlayed, left, board)){
                board.placeOnLeft(toBePlayed);
                if(super.getConsoleStatus()){
                    System.out.println("Playing [" + toBePlayed.getLeftVal() +
                            ", " + toBePlayed.getRightVal() + "] at left.");
                }

                super.getHand().remove(toBePlayed);
                return true;
            }
        }else{
            boolean left = false;
            if(isLegal(toBePlayed, left, board)){
                board.placeOnRight(toBePlayed);
                if(super.getConsoleStatus()){
                    System.out.println("Playing [" + toBePlayed.getLeftVal() +
                            ", " + toBePlayed.getRightVal() + "] at right.");
                }
                super.getHand().remove(toBePlayed);
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if a move is legal for a given side of the board.
     * @param toCheck Dominoe being checked
     * @param onLeft witch side of the board is being checkd
     *               true for left false for right
     * @param board reference to board
     * @return true if legal false if not
     */
    private boolean isLegal(Dominoe toCheck, boolean onLeft, Board board){
        if(board.getBoard().size() == 0){
            toCheck.setPosition(true);
            return true;
        }
        if(onLeft){
            if(toCheck.getRightVal() == board.getLeft().getLeftVal()
                    || toCheck.getRightVal() == 0 ||
                    board.getLeft().getLeftVal() == 0){
                super.checkWhereToPlace(toCheck,board.getLeft());
                return true;
            }

        }else{
            if(toCheck.getLeftVal() == board.getRight().getRightVal()
                    || toCheck.getLeftVal() == 0 ||
                    board.getRight().getRightVal() == 0){
                super.checkWhereToPlace(toCheck,board.getRight());
                return true;
            }
        }
        return false;
    }

    /**
     * Deermines if it is legal for the human player to draw from the
     * bonyard.
     * @param board reference to board
     * @return true if the player can still play, false if the player
     *          needs to draw from the boneyard
     */
    protected boolean cantPlay(Board board){
        int leftNum = board.getLeft().getLeftVal();
        int rightNum = board.getRight().getRightVal();
        for(Dominoe d : super.getHand()){
            if(d.getRightVal() == 0 || d.getLeftVal() == 0){
                return false;
            }
            if(d.getRightVal() == leftNum || d.getLeftVal() == leftNum){
                return false;
            }
            if(d.getRightVal() == rightNum || d.getLeftVal() == rightNum){
                return false;
            }
        }
        return true;
    }





}
