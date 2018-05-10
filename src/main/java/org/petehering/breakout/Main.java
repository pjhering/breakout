package org.petehering.breakout;

import static org.petehering.breakout.Constants.*;
import org.petehering.sandbox.DesktopApp;
import org.petehering.sandbox.DesktopConfig;

public class Main
{
    public static void main (String[] args) throws Exception
    {
        BreakoutInput input = new BreakoutInput ();
        DesktopConfig cfg = new DesktopConfig ();
        cfg.input = input;
        cfg.game = new BreakoutGame (input);
        cfg.title = VIEW_TITLE;
        cfg.width = VIEW_WIDTH;
        cfg.height = VIEW_HEIGHT;
        DesktopApp app = new DesktopApp (cfg);
        app.start ();
    }
}
