/**
 * 
 */
package choreography.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import choreography.model.fcw.FCW;
import choreography.model.lagtime.LagTime;
import choreography.model.lagtime.LagTimeTable;

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
            fileIn.close();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Your LagTimeDef.txt file "
                    + "may be corrupted. Please see the manual to find "
                    + "directons to fix it");
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
    
    public static void main(String[] args){
    	FCW f1 = new FCW(7, 21); //bazooka (3)
    	FCW f2 = new FCW(7, 37); //voice (1)
    	FCW f3 = new FCW(7, 69); //bazooka & voice (3)
    	FCW f4 = new FCW(9, 21); //front curtain (1)
    	FCW f5 = new FCW(9, 32); //back curtain (1)
    	FCW f6 = new FCW(9, 96); //peakcock (1)
    	FCW f7 = new FCW(8, 21); //candelabra (1)
    	FCW f8 = new FCW(47, 11); //multi (1)
    	FCW f9 = new FCW(48, 11); //multi (1)
    	FCW f10 = new FCW(1, 69); //r1 (1)
    	FCW f11= new FCW(2, 69); //r2 (1)
    	FCW f12= new FCW(3, 69); //r3 (1)
    	FCW f13= new FCW(4, 69); //r4 (1)
    	FCW f14 = new FCW(5, 69); //r5 (1)
    	FCW f15 = new FCW(6, 69); //sweep water level (1)
    	FCW f16 = new FCW(35, 53); //r5 (1)
    	FCW f17 = new FCW(36, 53); //r5 (1)
    	FCW f18 = new FCW(37, 53); //r5 (1)
    	FCW f19 = new FCW(38, 80); //r5 (1)
    	FCW f20 = new FCW(39, 80); //r5 (1)
    	FCW f21= new FCW(40, 80); //r5 (1)
    	FCW f22 = new FCW(42, 1); //r5 (1)
    	LagTimeLibrary l = LagTimeLibrary.getInstance();
    	System.out.println(l.getLagTimeInTenths(f1));
    	System.out.println(l.getLagTimeInTenths(f2));
    	System.out.println(l.getLagTimeInTenths(f3));
    	System.out.println(l.getLagTimeInTenths(f4));
    	System.out.println(l.getLagTimeInTenths(f5));
    	System.out.println(l.getLagTimeInTenths(f6));
    	System.out.println(l.getLagTimeInTenths(f7));
    	System.out.println(l.getLagTimeInTenths(f8));
    	System.out.println(l.getLagTimeInTenths(f9));
    	System.out.println(l.getLagTimeInTenths(f10));
    	System.out.println(l.getLagTimeInTenths(f11));
    	System.out.println(l.getLagTimeInTenths(f12));
    	System.out.println(l.getLagTimeInTenths(f13));
    	System.out.println(l.getLagTimeInTenths(f14));
    	System.out.println(l.getLagTimeInTenths(f15));
    	System.out.println(l.getLagTimeInTenths(f16));
    	System.out.println(l.getLagTimeInTenths(f17));
    	System.out.println(l.getLagTimeInTenths(f18));
    	System.out.println(l.getLagTimeInTenths(f19));
    	System.out.println(l.getLagTimeInTenths(f20));
    	System.out.println(l.getLagTimeInTenths(f21));
    	System.out.println(l.getLagTimeInTenths(f22));
    	

    }
}
