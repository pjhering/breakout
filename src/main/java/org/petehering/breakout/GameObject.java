package org.petehering.breakout;

import java.awt.Color;
import java.awt.Graphics2D;
import static java.lang.Math.round;

public abstract class GameObject
{
    protected final Color color;
    protected final int width;
    protected final int height;
    protected float x;
    protected float y;
    
    public GameObject (Color color, float x, float y, int width, int height)
    {
        this.color = color;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void setPosition (float x, float y)
    {
        this.x = x;
        this.y = y;
    }
    
    public boolean hits (GameObject that)
    {
        return this.x < that.x + that.width
            && this.y < that.y + that.height
            && that.x < this.x + this.width
            && that.y < this.y + this.height;
    }
    
    public void draw (Graphics2D g)
    {
        g.setColor (color);
        g.fillRect (round (x), round (y), width, height);
    }

    public Color getColor ()
    {
        return color;
    }

    public int getWidth ()
    {
        return width;
    }

    public int getHeight ()
    {
        return height;
    }

    public float getX ()
    {
        return x;
    }

    public void setX (float x)
    {
        this.x = x;
    }

    public float getY ()
    {
        return y;
    }

    public void setY (float y)
    {
        this.y = y;
    }
}
