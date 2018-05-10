package org.petehering.breakout;

import java.awt.Color;

public class Paddle extends GameObject
{
    public Paddle (Color color, float x, float y, int width, int height)
    {
        super (color, x, y, width, height);
    }

    void move (int x)
    {
        this.x = x - (width / 2);
    }
}
