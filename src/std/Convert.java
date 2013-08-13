package std;

public final class Convert {
    private static Convert Instance = new Convert();
    
    private Convert(){}

    public static Out IntTryParse(String Input) {
        try {
            return new Out(Integer.parseInt(Input), true);
        } catch (NumberFormatException Ex) {
            Ex.getStackTrace();
            return new Out(0, false);
        }
    }

    public static Out DoubleTryParse(String Input) {
        try {
            return new Out(Double.parseDouble(Input), true);
        } catch (NumberFormatException Ex) {
            Ex.getStackTrace();
            return new Out(0, false);
        }
    }

    public static Convert GetInstance() {
        return Instance;
    }
}