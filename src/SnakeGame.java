import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements  ActionListener, KeyListener{ //to inherit jpanel to take its properties
    private class Tile{

        int x;
        int y;

        //tile constructors
        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    int boardW;
    int boardH;

    //tile size
    int tileSize = 25;

    //snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;


    //apple
    Tile apple;
    Random random;

    //game functionality
    Timer gameLoop;
    int velX;
    int velY;

    //gave over
    boolean gameOver = false;

    //board constructor
    SnakeGame(int boardW, int boardH){
        this.boardW = boardW; //this.boardW means the boardw that belongs to the class SnakeGame
        this.boardH = boardH;

        setPreferredSize(new Dimension(this.boardW, this.boardH));
        setBackground(Color.black);

        //to listen to the keys
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5,5);
        snakeBody = new ArrayList<Tile>();

        apple = new Tile (10,10);
        random = new Random();
        placeApple();

        velX = 0;
        velY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        /*
        //grid
        for (int i=0; i<boardW/tileSize; i++){ //x1y1x2y2
            g.drawLine(i*tileSize, 0, i*tileSize, boardH );
            g.drawLine(0, i*tileSize, boardW, i*tileSize);
        }
         */

        //apple
        g.setColor(Color.red);
        g.fillRect(apple.x * tileSize, apple.y * tileSize, tileSize, tileSize);


        //snakeHead
        g.setColor(Color.green);
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);

        //snakeBody
        for (int i=0; i< snakeBody.size(); i++){
           Tile snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
        }

        //score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        else
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);

    }

    public void placeApple(){
        apple.x = random.nextInt(boardW/tileSize);
        apple.y = random.nextInt(boardH/tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }


    public void move(){
        //eat apple
        if(collision(snakeHead, apple)){
            snakeBody.add(new Tile(apple.x, apple.y));
            placeApple();
        }

        //snakeBody
        for(int i = snakeBody.size()-1; i>=0; i--){
            Tile snakePart = snakeBody.get(i);
            if(i == 0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else{
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        //snakeHead
        snakeHead.x += velX;
        snakeHead.y += velY;

        //game over
        for (int i=0; i<snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);

            //collide with snakeHead
            if (collision(snakeHead, snakePart)){
                gameOver=true;
            }
        }
            //boarders
            if(snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardW || snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardH){
                gameOver=true;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver){
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velY != 1){
            velX = 0;
            velY = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && velY !=  -1){
            velX = 0;
            velY = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && velX != 1){
            velX = -1;
            velY = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velX != -1){
            velX = 1;
            velY = 0;
        }

    }

    //useless but needed
    @Override
    public void keyTyped(KeyEvent e) {

    }

    //useless but needed
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
