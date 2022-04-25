package dominoes;

/**
 * Dominoe class contains methods and contructor
 * for dominoe object.
 * @author Sam Cox
 * @version date 2/14/20
 */
public class Dominoe {

    private int leftVal;
    private int rightVal;
    private boolean position;

    /**
     * Dominoe constructor initialises left and right
     * values of a dominoe
     * @param leftVal left side
     * @param rightVal right side
     */
    public Dominoe(int leftVal, int rightVal) {
        this.leftVal = leftVal;
        this.rightVal = rightVal;

    }

    /**
     * Returns value of left side of dominoe.
     * @return left value
     */
    protected int getLeftVal() {
        return this.leftVal;
    }

    /**
     * Returns value of right side of dominoe.
     * @return right value
     */
    protected int getRightVal() {
        return this.rightVal;
    }

    /**
     * Sets the position of the dominoe.
     * True for top row false for bottom row.
     * @param topBot true or false
     */
    protected void setPosition(boolean topBot){
        this.position = topBot;
    }

    /**
     * Returns the position (top or bottom) of the
     * dominoe.
     * @return true or false
     */
    protected boolean getPosition(){
        return this.position;
    }

    /**
     * Flips the values of the left and right
     * sides of the dominoe.
     */
    protected void rotateDominoe(){
        int temp = this.leftVal;
        this.leftVal = this.rightVal;
        this.rightVal = temp;
    }
}
