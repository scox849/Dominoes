package dominoes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Game class extends Application for javafx.
 * Contains methods to run the GUI and console versions
 * of the game.
 * @author Sam Cox
 * @version date 2/14/20
 */
public class Game extends Application {


    /**
     * Determines the winner of the game.
     * @param human Reference to human player
     * @param computer Reference to computer player
     * @param board Reference to board
     */
    private static boolean countDomVals(Player human, Player computer,
                                        Board board){
        int compCount = 0;
        int humnCount = 0;

        board.printBoard();

        if(human.getHand().size() > 0 && computer.getHand().size() > 0){
            for(Dominoe d : human.getHand()){
                humnCount += d.getRightVal();
                humnCount += d.getLeftVal();
            }
            for(Dominoe d : computer.getHand()){
                compCount += d.getRightVal();
                compCount += d.getLeftVal();
            }
            if(compCount > humnCount){
                System.out.print("\nHuman Wins!\n");
                return true;
            }else{
                System.out.print("\nComputer Wins!\n");
                return false;
            }
        }else if(human.getHand().size() == 0){
            System.out.print("\nHuman Wins!\n");
            return true;
        }else{
            System.out.print("\nComputer Wins!\n");
            return false;
        }

    }

    /**
     * Checks if the game is over.
     * @param bonyardSize size of boneyard
     * @param humnPlayed human turn played
     * @param compPlayed computer turn played
     * @param humnHandSize size of human hand
     * @param compHandSize size of computer hand
     * @return true or false
     */
    private static boolean checkOver(int bonyardSize, boolean humnPlayed,
                                     boolean compPlayed, int humnHandSize,
                                     int compHandSize){
        if(bonyardSize == 0 && !humnPlayed && !compPlayed){
            return true;
        }
        if(humnHandSize == 0){
            return true;
        }else{
            return compHandSize == 0;
        }
    }


    /**
     * Runs the console version of the game.
     */
    private static void playConsole(){
        Boneyard boneyard = new Boneyard();
        Board board = new Board();
        ComputerPlayer compPlayer = new ComputerPlayer(boneyard, true);
        HumanPlayer humanPlayer = new HumanPlayer(boneyard, true);

        boolean compPlayed = true;
        boolean humnPlayed = true;
        boolean humanTurn = true;
        int humnHandSize = humanPlayer.getHandSize();
        int computerHandSize = compPlayer.getHandSize();

        System.out.println("Dominoes!");

        while(!checkOver(board.getBoard().size(), humnPlayed, compPlayed,
                humnHandSize, computerHandSize)){

            compPlayed = false;
            humnPlayed = false;
            if(humanTurn){
                System.out.println("Computer has: " +
                        compPlayer.getHandSize()
                        + " dominoes.");
                System.out.println("Boneyard contains:" + boneyard.getSize()
                        + " dominoes.");
                board.printBoard();
                humanPlayer.printTray();
                System.out.print("Human's turn\n" +
                        "[p] Play Domino\n" +
                        "[d] Draw from boneyard\n" +
                        "[q] Quit\n");
                Scanner humnInput = new Scanner(System.in);
                switch (humnInput.next()){
                    case "p":
                        int index;
                        boolean flip = false;
                        String side;
                        System.out.println("Which domino?");
                        index = humnInput.nextInt();

                        System.out.println("Left or right (l/r)");

                        if(humnInput.next().equals("l")){
                            side = "l";
                        }else{
                            side = "r";
                        }

                        System.out.println("Rotate? (y/n)");
                        if(humnInput.next().equals("y")){
                            flip = true;
                        }

                        humnPlayed = humanPlayer.pickDominoe(index,side,
                                flip,board);
                        humnHandSize = humanPlayer.getHandSize();
                        if(!humnPlayed && boneyard.getSize() > 0){
                            System.out.println("Can't play that " +
                                    "dominoe. Try again!");
                        }else if (!checkOver(board.getBoard().size(),
                                humnPlayed, compPlayed,
                                humnHandSize, computerHandSize)){
                            System.out.println("Computer has: " +
                                    compPlayer.getHandSize()
                                    + " dominoes.");
                            System.out.println("Boneyard contains:" +
                                    boneyard.getSize()
                                    + " dominoes.");
                            board.printBoard();
                            humanPlayer.printTray();
                            humanTurn = false;
                        }
                        break;
                    case "d":
                        if(boneyard.getSize() > 0 &&
                                humanPlayer.cantPlay(board)){
                            Dominoe newDominoe = humanPlayer.
                                    getBoneyard().getDominoe();
                            humanPlayer.getHand().add(newDominoe);
                        }else if(boneyard.getSize() == 0){
                            System.out.println("\nNo more bones.\n");
                        }else{
                            System.out.println("\nMust play from " +
                                    "hand first!\n");
                        }

                        break;
                    case"q":
                        return;
                    default:
                        System.out.println("\nInput not recognized. " +
                                "Try again.\n");
                        break;

                }
            }else{
                System.out.println("Computer's Turn.");
                compPlayed = compPlayer.makeMove(board);
                computerHandSize = compPlayer.getHandSize();
                humanTurn = true;

            }


        }
        countDomVals(humanPlayer, compPlayer, board);

    }


