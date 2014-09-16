/**
 * Sample Skeleton for "MusicPane.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package choreography.view.music;

import SimpleJavaFXPlayer.AudioWaveformCreator;
import SimpleJavaFXPlayer.Music;
import choreography.io.FilePayload;
import choreography.io.MapLib;
import choreography.view.ChoreographyController;
import choreography.view.sim.FountainSimController;
import choreography.view.sliders.SlidersController;
import choreography.model.timeline.Timeline;
import static choreography.view.ChoreographyController.WORKINGDIRECTORY;
import choreography.view.timeline.TimelineController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 *
 * @author elementsking
 */
public class MusicPaneController {
    
    private int timeFactor;
	
    private static MusicPaneController instance;

    private MediaPlayer mediaPlayer;
    private double time, roundedTime;
    private Duration duration;
    Music music2;
    private boolean notFirst = false;
    final DecimalFormat f = new DecimalFormat("0.0");
    

    /**
     *
     */
    public static final int H_PIXEL_SIZE = 15;

    /**
     *
     */
    public static final int V_PIXEL_SIZE = 15;
    //public static final double SONG_TIME = 10;

    /**
     *
     */
    public static int SONG_TIME = 0;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="colorPicker"
    private ColorPicker colorPicker; // Value injected by FXMLLoader

    @FXML // fx:id="musicPane"
    private VBox musicPane; // Value injected by FXMLLoader

    @FXML // fx:id="songName"
    private Label songName; // Value injected by FXMLLoader

    @FXML // fx:id="songProgress"
    private Label songProgress; // Value injected by FXMLLoader

    @FXML // fx:id="volume"
    private Slider volume, timeSlider; // Value injected by FXMLLoader
    
    @FXML
    private ScrollPane waterTimeline, timeLabel;
    
    @FXML
    private LineChart labelChart;
    
    @FXML
    private NumberAxis labelAxis, numberLine;
    
    @FXML
    private Button playButton, resetButton;
    
    

    /**
     *
     * @return
     */
    public static MusicPaneController getInstance() {
    	if(instance == null)
    		instance = new MusicPaneController();
    	return instance;
    }

    public MusicPaneController() {
        this.timeFactor = 10;
    }

    // Handler for Button[Button[id=null, styleClass=button]] onAction
    @FXML
    void pauseSong(ActionEvent event) {
    	mediaPlayer.pause();
        ChoreographyController.getInstance().stopTimelineTimer();
        ChoreographyController.getInstance().stopSliderTimer();
        TimelineController.getInstance().fireSliderChangeEvent();
        FountainSimController.getInstance().pauseLeftSweep();
        FountainSimController.getInstance().pauseRightSweep();
//        FountainSimController.getInstance().disposeBuffer();
    }

    // Handler for Button[Button[id=null, styleClass=button]] onAction
    @FXML
    void playSong(ActionEvent event) {
    	if (mediaPlayer.statusProperty().getValue()==Status.PAUSED || 
    			mediaPlayer.statusProperty().getValue()==Status.STOPPED ||
    			mediaPlayer.statusProperty().getValue()==Status.READY){
            mediaPlayer.play();
            FountainSimController.getInstance().playLeftSweep();
            FountainSimController.getInstance().playRightSweep();
                playButton.setText("Pause");
                ChoreographyController.getInstance().startPollingTimeSliderAlgorithm();
                //ChoreographyController.getInstance().startPollingSlidersAlgorithm();
                ChoreographyController.getInstance().startPollingSimAlgorithm();
                ChoreographyController.getInstance().startPollingColorAlgorithm();
                //ChoreographyController.getInstance().startPlayingSim();
                SlidersController.getInstance().resetAllSliders();
    	}
    	
    	if (mediaPlayer.statusProperty().getValue()==Status.PLAYING){
            pauseSong(event);
            playButton.setText("Play");

//            ChoreographyController.getInstance().stopSliderTimer();
//            ChoreographyController.getInstance().stopTimelineTimer();
    	}
    }

