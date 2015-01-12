package com.willtaylor;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by willtaylor on 9/5/14.
 */
public class Model extends Thread{

    public volatile Control control;

    public int currentDirection;

    public String directionString = "222";

    public int length = 3;

    public boolean collision = false;

    public boolean DRAW_FRONT_BLOCK = false;

    public Coordinate foodCoordinate;

    public Model(Control control){

        this.control = control;

    }

    public void beginGame(){

        control.gameState = control.GAME_RUNNING;

        makeFood();

        currentDirection = control.player.LEFT;

        this.start();

    }

    @Override
    public void run(){

        control.screen.surface.drawScore();

        while(control.gameState.equals(control.GAME_RUNNING)){

            currentDirection = control.player.direction;

            Coordinate front = control.playerCoordinates.get(0);
            Coordinate back = control.playerCoordinates.get( 0 );

            int x = front.getX();
            int y = front.getY();

            Coordinate frontOfPlayer = new Coordinate(0,0);

            // Determine the "front" block
            if(control.player.direction == control.player.LEFT){

                frontOfPlayer = new Coordinate(x -= 1, y);

            }
            else if(control.player.direction == control.player.RIGHT){

                frontOfPlayer = new Coordinate(x += 1, y);

            }
            else if(control.player.direction == control.player.UP){

                frontOfPlayer = new Coordinate(x, y += 1);

            }
            else if(control.player.direction == control.player.DOWN){

                frontOfPlayer = new Coordinate(x, y -= 1);

            }

            // Add on the current direction and then remove the last one
            String temp = directionString.substring(0, length - 1);
            directionString = control.player.direction + temp;

            front = control.playerCoordinates.get(0);
            back = control.playerCoordinates.get( control.playerCoordinates.size() - 1 );

            // Determine collisions here

            // Player collisions

            for(int i = 0; i < control.playerCoordinates.size(); i++){

                Coordinate test = control.playerCoordinates.get(i);

                if(test.equals(frontOfPlayer)){

                    // We have a player collision
                    control.hurtClip.play();
                    control.gameState = control.POST_GAME;
                    drawPlayerRed();
                    control.screen.surface.drawCyanPlayerSquare(test);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    control.displayGameOver();
                    return;
                }
            }

            // Food collision

            Coordinate foodCoordinate = control.foodCoordinates.get(0);

            if(frontOfPlayer.equals(foodCoordinate)){
                control.foodCoordinates.remove(0);

                collision = true;

                control.playerCoordinates.add(foodCoordinate);

                control.screen.surface.drawPlayerSquare(foodCoordinate);

                directionString += ( "" + currentDirection);

                // Add on extra coordinate
                ArrayList<Coordinate> tempArray = control.playerCoordinates;
                control.playerCoordinates = null;
                control.playerCoordinates = new ArrayList<Coordinate>();
                control.playerCoordinates.add(foodCoordinate);

                for(int i = 0; i < tempArray.size() - 1; i++){
                    control.playerCoordinates.add( tempArray.get(i) );
                }

                length++;

                control.eatClip.play();

                if(control.speed > 50){
                    control.speed -= 3;
                }

                makeFood();

                control.screen.surface.eraseScore();
                control.score++;
                control.screen.surface.drawScore();
            }

            // Wall collisions

            // Left wall
            if(frontOfPlayer.getX() == -1){
                control.hurtClip.play();
                collision = true;
                control.gameState = control.POST_GAME;
                drawPlayerRed();
                control.screen.surface.drawCyanPlayerSquare(front);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                control.displayGameOver();
                return;
            }

            // Top Wall
            if(frontOfPlayer.getY() == 0){
                control.hurtClip.play();
                collision = true;
                control.gameState = control.POST_GAME;
                drawPlayerRed();
                control.screen.surface.drawCyanPlayerSquare(front);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                control.displayGameOver();
                return;
            }

            // Right wall
            if(frontOfPlayer.getX() == 28){
                control.hurtClip.play();
                collision = true;
                control.gameState = control.POST_GAME;
                drawPlayerRed();
                control.screen.surface.drawCyanPlayerSquare(front);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                control.displayGameOver();
                return;
            }

            // Bottom wall
            if(frontOfPlayer.getY() == 20){
                control.hurtClip.play();
                collision = true;
                control.gameState = control.POST_GAME;
                drawPlayerRed();
                control.screen.surface.drawCyanPlayerSquare(front);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                control.displayGameOver();
                return;
            }


            // If no collisions, move player as desired
            if(!collision){
                control.screen.surface.erasePlayerSquare(control.playerCoordinates.get(control.playerCoordinates.size() - 1));
                incrementPositions();
                control.screen.surface.drawPlayerSquare(control.playerCoordinates.get(0));
            }

            collision = false;

            if(DRAW_FRONT_BLOCK){
                control.screen.surface.drawCyanPlayerSquare(frontOfPlayer);

            }

            try {
                Thread.sleep(control.speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public synchronized void drawPlayerRed(){

        for(int i = 0; i < control.playerCoordinates.size(); i ++){

            control.screen.surface.drawRedPlayerSquare( control.playerCoordinates.get(i) );

        }
    }

    public synchronized void incrementPositions(){

        for(int i = 0; i < control.playerCoordinates.size(); i++){

            int direction = Integer.parseInt( "" + directionString.charAt(i) );

            if(direction == control.player.UP){

                Coordinate coordinate = control.playerCoordinates.get(i);

                coordinate.setY( (coordinate.getY() ) + 1);



            }
            else if(direction == control.player.DOWN){

                Coordinate coordinate = control.playerCoordinates.get(i);

                coordinate.setY( (coordinate.getY() ) - 1);

            }
            else if(direction == control.player.LEFT){

                Coordinate coordinate = control.playerCoordinates.get(i);

                coordinate.setX( (coordinate.getX() ) - 1);

            }
            else if(direction == control.player.RIGHT){

                Coordinate coordinate = control.playerCoordinates.get(i);

                coordinate.setX( (coordinate.getX() ) + 1);

            }

        }
    }

    public void makeFood(){


        Random random = new Random();

        boolean answerFound = false;

        // Algorithm to spawn a food block where there is not a player block
        while(!answerFound){

            boolean flag = false;

            int x = Math.abs( random.nextInt() % 27 );
            int y = Math.abs( random.nextInt() % 17 );

            if(x == 0){
                x++;
            }

            if(y == 0){
                y++;
            }

            foodCoordinate = new Coordinate(x, y);

            for(int i = 0; i < control.playerCoordinates.size(); i++){

                if(foodCoordinate.equals( control.playerCoordinates.get(i) )){
                    flag = true;
                }
            }

            if(!flag){
                answerFound = true;
            }

        }




        control.screen.surface.drawFood(foodCoordinate);

        control.foodCoordinates.add(foodCoordinate);

    }

}
