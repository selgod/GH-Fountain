package SimpleJavaFXPlayer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;


class SamplingGraph implements Runnable {
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

    private Thread thread;
    private Font font10 = new Font("serif", Font.PLAIN, 10);
    private Font font12 = new Font("serif", Font.PLAIN, 12);
    Color jfcBlue = new Color(000, 000, 255);
    Color pink = new Color(255, 175, 175);


    public SamplingGraph() {
    }


    public void createWaveForm(byte[] audioBytes) {

        lines.removeAllElements();  // clear the old vector

        AudioFormat format = audioInputStream.getFormat();
        if (audioBytes == null) {
            try {
                audioBytes = new byte[
                    (int) (audioInputStream.getFrameLength() 
                    * format.getFrameSize())];
                audioInputStream.read(audioBytes);
            } catch (Exception ex) { 
                //reportStatus(ex.getMessage());
                return; 
            }
        }
        int w = (int)tenths*H_PIXEL_SIZE;
        int h = 280;
        int[] audioData = null;
        if (format.getSampleSizeInBits() == 16) {
             int nlengthInSamples = audioBytes.length / 2;
             audioData = new int[nlengthInSamples];
             if (format.isBigEndian()) {
                for (int i = 0; i < nlengthInSamples; i++) {
                     /* First byte is MSB (high order) */
                     int MSB = (int) audioBytes[2*i];
                     /* Second byte is LSB (low order) */
                     int LSB = (int) audioBytes[2*i+1];
                     audioData[i] = MSB << 8 | (255 & LSB);
                 }
             } else {
                 for (int i = 0; i < nlengthInSamples; i++) {
                     /* First byte is LSB (low order) */
                     int LSB = (int) audioBytes[2*i];
                     /* Second byte is MSB (high order) */
                     int MSB = (int) audioBytes[2*i+1];
                     audioData[i] = MSB << 8 | (255 & LSB);
                 }
             }
         } else if (format.getSampleSizeInBits() == 8) {
             int nlengthInSamples = audioBytes.length;
             audioData = new int[nlengthInSamples];
             if (format.getEncoding().toString().startsWith("PCM_SIGN")) {
                 for (int i = 0; i < audioBytes.length; i++) {
                     audioData[i] = audioBytes[i];
                 }
             } else {
                 for (int i = 0; i < audioBytes.length; i++) {
                     audioData[i] = audioBytes[i] - 128;
                 }
             }
        }

        int frames_per_pixel = audioBytes.length / format.getFrameSize()/w;
        byte my_byte = 0;
        double y_last = 0;
        int numChannels = format.getChannels();
        for (double x = 0; x < w && audioData != null; x++) {
            int idx = (int) (frames_per_pixel * numChannels * x);
            if (format.getSampleSizeInBits() == 8) {
                 my_byte = (byte) audioData[idx];
            } else {
                 my_byte = (byte) (128 * audioData[idx] / 32768 );
            }
            double y_new = (double) (h * (128 - my_byte) / 256);
            lines.add(new Line2D.Double(x, y_last, x, y_new));
            y_last = y_new;
        }
        saveToFile();
    }


    public void saveToFile() {            
        int w = 33462;
        int h = 280;
        int INFOPAD = 15;

        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bufferedImage.createGraphics();

        createSampleOnGraphicsContext(w, h, INFOPAD, g2);            
        g2.dispose();
        // Write generated image to a file
        try {
            // Save as PNG
            File file = new File(fileName);
            file1 = file;
            System.out.println(file.getAbsolutePath());
            ImageIO.write(bufferedImage, "png", file);
            //JOptionPane.showMessageDialog(null, 
              //      new JLabel(new ImageIcon(fileName)));
        } catch (IOException e) {
        }
    }


    private void createSampleOnGraphicsContext(int w, int h, int INFOPAD, Graphics2D g2) {            
        g2.setBackground(imageBackgroundColor);
        g2.clearRect(0, 0, w, h);
        g2.setColor(Color.white);
        g2.fillRect(0, h-INFOPAD, w, INFOPAD);

        if (errStr != null) {
            g2.setColor(jfcBlue);
            g2.setFont(new Font("serif", Font.BOLD, 18));
            g2.drawString("ERROR", 5, 20);
            AttributedString as = new AttributedString(errStr);
            as.addAttribute(TextAttribute.FONT, font12, 0, errStr.length());
            AttributedCharacterIterator aci = as.getIterator();
            FontRenderContext frc = g2.getFontRenderContext();
            LineBreakMeasurer lbm = new LineBreakMeasurer(aci, frc);
            float x = 5, y = 25;
            lbm.setPosition(0);
            while (lbm.getPosition() < errStr.length()) {
                TextLayout tl = lbm.nextLayout(w-x-5);
                if (!tl.isLeftToRight()) {
                    x = w - tl.getAdvance();
                }
                tl.draw(g2, x, y += tl.getAscent());
                y += tl.getDescent() + tl.getLeading();
            }
        } else if (capture.thread != null) {
            g2.setColor(Color.black);
            g2.setFont(font12);
            //g2.drawString("Length: " + String.valueOf(seconds), 3, h-4);
        } else {
            g2.setColor(Color.black);
            g2.setFont(font12);
            g2.drawString("File: " + fileName + "  Length: " + String.valueOf(duration) + "  Position: " + String.valueOf(seconds), 3, h-4);
            //ADDDDD LINEEEEEE

            if (audioInputStream != null) {
                // .. render sampling graph ..
                g2.setColor(jfcBlue);
                for (int i = 1; i < lines.size(); i++) {
                    g2.draw((Line2D) lines.get(i));
                }

                // .. draw current position ..
                if (seconds != 0) {
                    double loc = seconds/duration*w;
                    g2.setColor(pink);
                    g2.setStroke(new BasicStroke(3));
                    g2.draw(new Line2D.Double(loc, 0, loc, h-INFOPAD-2));
                }
            }
        }
    }

    public void start() {
        thread = new Thread(this);
        thread.setName("SamplingGraph");
        thread.start();
        seconds = 0;
    }

    public void stop() {
        if (thread != null) {
            thread.interrupt();
        }
        thread = null;
    }

    public void run() {
        seconds = 0;
        while (thread != null) {
            if ( (capture.line != null) && (capture.line.isActive()) ) {
                long milliseconds = (long)(capture.line.getMicrosecondPosition() / 1000);
                seconds =  milliseconds / 1000.0;
                System.out.println(seconds);
            }
            try { thread.sleep(100); } catch (Exception e) { break; }                              
            while ((capture.line != null && !capture.line.isActive())) 
            {
                try { thread.sleep(10); } catch (Exception e) { break; }
            }
        }
        seconds = 0;
    }
} // End class SamplingGraph
