package osirisc.coastappli.Database;

/**
 * This abstract class defines the Measure objects, that represent every measure stored on the app
 */

public abstract class Measure {

    /**
     * Measures have a set of attributes :
     */

    private Double markerLatitude;
    private Double markerLongitude;
    private String date;
    private String time;
    private String user;
    private String notes;

    /**
     * Constructor of the class
     */

    public Measure() {
    }

    /**
     * The following functions are all the getters and setters for every single attribute of a Measure object
     * @return
     */

    public Double getMarkerLatitude() {
        return markerLatitude;
    }

    public void setMarkerLatitude(Double markerLatitude) {
        this.markerLatitude = markerLatitude;
    }

    public Double getMarkerLongitude() {
        return markerLongitude;
    }

    public void setMarkerLongitude(Double markerLongitude) {
        this.markerLongitude = markerLongitude;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
