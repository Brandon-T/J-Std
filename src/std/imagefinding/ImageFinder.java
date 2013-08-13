package imagefinding;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageFinder {

    private int CTSValue;
    private double TMod, HueMod, SatMod;
    private BufferedImage TargetImage;

    public void SetTargetImage(BufferedImage IMG) {
        this.TargetImage = null;
        this.TargetImage = IMG;
    }

    public void Reset() {
        CTSValue = 1;
        TMod = 4.0;
        HueMod = 0.2;
        SatMod = 0.2;
    }

    public int Colorref(int R, int G, int B) {
        return (R | (G << 8) | (B << 16));
    }

    public Color Colorref(int Colour) {
        int R = (Colour & 0x00ff0000) >> 16;
        int G = (Colour & 0x0000ff00) >> 8;
        int B = (Colour & 0x000000ff);
        return new Color(R, G, B);
    }
    
    public int ColourSwap(int Colour) {
        return ((Colour & 0xFF000000)|((Colour & 0xFF0000) >> 16)|(Colour & 0x00FF00)|((Colour & 0x0000FF) << 16));
    }
    
    public Color ColourSwap(Color Colour) {
        return new Color(ColourSwap(Colour.getRGB()));
    }

    public int GetColourToleranceSpeed() {
        return this.CTSValue;
    }

    void SetColourToleranceSpeed(int Value) {
        CTSValue = Value >= -1 && Value <= 3 ? Value : 1;
    }

    void GetSpeed2Modifiers(Double HueMod, Double SatMod) {
        HueMod = this.HueMod;
        SatMod = this.SatMod;
    }

    void SetSpeed2Modifiers(double HueMod, double SatMod) {
        this.HueMod = HueMod;
        this.SatMod = SatMod;
    }

    double GetSpeed3Modifier() {
        return TMod;
    }

    void SetSpeed3Modifier(double TMod) {
        this.TMod = TMod;
    }

    public boolean SimilarColours(Color FirstColour, Color SecondColour, int Tolerance) {
        switch (CTSValue) {
            case -1:
                return CTSN(FirstColour, SecondColour);
            case 0:
                return CTS0(FirstColour, SecondColour, Tolerance);
            case 1:
                return CTS1(FirstColour, SecondColour, Tolerance);
            case 2:
                return CTS2(FirstColour, SecondColour, Tolerance);
            case 3:
                return CTS3(FirstColour, SecondColour, Tolerance);
            default:
                return CTS1(FirstColour, SecondColour, Tolerance);
        }
    }

    private boolean CTSN(Color FirstCol, Color SecondCol) {
        return (FirstCol.getRed() == SecondCol.getRed() && FirstCol.getGreen() == SecondCol.getGreen() && FirstCol.getBlue() == SecondCol.getBlue());
    }

    private boolean CTS0(Color FirstCol, Color SecondCol, int Tol) {
        return Math.abs(FirstCol.getRed() - SecondCol.getRed()) <= Tol && Math.abs(FirstCol.getGreen() - SecondCol.getGreen()) <= Tol && Math.abs(FirstCol.getBlue() - SecondCol.getBlue()) <= Tol;
    }

    private boolean CTS1(Color FirstCol, Color SecondCol, int Tol) {
        return ((FirstCol.getRed() - SecondCol.getRed())*(FirstCol.getRed() - SecondCol.getRed()) + (FirstCol.getGreen() - SecondCol.getGreen())*(FirstCol.getGreen() - SecondCol.getGreen()) + (FirstCol.getBlue() - SecondCol.getBlue())*(FirstCol.getBlue() - SecondCol.getBlue())) <= (Tol * Tol);
    }

    private boolean CTS2(Color FirstCol, Color SecondCol, int Tol) {
        HSL HFirstCol = new HSL();
        HSL HSecondCol = new HSL();

        if (FirstCol.getRGB() != 0) {
            HFirstCol = ColourSpace.getInstance().Hsl(FirstCol);
        }

        if (SecondCol.getRGB() != 0) {
            HSecondCol = ColourSpace.getInstance().Hsl(SecondCol);
        }

        return (Math.abs(HSecondCol.Hue - HFirstCol.Hue) <= (Tol * HueMod)) && (Math.abs(HSecondCol.Sat - HFirstCol.Sat) <= (Tol * SatMod));
    }

    private boolean CTS3(Color FirstCol, Color SecondCol, int Tol) {
        LAB LabFirstCol = new LAB(), LabSecondCol = new LAB();
        if (FirstCol.getRGB() != 0) {
            LabFirstCol = ColourSpace.getInstance().Lab(ColourSpace.getInstance().XYZ(FirstCol));
        }

        if (SecondCol.getRGB() != 0) {
            LabSecondCol = ColourSpace.getInstance().Lab(ColourSpace.getInstance().XYZ(SecondCol));
        }

        double L = (LabSecondCol.L - LabFirstCol.L);
        double A = (LabSecondCol.A - LabFirstCol.A);
        double B = (LabSecondCol.B - LabFirstCol.B);

        return ((L * L) + (A * A) + (B * B)) <= Math.ceil(Math.sqrt(Tol * TMod));
    }

    public int CountColour(Color Colour, int X1, int Y1, int X2, int Y2) {
        int TemporaryCTS = this.CTSValue;
        SetColourToleranceSpeed(-1);
        int Result = CountColourTolerance(Colour, X1, Y1, X2, Y2, 0);
        SetColourToleranceSpeed(TemporaryCTS);
        return Result;
    }

    public int CountColourTolerance(Color Colour, int X1, int Y1, int X2, int Y2, int Tolerance) {
        int Result = 0;

        for (int I = Y1; I < Y2; ++I) {
            for (int J = X1; J < X2; ++J) {
                if (SimilarColours(ColourSwap(Colour), new Color(TargetImage.getRGB(J, I)), Tolerance)) {
                    ++Result;
                }
            }
        }

        return Result;
    }

    public boolean FindColour(Point P, Color Colour, int X1, int Y1, int X2, int Y2) {
        int TemporaryCTS = CTSValue;
        SetColourToleranceSpeed(-1);
        boolean Result = FindColourTolerance(P, Colour, X1, Y1, X2, Y2, 0);
        SetColourToleranceSpeed(TemporaryCTS);
        return Result;
    }

    public boolean FindColours(ArrayList<Point> Points, Color Colour, int X1, int Y1, int X2, int Y2) {
        int TemporaryCTS = CTSValue;
        SetColourToleranceSpeed(-1);
        boolean Result = FindColoursTolerance(Points, Colour, X1, Y1, X2, Y2, 0);
        SetColourToleranceSpeed(TemporaryCTS);
        return Result;
    }

    public boolean FindColourTolerance(Point P, Color Colour, int X1, int Y1, int X2, int Y2, int Tolerance) {
        
        for (int I = Y1; I < Y2; ++I) {
            for (int J = X1; J < X2; ++J) {
                if (SimilarColours(new Color(TargetImage.getRGB(J, I)), ColourSwap(Colour), Tolerance)) {
                    P.setLocation(J, I);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean FindColoursTolerance(ArrayList<Point> Points, Color Colour, int X1, int Y1, int X2, int Y2, int Tolerance) {
        for (int I = Y1; I < Y2; ++I) {
            for (int J = X1; J < X2; ++J) {
                if (SimilarColours(new Color(TargetImage.getRGB(J, I)), ColourSwap(Colour), Tolerance)) {
                    Points.add(new Point(J, I));
                }
            }
        }
        return (!Points.isEmpty());
    }

    public boolean FindImage(BufferedImage ImageToFind, Point P) {
        return FindImageIn(ImageToFind, P, 0, 0, TargetImage.getWidth(), TargetImage.getHeight());
    }

    public boolean FindImageIn(BufferedImage ImageToFind, Point P, int X1, int Y1, int X2, int Y2) {
        int TemporaryCTS = CTSValue;
        boolean Result = FindImageToleranceIn(ImageToFind, P, X1, Y1, X2, Y2, 0);
        SetColourToleranceSpeed(TemporaryCTS);
        return Result;
    }

    boolean FindImageToleranceIn(BufferedImage ImageToFind, Point P, int X1, int Y1, int X2, int Y2, int Tolerance) {
        int dX = (X2 - X1) - (ImageToFind.getWidth() - 1);
        int dY = (Y2 - Y1) - (ImageToFind.getHeight() - 1);

        for (int I = 0; I < dY; ++I) {
            for (int J = 0; J < dX; ++J) {
                Skip:
                for (int YY = 0; YY < ImageToFind.getHeight(); ++YY) {
                    for (int XX = 0; XX < ImageToFind.getWidth(); ++XX) {
                        if (ImageToFind.getRGB(XX, YY) != 0) {
                            if (!SimilarColours(new Color(ImageToFind.getRGB(XX, YY)), new Color(TargetImage.getRGB(XX + J, YY + I)), Tolerance)) {
                                ++J;
                                break Skip; //Java doesn't have goto :S
                            }
                        }
                    }
                }

                P.setLocation(J + X1, I + Y1);
                return true;
            }
        }

        P.setLocation(-1, -1);
        return false;
    }
}
