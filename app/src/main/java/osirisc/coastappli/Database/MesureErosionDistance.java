package osirisc.coastappli.Database;

public class MesureErosionDistance extends Mesure {
    private byte[] photo;

    public MesureErosionDistance() { super();    }

    public byte[] getPhoto() { return photo;    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
