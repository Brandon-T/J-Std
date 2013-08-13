package imagefinding;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;

public final class ImageHandler {
    private int ByteSize;
    
    public BufferedImage Screenshot(int X, int Y, int Width, int Height) throws AWTException {
        Rectangle Rect = new Rectangle(X, Y, Width, Height);
        return new Robot().createScreenCapture(Rect);
    }
    
    public BufferedImage ImageFromBytes(byte[] Bytes, int Width, int Height) {
        BufferedImage Image = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_BGR);
        WritableRaster raster = (WritableRaster) Image.getData();
        raster.setPixels(0, 0, Width, Height, ByteBufferToIntBuffer(Bytes));
        Image.setData(raster);
        return Image;
    }
    
    public BufferedImage ImageFromByteBuffer(ByteBuffer ByteBuffer, int Width, int Height) {
        byte Bytes[] = new byte[ByteSize];
        ByteBuffer.get(Bytes);
        return ImageFromBytes(Bytes, Width, Height);
    }

    public int[] ByteBufferToIntBuffer(byte[] Data) {
        int IntBuffer[] = new int[Data.length];
       
        for (int I = 0; I < Data.length; I++) {
            IntBuffer[I] = (int)Data[I] & 0xFF;
        }
        return IntBuffer;
    }
}
