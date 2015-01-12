package com.willtaylor;



import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

/**
 * Created by willtaylor on 8/24/14.
 */
public class Screen extends JFrame{

    private Control control;

    public volatile Surface surface;

    public final boolean DRAW_GRID = true;


    public Screen(Control control){

        super("Snake");

        this.control = control;

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(701, 498);
        setVisible(true);

        setResizable(false);

        surface = new Surface(this);

        this.addKeyListener(control);

        add(surface);

        try {
            Thread.sleep(100);
            surface.drawBackground();
            surface.drawGrid();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void drawPlayer(){

        // Draw coordinates (24, 15), (23, 15), and (22, 15)

        Coordinate one   = new Coordinate(25, 17);
        Coordinate two   = new Coordinate(24, 17);
        Coordinate three = new Coordinate(23, 17);

        surface.drawPlayerSquare(one);
        surface.drawPlayerSquare(two);
        surface.drawPlayerSquare(three);

        control.playerCoordinates.add(three);
        control.playerCoordinates.add(two);
        control.playerCoordinates.add(one);

    }

    public class Surface extends JPanel {

        Graphics2D g2d;

        private Color backgroundColor            = new Color(50, 50, 50);
        private Color gridColor                  = new Color(68, 71, 73);
        private Color foodColor                  = new Color(255, 201, 116);
        private Color playerColor                = new Color(200, 128, 57);

        private int width;
        private int height;

        private Screen screen;

        @Override
        public void paint(Graphics g){

            drawBackground();

            if(DRAW_GRID){
                drawGrid();
            }

            if( !control.gameState.equals(control.PRE_GAME ) ){
                drawScore();
            }
        }

        public synchronized void drawCyanPlayerSquare(Coordinate coordinate){

            g2d = (Graphics2D) getGraphics();

            int column = coordinate.getX();
            int row    = coordinate.getY();

            g2d.setColor(Color.CYAN);

            g2d.fillRect( (column * 25), (row * 25) - 25, 25 , 25);

            drawGrid();
        }

        public synchronized void drawPlayerSquare(Coordinate coordinate){

            g2d = (Graphics2D) getGraphics();

            int column = coordinate.getX();
            int row    = coordinate.getY();

            g2d.setColor(playerColor);

            g2d.fillRect( (column * 25), (row * 25) - 25, 25 , 25);

            drawGrid();
        }

        public synchronized void erasePlayerSquare(Coordinate coordinate){

            g2d = (Graphics2D) getGraphics();

            int column = coordinate.getX();
            int row    = coordinate.getY();

            g2d.setColor(backgroundColor);

            g2d.fillRect( (column * 25), (row * 25) - 25, 25 , 25);

            drawGrid();
        }

        public void drawRedPlayerSquare(Coordinate coordinate){
            g2d = (Graphics2D) getGraphics();

            int column = coordinate.getX();
            int row    = coordinate.getY();

            g2d.setColor(Color.RED);

            g2d.fillRect( (column * 25), (row * 25) - 25, 25 , 25);

            drawGrid();
        }

        public void drawFood(Coordinate coordinate){

            g2d = (Graphics2D) getGraphics();

            int column = coordinate.getX();
            int row    = coordinate.getY();

            g2d.setColor(foodColor);

            g2d.fillRect( (column * 25), (row * 25) - 25, 25 , 25);

            drawGrid();
        }

        // 28 wide, 19 wide

        public Surface(Screen screen){

            super();

            this.screen = screen;

            width = screen.getWidth();
            height = screen.getHeight();
        }

        public void drawGrid(){

            g2d = (Graphics2D) getGraphics();

            g2d.setColor(gridColor);

            // Draw vertical lines
            for(int i = 0; i < width; i += 25){

                g2d.drawLine(i, 0, i, height);

            }

            // Draw horizontal lines
            for(int i = 0; i < height; i += 25){

                g2d.drawLine(0, i, width, i);

            }

        }

        public void drawBackground(){

            g2d = (Graphics2D) getGraphics();

            g2d.setColor(backgroundColor);

            g2d.fillRect(0, 0, width, height);

        }

        public void drawScore(){

            g2d = (Graphics2D) getGraphics();

            g2d.setColor(Color.WHITE);

            Font scoreFont = new Font("helvetica", Font.PLAIN, 23);
            g2d.setFont(scoreFont);

            g2d.drawString("Score: " + control.score, 590, 25);
        }

        public void eraseScore(){

            g2d = (Graphics2D) getGraphics();

            g2d.setColor(backgroundColor);

            Font scoreFont = new Font("helvetica", Font.PLAIN, 23);
            g2d.setFont(scoreFont);

            g2d.drawString("Score: " + control.score, 590, 25);
        }
    }

}