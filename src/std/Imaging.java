package std;

import java.io.IOException;

public final class Imaging {

    private RGBA Pixels[];
    private int Width, Height, BitsPerPixel;

    public Imaging(String FilePath) throws java.io.FileNotFoundException, IOException, Exception {
        java.io.DataInputStream IOStream = new java.io.DataInputStream(new java.io.FileInputStream(FilePath));

        byte Header[] = new byte[IOStream.available()];
        if (IOStream.read(Header, 0, 54) <= 0) {
            IOStream.close();
            throw new IOException("Un-able to read file header.");
        }
        
        if (Header[0] != 'B' && Header[1] != 'M') {
            IOStream.close();
            throw new IOException("Invalid File Format!");
        }
        
        if (Header[28] != 24 && Header[28] != 32) {
            IOStream.close();
            throw new IOException("Invalid Bits Per Pixel. 24 or 32 Bit Image Required.");
        }
        
        BitsPerPixel = Header[28];
        Width = Header[18] + (Header[19] << 8);
        Height = Header[22] + (Header[23] << 8);
        int PixelsOffset = Header[10] + (Header[11] << 8);
        int Size = ((Width * BitsPerPixel + 31) / 32) * 4 * Height;
        
        IOStream.read(Header, PixelsOffset, Size);
        IOStream.close();
        
        int K = 0;
        Pixels = new RGBA[Width * Height];
        
        for (int I = 0; I < Height; ++I) {
            for (int J = 0; J < Width; ++J) {
                Pixels[(Height - 1 - I) * Width + J] = new RGBA();
                Pixels[(Height - 1 - I) * Width + J].B = Header[K++];
                Pixels[(Height - 1 - I) * Width + J].G = Header[K++];
                Pixels[(Height - 1 - I) * Width + J].R = Header[K++];
                Pixels[(Height - 1 - I) * Width + J].A = (byte)(BitsPerPixel > 24 ? Header[K++] : 0xFF);
            }
            
            if (BitsPerPixel == 24) {
                K += (-Width * 3) & 3;
            }
        }
    }

    public Imaging(Imaging Img) {
    }

    public Imaging(byte Pixels[], int Width, int Height, int BitsPerPixel) {
    }

    public Imaging(RGBA Pixels[]) {
    }
    
    public RGBA[] Pixels() {
        return this.Pixels;
    }
    
    private boolean BytesEqual(byte[] One, byte[] Two) {
        for (int I = 0; I < One.length; ++I)
        {
            if (One[I] != Two[I]) {
                return false;
            }
        }
        return true;
    }

    private int ValidImage(byte[] ImageBytes) {
        byte[] GIFBytesOne = {0x47, 0x49, 0x46, 0x38, 0x37, 0x61};
        byte[] GIFBytesTwo = {0x47, 0x49, 0x46, 0x38, 0x39, 0x61};
        //byte[] PNGBytes = {0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};
        byte[] BMPBytes = {0x42, 0x4D};
        //byte[] JPGBytes = {0xFF, 0xD8, 0xFF};
        byte[] JPEGBytes = {0x00, 0x00, 0x00, 0x0C, 0x6A, 0x50, 0x20, 0x20};
        //byte[] TIFFMonoChrome = {0x0C, 0xED};
        byte[] TIFFOne = {0x49, 0x20, 0x49};
        byte[] TIFFTwo = {0x49, 0x49, 0x2A, 0x00};
        byte[] TIFFThree = {0x4D, 0x4D, 0x00, 0x2A};
        byte[] TIFFFour = {0x4D, 0x4D, 0x00, 0x2B};

        byte[][] All = {GIFBytesOne, GIFBytesTwo, BMPBytes, JPEGBytes, TIFFOne, TIFFTwo, TIFFThree, TIFFFour};

        int I = 0;
        for (byte[] Bytes : All) {
            if (BytesEqual(Bytes, ImageBytes)) {
                return I;
            }
            ++I;
        }
        return -1;
    }
}
