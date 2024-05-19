/*
	This package:
		1) Creates and manages all the graphic components.
		2) Implements all the rules of this game.
*/

package com.snake.game.util;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Arrays;
import java.util.Random;
public class Game extends JPanel implements ActionListener, KeyListener{

	// Variables to store the direction in which the snake will move.
	private boolean left = false;
	private boolean right = true;
	private boolean up = false;
	private boolean down = false;

	// Variable to check if game is over, i.e. when the snake collides with itself.
	private boolean gameOver = false;

	// Arrays to store the position of the snake's body parts.
	private int[] snakeXLength = new int[700];
	private int[] snakeYLength = new int[700];

	// Length of snake including its head.
	private int lengthOfSnake = 3;

	// Stores the score.
	private int score = 0;

	// Variable to store the number of moves when a key is pressed to change the direction of snake's movement.
	private int moves = 0;

	// To generate random numbers as location for snake's food.
	private Random random = new Random();

	// Variables to store the location of food.
	private int foodX;
	private int foodY;

	// Controls the speed of the game and repaints the components after 100ms.
	private Timer timer;
	private int delay = 100;

	public Game() {

		// To initialize the array with all its variables set as zero.
		Arrays.fill(snakeXLength, 0);
		Arrays.fill(snakeYLength, 0);

		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);

		timer = new Timer(delay, this);
		timer.start();

		// Add snake's food to the screen at the randomly generated location.
		newFood();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		// Sets background colour.
		g.setColor(Color.decode("#1E1E1E"));
		g.fillRect(0, 0, 705, 600);

		// Paints 2 rectangles with borders of white colour.
		g.setColor(Color.decode("#FFFFFF"));
		g.drawRect(24, 10, 651, 56);
		g.drawRect(24, 74, 651, 476);

		// Paints a rectangle of black colour inside the second rectangle with white borders.
		g.setColor(Color.decode("#000000"));
		g.fillRect(25, 75, 650, 475);

		// When moves equal to zero, this if clock sets the value of position of snake's body parts.
		if(moves == 0) {
			snakeXLength[0] = 150;
			snakeXLength[1] = 125;
			snakeXLength[2] = 100;

			snakeYLength[0] = 500;
			snakeYLength[1] = 500;
			snakeYLength[2] = 500;
		}

		// Paints the snake's head.
		g.setColor(Color.decode("#FF007A"));
		g.fillOval(snakeXLength[0], snakeYLength[0], 25, 25);

		// This loop paints the remaining body parts of the snake.
		for (int i = 1; i < lengthOfSnake; i++) g.drawOval(snakeXLength[i], snakeYLength[i], 25, 25);

		// Paints snake's food.
		g.setColor(Color.decode("#FF9900"));
		g.fillOval(foodX, foodY, 25, 25);

		// When gameOver is true, this if block draws the strings "Game Over" and "Press SPACE to Restart..".
		if(gameOver) {
			g.setColor(Color.decode("#FFFFFF"));
			g.setFont(new Font("Serif", Font.BOLD, 50));
			g.drawString("Game Over", 225, 275);
			g.setFont(new Font("Serif", Font.PLAIN, 25));
			g.drawString("Press SPACE to Restart..", 225, 325);
		}

		// Draws the score and length of snake inside the 1st rectangle with white borders. 
		g.setColor(Color.decode("#FFFFFF"));
		g.setFont(new Font("Serif", Font.PLAIN, 20));
		g.drawString("Score: " + score, 130, 45);
		g.drawString("Length: " + lengthOfSnake, 500, 45);
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		// This loop assigns the snake's (i - 1)th body part's location to the snake's ith body part.
		for(int i = lengthOfSnake - 1; i > 0; i--) {
			snakeXLength[i] = snakeXLength[i - 1];
			snakeYLength[i] = snakeYLength[i - 1];
		}

		// These if blocks change the location of the snake according to the boolean variable tested in the if block, when a key is pressed to change the direction of the snake's movement.
		if(left) snakeXLength[0] = snakeXLength[0] - 25;
		if(right) snakeXLength[0] = snakeXLength[0] + 25;
		if(up) snakeYLength[0] = snakeYLength[0] - 25;
		if(down) snakeYLength[0] = snakeYLength[0] + 25;

		// Ensures that if the snake goes out of the screen, then it reappears from the opposite side.
		if(snakeXLength[0] > 650) snakeXLength[0] = 25;
		if(snakeXLength[0] < 25) snakeXLength[0] = 650;
		if(snakeYLength[0] > 525) snakeYLength[0] = 75;
		if(snakeYLength[0] < 75) snakeYLength[0] = 525;

		eatsFood();
		collidesWithBody();
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent ke) {

		// When the left arrow key is pressed or when the right arrow key is pressed when the snake is moving in the left direction.
		if(ke.getKeyCode() == KeyEvent.VK_LEFT && (!right)) {
			left = true;
			right = false;
			up = false;
			down = false;
			moves++;
		}

		// When the right arrow key is pressed or when the left arrow key is pressed when the snake is moving in the right direction.
		if(ke.getKeyCode() == KeyEvent.VK_RIGHT && (!left)) {
			left = false;
			right = true;
			up = false;
			down = false;
			moves++;
		}

		// When the up arrow key is pressed or when the down arrow key is pressed when the snake is moving in the up direction.
		if(ke.getKeyCode() == KeyEvent.VK_UP && (!down)) {
			left = false;
			right = false;
			up = true;
			down = false;
			moves++;
		}

		// When the down arrow key is pressed or when the up arrow key is pressed when the snake is moving in the down direction.
		if(ke.getKeyCode() == KeyEvent.VK_DOWN && (!up)) {
			left = false;
			right = false;
			up = false;
			down = true;
			moves++;
		}

		// This if block restarts the game when spacebar key is pressed after the game is over.
		if(ke.getKeyCode() == KeyEvent.VK_SPACE && gameOver) restart();
	}

	@Override
	public void keyTyped(KeyEvent ke) {

	}

	@Override
	public void keyReleased(KeyEvent ke) {

	}

	// Generates the location of the food for the snake.
	private void newFood() {

		// The foodX and foodY variables store random integers generated within the range of the rectangle in which the snake is allowed to move.
		foodX = random.nextInt(26) * 25 + 25;
		foodY = random.nextInt(19) * 25 + 75;

		// Ensures that the food is not drawn over the body of the snake.
		for(int i = lengthOfSnake - 1; i >= 0; i--) {
			if(snakeXLength[i] == foodX && snakeYLength[i] == foodY) newFood();
		}
	}

	// Generates food for the snake when it eats its food and increments the length of the snake and the score.
	private void eatsFood() {
		if(snakeXLength[0] == foodX && snakeYLength[0] == foodY) {
			newFood();
			lengthOfSnake++;
			score++;
		}
	}

	// Checks if the snake has collided with itself after every move.
	private void collidesWithBody() {
		for(int i = lengthOfSnake - 1; i > 0; i--) {
			if(snakeXLength[i] == snakeXLength[0] && snakeYLength[i] == snakeYLength[0]) {
				timer.stop();
				gameOver = true;
			}
		}
	}

	// Resets the necessary variables and calls necessary methods to start new game.
	private void restart() {
		gameOver = false;
		moves = 0;
		score = 0;
		lengthOfSnake = 3;
		Arrays.fill(snakeXLength, 0);
		Arrays.fill(snakeYLength, 0);
		left = false;
		right = true;
		up = false;
		down = false;
		timer.start();
		newFood();
		repaint();
	}
}