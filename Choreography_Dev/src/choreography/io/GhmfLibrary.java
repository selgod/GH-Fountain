package choreography.io;

import choreography.view.ChoreographyController;
import choreography.view.music.MusicPaneController;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import javafx.stage.FileChooser;

/**
 * This class is responsible for writing and reading .zip format proprietary files
 * containing a map, ctl, wav, (mandatory) and marks (optional) files 
 * 
 * @author elementsking
 */
public class GhmfLibrary {
    
    /**
     * Opens a FileChooser and reads it
     */
    public static void openGhmfFile() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Open GHMF Directory");
            fc.setInitialFileName(System.getProperty("user.dir"));
//            fc.getExtensionFilters().add(new ExtensionFilter("GHMF Folders", "*.ghmf"));
            ZipFile ghmfFile = new ZipFile(fc.showOpenDialog(null));
            readGhmfZip(ghmfFile);
        } catch (IOException ex) {
            Logger.getLogger(GhmfLibrary.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Opens the zip and reads the contents into memory. It organizes the other
     * IO classes to perform the necessary operations. Needs access to a temp file
     * for reading music files because JavaFX mediaPlayer doesn't support InputStreams
     * 
     * @param ghmfFile
     * @throws IOException 
     */
    public static void readGhmfZip(ZipFile ghmfFile) throws IOException {
        Enumeration<? extends ZipEntry> entries = ghmfFile.entries();
        InputStream ctl = null, map = null, music = null, marks = null;
        File musicFile = null;
        
        while(entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if(entry != null) {
                if(entry.getName().endsWith(".ctl")) ctl = ghmfFile.getInputStream(entry);
                if(entry.getName().endsWith(".map")) map = ghmfFile.getInputStream(entry);
                if(entry.getName().endsWith(".wav")) {
                    music = ghmfFile.getInputStream(entry);
                    String name = entry.getName().substring(entry.getName().lastIndexOf("/")+1, entry.getName().length());
                    String suffix = name.substring(name.length() - 4, name.length());
                    Path out = Files.createTempFile(name, suffix);
                    Files.copy(music, out, REPLACE_EXISTING);
                    musicFile = new File(out.toUri());
                }
                if(entry.getName().endsWith(".mark")) marks = ghmfFile.getInputStream(entry);
            }
        }
        
        try {
            MusicPaneController.getInstance().openMusicFile(musicFile);    

            MapLib.openMap(map);
            CtlLib.getInstance().openCtl(ctl);
            if(marks != null) 
                ChoreographyController.getInstance().setBeatmarks(MarkLib.readMarks(marks));
        } catch (NullPointerException e){
            throw new IllegalArgumentException("Your GHMF archive was corrupted or missing files.");
        }
    }
    
    /**
     * Marshals FilePayloads and writes them to a zip archive.
     * 
     * @param zipLoc - the location where it will be saved
     * @param buffers - the FilePayLoads which will be written to disk
     * @return Whether the operation was successful or not 
     */
    public static boolean writeGhmfZip(File zipLoc, FilePayload... buffers) {
        try (ZipOutputStream output = new ZipOutputStream(new FileOutputStream(zipLoc))) {
            for(FilePayload buffer: buffers) {
                buffer.setName(buffer.getName().replaceAll("\\d*$", ""));
                ZipEntry entry = new ZipEntry(buffer.getName());
                output.putNextEntry(entry);
                output.write(buffer.getPayload());
                output.closeEntry();
            }
            return true;
        } catch(IOException e) {
            return false;
        }
        
    }
}
