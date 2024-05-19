Snake Game is an arcade game. This project is a 1 Player snake game implemented by using Java AWT and Swing.

This project is divided into 2 classes.
    1) Game class: This class creates and manages the GUI components required for this game, performs event handling, and implements the rules of this game.
    2) SnakeGame class: This class contains the main function i.e. the entry point function. This function creates the JFrame object and Game class object and adds the Game class object to the JFrame object.

The flow of this game:

           1) Press any of the arrow keys to start the game.

           2) Press the arrow keys so that the snake can eat its food.

           3) When the snake eats, the score and its length is incremented.

           4) Avoid letting the snake collide with its own body otherwise the game ends.

           5) Press the SPACEBAR key to restart the game.

 

Algorithm of the snake game:

           1) Initialize the game board with a snake of a particular length and food.

           2) a) Take user input for the snake to move in a particular direction. 

               b) Move the snake's body in the direction specified by the user.

               c) If (snake has eaten the food on the game board)

                   {

                                     (length of the snake)++;

                                     score++;

                                     generate new food;

                    }

               d) If (snake has collided with itself)

                   {

                                     declare ("game is over");

                                     end the game;

                   }

           3) Perform steps 2 and 3 until the game ends.
