package choreography.io;

import choreography.model.fcw.FCW;
import choreography.view.ChoreographyController;
import choreography.model.color.ColorPaletteModel;
import choreography.view.music.MusicPaneController;
import choreography.view.specialOperations.SpecialoperationsController;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * This class is responsible for reading and writing CTL files in the format
 * <Time Signature><FCW>...<FCW> Upon reading the CTL, it sets a number of options
 * 
 * @author Frank Madrid
 */
public class CtlLib {
    
    private static CtlLib instance;
    
    /**
     * Returns an instance of CtlLib. Controls instantiation and access to the 
     * class
     * @return CtlLib
     */
    public static synchronized CtlLib getInstance() {
        if(instance == null)
            instance = new CtlLib();
        return instance;
    }
    
    // Used to flag if the time is compensated within the CTL file or not
    private boolean isTimeCompensated;
    
    private CtlLib(){

    }

    /**
     * Method throws up a Open File dialog to select a CTL file
     * 
     * @throws java.io.IOException
     */
    public synchronized void openCtl() throws IOException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open CTL File");
        fc.setInitialFileName(System.getProperty("user.dir"));
        fc.getExtensionFilters().add(new ExtensionFilter("CTL Files", "*.ctl"));
        File ctlFile = fc.showOpenDialog(null);
        openCtl(ctlFile);
    }
    
    /**
     *  Wraps the CTL file in a buffered reader and sets the event timeline
     * 
     * @param file
     * @throws IOException 
     */
    public void openCtl(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ChoreographyController.getInstance().setEventTimeline(parseCTL(readFile(reader)));
    }
    
    /**
     * Wraps the incoming zipEntryInputStream in a buffered reader and sets 
     * event timeline
     * @param is
     * @throws IOException 
     */
    public void openCtl(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ChoreographyController.getInstance().setEventTimeline(parseCTL(readFile(reader)));
    }

    /**
     * Reads the CTL contents from the file into memory and sets options based on
     * its contents.
     * 
     * @param reader A reader wrapped around the ctl data
     * @return the contents of the CTL file
     */
    public synchronized String readFile(BufferedReader reader) throws IOException{
        StringBuilder stringBuffer = new StringBuilder();

        try {
            String version = reader.readLine();
            switch(version) {
                case "ct0-382":
                    ColorPaletteModel.getInstance().setClassicColors(true);
                    FCWLib.getInstance().usesClassicColors(true);
                    SpecialoperationsController.getInstance().initializeSweepSpeedSelectors();
                    break;
                case "gvsuCapstone2014A":
                    ChoreographyController.getInstance().setAdvanced(true);
                case "gvsuCapstone2014B":
                    isTimeCompensated = true;
                    break;
            }
            String text = null;
            
            while ((text = reader.readLine()) != null) {
                stringBuffer.append(text);
                stringBuffer.append(System.getProperty("line.separator"));
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            reader.close();
        }

        return stringBuffer.toString();
    }

    /**
     * Parses the input into timeIndex and an ArrayList of FCW commands.
     * 
     * @param input contents of the CTL file
     * @return A map containing <timeIndex, ArrayList<FCW>>
     */
    public synchronized ConcurrentSkipListMap<Integer, ArrayList<FCW>> parseCTL(String input){
        //Split file into tokens of lines
        String[] lines = input.split(System.getProperty("line.separator"));
        //Create an Event[] to hold all events
        ConcurrentSkipListMap<Integer, ArrayList<FCW>> events = new ConcurrentSkipListMap<>();
        try {
        // For each line,
        for (String line : lines) {
            //Get the time signature
            String totalTime = line.substring(0, 7);
            //Get the minutes
            int minutes = (Integer.parseInt(totalTime.substring(0, 2)));
            //get the seconds
            int seconds = Integer.parseInt(totalTime.substring(3, 5));
            //get the tenths
            int tenths = Integer.parseInt(totalTime.substring(6, 7));
            //find the total time in seconds
            int totalTimeinTenthSecs = (minutes * 600) + (seconds * 10) + tenths;
            //get the commands section on the line
            String commands = line.substring(7, line.length());
            //break the commands into tokens
            String[] commandTokens = commands.split(" ");
            //create a new FCW for the command token
            FCW fcw = null;
            ArrayList<FCW> fcws = new ArrayList<>();
            for (String command : commandTokens) {
                String[] tokens = command.split("-");
                fcw = new FCW(Integer.parseInt(tokens[0]),
                        Integer.parseInt(tokens[1]));

                fcws.add(fcw);
            }
            events.put(totalTimeinTenthSecs, fcws);
        }
        if (isTimeCompensated) {
            reversePostDate(events);
        }
        } catch(StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Your CTL file may be corrupted..."
                    + " Check the manual.");
        }
        return events;
    }

    /**
     * Writes the CTL data from memory into a file.
     * 
     * @param file
     * @param content
     * @return 
     */
    public synchronized boolean saveFile(File file, SortedMap<Integer, ArrayList<FCW>> content){
//
        try (FileWriter fileWriter = new FileWriter(file)){
            StringBuilder commandsOutput = createCtlData(content);
            fileWriter.write(commandsOutput.toString());
            ChoreographyController.getInstance().setfcwOutput("Finished saving CTL!");
        return true;    
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    /**
     * Puts a version header at the top of the file. Iterates through the timeline
     * and builds lines of <MM:SS.T000-000 111-111 222-222>
     * 
     * @param content the timeline you want to save
     * @return a string holding that data in the ctl format
     * @throws IOException 
     */
    private StringBuilder createCtlData(SortedMap<Integer, ArrayList<FCW>> content) throws IOException {
        StringBuilder commandsOutput = new StringBuilder();
        if(ColorPaletteModel.getInstance().isClassicColors()) {
            commandsOutput.append("ct0-382");
            commandsOutput.append(System.lineSeparator());
        }
        else if(ChoreographyController.getInstance().getAdvanced()) {
            commandsOutput.append("gvsuCapstone2014A");
            commandsOutput.append(System.lineSeparator());
        }
        else {
            commandsOutput.append("gvsuCapstone2014B");
            commandsOutput.append(System.lineSeparator());
        }
        for(Integer timeIndex: content.keySet()) {
            Iterator<FCW> it = content.get(timeIndex).iterator();
            while(it.hasNext()){
                FCW f = it.next();
                if(f.getIsWater()) {
//                        System.out.println(f + " " + timeIndex);
                    if(postDateSingleFcw(f, content, timeIndex)){
                        it.remove();
                    }
                }
                if(content.get(timeIndex).isEmpty()) {
                    content.remove(timeIndex);
                }
            }
        }
        
        for (Iterator<Integer> it = content.keySet().iterator(); it.hasNext();) {
            Integer i = it.next();
            String totTime = "";
            int timeIndex = i;
            if(i < 0) {
                totTime = "-";
                timeIndex = Math.abs(i);
            }
            int tenths = Math.abs(timeIndex % 10);
            int seconds = Math.abs(timeIndex / 10 % 60);
            int minutes = Math.abs(((timeIndex/10)-seconds) /60);
            totTime += String.format("%1$02d:%2$02d.%3$01d", minutes, seconds, tenths);
            commandsOutput.append(totTime);
            for(FCW f: content.get(i)) {
                commandsOutput.append(f);
                commandsOutput.append(" ");
            }
            commandsOutput.append(System.lineSeparator());
        }
        return commandsOutput;
    }

    /**
     * Moves water CTLs back by timeIndex before exporting
     * 
     * @param content the CTLs to post date
     */
    private synchronized void postDate(SortedMap<Integer, ArrayList<FCW>> content) {
        for(Integer timeIndex: content.keySet()) {
            Iterator<FCW> it = content.get(timeIndex).iterator();
            while(it.hasNext()){
                FCW f = it.next();
                if(f.getIsWater()) {
                    if(postDateSingleFcw(f, content, timeIndex))
                        it.remove();
                }
            }
            if(content.get(timeIndex).isEmpty()) {
                content.remove(timeIndex);
            }
        }
    }

    /**
     * post dates a single FCW by lag time
     * 
     * @param f the FCW
     * @param content the timeline
     * @param timeIndex the point at which the fcw currently exists
     * @return whether the fcw was moved or not
     */
    public synchronized boolean postDateSingleFcw(FCW f, SortedMap<Integer, ArrayList<FCW>> content, Integer timeIndex) {
        int lag = LagTimeLibrary.getInstance().getLagTimeInTenths(f);
        int adjustedTime = timeIndex - lag;
        if(adjustedTime > 0) {
            if(lag != 0){
                if(content.containsKey(timeIndex - lag)) {
                    content.get(timeIndex - lag).add(f);
                }
                else {
                    content.put(timeIndex - lag, new ArrayList(10));
                    content.get(timeIndex - lag).add(f);
                }
                return true;
            }
        }
        return false;
    }
    
    /**
     * moves FCWs forward when opening a file
     * 
     * @param content the timeline
     * @return whether the move was successful or not
     */
    private boolean reversePostDate(SortedMap<Integer, ArrayList<FCW>> content) {
        for(Integer timeIndex: content.keySet()) {
            Iterator<FCW> it = content.get(timeIndex).iterator();
            while(it.hasNext()){
                FCW f = it.next();
                if(f.getIsWater()) {
                    int lag = LagTimeLibrary.getInstance().getLagTimeInTenths(f);
                    if(lag != 0){
                        if(content.containsKey(timeIndex + lag)) {
                            content.get(timeIndex + lag).add(f);
                        }
                        else {
                            content.put(timeIndex + lag, new ArrayList(10));
                            content.get(timeIndex + lag).add(f);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Creates a FilePayload for output as a ZipEntry
     * 
     * @param timeline
     * @return FilePayload(songName, ctlData) containing the CTL data
     * @throws IOException 
     */
    public FilePayload createFilePayload(SortedMap<Integer, ArrayList<FCW>> timeline) throws IOException {
        StringBuilder sb = createCtlData(timeline);
        return new FilePayload(MusicPaneController.getInstance().getMusicName() + ".ctl", sb.toString().getBytes());
    }
}