    /**
     * Draws a half of a dominoe onto a convas.
     * @param domVal value of dominoe half
     * @return Canvas with dominoe face.
     */
    private static Canvas drawDominoeHalf(int domVal){
        double domSize = 50.0;
        double circleSize = 8;
        Canvas dominowHalf = new Canvas(domSize,domSize);
        GraphicsContext canvas = dominowHalf.getGraphicsContext2D();
        canvas.setFill(Color.BLACK);
        canvas.fillRect(0,0,domSize,domSize);
        canvas.setFill(Color.WHITE);

        switch(domVal){
            case 1:
                canvas.fillOval(domSize/2 - circleSize/2,
                        domSize/2 - circleSize/2, circleSize,circleSize);
                break;
            case 2:
                canvas.fillOval(domSize/2 - circleSize*2,
                        domSize/2 - circleSize*2, circleSize,circleSize);
                canvas.fillOval(domSize/2 + circleSize,
                        domSize/2 + circleSize, circleSize,circleSize);
                break;
            case 3:
                canvas.fillOval(domSize/2 - circleSize*2,
                        domSize/2 - circleSize*2, circleSize,circleSize);
                canvas.fillOval(domSize/2 + circleSize,
                        domSize/2 + circleSize, circleSize,circleSize);
                canvas.fillOval(domSize/2 - circleSize/2,
                        domSize/2 - circleSize/2, circleSize,circleSize);
                break;
            case 4:
                canvas.fillOval(domSize/2 - circleSize*2,
                        domSize/2 - circleSize*2, circleSize,circleSize);
                canvas.fillOval(domSize/2 + circleSize,
                        domSize/2 + circleSize, circleSize,circleSize);
                canvas.fillOval(domSize/2 - circleSize*2,
                        domSize/2 + circleSize, circleSize,circleSize);
                canvas.fillOval(domSize/2 + circleSize,
                        domSize/2 - circleSize*2, circleSize,circleSize);
                break;
            case 5:
                canvas.fillOval(domSize/2 - circleSize*2,
                        domSize/2 - circleSize*2, circleSize,circleSize);
                canvas.fillOval(domSize/2 + circleSize,
                        domSize/2 + circleSize, circleSize,circleSize);
                canvas.fillOval(domSize/2 - circleSize*2,
                        domSize/2 + circleSize, circleSize,circleSize);
                canvas.fillOval(domSize/2 + circleSize,
                        domSize/2 - circleSize*2, circleSize,circleSize);
                canvas.fillOval(domSize/2 - circleSize/2,
                        domSize/2 - circleSize/2, circleSize,circleSize);
                break;

            case 6:
                canvas.fillOval(domSize/2 - circleSize*2,
                        domSize/2 - circleSize*2, circleSize,circleSize);
                canvas.fillOval(domSize/2 + circleSize,
                        domSize/2 + circleSize, circleSize,circleSize);
                canvas.fillOval(domSize/2 - circleSize*2,
                        domSize/2 + circleSize, circleSize,circleSize);
                canvas.fillOval(domSize/2 + circleSize,
                        domSize/2 - circleSize*2, circleSize,circleSize);
                canvas.fillOval(domSize/2 - circleSize*2,
                        domSize/2 - circleSize/2, circleSize,circleSize);
                canvas.fillOval(domSize/2 + circleSize,
                        domSize/2 - circleSize/2, circleSize,circleSize);

            default:
                break;
        }
        return dominowHalf;
    }


