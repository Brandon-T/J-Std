package imagefinding;

import java.awt.Color;

public class ColourSpace {

    public XYZ XYZ(Color C) {
        XYZ Result = new XYZ();
        double Red = (C.getRed() / 255.0);
        double Green = (C.getGreen() / 255.0);
        double Blue = (C.getBlue() / 255.0);

        Red = (Red > 0.04045) ? Math.pow(((Red + 0.055) / 1.055), 2.4) * 100 : Red / 12.92;
        Green = (Green > 0.04045) ? Math.pow(((Green + 0.055) / 1.055), 2.4) * 100.0 : Green / 12.92;
        Blue = (Blue > 0.04045) ? Math.pow(((Blue + 0.055) / 1.055), 2.4) * 100.0 : Blue / 12.92;
        Result.X = Red * 0.4124 + Green * 0.3576 + Blue * 0.1805;
        Result.Y = Red * 0.2126 + Green * 0.7152 + Blue * 0.0722;
        Result.Z = Red * 0.0193 + Green * 0.1192 + Blue * 0.9505;
        return Result;
    }

    public Color Rgba(XYZ Xyz) {
        double X = Xyz.X / 100.0;
        double Y = Xyz.Y / 100.0;
        double Z = Xyz.Z / 100.0;

        double Red = X * 3.2406 + Y * -1.5372 + Z * -0.4986;
        double Green = X * -0.9689 + Y * 1.8758 + Z * 0.0415;
        double Blue = X * 0.0557 + Y * -0.2040 + Z * 1.0570;

        Red = (Red > 0.0031308) ? (1.055 * Math.pow(Red, (1 / 2.4)) - 0.055) : 12.92 * Red;
        Green = (Green > 0.0031308) ? (1.055 * Math.pow(Green, (1 / 2.4)) - 0.055) : 12.92 * Green;
        Blue = (Blue > 0.0031308) ? (1.055 * Math.pow(Blue, (1 / 2.4)) - 0.055) : 12.92 * Blue;

        long R = Math.round(Red * 255.0);
        long G = Math.round(Green * 255.0);
        long B = Math.round(Blue * 255.0);

        return new Color(R, G, B);
    }

    public HSL Hsl(Color Rgba) {
        HSL Result = new HSL();
        double Red, Green, Blue;
        Red = Rgba.getRed() / 255.0;
        Green = Rgba.getGreen() / 255.0;
        Blue = Rgba.getBlue() / 255.0;
        double Max = Red > Blue ? Red > Green ? Red : Green : Blue > Green ? Blue : Green;
        double Min = Red < Blue ? Red < Green ? Red : Green : Blue < Green ? Blue : Green;
        Result.Lum = (Max + Min) / 2.0;

        if (Max == Min) {
            Result.Hue = Result.Sat = 0.0;
        } else {
            double Delta = Max - Min;
            Result.Sat = Result.Lum > 0.5 ? Delta / (2.0 - Max - Min) : Delta / (Max + Min);
            if (Max == Red) {
                Result.Hue = (Green - Blue) / Delta + (Green < Blue ? 6.0 : 0.0);
            } else if (Max == Green) {
                Result.Hue = (Blue - Red) / Delta + 2.0;
            } else {
                Result.Hue = (Red - Green) / Delta + 4.0;
            }

            Result.Hue /= 6.0;
        }

        Result.Hue *= 100.0;
        Result.Sat *= 100.0;
        Result.Lum *= 100.0;
        return Result;
    }

    private static double HSLToRGBHelper(double I, double J, double Hue) {
        if (Hue < 0.0) {
            Hue += 1.0;
        }
        if (Hue > 1.0) {
            Hue -= 1.0;
        }
        if (Hue < (1.0 / 6.0)) {
            return Math.round((I + (J - I) * 6.0 * Hue) * 255.0);
        } else if (Hue < (1.0 / 2.0)) {
            return Math.round(J * 255.0);
        } else if (Hue < (2.0 / 3.0)) {
            return Math.round((I + (J - I) * 6.0 * ((2.0 / 3.0) - Hue)) * 255.0);
        } else {
            return Math.round(I * 255.0);
        }
    }
    
    public Color Rgba(HSL Hsl) {
        double H = Hsl.Hue / 100.0;
        double S = Hsl.Sat / 100.0;
        double L = Hsl.Lum / 100.0;
        Color Result = null;

        if (S == 0.0) {
            double Value = L * 255.0;
            Result = new Color((int) Value, (int) Value, (int) Value);
        } else {
            double J = (L < 0.5) ? (L * (1.0 + S)) : ((L + S) - (S * L));
            double I = 2.0 * L - J;

            double R = HSLToRGBHelper(I, J, H + (1.0 / 3.0));
            double G = HSLToRGBHelper(I, J, H);
            double B = HSLToRGBHelper(I, J, H - (1.0 / 3.0));
            Result = new Color((int) R, (int) G, (int) B);
        }
        return Result;
    }

    public LAB Lab(XYZ Xyz) {
        double X = Xyz.X / 95.047;
        double Y = Xyz.Y / 100.000;
        double Z = Xyz.Z / 108.883;

        X = (X > 0.008856) ? Math.pow(X, (1.0 / 3.0)) : (X * 7.787) + (16.0 / 116.0);
        Y = (Y > 0.008856) ? Math.pow(Y, (1.0 / 3.0)) : (Y * 7.787) + (16.0 / 116.0);
        Z = (Z > 0.008856) ? Math.pow(Z, (1.0 / 3.0)) : (Z * 7.787) + (16.0 / 116.0);

        LAB Result = new LAB();
        Result.L = ((Y * 116.0) - 16.0);
        Result.A = ((X - Y) * 500);
        Result.B = ((Y - Z) * 200);
        return Result;
    }

    public XYZ Xyz(LAB Lab) {
        double Y = (Lab.L + 16.0) / 116.0;
        double X = ((Lab.A / 500.0) + Y);
        double Z = (Y - (Lab.B / 200.0));

        Y = (Math.pow(Y, 3) > 0.008856) ? Math.pow(Y, 3) : ((Y - (16.0 / 116.0)) / 7.787);
        X = (Math.pow(X, 3) > 0.008856) ? Math.pow(X, 3) : ((X - (16.0 / 116.0)) / 7.787);
        Z = (Math.pow(Z, 3) > 0.008856) ? Math.pow(Z, 3) : ((Z - (16.0 / 116.0)) / 7.787);

        XYZ Result = new XYZ();
        Result.X = (X * 95.047);
        Result.Y = (Y * 100.000);
        Result.Z = (Z * 108.883);
        return Result;
    }
    
    public static ColourSpace Instance = new ColourSpace();

    private ColourSpace() {
    }

    public static ColourSpace getInstance() {
        return Instance;
    }
}
