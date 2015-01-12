package com.willtaylor;

/**
 * Created by willtaylor on 9/5/14.
 */
public class Coordinate {

    public int x;
    public int y;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    @Override
    public String toString(){

        return "X: " + x + " Y: " + y;
    }

    public boolean equals(Coordinate coordinate){

        if( (coordinate.getX() == x) && (coordinate.getY() == y) ){
            return true;
        }else{
            return false;
        }
    }
}
