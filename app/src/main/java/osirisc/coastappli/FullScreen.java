package osirisc.coastappli;

import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class FullScreen extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            byte[] image = extras.getByteArray("Image");
            ImageView imageFullScreen = findViewById(R.id.imageFullScreen);
            if (image != null) {
                imageFullScreen.setImageBitmap(BitmapFactory.decodeByteArray(image, 0,image.length));
            }
        }
    }
}