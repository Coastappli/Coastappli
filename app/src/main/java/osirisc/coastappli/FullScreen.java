package osirisc.coastappli;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import osirisc.coastappli.R;
import osirisc.coastappli.place.TabTraceFragment;

public class FullScreen extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            Integer imageId = extras.getInt("ImageId");
            ImageView imageViewRetrieved = findViewById(imageId);
            ImageView imageFullScreen = findViewById(R.id.imageFullScreen);
            if (imageViewRetrieved != null) {
                imageFullScreen.setImageBitmap(((BitmapDrawable) imageViewRetrieved.getDrawable()).getBitmap());

            }
        }
    }
}