import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        //board size
        int boardW = 600;
        int boardH = 600;

        //frame
        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(boardW, boardH);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //to terminate when user presses X

        SnakeGame snakeGame = new SnakeGame(boardW, boardH);
        frame.add(snakeGame); //jpanel inside our frame
        frame.pack(); //place the jpanel inside the pane with the full dimension

        snakeGame.requestFocus(); //the snake game will be listening for key presses

    }
}