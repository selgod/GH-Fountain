package SimpleJavaFXPlayer;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Vector;




import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class Capture implements Runnable {
	AudioInputStream audioInputStream;
    Vector<Line2D.Double> lines = new Vector<Line2D.Double>();
    String errStr;
    Capture capture = new Capture();
    double duration, seconds, tenths;
    //File file;
    String fileName = "out.png";
    SamplingGraph samplingGraph;
    String waveformFilename;
    File file1;
    public static final int H_PIXEL_SIZE = 15;
    public static final int V_PIXEL_SIZE = 20;
    public static final double SONG_TIME = 10;
    Color imageBackgroundColor = new Color(20,20,20);

    TargetDataLine line;
    Thread thread;

    public void start() {
        errStr = null;
        thread = new Thread(this);
        thread.setName("Capture");
        thread.start();
    }

    public void stop() {
        thread = null;
    }

    private void shutDown(String message) {
        if ((errStr = message) != null && thread != null) {
            thread = null;
            samplingGraph.stop();                
            System.err.println(errStr);
        }
    }

    public void run() {

        duration = 0;
        audioInputStream = null;

        // define the required attributes for our line, 
        // and make sure a compatible line is supported.

        AudioFormat format = audioInputStream.getFormat();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, 
            format);

        if (!AudioSystem.isLineSupported(info)) {
            shutDown("Line matching " + info + " not supported.");
            return;
        }

        // get and open the target data line for capture.

        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format, line.getBufferSize());
        } catch (LineUnavailableException ex) { 
            shutDown("Unable to open the line: " + ex);
            return;
        } catch (SecurityException ex) { 
            shutDown(ex.toString());
            //JavaSound.showInfoDialog();
            return;
        } catch (Exception ex) { 
            shutDown(ex.toString());
            return;
        }

        // play back the captured audio data
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int frameSizeInBytes = format.getFrameSize();
        int bufferLengthInFrames = line.getBufferSize() / 8;
        int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
        byte[] data = new byte[bufferLengthInBytes];
        int numBytesRead;

        line.start();

        while (thread != null) {
            if((numBytesRead = line.read(data, 0, bufferLengthInBytes)) == -1) {
                break;
            }
            out.write(data, 0, numBytesRead);
        }

        // we reached the end of the stream.  stop and close the line.
        line.stop();
        line.close();
        line = null;

        // stop and close the output stream
        try {
            out.flush();
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // load bytes into the audio input stream for playback

        byte audioBytes[] = out.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);
        audioInputStream = new AudioInputStream(bais, format, audioBytes.length / frameSizeInBytes);

        long milliseconds = (long)((audioInputStream.getFrameLength() * 1000) / format.getFrameRate());
        duration = milliseconds / 1000.0;
        System.out.println(duration);

        try {
            audioInputStream.reset();
        } catch (Exception ex) { 
            ex.printStackTrace(); 
            return;
        }

        samplingGraph.createWaveForm(audioBytes);
    }
} // End class Capture    
