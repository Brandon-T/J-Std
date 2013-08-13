package std;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;

public final class Image {
    private int ByteSize;
    
    private BufferedImage ImageFromBytes(byte[] Bytes, int Width, int Height) {
        BufferedImage Image = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_BGR);
        WritableRaster raster = (WritableRaster) Image.getData();
        raster.setPixels(0, 0, Width, Height, ByteBufferToIntBuffer(Bytes));
        Image.setData(raster);
        return Image;
    }
    
    private BufferedImage ImageFromByteBuffer(ByteBuffer ByteBuffer, int Width, int Height) {
        byte Bytes[] = new byte[ByteSize];
        ByteBuffer.get(Bytes);
        return ImageFromBytes(Bytes, Width, Height);
    }

    private int[] ByteBufferToIntBuffer(byte[] Data) {
        int IntBuffer[] = new int[Data.length];
       
        for (int I = 0; I < Data.length; I++) {
            IntBuffer[I] = (int)Data[I] & 0xFF;
        }
        return IntBuffer;
    }
}
