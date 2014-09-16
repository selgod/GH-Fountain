package choreography.io;

import choreography.model.fcw.FCW;
import choreography.view.timeline.TimelineController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * 
 * This class represents a dictionary which uses a String to identify the fixture 
 * and returns the address of that fixture. Then, it looks up the appropriate table
 * using an intermediate table. Then, looks in that table and retrieves the 
 * appropriate command code.
 * 
 * @author Frank Madrid
 *
 */
public final class FCWLib {
	// An instance of the fcwLib
    private static FCWLib fcwLib;
    // Logger used for an old implementation of an event logger
    private static final Logger LOG = Logger.getLogger(FCWLib.class.getName());
    // The input stream used for the reading of the CTL files
    private InputStream fcwInfo;
    // Used to store the water addresses <name of address, channel # of address>
    private HashMap<String, Integer> waterAddress;
    // Used to store the light addresses <name of address, channel # of address>
    private HashMap<String, Integer> lightAddress;
    // Used to store the function addresses <name of address, channel # of address>
    private HashMap<String, Integer> functionAddress;
    // Used to store the light timeline names
    private String[] lightNames;
    // Used to store the water timeline names
    private String[] waterNames;
    // Used to store the function names
    private String[] functionNames;
    // Used to store the function tables
    private HashMap<HashSet<Integer>, String> functionTables;
    // Used to store the table commands <Name of table, <Command name, Command address>>
    private HashMap<String, HashMap<String, Integer>> tableCommands;
    // Used to check if classic colors are used (legacy colors)
    private boolean usesClassicColors;

    private FCWLib(){
        fcwInfo = this.getClass().getResourceAsStream("/resources/FCW_DEF.txt");
        waterAddress = new HashMap<>();
        lightAddress = new HashMap<>();
        functionTables = new HashMap<>();
        tableCommands = new HashMap<>();
        functionAddress = new HashMap<>();
        lightNames = new String[1];
        waterNames = new String[1];

        readFCWInfoFromFile(fcwInfo);

        lightNames = lightAddress.keySet().toArray(lightNames);
        waterNames = waterAddress.keySet().toArray(waterNames);
    }	
	
    /**
     * Controls access to constructor and instance
     * 
     * @return the fcwLib
     */
    public static synchronized FCWLib getInstance() {
        if(fcwLib == null)
           fcwLib = new FCWLib();
        return fcwLib;
    }

    /**
     * @param aFcwLib the fcwLib to set
     */
    public synchronized void setFcwLib(FCWLib aFcwLib) {
        fcwLib = aFcwLib;
    }
    
    /**
     * Coordinates actions to read the FCW information from disk into memory
     * 
     * @param fcwInfo 
     */
    public synchronized void readFCWInfoFromFile(InputStream fcwInfo) {
        readFCWInfoFromFile(new BufferedReader(new InputStreamReader(fcwInfo)));
    }
	
    /**
     * Searches for and reads fcw info from the file.
     * 
     * @param fcwInfo
     */
    public synchronized void readFCWInfoFromFile(BufferedReader fcwInfo){
        Scanner fileIn = new Scanner(fcwInfo);
        fileIn.useDelimiter("|");
        // Jumps to the water addresses section of the file
        fileIn.findWithinHorizon("|WaterAddresses|", 0);
        fileIn.nextLine();
        readWaterAddressesFromFile(fileIn);
        // Jumps to the light addresses section of the file
        fileIn.findWithinHorizon("|LightAddresses|", 0);
        fileIn.nextLine();
        readLightAddressesFromFile(fileIn);
        // Jumps to the functions section of the file
        fileIn.findWithinHorizon("|Functions|", 0);
        fileIn.nextLine();
        readFunctionsFromFile(fileIn);
        // Jumps to the tables section of the file
        fileIn.findWithinHorizon("|Tables|", 0);
        fileIn.nextLine();
        readAddressTableFromFile(fileIn);
        // Jumps to the commands section of the file
        fileIn.findWithinHorizon("|Commands|", 0);
        fileIn.nextLine();
        readTableCommandsFromFile(fileIn);
    }

    /**
     * Reads function addresses from file
     * @param fileIn 
     */
    private synchronized void readFunctionsFromFile(Scanner fileIn) {
        String line = null;
        while(fileIn.hasNextLine()) { // while we aren't EOF
            line = fileIn.nextLine(); //get the next legitmate line
            if(line.equals("|EndFunctions|")) { //If we hit end of table
                    return; //Exit
            } else {
                    String[] tokens = line.split(", "); //split into components
                    functionAddress.put(tokens[0].trim(), new Integer(tokens[1].trim())); // add them to the map
            }
        }

    }