    // Handler for Button[Button[id=null, styleClass=button]] onAction
    @FXML
    private void stopSong(ActionEvent event) {
    	mediaPlayer.stop();
        mediaPlayer.seek(Duration.ZERO);
        timeSlider.setValue(0.0);
        TimelineController.getInstance().fireSimClearEvent();
//        FountainSimController.getInstance().disposeBuffer();
        SlidersController.getInstance().resetAllSliders();
        FountainSimController.getInstance().clearSweeps();
        playButton.setText("Play");
    }
    
    private void getAllMusic(File fileChosen) {
        music2.setName(fileChosen.getName());
        music2.setDirectoryFile(fileChosen.getAbsolutePath());
        songName.setText(music2.getName());
    }
    
    /**
     *
     * @return
     */
    public MediaPlayer getMediaPlayer(){
    	return mediaPlayer;
    }
    
    public ScrollPane getWaterPane(){
    	return waterTimeline;
    }
    
    public ScrollPane getLabelPane(){
    	return timeLabel;
    }
    
    /**
     *
     */
    public void selectMusic() {
    	if (notFirst){
    		mediaPlayer.dispose();    		
    	}
    	FileChooser fc = new FileChooser();
    	fc.setInitialDirectory(new File(System.getProperty("user.dir")));
        fc.getExtensionFilters().setAll(new FileChooser.ExtensionFilter(
                "Music Files", "*.wav", "*.flac"));
    	File file2 = fc.showOpenDialog(null);
    	openMusicFile(file2);
        playButton.setDisable(false);
        resetButton.setDisable(false);
    }
    	
    public void loadMusicFile(File file2) {
    	URL url = null;
        try {
                url = file2.toURI().toURL();
        } catch (MalformedURLException ec) {
                ec.printStackTrace();
        }
		
        try {
            AudioWaveformCreator awc = new AudioWaveformCreator(url, "out.png");

            setTime(awc.getTime());
            DecimalFormat f = new DecimalFormat("0.0");
            roundedTime = Double.parseDouble(f.format(getTime()));
            setTime(getTimeFactor() * Double.parseDouble(f.format(getTime())));
            SONG_TIME = (int) getTime();
            TimelineController.getInstance().getTimeline().setTime(SONG_TIME);
            TimelineController.getInstance().setTimelineGridPane();
            TimelineController.getInstance().setWaterGridPane();
            ChoreographyController.getInstance().setBeatMarkGridPane();
            numberLine.setMinWidth(getTime()*26);
            numberLine.setPrefWidth(getTime()*26);
            numberLine.setUpperBound(roundedTime);
            numberLine.setVisible(true);
            songProgress.setText("0/"+roundedTime);
        } catch (Exception ex) {

                ex.printStackTrace();
        }
        notFirst = true;
    }

    public void openMusicFile(File file2) {
        music2 = new Music();
        if (file2 != null){
            getAllMusic(file2);
            music2.setDirectoryFile(file2.getAbsolutePath());
        }
        
        loadMusicFile(file2);
        
        String source = new File(music2.getDirectoryFile()).toURI().toString();
        Media media = new Media(source);
        mediaPlayer = new MediaPlayer(media);
        //mediaPlayer.setVolume(volume.getValue());
        songName.setText(music2.getName());
        mediaPlayer.play();
        mediaPlayer.pause();
//        updateProgressTimer();
//        ChoreographyController.getInstance().startPollingTimeSliderAlgorithm();
    }

