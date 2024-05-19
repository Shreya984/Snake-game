/*
	SnakeGame class creates a JFrame object and a Game object which is then added to the frame.
*/

import com.snake.game.util.*;
import javax.swing.JFrame;
class SnakeGame {
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		Game game = new Game();
		frame.setBounds(10, 10, 705, 600);
		frame.setTitle("Snake Game");
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Game());
		frame.setLocationRelativeTo(null);
	}
}