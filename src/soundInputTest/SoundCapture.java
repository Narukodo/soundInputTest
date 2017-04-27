package soundInputTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat; 
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.DataLine; 
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine; 
 
public class SoundCapture{ 

	static TargetDataLine targetLine;
	static SourceDataLine sourceLine;
	static AudioFormat format = new AudioFormat(44100, 16, 2, true, true);

	static DataLine.Info targetInfo = new DataLine.Info(TargetDataLine.class, format);
	static DataLine.Info sourceInfo = new DataLine.Info(SourceDataLine.class, format);
	
	public static void record() throws IOException{

		AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
        TargetDataLine microphone;
        AudioInputStream audioInputStream;
        SourceDataLine sourceDataLine;
        try {
            microphone = AudioSystem.getTargetDataLine(format);

            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int numBytesRead;
            int CHUNK_SIZE = 1024;
            byte[] data = new byte[microphone.getBufferSize() / 5];
            microphone.start();

            int bytesRead = 0;

            try {
                while (bytesRead < 100000) { // Just so I can test if recording
                                                // my mic works...
                    numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
                    bytesRead = bytesRead + numBytesRead;
                    System.out.println(bytesRead);
                    out.write(data, 0, numBytesRead);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            byte audioData[] = out.toByteArray();
            // Get an input stream on the byte array
            // containing the data
            InputStream byteArrayInputStream = new ByteArrayInputStream(
                    audioData);
            audioInputStream = new AudioInputStream(byteArrayInputStream,format, audioData.length / format.getFrameSize());
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            sourceDataLine.open(format);
            sourceDataLine.start();
            
            FrameBuffer frameStream = new FrameBuffer(audioInputStream); //austream is the audiostream
            int frame = frameStream.numberFrames() - 1;
            while (frame >= 0) {
                 sourceDataLine.write(frameStream.getFrame(frame), 0, frameStream.frameSize());
                 frame--;
            }
            // Block and wait for internal buffer of the
            // data line to empty.
            sourceDataLine.drain();
            sourceDataLine.close();
            microphone.close();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
	}
}