    /**
     * Displays the game on screen makes text for the player to click
     * on to move dominoes around. Properly places dominoes on screen.
     * Tells computer player when to make move and displays winner when
     * game is over.
     * @param foundation gridpane
     * @param toBePlayed dominoe that has been marked to be played
     * @param humanPlayer human player
     * @param board board
     * @param boneyard boneyard
     * @param playerHandMap map of dominoes to rectangels
     * @param dominoes map of rectangles to canvases
     * @param computerPlayer computer player
     */
    private static void drawText(GridPane foundation,
                                 AtomicReference<Dominoe> toBePlayed,
                                 HumanPlayer humanPlayer,
                                 Board board, Boneyard boneyard,
                                 Map<Dominoe,Rectangle> playerHandMap,
                                 Map<Rectangle, List<Canvas>> dominoes,
                                 ComputerPlayer computerPlayer){

        int colSpan = 2;
        int rowSpan = 1;
        int fontSize = 30;
        int trayX = 5;
        int trayY = 11;
        int firstHalf = 0;
        int secondHalf = 1;
        int topTextX = 11;
        int topTextY = 0;

        AtomicBoolean humanTurn = new AtomicBoolean(true);
        AtomicInteger boardLeftX = new AtomicInteger(trayX + 10);
        AtomicInteger boardLeftY = new AtomicInteger(trayY - 6);
        AtomicInteger boardRightX = new AtomicInteger(trayX + 10);
        AtomicInteger boardRightY = new AtomicInteger(trayY - 6);
        AtomicBoolean computerPlayed = new AtomicBoolean(false);
        AtomicBoolean humanPlayed = new AtomicBoolean(false);

        AtomicReference<Text> boneYardSize = new AtomicReference<>(
                new Text("Boneyard has " + boneyard.getSize() +
                " dominoes."));
        AtomicReference<Text> computerDomNum = new AtomicReference<>(
                new Text("Computer has " +
                computerPlayer.getHandSize() +
                " dominoes."));
        boneYardSize.get().setFont(new Font(fontSize));
        computerDomNum.get().setFont(new Font(fontSize));

        Text placeLeft = new Text("Place left side");
        placeLeft.setFont(new Font(fontSize));

        placeLeft.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            if(!humanTurn.get()){
                return;
            }
            String leftSide = "l";
            if(toBePlayed != null){
                if(humanPlayer.pickDominoe(
                        humanPlayer.getHand().indexOf(toBePlayed.get()),
                        leftSide, false,board)) {
                    humanPlayed.set(true);
                    humanTurn.set(false);
                    Rectangle rect = playerHandMap.get(toBePlayed.get());
                    rect.setStroke(Color.CORNSILK);
                    List<Canvas> fullDominoe = dominoes.get(rect);
                    foundation.getChildren().remove(rect);
                    foundation.getChildren().remove(fullDominoe.get(firstHalf));
                    foundation.getChildren().remove(
                            fullDominoe.get(secondHalf));
                    foundation.add(fullDominoe.get(firstHalf),
                            boardLeftX.get() - 1, boardLeftY.get(),
                            colSpan - 1,rowSpan);
                    foundation.add(fullDominoe.get(secondHalf),
                            boardLeftX.get(), boardLeftY.get(),
                            colSpan - 1,rowSpan);
                    foundation.add(rect, boardLeftX.get() - 1,
                            boardLeftY.get(),colSpan,rowSpan);
                    if(board.getBoard().size() == 1){
                        boardRightY.getAndIncrement();
                        boardRightX.getAndIncrement();
                        boardLeftY.getAndIncrement();
                    }else if(board.getLeft().getPosition()){
                        boardLeftY.getAndIncrement();
                    }else{
                        boardLeftY.getAndDecrement();
                    }
                    boardLeftX.getAndDecrement();
                }else{
                    playerHandMap.get(toBePlayed.get()).setStroke(
                            Color.TRANSPARENT);
                }
                if(checkOver(boneyard.getSize(),humanPlayed.get(),
                        computerPlayed.get()
                        ,humanPlayer.getHandSize(),
                        computerPlayer.getHandSize())){
                    if(countDomVals(humanPlayer, computerPlayer, board)){
                        Text winner = new Text("Human Wins!");
                        winner.setFont(new Font(fontSize));
                        foundation.add(winner, trayX + 7,
                                trayY - 3);
                    }else{
                        Text winner = new Text("Computer Wins!");
                        winner.setFont(new Font(fontSize));
                        foundation.add(winner, trayX + 7,
                                trayY - 3);
                    }
                    return;
                }
                if(humanTurn.get()){
                    return;
                }
                if(computerPlayer.makeMove(board)){
                    computerPlayed.set(true);
                    humanTurn.set(true);
                    if(computerPlayer.getSidePlaced()){
                        placeCompDominoe(foundation,boardLeftX.get(),
                                boardLeftY.get(),board.getLeft().getLeftVal(),
                                board.getLeft().getRightVal());
                        if(board.getLeft().getPosition()){
                            boardLeftY.getAndIncrement();
                        }else{
                            boardLeftY.getAndDecrement();
                        }
                        boardLeftX.getAndDecrement();
                    }else{
                        placeCompDominoe(foundation,boardRightX.get(),
                                boardRightY.get(),board.getRight().getLeftVal(),
                                board.getRight().getRightVal());
                        if(board.getRight().getPosition()){
                            boardRightY.getAndIncrement();
                        }else{
                            boardRightY.getAndDecrement();
                        }
                        boardRightX.getAndIncrement();
                    }
                    foundation.getChildren().remove(computerDomNum.get());
                    Text newState = new Text("Computer has " +
                            computerPlayer.getHandSize() +
                            " dominoes.");
                    newState.setFont(new Font(fontSize));
                    computerDomNum.set(newState);
                    foundation.add(computerDomNum.get(),topTextX,
                            topTextY + 1,
                            colSpan*3,rowSpan);

                }
            }
            if(checkOver(boneyard.getSize(),humanPlayed.get(),
                    computerPlayed.get()
                    ,humanPlayer.getHandSize(), computerPlayer.getHandSize())){
                if(countDomVals(humanPlayer, computerPlayer, board)){
                    Text winner = new Text("Human Wins!");
                    winner.setFont(new Font(fontSize));
                    foundation.add(winner, trayX + 7,
                            trayY - 3);
                }else{
                    Text winner = new Text("Computer Wins!");
                    winner.setFont(new Font(fontSize));
                    foundation.add(winner, trayX + 7,
                            trayY - 3);
                }
            }
            humanPlayed.set(false);
            computerPlayed.set(false);

        });

        Text placeRight = new Text("Place right side");
        placeRight.setFont(new Font(fontSize));
        placeRight.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            if(!humanTurn.get()){
                return;
            }
            String rightSide = "r";
            if(toBePlayed != null){
                if(humanPlayer.pickDominoe(
                        humanPlayer.getHand().indexOf(toBePlayed.get()),
                        rightSide,
                        false,board)) {
                    humanTurn.set(false);
                    humanPlayed.set(true);
                    Rectangle rect = playerHandMap.get(toBePlayed.get());
                    rect.setStroke(Color.CORNSILK);
                    List<Canvas> fullDominoe = dominoes.get(rect);
                    foundation.getChildren().remove(rect);
                    foundation.getChildren().remove(
                            fullDominoe.get(firstHalf));
                    foundation.getChildren().remove(
                            fullDominoe.get(secondHalf));
                    foundation.add(fullDominoe.get(firstHalf),
                            boardRightX.get() - 1,
                            boardRightY.get(), colSpan - 1,rowSpan);
                    foundation.add(fullDominoe.get(secondHalf),
                            boardRightX.get(), boardRightY.get(),
                            colSpan - 1,rowSpan);
                    foundation.add(rect, boardRightX.get() - 1,
                            boardRightY.get(),
                            colSpan,rowSpan);
                    if(board.getBoard().size() == 1){
                        boardLeftY.getAndIncrement();
                        boardLeftX.getAndDecrement();
                        boardRightY.getAndIncrement();
                    }else if(board.getRight().getPosition()){
                        boardRightY.getAndIncrement();
                    }else{
                        boardRightY.getAndDecrement();
                    }
                    boardRightX.getAndIncrement();
                }else{
                    playerHandMap.get(toBePlayed.get()).setStroke(
                            Color.TRANSPARENT);
                }
                if(checkOver(boneyard.getSize(),humanPlayed.get(),
                        computerPlayed.get()
                        ,humanPlayer.getHandSize(),
                        computerPlayer.getHandSize())){
                    if(countDomVals(humanPlayer, computerPlayer, board)){
                        Text winner = new Text("Human Wins!");
                        winner.setFont(new Font(fontSize));
                        foundation.add(winner, trayX + 7,
                                trayY - 3);
                    }else{
                        Text winner = new Text("Computer Wins!");
                        winner.setFont(new Font(fontSize));
                        foundation.add(winner, trayX + 7,
                                trayY - 3);
                    }
                    return;
                }
                if(humanTurn.get()){
                    return;
                }
                if(computerPlayer.makeMove(board)){
                    humanTurn.set(true);
                    computerPlayed.set(true);
                    if(computerPlayer.getSidePlaced()){
                        placeCompDominoe(foundation,boardLeftX.get(),
                                boardLeftY.get(),
                                board.getLeft().getLeftVal(),
                                board.getLeft().getRightVal());
                        if(board.getLeft().getPosition()){
                            boardLeftY.getAndIncrement();
                        }else{
                            boardLeftY.getAndDecrement();
                        }
                        boardLeftX.getAndDecrement();
                    }else{
                        placeCompDominoe(foundation,boardRightX.get(),
                                boardRightY.get(),
                                board.getRight().getLeftVal(),
                                board.getRight().getRightVal());
                        if(board.getRight().getPosition()){
                            boardRightY.getAndIncrement();
                        }else{
                            boardRightY.getAndDecrement();
                        }
                        boardRightX.getAndIncrement();
                    }
                    foundation.getChildren().remove(computerDomNum.get());
                    Text newState = new Text("Computer has " +
                            computerPlayer.getHandSize() +
                            " dominoes.");
                    newState.setFont(new Font(fontSize));
                    computerDomNum.set(newState);
                    foundation.add(computerDomNum.get(),topTextX,
                            topTextY + 1,
                            colSpan*3,rowSpan);


                }
            }

            if(checkOver(boneyard.getSize(),humanPlayed.get(),computerPlayed.get()
                    ,humanPlayer.getHandSize(), computerPlayer.getHandSize())){
                if(countDomVals(humanPlayer, computerPlayer, board)){
                    Text winner = new Text("Human Wins!");
                    winner.setFont(new Font(fontSize));
                    foundation.add(winner, trayX + 7,
                            trayY - 3);
                }else{
                    Text winner = new Text("Computer Wins!");
                    winner.setFont(new Font(fontSize));
                    foundation.add(winner, trayX + 7,
                            trayY - 3);
                }
            }
            humanPlayed.set(false);
            computerPlayed.set(false);

        });

        Text drawBonyard = new Text("Draw from boneyard");
        drawBonyard.setFont(new Font(fontSize));
        drawBonyard.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            if(boneyard.getSize() > 0 && board.getBoard().size() > 0
                    && humanPlayer.cantPlay(board)){
                clearDominoes(foundation, humanPlayer.getHand(), playerHandMap,
                        dominoes);
                Dominoe newDominoe = humanPlayer.
                        getBoneyard().getDominoe();
                humanPlayer.getHand().add(newDominoe);
                drawHand(foundation, humanPlayer, playerHandMap,
                        dominoes, toBePlayed);
                foundation.getChildren().remove(boneYardSize.get());
                boneYardSize.set(new Text("Boneyard has " + boneyard.getSize() +
                        " dominoes."));
                boneYardSize.get().setFont(new Font(fontSize));
                foundation.add(boneYardSize.get(),topTextX,topTextY,
                        colSpan*3,rowSpan);
            }else if(boneyard.getSize() == 0){
                drawBonyard.setText("Empty");
            }

        });

        Text flipDom = new Text("Flip Dominoe");
        flipDom.setFont(new Font(fontSize - 10));
        flipDom.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            if(toBePlayed.get() != null){
                Rectangle rect = playerHandMap.get(toBePlayed.get());
                foundation.getChildren().remove(rect);
                List<Canvas> fullDominoe = dominoes.get(rect);
                Canvas first = fullDominoe.get(firstHalf);
                Canvas second = fullDominoe.get(secondHalf);
                int firstTrayX = GridPane.getColumnIndex(first);
                int firstTrayY = GridPane.getRowIndex(first);
                int secondTrayX = GridPane.getColumnIndex(second);
                int secondTrayY = GridPane.getRowIndex(second);
                foundation.getChildren().remove(first);
                foundation.getChildren().remove(second);
                fullDominoe.remove(first);
                fullDominoe.add(first);
                foundation.add(second, firstTrayX,firstTrayY,
                        colSpan - 1,rowSpan);
                foundation.add(first, secondTrayX,secondTrayY,
                        colSpan - 1,rowSpan);
                foundation.add(rect, firstTrayX,firstTrayY, colSpan,rowSpan);

                toBePlayed.get().rotateDominoe();
            }
        });
        foundation.add(boneYardSize.get(),topTextX,topTextY,colSpan*3,
                rowSpan);
        foundation.add(computerDomNum.get(),topTextX,topTextY + 1,
                colSpan*3,rowSpan);
        foundation.add(flipDom, trayX + 8, trayY - 1,
                colSpan, rowSpan);
        foundation.add(drawBonyard, trayX + 7, trayY - 2,
                colSpan, rowSpan);
        foundation.add(placeRight, trayX + 15, trayY - 2,
                colSpan,rowSpan);
        foundation.add(placeLeft, trayX , trayY - 2,
                colSpan,rowSpan);
    }

    /**
     * Clears the maked dominoes in tray.
     * @param tray tray on screen
     * @param human human player
     */
    private static void clearMarked(Map<Dominoe, Rectangle> tray,
                                    HumanPlayer human){
        for(Dominoe d: human.getHand()){
            tray.get(d).setStroke(Color.CORNSILK);
        }
    }


    /**
     * Draws computer dominoe on sceen
     * @param foundation gridpane
     * @param x column
     * @param y row
     * @param leftVal left side of dominoe
     * @param rightVal right side of dominoe
     */
    private static void placeCompDominoe(GridPane foundation, int x, int y,
                                         int leftVal, int rightVal){
        int rowSpan = 1;
        int colSpan = 2;
        int rectWidth = 100;
        int rectHeight = 50;
        int strokeWeight = 4;
        Canvas compLeft = drawDominoeHalf(leftVal);
        Canvas compRight = drawDominoeHalf(rightVal);
        Rectangle rect = new Rectangle(rectWidth, rectHeight);
        rect.setStroke(Color.CORNSILK);
        rect.setFill(Color.TRANSPARENT);
        rect.setStrokeWidth(strokeWeight);
        foundation.add(compLeft,x - 1 ,y, colSpan - 1,
                rowSpan);
        foundation.add(compRight,x ,y, colSpan - 1, rowSpan);
        foundation.add(rect,x - 1,y,colSpan,rowSpan);

    }

    /**
     * Clears dominoes in players hand on sceen.
     * @param foundation gridpane
     * @param allDominoes list of player hand
     * @param rectangles map to listeners
     * @param dominoes map to canvases
     */
    private static void clearDominoes(GridPane foundation,
                                       List<Dominoe> allDominoes,
                                Map<Dominoe,Rectangle> rectangles,
                                Map<Rectangle, List<Canvas>> dominoes){
        int first = 0;
        int second = 1;
        if(allDominoes.size() > 0){
            for(Dominoe d : allDominoes){
                Rectangle rect = rectangles.get(d);
                List<Canvas> dominoe = dominoes.get(rect);
                foundation.getChildren().remove(rect);
                foundation.getChildren().remove(dominoe.get(first));
                foundation.getChildren().remove(dominoe.get(second));

            }
        }

        rectangles.clear();
        dominoes.clear();

    }


    /**
     * Displays the players hand on screen.
     * @param foundation gridpane
     * @param humanPlayer human
     * @param playerHandMap map of dominoes to event listeners
     * @param dominoes map of event listeners to canvases
     * @param toBePlayed dominoe that has been marked by the player
     */
    private static void drawHand(GridPane foundation,HumanPlayer humanPlayer,
                          Map<Dominoe,Rectangle> playerHandMap,
                          Map<Rectangle, List<Canvas>> dominoes,
                          AtomicReference<Dominoe> toBePlayed){

        int trayX = 5;
        int trayY = 11;
        int strokeWeight = 4;
        int rowSpan = 1;
        int colSpan = 2;
        int rectWidth = 100;
        int rectHeight = 50;

        for(Dominoe d : humanPlayer.getHand()){
            Canvas leftVal = drawDominoeHalf(d.getLeftVal());
            Canvas rightVal = drawDominoeHalf(d.getRightVal());
            List<Canvas> fullDominoe = new LinkedList<>();
            fullDominoe.add(leftVal);
            fullDominoe.add(rightVal);
            Rectangle rect = new Rectangle(rectWidth,rectHeight);
            rect.setStroke(Color.BLACK);
            rect.setFill(Color.TRANSPARENT);
            playerHandMap.put(d,rect);
            dominoes.put(rect,fullDominoe);
            rect.addEventHandler(MouseEvent.MOUSE_RELEASED, event ->{
                clearMarked(playerHandMap, humanPlayer);
                toBePlayed.set(d);
                rect.setStrokeWidth(strokeWeight);
                rect.setStroke(Color.RED);
            });
            foundation.add(leftVal,trayX,trayY,colSpan - 1,rowSpan);
            foundation.add(rightVal,trayX+1,trayY,
                    colSpan - 1,rowSpan);
            foundation.add(rect,trayX,trayY,colSpan,rowSpan);
            trayX+=3;
            if(humanPlayer.getHand().indexOf(d) == 6){
                trayY = 13;
                trayX = 5;
            }
        }
    }


    /**
     * Starts the GUI
     * @param stage primary stage
     */
    @Override
    public void start(Stage stage) {

        Board board = new Board();
        Boneyard boneyard = new Boneyard();
        HumanPlayer humanPlayer = new HumanPlayer(boneyard, false);
        ComputerPlayer computerPlayer = new ComputerPlayer(boneyard, false);
        Map<Dominoe,Rectangle> playerHandMap = new HashMap<>();
        Map<Rectangle,List<Canvas>> dominoes = new HashMap<>();

        double sceneWidth = 1500;
        double sceneHeight = 800;
        int numCols = 30;
        int numRows = 14;


        AtomicReference<Dominoe> toBePlayed = new AtomicReference<>();

        GridPane foundation = new GridPane();

        //Source for the for loops:
        //https://stackoverflow.com/questions/29609916/possible
        //-to-decide-number-of-rows-and-columns-in-gridpane-javafx
        //This showed me how to give the grid pane a certain number
        //or rows and columns.
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            foundation.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            foundation.getRowConstraints().add(rowConst);
        } 

        drawText(foundation, toBePlayed, humanPlayer, board, boneyard
                 ,playerHandMap,dominoes, computerPlayer);
        drawHand(foundation, humanPlayer,playerHandMap,dominoes,toBePlayed);

        Scene root = new Scene(foundation, sceneWidth,sceneHeight,
                Color.CORNSILK);
        stage.setTitle("Dominoes");
        stage.setScene(root);
        stage.show();
    }

    /**
     * Main, starts program.
     * If there are no args plays GUI.
     * If there is one arg "CSL" plays console version.
     * @param args command line args
     */
    public static void main(String[] args){

        if(args.length > 0){
            if(args[0].equals("CSL")){
                playConsole();
            }
        }else{
            launch(args);
        }


    }


}
