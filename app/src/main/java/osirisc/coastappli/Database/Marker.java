package osirisc.coastappli.Database;

public class Marker {

    private double longitude;
    private double latitude;
    private String nameBeach;
    private String nameTown = "Town name not provided";
    private String coastType = "Coast Type not provided";
    private String INEC = "INEC not provided";
    private int erosionDistanceMesure = 1;

    public Marker() {}

    public Marker(double latitude, double longitude, String nameBeach, String nameTown) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.nameBeach = nameBeach;
        this.nameTown = nameTown;
    }

    public Marker(double latitude, double longitude, String nameBeach, String nameTown, String coastType, String INEC, int erosionDistanceMesure) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.nameBeach = nameBeach;
        this.nameTown = nameTown;
        this.coastType = coastType;
        this.erosionDistanceMesure = erosionDistanceMesure;
        this.INEC = INEC;
    }

    public String getNameTown() { return nameTown; }

    public String getCoastType() { return coastType; }

    public String getINEC() { return INEC; }

    public int getErosionDistanceMesureBool() { return erosionDistanceMesure; }

    public void setErosionDistanceMesure(int erosionDistanceMesure) { this.erosionDistanceMesure = erosionDistanceMesure; }

    public void setINEC(String INEC) { this.INEC = INEC; }

    public void setCoastType(String coastType) { this.coastType = coastType; }

    public void setNameTown(String nameTown) { this.nameTown = nameTown; }

    public String getNameBeach() { return nameBeach; }

    public void setNameBeach(String name) { this.nameBeach = name; }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