    /**
     * Reads all water addresses from the file into memory
     * @param fileIn
     */
    public synchronized void readWaterAddressesFromFile(Scanner fileIn) {
        String line = null;
        while(fileIn.hasNextLine()){ //while we aren't EOF
            line = fileIn.nextLine(); //get the next legitmate line
            if(line.equals("|EndWaterAddresses|")) { //If we hit end of table
                    return; //Exit
            } else { 
                    String[] tokens= line.split(", "); //split into components
                    waterAddress.put(tokens[0].trim(), new Integer(tokens[1].trim())); //add them
            }
        }
    }

    /**
     * Reads light addresses from file
     * @param fileIn
     */
    public synchronized void readLightAddressesFromFile(Scanner fileIn) {
        while(fileIn.hasNextLine()){
            String line = fileIn.nextLine();
            if(line.equals("|EndLightAddresses|")) {
                return;
            } else {
                String[] tokens= line.split(", ");
                lightAddress.put(tokens[0].trim(), new Integer(tokens[1].trim()));
            }
        }
    }

    /**
     * Reads the table which maps addresses to tables into memory.
     * @param fileIn
     */
    public synchronized void readAddressTableFromFile(Scanner fileIn) {
        while(fileIn.hasNextLine()){
            String line = fileIn.nextLine();
            if(line.equals("|EndTables|")) {
                return;
            } else {
                String[] tokens= line.split(", ");
                HashSet<Integer> addresses = new HashSet<>();
                for(int i = 1; i < tokens.length; i++) {
                        addresses.add(new Integer(tokens[i]));
                }
                functionTables.put(addresses, tokens[0].trim());
            }
        }
    }

    /**
     * Reads the tables into memory and adds them to the table of tables of commands.
     * @param fileIn
     */
    public synchronized void readTableCommandsFromFile(Scanner fileIn) {
            while(fileIn.hasNextLine()){
            String line = fileIn.nextLine(); //pull in the first line
            if(line.startsWith("| ")){ //if the line is a new table...
                HashMap<String, Integer> commands = new HashMap<>(); //create something to store them in...
                String[] tokens= line.split(" "); //break it into pieces
                String table = tokens[1].trim(); //take the table name and store it

                    do { //while I have commands...
                       String command = fileIn.nextLine().trim(); //read the next in
                       if(!command.equals("|EndTable|")) {
                           String[] commandTokens = command.split(", "); //split into command and number
                           commands.put(commandTokens[0].trim(), new Integer(commandTokens[1].trim()));
                       } else {
                           tableCommands.put(table, commands);
                           break;
                       }

                   } while(fileIn.hasNextLine()); 
                } else if (line.equals("|EndCommands|")) {
                        break;
                }

        }
    }

    /**
     *
     * @return all of the light names currently recognized by the program
     */
    public synchronized String[] getLightTable() {
            return lightNames;
    }

    /**
     *
     * @return all of the water address names recognized by the program
     */
    public synchronized String[] getWaterTable() {
            return waterNames;
    }
    
    /**
     * When you supply a cannon name and a set of actions, this method will 
     * return an FCW with the appropriate address and data.
     * 
     * @param cannon Must be of CannonEnum, but in String form.
     * @param actions An array containing the strings representing actions 
     * which the cannon will perform
     * @return the FCW representing the information sent
     */
    public synchronized FCW getFCW(String cannon, String[] actions) {

        int addr = 0;
        String table = null;
        int data = 0;

        addr = searchAddresses(cannon);
        table = searchFunctionTables(addr);
        data = getCommandsForAction(actions, table, data);

        return new FCW(addr, data); //get rid of this crap!
    }

    /**
     * Gets values for string representation of commands and creates the data 
     * section of the FCW
     * 
     * @param actions the actions to search for
     * @param table the table containing the appropriate commands
     * @param data the variable to store the result in
     * @return 
     */
    private synchronized int getCommandsForAction(String[] actions, String table, int data) {
//        int data = 0;
        for (String action : actions) {
            action = action.toUpperCase();
            int value = tableCommands.get(table).get(action);
            data += value;
        }
        return data;
    }

