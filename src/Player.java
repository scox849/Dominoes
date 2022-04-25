package dominoes;

import java.util.*;

/**
 * Player parent class contains methods and contructor
 * for player object.
 * @author Sam Cox
 * @version date 2/14/20
 */
public class Player {

    private List<Dominoe> hand;
    private Boneyard boneyard;
    private boolean consoleGame;

    /**
     * Player constructor initializes the player hand
     * with the reference to the boneyard and determines the
     * version of the game being played.
     * @param boneyard referene to boneyard
     * @param consoleGame GUI(false) or console(true)
     */
    public Player(Boneyard boneyard, boolean consoleGame){
        this.consoleGame = consoleGame;
        this.boneyard = boneyard;
        this.hand = boneyard.getHand();

    }

    /**
     * Returns a reference to the players hand.
     * @return hand
     */
    protected List<Dominoe> getHand(){

        return this.hand;
    }

    /**
     * Returns a reference to the boneyard.
     * @return boneyard
     */
    protected Boneyard getBoneyard(){

        return this.boneyard;
    }

    /**
     * Retunds the type of game being played GUI or console.
     * @return true for console false for GUI
     */
    protected boolean getConsoleStatus(){

        return this.consoleGame;
    }

    /**
     * Places the dominoe on the top or bottom of the board
     * @param inHand dominoe in players hand
     * @param onBoard dominoe on the board
     */
    protected void checkWhereToPlace(Dominoe inHand, Dominoe onBoard){
        if(onBoard.getPosition()){
            inHand.setPosition(false);
        }else{
            inHand.setPosition(true);
        }
    }

    /**
     * Returns the size of the players hand.
     * @return hand size
     */
    protected int getHandSize(){

        return this.hand.size();
    }

    /**
     * Prints the players tray of dominoes.
     */
    protected void printTray(){
        System.out.print("\n\nTray: [");
        for (Dominoe dominoe : this.getHand()) {
            System.out.print("[" + dominoe.getLeftVal() + ", "
                    + dominoe.getRightVal() + "]");
        }
        System.out.print("]\n");
    }



}
