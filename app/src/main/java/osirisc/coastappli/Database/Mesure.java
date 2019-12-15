package osirisc.coastappli.Database;

public abstract class Mesure {

    private Double markerLatitude;
    private Double markerLongitude;
    private String date;
    private String time;
    private String user;
    private String notes;

    public Mesure() {
    }

    public Mesure(Double markerLatitude, Double markerLongitude, String date, String time, String user, String notes) {
        this.markerLatitude = markerLatitude;
        this.markerLongitude = markerLongitude;
        this.date = date;
        this.time = time;
        this.user = user;
        this.notes = notes;
    }

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
