package org.petehering.breakout;

import java.awt.Color;

public class Brick extends GameObject
{
    private boolean alive;
    
    public Brick (Color color, float x, float y, int width, int height)
    {
        super (color, x, y, width, height);
        alive = true;
    }
    
    public void setAlive (boolean value)
    {
        alive = value;
    }
    
    public boolean isAlive ()
    {
        return alive;
    }
}
