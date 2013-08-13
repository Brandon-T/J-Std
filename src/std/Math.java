package std;

public final class Math {
    private static Math Instance = new Math();
    
    private Math() {}
    
    public static double Currency(double Cost) {
        java.math.BigDecimal Price = java.math.BigDecimal.valueOf(Cost).setScale(2, java.math.BigDecimal.ROUND_HALF_UP);
        return Double.valueOf(Price.toString());
    }
    
    public static String FormatDecimal(double Value, int DecimalPlaces) {
        java.text.DecimalFormat Format = new java.text.DecimalFormat("#." + String.format("%-" + String.valueOf(DecimalPlaces) + "s", "").replace(' ', '0'));
        return Format.format(Value);
    }
    
    public static double RandomRange(double Min, double Max) {
        java.util.Random R = new java.util.Random();
        double Range = Max - Min;
        double Scaled = R.nextDouble() * Range;
        return Scaled + Min;
    }

    public static long Fibonachi(long Number) {
        return Number <= 1 ? Number : Fibonachi(Number - 1) + Fibonachi(Number - 2);
    }

    public static int ReverseNumber(int Number) {
        int Sum = 0;
        while (Number != 0) {
            Sum *= 10;
            Sum += Number % 10;
            Number /= 10;
        }
        return Sum;
    }

    public static String DecimalToBinary(int Value, int Padding) {
        String Result = new String();

        for (int I = Padding; I > 0; --I) {
            if ((Value & (1 << (I - 1))) != 0) {
                Result += '1';
            } else {
                Result += '0';
            }
        }

        return Result;
    }

    public static int BinaryToDecimal(String Value) {
        int Dec = 0, Bin = Integer.parseInt(Value);
        boolean Negative = ((Value.length() == 8) && (Value.charAt(0) == '1'));

        for (int I = 0; Bin > 0; ++I) {
            if (Bin % 10 == 1) {
                Dec += (1 << I);
            }
            Bin /= 10;
        }

        return Negative ? (Dec -= (1 << 8)) : Dec;
    }
    
    public Math GetInstance() {
        return Instance;
    }
}
