package org.petehering.breakout;

import java.awt.Color;
import static java.lang.System.out;

public class Ball extends GameObject
{
    private float deltaX;
    private float deltaY;
    
    public Ball (Color color, float x, float y, int width, int height)
    {
        super (color, x, y, width, height);
    }

    public void setVelocity (float dx, float dy)
    {
        this.deltaX = clamp (dx, -0.3f, 0.3f);
        this.deltaY = dy;
    }
    
    public void update (long elapsed)
    {
        this.x = this.x + (elapsed * deltaX);
        this.y = this.y + (elapsed * deltaY);
    }

    public void bounceOffOf (Paddle paddle)
    {
        float n = 20.0f + (this.getX () - paddle.getX ());

        if (n < 33.33f)
        {
            this.deltaX = clamp (this.deltaX - 0.05f, -0.3f, 0.3f);
        }
        else if (n > 66.66f)
        {
            this.deltaX = clamp (this.deltaX + 0.05f, -0.3f, 0.3f);
        }

        this.deltaY *= -1;
    }

    private float clamp (float value, float min, float max)
    {
        if (value < min)
        {
            return min;
        }
        
        if (value > max)
        {
            return max;
        }
        
        return value;
    }
    
    public void reverseYVelocity ()
    {
        this.deltaY *= -1;
    }

    public void reverseXVelocity ()
    {
        this.deltaX *= -1;
    }
}
