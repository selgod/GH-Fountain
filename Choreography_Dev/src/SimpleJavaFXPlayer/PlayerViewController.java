package SimpleJavaFXPlayer;

//import Model.Music;
import SimpleJavaFXPlayer.AudioWaveformCreator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import javax.swing.JFileChooser;

/**
 *
 * @author Steven Merdzinski
 */
public class PlayerViewController implements Initializable {
    
//    @FXML
//    private ListView musicList;
    @FXML
    private Label songProgress, songName;
    @FXML
    private Slider volume, timeSlider;
    @FXML
    private ScrollPane timelineScrollPane, waveFilePane;
    @FXML
    private ColorPicker colorPicker;
    /**/
    private MediaPlayer mediaPlayer;
    private int musicId = -1;
    private double time, roundedTime;
    private Duration duration;
    Music music2;
    private String fileName = "out.png";
    private boolean notFirst = false;
	final DecimalFormat f = new DecimalFormat("#.0");

    
    public static final int H_PIXEL_SIZE = 15;
    public static final int V_PIXEL_SIZE = 15;
    public static final double SONG_TIME = 10;

    private void getAllMusic(File fileChosen) {

            if (fileChosen.getName().toLowerCase().endsWith(".mp3") || fileChosen.getName().toLowerCase().endsWith(".wav")) {
                
                music2.setName(fileChosen.getName());
                music2.setDirectoryFile(fileChosen.getAbsolutePath());
                songName.setText(music2.getName());
            }
    }
    
    
    public void selectMusic() {
    	if (notFirst){
    		mediaPlayer.dispose();    		
    	}
//    	if (mediaPlayer.statusProperty().getValue().compareTo(Status.PLAYING)!=0){
//    		mediaPlayer.dispose();
//    	}
//    	if (mediaPlayer.statusProperty().getValue().compareTo(Status.PAUSED)!=0){
//    		mediaPlayer.dispose();
//    	}
//    	if (mediaPlayer.statusProperty().getValue().compareTo(Status.STOPPED)!=0){
//    		mediaPlayer.dispose();
//    	}
//    	try{
//    	mediaPlayer.dispose();
//    	}
    	//catch(Exception e){
    	FileChooser fc = new FileChooser();
    	File direct = new File("C:\\Users\\Steve\\Desktop");
    	fc.setInitialDirectory(direct);
    	File file2 = fc.showOpenDialog(null);
    	music2 = new Music();
    	//System.out.println(file2.getAbsolutePath());
    	if (file2 != null){
    		getAllMusic(file2);
    		music2.setDirectoryFile(file2.getAbsolutePath());
    	}
    	
        URL url = null;
		try {
			url = file2.toURI().toURL();
			
		} catch (MalformedURLException ec) {
			ec.printStackTrace();
		}
		
        try {
			AudioWaveformCreator awc = new AudioWaveformCreator(url, "out.png");

			time = awc.getTime();
			DecimalFormat f = new DecimalFormat("#.0");
			roundedTime = Double.parseDouble(f.format(time));
        	time = 2*Double.parseDouble(f.format(time));
        	File fq = new File(awc.getImage().getAbsolutePath());
        	System.out.println(fq.getAbsolutePath());
        	//Image image = new Image(fq.getCanonicalPath());
			Image image = new Image("file:///C:/Users/Steve/Documents/GitHub/Grand-Haven-Musical-Fountain/MediaPlayer/out.png");
			ImageView iv1 = new ImageView();
			iv1.setImage(image);
			waveFilePane.setContent(iv1);
			songProgress.setText(roundedTime + "s");
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
		}
        
        createTimeline(time);
        //notFirst = true;
        
        String source = new File(music2.getDirectoryFile()).toURI().toString();
    	Media media = new Media(source);
    	mediaPlayer = new MediaPlayer(media);
    	mediaPlayer.setVolume(volume.getValue());
    	songName.setText(music2.getName());
    	updateProgress(); 
    	
    	
    	mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                updateProgress();
                //Duration d = mediaPlayer.getCurrentTime();
//                timeSlider.setValue(mediaPlayer.getCurrentTime().toSeconds()/duration.toSeconds());
            }
        });
    	
    	
    	mediaPlayer.play();	
    //}
    }
    
    public void updateProgress() {
    	final DecimalFormat f = new DecimalFormat("#.0");
        
        try {
            //songProgress.setText(time + "s");
            ChangeListener<Duration> changeListener = new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> ov, Duration t, Duration t1) {
                    songProgress.setText( f.format((mediaPlayer.getTotalDuration().toSeconds() - mediaPlayer.getCurrentTime().toSeconds())) + "s");
                    duration = mediaPlayer.getMedia().getDuration();
                    //timeSlider.setValue(mediaPlayer.getCurrentTime().toSeconds()/roundedTime);
                }
            };
            mediaPlayer.currentTimeProperty().addListener(changeListener);
            
        } catch (Exception e) {
            System.out.println("Error updating song progress " + e);
        }
        
    }
    
