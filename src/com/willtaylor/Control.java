package com.willtaylor;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;

public class Control implements KeyListener {

    public Screen screen;
    private Model model;

    public ArrayList<Coordinate> foodCoordinates, playerCoordinates;

    public final int EMPTY  = 0;
    public final int PLAYER = 1;
    public final int FOOD   = 2;

    public final String PRE_GAME            = "PREGAME";
    public final String GAME_RUNNING        = "GAME_RUNNING";
    public final String POST_GAME           = "POST_GAME";

    public volatile String gameState = PRE_GAME;

    public volatile int score = 0;

    public volatile int speed = 200;

    public volatile Player player;

    public volatile AudioClip eatClip, hurtClip, introClip;

    public static void main(String[] args) {

        Control control = new Control();

    }

    public Control(){


        // Instantiate info arrays
        foodCoordinates = new ArrayList<Coordinate>();
        playerCoordinates = new ArrayList<Coordinate>();

        screen = new Screen(this);
        model  = new Model(this);

        // Create and populate array of cell states

        player = new Player(this);

        // Instruct screen to draw player
        screen.drawPlayer();

        // Instantiate audio players

        URL eatURL = this.getClass().getClassLoader().getResource("com/willtaylor/eat.wav");
        eatClip = Applet.newAudioClip(eatURL);

        URL hurtURL = this.getClass().getClassLoader().getResource("com/willtaylor/hurt.wav");
        hurtClip = Applet.newAudioClip(hurtURL);

        URL introURL = this.getClass().getClassLoader().getResource("com/willtaylor/intro.wav");
        introClip = Applet.newAudioClip(introURL);

        introClip.play();
    }

    @Override
    public void keyTyped(KeyEvent e) {

        if( (e.getKeyChar() == ' ') && (gameState.equals(PRE_GAME) ) ){

            // If player presses space and we are in pregame
            model.beginGame();

        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if( (e.getKeyCode() == KeyEvent.VK_DOWN) && (model.currentDirection != player.DOWN) && (gameState.equals(GAME_RUNNING) )){
            player.direction = player.UP;
        }
        else if( (e.getKeyCode() == KeyEvent.VK_UP) && (model.currentDirection != player.UP) && (gameState.equals(GAME_RUNNING) )){
            player.direction = player.DOWN;
        }
        else if( (e.getKeyCode() == KeyEvent.VK_LEFT) && (model.currentDirection != player.RIGHT) && (gameState.equals(GAME_RUNNING) ) ){
            player.direction = player.LEFT;
        }
        else if( (e.getKeyCode() == KeyEvent.VK_RIGHT) && (model.currentDirection != player.LEFT) && (gameState.equals(GAME_RUNNING) )) {
            player.direction = player.RIGHT;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void displayGameOver(){
        GameOverDialog gameOverDialog = new GameOverDialog(this);
    }

    public void resetGame(){

        speed = 200;

        score = 0;

        gameState = PRE_GAME;

        model = null;
        model = new Model(this);

        playerCoordinates = null;
        foodCoordinates = null;

        foodCoordinates = new ArrayList<Coordinate>();
        playerCoordinates = new ArrayList<Coordinate>();

        // Create and populate array of cell states

        screen.surface.drawBackground();
        screen.surface.drawGrid();

        player = new Player(this);

        // Instruct screen to draw player
        screen.drawPlayer();
    }
}
