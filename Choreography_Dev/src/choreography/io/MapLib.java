/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2014 Sun Microsystems, Inc.
 */

package choreography.io;

import choreography.model.color.ColorPaletteModel;
import choreography.view.colorPalette.ColorPaletteController;
import choreography.view.music.MusicPaneController;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

/**
 *
 * @author elementsking
 */
public class MapLib {
    
    private static boolean mapLoaded;
    
    public static void openMap() throws FileNotFoundException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open MAP File");
        fc.setInitialFileName(System.getProperty("user.home"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("MAP Files", "*.map"));
        File mapFile = fc.showOpenDialog(null);
        openMap(mapFile);
    }
    
    public static void openMap(File file) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ColorPaletteModel.getInstance().setColors(parseMap(readMap(reader)));
                //(parseMap(readMap(file)));
    }
    
    public synchronized static void openMap(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        ColorPaletteModel.getInstance().setColors(parseMap(readMap(reader)));
    }
    
    public static String readMap(BufferedReader reader) {
        StringBuilder sb = new StringBuilder();
        try {
            String text;
            while((text = reader.readLine()) != null) {
                if(text.startsWith("//")) {
                }
                else {
                    sb.append(text);
                    sb.append(System.lineSeparator());
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MapLib.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MapLib.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sb.toString();
    }   
        
    public static Color[] parseMap(String input) {
        Scanner sc = new Scanner(input);
        ArrayList<Color> colors = new ArrayList<>(32);
        while(sc.hasNext()) {
            String line = sc.nextLine();
            if(line != null) {
                int indexOfSlash = line.indexOf("/");
                String colorHex = "";
                if(indexOfSlash != -1) {
                    colorHex = line.substring(0, indexOfSlash).trim();
                }
                else {
                    colorHex = line.substring(0, 6).trim();
                }
                colors.add(Color.web(colorHex));
            }
            
        }
        colors.trimToSize();
        
        setMapLoaded(true);
        return colors.toArray(new Color[1]);
    }
    
    public static void saveMap(File file) {
        throw new UnsupportedOperationException();
    }

    public static FilePayload createFilePayload() {
        StringBuilder sb = new StringBuilder(32);
        for(Color c: ColorPaletteModel.getInstance().getColors()) {
            sb.append(c.toString().substring(2, c.toString().length()));
            sb.append(System.lineSeparator());
        }
        return new FilePayload(MusicPaneController.getInstance().getMusicName() + ".map", sb.toString().getBytes());
    }

    public static void setMapLoaded(boolean b) {
        mapLoaded = b;
    }

    /**
     * @return the mapLoaded
     */
    public static boolean isMapLoaded() {
        return mapLoaded;
    }
}
