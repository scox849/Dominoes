#Dominoes

Samuel Cox

##Introduction
This dominoes game has two different versions. One playable and the console and the other playable within a GUI.
There are seven classes, six objects and one procedure. 

Objects: Board,Boneyard,Dominoe,Player,HumanPlayer(extends Player),ComputerPlayer(extends Player).

Procedures:Game

The Game procedure handles input from the user and runs both versions of the game. The objects are used for managing the back end of the program.

##Usage
To run the console version of the program run the jar with the command line argument:

"CSL"

to run the GUI version run the jar with no command line arguments.

To play the console version type the letter associated with the action you wish to take and press enter.
When choosing a dominoe they are indexed starting at 0 going from left to right.

To play the GUI version click on the dominoe you would like to play. It will be highlighted.
Then click on the text saying either, "Place on left" or "Place on right" doing so will
place your dominoe on the side of the board you requested if it is legal. To flip a domioe, click a dominoe
and then click on the text that says "Filp dominoe". If you need to pull from the boneyard
click the text that says "Draw from boneyard" you wont be able to draw however if there is 
a dominoe in your hand you can play.

##Project Assumptions
When playing the console version, when the player has chosen to play a dominoe, it is assumed that the integer value input 
was within the bounds of the had. For example if the hand contained 7 dominoes indexes 0 - 6 are the only available indexes to chose from.
It is also assumed that all other input while placing the dominoe is correct. However, if you chose a dominoe and it is not legal
to place it where you specified it will not allow you to do so and ask you to chose another dominoe. It will also not allow you
to get a dominoe from the boneyard if you have dominoes in your hand that you can play.

##Design Choice

For the GUI version I used two maps one to link the dominoes in the back end to 
rectangles with listeners in the and then another map to link those rectangles to 
lists containing two canvases, one for each side of a dominoe. I don't think this
is very clever nor do I think it is the absolute best way to do things but its what 
I was able to come up with.

##Versions
There are two versions of the program, one playable on the console,
and one playable within a GUI.

##Docs
The design document is located in the docs directory as a pdf.

##Known Issues
If you chose an index when placing a dominoe that is outside the bounds of your hand it will crash (For the console version).
That's why it is assumed your input here will be correct. I don't really see that as a bug though more
as my lack of time to implement error handling for everything.

In the GUI version when if you click on the text to place on either side of the board
or flip the dominoe without a dominoe marked an exception will be thrown but the game won't crash and you 
can contiue to play. Also if you try and place a dominoe on the beyond the edge of the window the game will crash.
However, its extremely difficult to do this. The window is large enough to accomodate 28 dominoes, so if both side are
balanced you can get all 28 onto the board but if you put everything on one size it is possible to go out of bounds.

##Testing and Debugging
I mostly just used print statements for testing and debugging this project.