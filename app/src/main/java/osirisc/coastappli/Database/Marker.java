package osirisc.coastappli.Database;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;

import osirisc.coastappli.R;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

/**
 * This class defines the Marker objects, that represent every location at which a indicator can be measured
 */

public class Marker {

    /**
     * Markers have a set of attributes :
     */
    private double longitude;
    private double latitude;
    private String nameBeach;
    private String nameTown = "Town name not provided";
    private String coastType = "Coast Type not provided";
    private String INEC = "INEC not provided";

    // This attribute is an int equal to 0 or 1. If equal to 1, the marker uses the measure of the photo capture for the erosion.
    // If equal to 0; it doesn't use it. Perhaps a better system could be implemented.
    private int erosionMeasurePhotoCapture = 1;
    private byte[] photo;

    public Marker() {}

    /**
     * This function is a constructor used if we don't have a picture to assign to the marker
     * @param latitude
     * @param longitude
     * @param nameBeach
     * @param nameTown
     * @param coastType
     * @param INEC
     * @param erosionMeasurePhotoCapture
     */

    public Marker(double latitude, double longitude, String nameBeach, String nameTown, String coastType, String INEC, int erosionMeasurePhotoCapture) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.nameBeach = nameBeach;
        this.nameTown = nameTown;
        this.coastType = coastType;
        this.erosionMeasurePhotoCapture = erosionMeasurePhotoCapture;
        this.INEC = INEC;
        //As we don't have an image to illustrate, we put a default picture
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ((BitmapDrawable) ContextCompat.getDrawable(getApplicationContext(), R.mipmap.ic_launcher_coast)).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        this.photo = byteArray;
    }

    /**
     * This constructor is used if there's an precise image to illsutrate the marker
     * @param latitude
     * @param longitude
     * @param nameBeach
     * @param nameTown
     * @param coastType
     * @param INEC
     * @param erosionMeasurePhotoCapture
     * @param photo
     */

    public Marker(double latitude, double longitude, String nameBeach, String nameTown, String coastType, String INEC, int erosionMeasurePhotoCapture, byte[] photo) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.nameBeach = nameBeach;
        this.nameTown = nameTown;
        this.coastType = coastType;
        this.erosionMeasurePhotoCapture = erosionMeasurePhotoCapture;
        this.INEC = INEC;
        this.photo = photo;
    }

    /**
     * The following functions are all the getters and setters for every single attribute of a Marker object
     * @return
     */

    public String getNameTown() { return nameTown; }

    public String getCoastType() { return coastType; }

    public String getINEC() { return INEC; }

    public int getErosionDistanceMesureBool() { return erosionMeasurePhotoCapture; }

    public void setErosionMeasurePhotoCapture(int erosionMeasurePhotoCapture) { this.erosionMeasurePhotoCapture = erosionMeasurePhotoCapture; }

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

    public byte[] getPhoto() { return photo;    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