    /**
     * Searches the address tables for the cannon
     * 
     * @param cannon the cannon you are searching for
     * @return the address of the cannon
     */
    private synchronized int searchAddresses(String cannon) {
        if(waterAddress.containsKey(cannon)) {
                return searchWaterAddresses(cannon);
        }
        else if(lightAddress.containsKey(cannon)){
                return searchLightAddresses(cannon);
        }
        else throw new IllegalArgumentException(cannon + " isn't associated with "
                        + "a water or light address");
    }

    /**
     * Searches function tables for an address
     * @param addr the address you are querying for
     * @return the name of the table the address maps to
     */
    private synchronized String searchFunctionTables(int addr) {
        String table = null;
        for(HashSet<Integer> hs : functionTables.keySet()){
            if(hs.contains(addr)){
                table = functionTables.get(hs);
                break;
            }
        }
        return table;
    }

    private synchronized int searchWaterAddresses(String cannon) throws IllegalArgumentException {
        return waterAddress.get(cannon); //get it!
    }
    
    private synchronized int searchLightAddresses(String cannon) throws IllegalArgumentException {
        return lightAddress.get(cannon); //get it!
    }
    
    public synchronized String reverseLookupAddress(FCW f) {
        String name = "";
        switch(f.getAddr()) {
            case 1: break;
            case 2: break;
            case 3: break;
            case 4: break;
            case 5: break;
            case 6: break;
            case 8: break;
            case 7:
            case 9:
                ArrayList<String> possible = new ArrayList<>(Arrays.asList(reverseLookupData(f)));
                for(String s: possible) {
                    if(!isLevel(s)) 
                        name += s + " ";
//                    possible.remove(s);
                }
                return name;
        }
        for(Entry entry: waterAddress.entrySet()) {
            if((Integer)entry.getValue() == f.getAddr()) {
//                String[] poss = reverseLookupData(f);
                return (String)entry.getKey();
            }
        }
        for(Entry entry: lightAddress.entrySet()) {
            if((Integer)entry.getValue() == f.getAddr()) {
                return (String)entry.getKey();
            }
        }
        return null;
    }
    
    public boolean reverseIsWater(FCW f) {
        boolean result = waterAddress.containsValue(f.getAddr());
        f.setIsWater(result);
        return result;
    }
    
//    public synchronized String[] reverseLookupLegacyColor(int c) {
//        case 24:
//                List<String> rejected = new ArrayList<>();
//                rejected.add("RED");rejected.add("BLUE");
//                rejected.add("AMBER"); rejected.add(("White"));
//                actions.removeAll(rejected); //Only Green Yellow  
//                break;
//            case 25:case 26:case 27:case 41:
//                actions.remove("WHITE"); //No white
//                break;
//            case 17:case 18:case 19:case 20:case 21:case 22:case 23:
//            case 49:case 50:case 51:case 52:case 53:  
////                actions.remove("OFF"); //No change
//                break;
//        return reverseLookupData(new FCW(17, c));
//    }
    
    public synchronized int reverseGetLevel(FCW f) {
        String[] actions = reverseLookupData(f);
        for(String s: actions) {
            switch(s) {
                case "1":
                    return 1;
                case "2":
                    return 2;
                case "3":
                    return 3;
                case "4":
                    return 4;
                case "5":
                    return 5;
                case "6":
                    return 6;
                case "0":
                    return 0;
            }
        }
        throw new IllegalArgumentException(f + " doesn't have a level!");
    }
    
    public synchronized boolean isLevel(String s) {
        return s.matches("-?\\d+");
    }
    
    /**
     *
     * @param f
     * @return
     */
    public synchronized String[] reverseLookupData(FCW f){
        Integer[] values;
        String table = searchFunctionTables(f.getAddr());
        switch(table) {
            case "TableTime":
            return new String[]{Integer.toString(f.getData())};
            case "TableA":
                values = new Integer[]{64, 32, 16, 8};
                break;
            case "TableC":
                values = new Integer[]{32, 16, 8, 4, 2, 1};
                break;
            case "TableD1":
                values = new Integer[]{102, 96, 80, 64, 48, 32, 16, 8, 2, 1};
                break;
            case "TableD2":
                values = new Integer[]{102, 80, 48, 16, 8};
                break;
            case "TableD3":
                values = new Integer[]{2, 1};
                break;
                        
            case "TableE":
                values = new Integer[]{8, 4, 2};
                break;
            case "TableF":
                values = new Integer[]{1};
                break;
            case "TableG":
                values = new Integer[]{64, 32, 16, 2, 1};
                break;
            case "TableH":
                values = new Integer[]{64, 32, 16, 6};
                break;
            case "TableI":
                values = new Integer[]{64, 32, 16, 2, 1};
                break;
            case "TableJ":
                values = new Integer[]{102, 86, 85, 70, 69, 68, 54, 53, 52, 51, 38, 
                37, 36, 35, 34, 22, 21, 20, 19, 18, 17, 6, 5, 4, 3, 2, 1};
                break;
            default:
                 values = new Integer[]{256, 128, 64, 32, 16, 8};
        }
        
        ArrayList<Integer> flags = new ArrayList<>();
        ArrayList<String> actions = new ArrayList<>();
        int data = f.getData();
        
        setFlags(values, data, flags);
        
        for(Entry<String, Integer> entry: tableCommands.get(table).entrySet()) {
            for(Integer i : flags) {
                if(entry.getValue().equals(i))
                    actions.add(entry.getKey());
            }
        }
        
        cleanupActionsArrayList(f, actions);
        
        return actions.toArray(new String[0]);
    }

