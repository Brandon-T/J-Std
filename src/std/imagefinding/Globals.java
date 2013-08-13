package imagefinding;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Globals {
    private static int X, Y, Width, Height;
    private static Robot robot = null;
    private static Globals instance = new Globals();
    private static ImageHandler Handler = new ImageHandler();
    private static ImageFinder Finder = new ImageFinder();
    private static BufferedImage TargetImage = null;
    
    private Globals() {
        try {
            robot = new Robot();
        } catch (Exception Ex) {
        }
    }
    
    public static Globals getInstance() {
        return instance;
    }
    
    public static void MoveMouse(int X, int Y) {
        robot.mouseMove(X, Y);
    }
    
    public static void ClickMouse(int X, int Y, boolean LeftClick) {
        MoveMouse(X, Y);
        robot.mousePress(LeftClick ? InputEvent.BUTTON1_MASK : InputEvent.BUTTON3_MASK);
        robot.mouseRelease(LeftClick ? InputEvent.BUTTON1_MASK : InputEvent.BUTTON3_MASK);
    }
    
    public static void DragMouse(int FromX, int FromY, int ToX, int ToY) {
        MoveMouse(FromX, FromY);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        MoveMouse(ToX, ToY);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }
    
    public static void Sleep(int HowLong) {
        try {
            Thread.sleep(HowLong);
        } catch (Exception Ex) {
        }
    }
    
    public static void SleepRandomRange(int Min, int Max) {
        Sleep(Min + (int)(Math.random() * ((Max - Min) + 1)));
    }
    
    public static BufferedImage GetTarget() {
        return TargetImage;
    }
    
    public static void Target(int X, int Y, int Width, int Height) {
        try {
            TargetImage = null;
            TargetImage = Handler.Screenshot(X, Y, Width, Height);
            Globals.X = X;
            Globals.Y = Y;
            Globals.Width = Width;
            Globals.Height = Height;
            Finder.SetTargetImage(TargetImage);
        } catch (Exception Ex) {
        }
    }
    
    public static void SetToleranceSpeed(int Value) {
        Finder.SetColourToleranceSpeed(Value);
    }
    
    public static void ResetToleranceSpeed() {
        Finder.Reset();
    }
    
    public static int GetColourToleranceSpeed() {
        return Finder.GetColourToleranceSpeed();
    }
    
    public static boolean SimilarColours(Color FirstColour, Color SecondColour, int Tolerance) {
        Target(X, Y, Width, Height);
        return Finder.SimilarColours(FirstColour, SecondColour, Tolerance);
    }
    
    public static int CountColour(Color Colour, int X1, int Y1, int X2, int Y2) {
        Target(X, Y, Width, Height);
        return Finder.CountColour(Colour, X1, Y1, X2, Y2);
    }
    
    public static int CountColourTolerance(Color Colour, int X1, int Y1, int X2, int Y2, int Tolerance) {
        return Finder.CountColourTolerance(Colour, X1, Y1, X2, Y2, Tolerance);
    }
    
    public static boolean FindColour(Point P, Color Colour, int X1, int Y1, int X2, int Y2) {
        Target(X, Y, Width, Height);
        return Finder.FindColour(P, Colour, X1, Y1, X2, Y2);
    }
    
    public static boolean FindColours(ArrayList<Point> Points, Color Colour, int X1, int Y1, int X2, int Y2) {
        Target(X, Y, Width, Height);
        return Finder.FindColours(Points, Colour, X1, Y1, X2, Y2);
    }
    
    public static boolean FindColourTolerance(Point P, Color Colour, int X1, int Y1, int X2, int Y2, int Tolerance) {
        Target(X, Y, Width, Height);
        return Finder.FindColourTolerance(P, Colour, X1, Y1, X2, Y2, Tolerance);
    }
    
    public static boolean FindColoursTolerance(ArrayList<Point> Points, Color Colour, int X1, int Y1, int X2, int Y2, int Tolerance) {
        Target(X, Y, Width, Height);
        return Finder.FindColoursTolerance(Points, Colour, X1, Y1, X2, Y2, Tolerance);
    }
    
    public static boolean FindImage(BufferedImage ImageToFind, Point P) {
        Target(X, Y, Width, Height);
        return Finder.FindImage(ImageToFind, P);
    }
    
    public static boolean FindImageIn(BufferedImage ImageToFind, Point P, int X1, int Y1, int X2, int Y2) {
        Target(X, Y, Width, Height);
        return Finder.FindImageIn(ImageToFind, P, X1, Y1, X2, Y2);
    }
    
    boolean FindImageToleranceIn(BufferedImage ImageToFind, Point P, int X1, int Y1, int X2, int Y2, int Tolerance) {
        Target(X, Y, Width, Height);
        return Finder.FindImageToleranceIn(ImageToFind, P, X1, Y1, X2, Y2, Tolerance);
    }
}
