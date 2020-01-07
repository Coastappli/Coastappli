package osirisc.coastappli.Database;

public class MeasureErosionPhotoCapture extends Measure {
    private byte[] photo;

    public MeasureErosionPhotoCapture() { super();    }

    public byte[] getPhoto() { return photo;    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
