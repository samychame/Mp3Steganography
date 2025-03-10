import java.io.*;

public class Extract {
    public static String extractMessage(String mp3File) throws IOException {
        byte[] mp3Bytes = readBytesFromFile(mp3File);

        byte[] extractedData = extractData(mp3Bytes, 0); // Extract all available bits
        String message = new String(extractedData);

        // Trim everything after the first `\0`
//        int nullIndex = message.indexOf("\0");
//        if (nullIndex != -1) {
//            message = message.substring(0, nullIndex);
//        }

        return message;
    }

    private static byte[] extractData(byte[] mp3Bytes, int startOffset) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte currentByte = 0;

        for (int i = startOffset; i < mp3Bytes.length; i++) {
            if (i % 100 == 0) {
                for (int bit = 0; bit < 8; bit++) {
                    currentByte |= ((mp3Bytes[i] & 1) << bit);
                    i++; // Move to next byte
                }
                if (currentByte == 0) break; // Stop at null terminator
                buffer.write(currentByte);
                currentByte = 0;
            }
        }
        return buffer.toByteArray();
    }

    private static byte[] readBytesFromFile(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return data;
    }
}

