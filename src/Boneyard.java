package dominoes;

import java.util.*;

/**
 * Bonyard class contains methods and constructor
 * needed to make a boneyard.
 * @author Sam Cox
 * @version date 2/14/20
 */
public class Boneyard {

    private List<Dominoe> boneyard;

    /**
     * Boneyard constructor initializes bonyard
     * as a linked list and calls o method to fill
     * the boneyard.
     */
    public Boneyard(){
        boneyard = new LinkedList<>();
        this.addDominoes();
    }

    /**
     * Fills the boneyard with 28 dominoes.
     */
    private void addDominoes(){
        int dominesSize = 28;
        int maxDominoe = 6;

        for(int i = 0; i < dominesSize; i++){
            for(int j = i; j <= maxDominoe; j++){
                this.boneyard.add(new Dominoe(i,j));
            }
        }
        Collections.shuffle(this.boneyard);
    }

    /**
     * Returns the size of the boneyard.
     * @return boneyard size
     */
    protected int getSize(){
        return this.boneyard.size();
    }

    /**
     * Returns a rondom dominoe from the bonyard to
     * one of the players.
     * @return random dominoe
     */
    protected Dominoe getDominoe(){
        if(this.boneyard.size() > 0){
            int randIdx = (int)(Math.random() * this.boneyard.size());
            Dominoe randDom = this.boneyard.get(randIdx);
            this.boneyard.remove(randDom);
            return randDom;
        }
        return null;
    }

    /**
     * Returns a full hand of random dominoes.
     * @return hand of random dominoes.
     */
    protected List<Dominoe> getHand(){
        List<Dominoe> hand = new LinkedList<>();
        int handLength = 7;

        for(int i = 0; i < handLength; i++){
            int randIdx = (int)(Math.random() * this.boneyard.size());
            Dominoe randDom = this.boneyard.get(randIdx);
            hand.add(randDom);
            this.boneyard.remove(randDom);

        }

        return hand;
    }
}