    public void cleanupActionsArrayList(FCW f, ArrayList<String> actions) {
        //        for(String action: actions) {
        if(actions.size() > 1 && actions.contains("OFF")) {
            actions.remove("OFF");
        }
        if(actions.size() > 1 && actions.contains("INDEPENDENT")) {
            actions.remove("INDEPENDENT");
        }
//        if(actions.size() > 1 && actions.contains("0")) {
//            actions.remove("0");
//        }
        if(actions.size() > 1 && actions.contains("OFFRESET")) {
            actions.remove("OFFRESET");
        }
        if(actions.size() > 1 && actions.contains("STOP")) {
            actions.remove("STOP");
        }
        if(actions.size() > 1 && actions.contains("HOLDRIGHTOT")) {
            actions.remove("HOLDRIGHTOT");
        }
        
        switch(f.getAddr()) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 1:
                actions.remove("SPOUT");
                actions.remove("BAZOOKA");
            
                break;
            case 7:
                actions.remove("MODULEA");
                actions.remove("MODULEB");
                break;
            case 9:
                break;
            case 35:
            case 36:
            case 37:
                actions.remove("HOLDRIGHTOT");
                break;
            case 40:
                break;
            case 48:
                actions.remove("SPOUT");
                actions.remove("BAZOOKA");
                break;
               
            
        }
//        }
    }

    private void setFlags(Integer[] values, int data, ArrayList<Integer> flags) {
        for(Integer value: values){
            if(data >= value) {
                data -= value;
                flags.add(value);
            }
        } 
        if(data > 0 && data <= 6)
            flags.add(data);
        else if (data == 0) {
            flags.add(0);
        }
    }

	/**
	 * @return the functionAddress
	 */
    public synchronized HashMap<String, Integer> getFunctionAddress() {
            return functionAddress;
    }

    /**
     * @param functionAddress the functionAddress to set
     */
    public synchronized void setFunctionAddress(HashMap<String, Integer> functionAddress) {
            this.functionAddress = functionAddress;
    }

    /**
     * @return the functionNames
     */
    public synchronized String[] getFunctionNames() {
            return functionNames;
    }

    /**
     * @param functionNames the functionNames to set
     */
    public synchronized void setFunctionNames(String[] functionNames) {
            this.functionNames = functionNames;
    }

    public void usesClassicColors(boolean b) {
        usesClassicColors = b;
    }
    
    public String reverseLookupAddress(int i) {
        FCW f = new FCW(i, 0);
        return reverseLookupAddress(f);
    }

    public Integer lookupAddress(String in) {
        in = in.toUpperCase();
        for(Entry<String, Integer> entry: waterAddress.entrySet()) {
            if(entry.getKey().equals(in)) {
                return entry.getValue();
            }
        }
        for(Entry<String, Integer> entry: lightAddress.entrySet()) {
            if(entry.getKey().equals(in)) {
                return entry.getValue();
            }
        }
        return null;
    }
    
    public String[] getSweepSpeeds() {
        if(usesClassicColors) {
            return tableCommands.get(("TableD1")).keySet().toArray(new String[1]);
        }
        return tableCommands.get("TableD2").keySet().toArray(new String[1]);
    }
    
    public Integer[] getAdvancedLightNames(){
        Collection<Integer> output = lightAddress.values();
    	//get exclude list
        Integer[] excluded = TimelineController.getInstance().getSpecialChannels();
        for(Integer exclusion: excluded) {
            if(output.contains(exclusion)) {
                output.remove(exclusion);
            }
        }
        Integer[] outputArray = output.toArray(new Integer[1]);
        Arrays.sort(outputArray);
        return outputArray;
    }
}
