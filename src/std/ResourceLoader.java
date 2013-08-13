package std;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.ImageIcon;

public final class ResourceLoader {

    URI Location = null;

    ResourceLoader(String FilePath) {
        try {
            this.Location = ResourceLoader.class.getResource(FilePath).toURI();
        } catch (Exception Ex) {
            Ex.toString();
        }
    }

    File ToFile() {
        return new File(Location);
    }

    byte[] ReadAsByteArray() throws FileNotFoundException {
        File F = ToFile();
        byte[] Result = new byte[(int) F.length()];
        try {
            InputStream Input = null;
            try {
                int TotalBytesRead = 0;
                Input = new BufferedInputStream(new FileInputStream(F));
                while (TotalBytesRead < Result.length) {
                    int BytesRemaining = Result.length - TotalBytesRead;
                    int BytesRead = Input.read(Result, TotalBytesRead, BytesRemaining);

                    if (BytesRead > 0) {
                        TotalBytesRead = TotalBytesRead + BytesRead;
                    }
                }
            } finally {
                Input.close();
            }
        } catch (FileNotFoundException Ex) {
            Ex.toString();
        } catch (IOException Ex) {
            Ex.toString();
        }
        return Result;
    }

    void WriteByteArray(byte[] Bytes) throws FileNotFoundException {
        try {
            OutputStream Output = null;
            try {
                File F = ToFile();
                Output = new BufferedOutputStream(new FileOutputStream(F));
                Output.write(Bytes);
            } finally {
                Output.close();
            }
        } catch (Exception Ex) {
            Ex.toString();
        }
    }

    List<String> ReadAsFileToList() throws IOException {
        return Files.readAllLines(Paths.get(Location), StandardCharsets.UTF_8);
    }

    void WriteListToFile(List<String> Lines) throws IOException {
        Files.write(Paths.get(Location), Lines, StandardCharsets.UTF_8);
    }

    ImageIcon ToImage() {
        ImageIcon Result = null;
        try {
            Result = new ImageIcon(Location.toURL());
        } catch (Exception Ex) {
            Ex.toString();
        }
        return Result;
    }
}