//    protected void updateValues() {
//        if (songProgress != null && timeSlider != null && volume != null) {
//            Platform.runLater(new Runnable() {
//                public void run() {
//                    Duration currentTime = mediaPlayer.getCurrentTime();
//                    songProgress.setText(f.format(currentTime));
//                    timeSlider.setDisable(duration.isUnknown());
//                    if (!timeSlider.isDisabled()
//                            && duration.greaterThan(Duration.ZERO)
//                            && !timeSlider.isValueChanging()) {
//                        timeSlider.setValue(currentTime.divide(duration).toMillis()
//                                * 100.0);
//                    }
//                    if (!volume.isValueChanging()) {
//                        volume.setValue((int) Math.round(mediaPlayer.getVolume()
//                                * 100));
//                    }
//                }
//            });
//        }
//    }
    
    public void play(Music music) {
        String source = new File(music2.getDirectoryFile()).toURI().toString();
        Media media = new Media(source);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(volume.getValue());
        songName.setText(music2.getName());
        updateProgress();
        mediaPlayer.play();        
    }
    
    public void playByDoubleClick(MouseEvent evt) {
        if (evt.getButton().equals(MouseButton.PRIMARY)) {
            if (evt.getClickCount() == 2 && (mediaPlayer.getStatus() == Status.READY || mediaPlayer.getStatus() == Status.PLAYING )) {

            	mediaPlayer.play();
            }
            if (evt.getClickCount() == 1) {
            	
            	System.out.println(time);            	
                
            }
        }
    }
    
    public void createTimeline(double tenths){
    	//double songLength = (mediaPlayer.getTotalDuration().toSeconds())*10;
    	GridPane gridpaneRec = new GridPane();
    	int intTenths = (int) tenths;

    	final Rectangle[][] recArray = new Rectangle[intTenths+1][17];
   	 
    	for(int i=0; i<intTenths+1; i++){
     		  gridpaneRec.getColumnConstraints().add(new ColumnConstraints(V_PIXEL_SIZE+1));
     		  if (i < 17){ //because the array is not square this needs to be here
     			 gridpaneRec.getRowConstraints().add(new RowConstraints(V_PIXEL_SIZE+1));
     		  }
     		  
      	  for(int j=0; j<17; j++){
      		  if (i == 0){
      			 recArray[i][j] = new Rectangle(H_PIXEL_SIZE,V_PIXEL_SIZE, Color.RED);
      			 continue;
      		  }
      		  recArray[i][j] = new Rectangle(H_PIXEL_SIZE,V_PIXEL_SIZE, Color.LIGHTGREY);
      		  gridpaneRec.add(recArray[i][j], i, j);
      		  //these are needed to talk to the mouse pressed events
      		  final int testI = i;
      		  final int testJ = j;
      		  
      		recArray[i][j].setOnMousePressed(new EventHandler<MouseEvent>() {
      			  public void handle(MouseEvent me) {
      	        System.out.println("Col " + (testI) + " Row " + (testJ+1));
      	     recArray[testI][testJ].setFill(colorPicker.getValue());
      	    }
      	});
      		
      		recArray[i][j].setOnDragDetected(new EventHandler<MouseEvent>() {
    			  public void handle(MouseEvent me) {
    				  //starts the drag event
    				 recArray[testI][testJ].startFullDrag();
    				 
    	    }
    			  
    	});
      		//continues and ends the drag event
      		recArray[i][j].setOnMouseDragOver(new EventHandler<MouseEvent>() {
  			  public void handle(MouseEvent me) {
  				  recArray[testI][testJ].setFill(colorPicker.getValue());
  	    }
      	});
 
      	  }
        }
    	
   	 timelineScrollPane.setContent(gridpaneRec);  	
   	 
    }
    
    public void playSong() {
    	try{
    		mediaPlayer.play();
    	}
    	catch (Exception e){

//    		String source = new File(music2.getDirectoryFile()).toURI().toString();
//        	Media media = new Media(source);
//        	mediaPlayer = new MediaPlayer(media);
//        	mediaPlayer.setVolume(volume.getValue());
//        	songName.setText(music2.getName());
//        	updateProgress();      
//        	mediaPlayer.play();		
    	}
    }
    
    public void pauseSong() {
        mediaPlayer.pause();
    }
    
    public void stopSong() {
        mediaPlayer.stop();
    }
        
    public void close() {
        SimpleJavaFXPlayer.getInstance().close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // loadDirectory();
        volume.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {
                //System.out.println(volume.getValue());
                mediaPlayer.setVolume(volume.getValue());
            }
        }); 
        
        timeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (timeSlider.isValueChanging()) {
                    // multiply duration by percentage calculated by slider position
                    mediaPlayer.seek(duration.multiply(timeSlider.getValue() / 100.0));
                }
            }
        });
        
//        mediaPlayer.setOnReady(new Runnable() {
//            public void run() {
//                duration = mediaPlayer.getMedia().getDuration();
//                updateValues();
//            }
//        });
        
//        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
//            public void invalidated(Observable ov) {
//                updateValues();
//            }
//        });

    }
}