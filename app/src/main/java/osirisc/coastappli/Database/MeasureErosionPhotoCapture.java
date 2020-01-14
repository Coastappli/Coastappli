package osirisc.coastappli.Database;

/**
 * This class defines the MeasureErosionPhotoCapture objects
 * They are the pictures included in the measure of ErosionDistance indicator
 */

public class MeasureErosionPhotoCapture extends Measure {
    private byte[] photo;

    public MeasureErosionPhotoCapture() { super();    }

    public byte[] getPhoto() { return photo;    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
