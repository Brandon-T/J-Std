package imagefinding;

import java.awt.*;

public class ImageFinding {
    public static void main(String[] args) {
        
        Point P = new Point();
        Globals.Target(0, 0, 765, 553);
        
        if (Globals.FindColour(P, new Color(16148025), 0, 0, 700, 500)) {
            Globals.MoveMouse(P.x, P.y);
        }
        
        if (Globals.FindColourTolerance(P, new Color(16148025), 0, 0, 765, 553, 1)) {
            Globals.ClickMouse(P.x, P.y, false);
        }
        
        if (Globals.FindColourTolerance(P, new Color(16148025), 0, 0, 765, 553, 1)) {
            Globals.DragMouse(P.x, P.y, P.x, P.y + 50);
        }
    }
}
