package osirisc.coastappli.Database;

public class MesureErosionDistance extends Mesure {
    private byte[] photo;

    public MesureErosionDistance(Double markerLatitude, Double markerLongitude, String date, String time, String user, String note, byte[] photo) {
        super(markerLatitude, markerLongitude, date, time, user, note);
        this.photo = photo;
    }

    public MesureErosionDistance() { super();    }

    public byte[] getPhoto() { return photo;    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
