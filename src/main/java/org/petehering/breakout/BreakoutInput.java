package org.petehering.breakout;

import static java.awt.event.KeyEvent.*;
import static org.petehering.breakout.Constants.*;
import org.petehering.sandbox.DesktopInput;

class BreakoutInput extends DesktopInput
{

    public BreakoutInput ()
    {
        super (3);
    }

    @Override
    public int keyToIndex (int keyCode)
    {
        switch (keyCode)
        {
            case VK_S:
            case VK_J:
            case VK_LEFT:
                return LEFT;
            case VK_F:
            case VK_L:
            case VK_RIGHT:
                return RIGHT;
            case VK_ENTER:
                return RESET;
            default:
                return -1;
        }
    }
}
