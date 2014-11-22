/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package choreography.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import choreography.view.ChoreographyController;
import choreography.view.music.MusicPaneController;

/**
 *
 * @author elementsking
 */
public class MarkLib {
    private static Integer[] marks;

    /**
     * Reads the marks into an array
     * 
     * @param input
     * @return
     */
    public static Integer[] readMarks(InputStream input) {
        ArrayList<Integer> marksAl = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String mark = "";
            while((mark = br.readLine()) != null) {
                marksAl.add(Integer.parseInt(mark));
            }   
        } catch (IOException ex) {
            Logger.getLogger(MarkLib.class.getName()).log(Level.SEVERE, null, ex);
        }
        return marksAl.toArray(new Integer[1]);
    }
    
    /**
     * Prepares information to be put into a file
     * @return
     */
    public static FilePayload createFilePayload() {
        marks = ChoreographyController.getInstance().getBeatmarks();
        StringBuilder sb = new StringBuilder();
        for(Integer mark: marks) {
            if(mark != null) {
                sb.append(mark);
                sb.append(System.lineSeparator());
            }
        }
        return new FilePayload(MusicPaneController.getInstance().getMusicName()
                + ".marks", sb.toString().getBytes());
    }
    
    
}
