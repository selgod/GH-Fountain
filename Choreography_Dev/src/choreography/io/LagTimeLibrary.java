/**
 * 
 */
package choreography.io;

import choreography.model.fcw.FCW;
import choreography.model.lagtime.LagTime;
import choreography.model.lagtime.LagTimeTable;
import choreography.view.ChoreographyController;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author elementsking
 *
 */
public class LagTimeLibrary {
    private static LagTimeLibrary instance;
    private final LagTimeTable lagTimeTableInstance;
    private String filename;

    /**
     *
     * @return
     */
    public static LagTimeLibrary getInstance() {
            if(instance == null) {
                try {
                    instance = new LagTimeLibrary();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(LagTimeLibrary.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return instance;
    }

    /**
     *
     * @return
     */
    public LagTimeTable getLagTimeTable() {
        return lagTimeTableInstance;
    }

    /**
     *
     * @return
     */
    public ArrayList<LagTime> getLagTimes() {
        return LagTimeTable.getDelays();
    }

    private LagTimeLibrary() throws FileNotFoundException {
        
        lagTimeTableInstance = LagTimeTable.getInstance();
        filename = "/resources/LagTimeDef.txt";
        loadTimesFromFile(new BufferedReader(
            new InputStreamReader(
            this.getClass().getResourceAsStream(filename))));
    }

    private void loadTimesFromFile(BufferedReader reader) throws IllegalArgumentException, FileNotFoundException {
        try (Scanner fileIn = new Scanner(reader)){
            ArrayList<LagTime> delayTimes = new ArrayList<>();
            while(fileIn.hasNext()) {
                String line = fileIn.nextLine();
                String[] tokens = line.split("=");
                LagTime lt = new LagTime(tokens[0].trim(), Double.parseDouble(tokens[1].trim()));
                delayTimes.add(lt);
            }
            LagTimeTable.setLagTimes(delayTimes);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Your LagTimeDef.txt file "
                    + "may be corrupted. Please see the manual to find "
                    + "directons to fix it");
            //TODO insert instructions to fix corrupt LagTimeDef into manual
        }
    }

    /**
     *
     * @param f
     * @return
     */
    public int getLagTimeInTenths(FCW f) {
       double lag = LagTimeTable.getLagTime(f);
       double tenths = lag * 10;
       int retVal = (int) tenths;
       return retVal;
   }
    
    public double getLagTimeInSeconds(FCW f) {
        return LagTimeTable.getLagTime(f);
    }
    
    /**
     *
     * @param dataTable
     * @throws IllegalArgumentException
     * @throws FileNotFoundException
     */
    public void saveLagTimes(ArrayList<LagTime> dataTable) throws IllegalArgumentException, FileNotFoundException {
        try(FileWriter fileOut = new FileWriter(new File(filename))) {
            Collections.sort(dataTable, new Comparator<LagTime>() {

                @Override
                public int compare(LagTime t, LagTime t1) {
                    return t.getDelayName().compareTo(t1.getDelayName());
                }
            });
            for(LagTime lt: dataTable) {
                fileOut.write(lt.getDelayName() + " = " + lt.getDelayTime() + "\n");
            }
            
        } catch(IOException e){
            
        }
        loadTimesFromFile(
                new BufferedReader(
                new InputStreamReader(
                    getClass().getResourceAsStream(filename))));
    }
}
