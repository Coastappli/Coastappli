package osirisc.coastappli.Database;

/**
 * This class defines the MethodErosionPhotoCapture objects, that store the necessary information for every measurement protocol
 */

public class MethodErosionPhotoCapture {

    private Double markerLatitude;
    private Double markerLongitude;
    private byte[] photo;
    private byte[] photoPerson;
    private String clue1;
    private String clue2;
    private String clue3;

    /**
     * We have the 2 following constructors :
     */

    public MethodErosionPhotoCapture(){}

    public MethodErosionPhotoCapture(Double markerLatitude, Double markerLongitude, byte[] photo, byte[] photoPerson, String clue1, String clue2, String clue3) {
        this.markerLatitude = markerLatitude;
        this.markerLongitude = markerLongitude;
        this.photo = photo;
        this.photoPerson = photoPerson;
        this.clue1 = clue1;
        this.clue2 = clue2;
        this.clue3 = clue3;
    }

    /**
     * The following functions are all the getters and setters for every single attribute of a MethodErosionPhotoCapture object
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

    public String getClue1() {
        return clue1;
    }

    public void setClue1(String clue1) {
        this.clue1 = clue1;
    }

    public String getClue2() {
        return clue2;
    }

    public void setClue2(String clue2) {
        this.clue2 = clue2;
    }

    public String getClue3() {
        return clue3;
    }

    public void setClue3(String clue3) {
        this.clue3 = clue3;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public byte[] getPhotoPerson() {
        return photoPerson;
    }

    public void setPhotoPerson(byte[] photoPerson) {
        this.photoPerson = photoPerson;
    }
}
