package std;

public final class Console {

    private static java.io.BufferedReader In;
    private static java.io.PrintStream Out;
    private static Console Instance = new Console();

    private Console() {
        Out = System.out;
        System.setErr(Out);
        In = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override public void run() {
                try {
                    Out.close();
                    In.close();
                } catch (java.io.IOException Ex) {
                    Ex.getStackTrace();
                }
            }
        });
    }

    public static char Read() {
        try {
            return (char) In.read();
        } catch (java.io.IOException Ex) {
            System.out.println(Ex.getStackTrace());
        }
        return 0;
    }

    public static String ReadLine() {
        try {
            return In.readLine();
        } catch (java.io.IOException Ex) {
            System.out.println(Ex.getStackTrace());
        }
        return null;
    }

    public static void Write(Object Text) {
        Out.print(Text);
    }
    
    public static void Write(Object Array[]) {
        Out.print(java.util.Arrays.toString(Array));
    }
    
    public static void WriteLine() {
        Out.println();
    }
    
    public static void WriteLine(Object Array[]) {
        Out.println(java.util.Arrays.toString(Array));
    }

    public static void WriteLine(Object Text) {
        Out.println(Text);
    }
    
    public static Console GetInstance() {
        return Instance;
    }
}
