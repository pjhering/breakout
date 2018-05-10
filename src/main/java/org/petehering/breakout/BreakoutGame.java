package org.petehering.breakout;

import java.awt.Color;
import static java.awt.Color.*;
import java.awt.Font;
import java.awt.Graphics2D;
import static java.lang.System.currentTimeMillis;
import static org.petehering.breakout.Constants.*;
import org.petehering.sandbox.Game;
import org.petehering.sandbox.View;

class BreakoutGame implements Game
{
    private final Audio audio;
    private final Paddle paddle;
    private final Ball ball;
    private final Brick[] bricks;
    private final BreakoutInput input;
    private final Font countDownFont;
    private final Font hudFont;
    private boolean countingDown;
    private long countDownStartTime;
    private int countDown;
    private int livesRemaining;
    private int bricksRemaining;

    public BreakoutGame (BreakoutInput input) throws Exception
    {
        this.input = input;
        
        this.audio = new Audio ();
        audio.loadClip ("/pluck-midi-50.wav");
        audio.loadClip ("/pluck-midi-72.wav");
        audio.loadClip ("/pluck-midi-74.wav");
        audio.loadClip ("/pluck-midi-76.wav");
        audio.loadClip ("/pluck-midi-78.wav");
        
        this.paddle = new Paddle (WHITE, 0, VIEW_HEIGHT - 20, 80, 20);
        this.ball = new Ball (RED, (VIEW_WIDTH - 10) / 2, (VIEW_HEIGHT - 10) / 2, 20, 20);
        this.bricks = new Brick[50];
        this.countDownFont = Font.decode ("Arial-BOLD-32");
        this.hudFont = Font.decode ("Arial-BOLD-16");
        this.livesRemaining = 3;
        this.bricksRemaining = 50;
        
        Color color = BLUE;
        int i = 0;
        for (int r = 0; r < 5; r++)
        {
            int y = 80 + (r * 20);
            
            if (r > 3) color = YELLOW;
            else if (r > 0) color = GREEN;
            
            for (int c = 0; c < 10; c++)
            {
                int x = (c * 80) + 2;
                
                bricks[i] = new Brick (color, x, y, 76, 15);
                i += 1;
            }
        }
        
        startCountDown ();
    }
    
    private void reset ()
    {
        this.livesRemaining = 3;
        this.bricksRemaining = 50;
        
        for (Brick b : bricks)
        {
            b.setAlive (true);
        }
    }
    
    private void startPlay ()
    {
        this.ball.setPosition ((VIEW_WIDTH - 10) / 2, (VIEW_HEIGHT - 10) / 2);
        this.ball.setVelocity (0.0f, 0.35f);
    }
    
    private void updatePlay (long elapsed)
    {
        ball.update (elapsed);
        
        if (ball.getY () <= 0.0f)
        {
            ball.reverseYVelocity ();
            audio.playClip ("/pluck-midi-78.wav");
        }
        
        if (ball.getX () <= 0.0f || ball.getX () >= VIEW_WIDTH - ball.getWidth ())
        {
            ball.reverseXVelocity ();
            audio.playClip ("/pluck-midi-76.wav");
        }
        
        if (ball.hits (paddle))
        {
            ball.bounceOffOf (paddle);
            audio.playClip ("/pluck-midi-74.wav");
        }
        
        for (Brick b : bricks)
        {
            if (b.isAlive () && ball.hits (b))
            {
                audio.playClip ("/pluck-midi-72.wav");
                ball.reverseYVelocity ();
                b.setAlive (false);
                bricksRemaining -= 1;
                break;
            }
        }
        
        if (ball.getY () > VIEW_HEIGHT)
        {
            audio.playClip ("/pluck-midi-50.wav");
            livesRemaining -= 1;
            startCountDown ();
        }
    }
    
    private void startCountDown ()
    {
        countingDown = true;
        countDownStartTime = currentTimeMillis ();
        countDown = 3;
    }
    
    private void updateCountDown ()
    {
        long elapsed = currentTimeMillis () - countDownStartTime;
        
        if (elapsed < 1500L)
        {
            countDown = 3;
        }
        else if (elapsed < 2500L)
        {
            countDown = 2;
        }
        else if (elapsed < 3500L)
        {
            countDown = 1;
        }
        else
        {
            countingDown = false;
            startPlay ();
        }
    }

    @Override
    public void update (long elapsed)
    {
        paddle.move (input.getMousePoint ().x);
        
        if (livesRemaining <= 0 || bricksRemaining <= 0)
        {
            if (input.isKeyPressed (RESET))
            {
                reset ();
                startCountDown ();
            }
        }
        else if (countingDown)
        {
            updateCountDown ();
        }
        else
        {
            updatePlay (elapsed);
        }
        
        input.update ();
    }

    @Override
    public void render (View view)
    {
        Graphics2D g = view.getViewGraphics ();
        g.setColor (BLACK);
        g.fillRect (0, 0, VIEW_WIDTH, VIEW_HEIGHT);
        
        paddle.draw (g);
        
        for (Brick brick : bricks)
        {
            if (brick.isAlive ())
            {
                brick.draw (g);
            }
        }
        
        if (livesRemaining <= 0)
        {
            g.setFont (hudFont);
            g.setColor (WHITE);
            g.drawString ("PRESS ENTER TO PLAY AGAIN", 300, 320);
        }
        else if (countingDown)
        {
            g.setFont (countDownFont);
            g.setColor (CYAN);
            g.drawString ("" + countDown, 395, 320);
        }
        else
        {
            ball.draw (g);
        }
        
        g.setFont (hudFont);
        g.setColor (WHITE);
        g.drawString ("LIVES: " + livesRemaining, 10, VIEW_HEIGHT - 25);
        g.drawString ("BRICKS: " + bricksRemaining, 100, VIEW_HEIGHT - 25);
        
        view.present ();
    }

    @Override
    public void dispose ()
    {
        this.audio.dispose ();
    }
}
