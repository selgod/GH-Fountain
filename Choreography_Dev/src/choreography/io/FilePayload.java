package choreography.io;

/**
 * Used to get information out of a file. 
 * @author Frank Madrid
 */
public class FilePayload {
    private String name;
    private byte[] payload;
    
    public FilePayload(String name, byte[] payload) {
        this.name = name;
        this.payload = payload;
    }

    /**
     * @return name the name of the file
     */
    public String getName() {
        return name;
    }

    /**
     * @return payload the payload of the file
     */
    public byte[] getPayload() {
        return payload;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param payload the payload to set
     */
    public void setPayload(byte[] payload) {
        this.payload = payload;
    }
}
