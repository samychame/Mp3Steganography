import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            String inputMp3 = "AudioFiles/input.mp3";
            String outputMp3 = "OutputFiles/output.mp3";
            String secretMessage = "Hello world, my name is samy";

            Embed.embedMessage(inputMp3, outputMp3, secretMessage);
            System.out.println("Message embedded successfully!");

            String extractedMessage = Extract.extractMessage(outputMp3);
            System.out.println("Extracted Message: " + extractedMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