    /**
     *
     */
    public void updateProgress() {
        final DecimalFormat f = new DecimalFormat("0.0");
        try {
            //songProgress.setText(time + "s");
//                    songProgress.setText( f.format((mediaPlayer.getTotalDuration().toSeconds() - mediaPlayer.getCurrentTime().toSeconds())) + "s");
            songProgress.setText( f.format(mediaPlayer.getCurrentTime().toSeconds()) + "/"+ f.format(mediaPlayer.getTotalDuration().toSeconds()));
                duration = mediaPlayer.getMedia().getDuration();
//                int currTime = (int)mediaPlayer.getCurrentTime().toSeconds()*10;
//                FountainSimController.getInstance().updateColors(currTime);
            TimelineController.getInstance().getScrollPane().setHvalue( (mediaPlayer.getCurrentTime().toSeconds()/mediaPlayer.getTotalDuration().toSeconds())*100);
            timeSlider.setValue((mediaPlayer.getCurrentTime().toSeconds()/mediaPlayer.getTotalDuration().toSeconds())*100);
            timeSlider.setValue( (mediaPlayer.getCurrentTime().toSeconds()/mediaPlayer.getTotalDuration().toSeconds())*100);
            waterTimeline.setHvalue( (mediaPlayer.getCurrentTime().toSeconds()/mediaPlayer.getTotalDuration().toSeconds())*100);
            ChoreographyController.getInstance().getBeatMarkScrollPane().setHvalue( (mediaPlayer.getCurrentTime().toSeconds()/mediaPlayer.getTotalDuration().toSeconds())*100);
            timeLabel.setHvalue( (mediaPlayer.getCurrentTime().toSeconds()/mediaPlayer.getTotalDuration().toSeconds())*100);
        } catch (Exception e) {
            System.out.println("Error updating song progress " + e);
        }

    }
    
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert colorPicker != null : "fx:id=\"colorPicker\" was not injected: check your FXML file 'MusicPane.fxml'.";
        assert musicPane != null : "fx:id=\"musicPane\" was not injected: check your FXML file 'MusicPane.fxml'.";
        assert songName != null : "fx:id=\"songName\" was not injected: check your FXML file 'MusicPane.fxml'.";
        assert songProgress != null : "fx:id=\"songProgress\" was not injected: check your FXML file 'MusicPane.fxml'.";
        //assert volume != null : "fx:id=\"volume\" was not injected: check your FXML file 'MusicPane.fxml'.";
        
        timeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable ov) {
                if (timeSlider.isValueChanging()) {
                    // multiply duration by percentage calculated by slider position
                	mediaPlayer.pause();
                	playButton.setText("Play");
                    mediaPlayer.seek(duration.multiply(timeSlider.getValue() / 100.0));
                    //mediaPlayer.play();
                }
            }
        });
        this.timeFactor = 10;
        instance = this;
    }

    /**
     * @return the timeFactor
     */
    public int getTimeFactor() {
        return timeFactor;
    }

    /**
     * @param timeFactor the timeFactor to set
     */
    public void setTimeFactor(int timeFactor) {
        this.timeFactor = timeFactor;
    }

    /**
     * @return the time
     */
    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
    
    public int getTenthsTime() {
        double wholeTime = timeSlider.getValue() /100 * time;
//        double inter = wholeTime * 10;
//        int seconds = (int) inter;
        int tenths = (int) wholeTime;
//        System.out.println(wholeTime + " " + tenths);
        return tenths;
    }

    public Slider getTimeSlider() {
        return timeSlider;
    }
    
    public String getMusicName() {
        return music2.getName().substring(0, music2.getName().length() - 4).replaceAll("\\d*$", "");
    }

    public FilePayload createFilePayload() {
        try {
            File musicFile = new File(music2.getDirectoryFile());
            FileInputStream input = new FileInputStream(musicFile);
            int length = (int)musicFile.length();
            byte[] musicFileBytes = new byte[length];
            input.read(musicFileBytes);
            return new FilePayload(music2.getName(), musicFileBytes);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MusicPaneController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MusicPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException("Unable to create music FilePayload");
    }

    public void disposeMusic() {
        mediaPlayer.dispose();
        notFirst = false;
    }

    public void resetAll() {
        disposeMusic();
        songName.setText("");
        songProgress.setText("");
        waterTimeline.setContent(null);
        timeSlider.setValue(0);
    }
}
