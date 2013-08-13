package std;

public final class Memory {

    private static final Memory Instance = new Memory();

    private Memory() {
    }

    public Memory GetInstance() {
        return Instance;
    }

    public static sun.misc.Unsafe getUnsafe() {
        try {
            java.lang.reflect.Field F = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            F.setAccessible(true);
            return (sun.misc.Unsafe) F.get(null);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException("Error! Invalid Field.");
        }
    }

    public static long sizeof(Object object) {
        sun.misc.Unsafe unsafe = getUnsafe();
        return unsafe.getAddress(toUnsigned(unsafe.getInt(object, 4L)) + 12L);
    }

    public static long addressOf(Object O) {
        Object[] array = new Object[]{O};
        sun.misc.Unsafe unsafe = getUnsafe();
        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        int addressSize = unsafe.addressSize();
        long objectAddress = 0;
        
        switch (addressSize) {
            case 4:
                objectAddress = unsafe.getInt(array, baseOffset);
                break;
            case 8:
                objectAddress = unsafe.getLong(array, baseOffset);
                break;
            default:
                throw new Error("Unsupported Address Size: " + addressSize);
        }

        return (objectAddress);
    }

    public static long allocateMemory(long byteSize) {
        return getUnsafe().allocateMemory(byteSize);
    }

    public static void freeMemory(long Address) {
        getUnsafe().freeMemory(Address);
    }

    public static void copyMemory(long Source, long DestinationAddress, int byteSize) {
        getUnsafe().copyMemory(Source, DestinationAddress, byteSize);
    }

    public static void setMemory(long Address, long bytes, byte value) {
        getUnsafe().setMemory(Address, bytes, value);
    }

    private static long toUnsigned(int value) {
        if (value >= 0) {
            return value;
        }
        return (~0L >>> 32) & value;
    }
}
