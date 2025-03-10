import java.io.*;
public class Embed {
    private static final int LSB_MASK = 0xFE;

    public static void embedMessage(String mp3File, String outputFile, String message) throws IOException {
        byte[] mp3Bytes = readBytesFromFile(mp3File);

        // add a null terminator to the text
        byte[] messageBytes = (message + "\0").getBytes();

        embedData(mp3Bytes, messageBytes, 0); // Store message in mp3Bytes
        writeBytesToFile(outputFile, mp3Bytes);
    }

    private static void embedData(byte[] mp3Bytes, byte[] data, int startOffset) {
        int byteIndex = 0;
        for (int i = startOffset; i < mp3Bytes.length && byteIndex < data.length; i++) {
            if (i % 100 == 0) {//spread the text through the mp3 file
                for (int bit = 0; bit < 8; bit++) {
                    mp3Bytes[i] = (byte) ((mp3Bytes[i] & LSB_MASK) | ((data[byteIndex] >> bit) & 1));
                    i++; // Move to next mp3 byte for the next data bit
                }
                byteIndex++;
            }
        }
    }

    private static byte[] readBytesFromFile(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return data;
    }

    private static void writeBytesToFile(String filePath, byte[] data) throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(data);
        fos.close();
    }
}

