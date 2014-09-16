package SimpleJavaFXPlayer;

import java.net.UnknownHostException;
import java.util.ArrayList;

/**
*
* @author Steven Merdzinski
*/

public class Music {

    private String code;
    private String name;
    private int year;
    private String directoryFile;
    private String artist;
    private String album;
    private String genre;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDirectoryFile() {
        return this.directoryFile;
    }

    public void setDirectoryFile(String directoryFile) {
        this.directoryFile = directoryFile;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}