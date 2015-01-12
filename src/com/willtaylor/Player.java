package com.willtaylor;

import java.util.ArrayList;

/**
 * Created by willtaylor on 9/5/14.
 */
public class Player {

    public Control control;

    public final int UP     = 1;
    public final int DOWN   = 0;
    public final int LEFT   = 2;
    public final int RIGHT  = 3;

    public volatile int direction = LEFT;

    public ArrayList<Integer> directions;

    public Player(Control control){

        this.control = control;

    }
}
