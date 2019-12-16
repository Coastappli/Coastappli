package osirisc.coastappli;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


import osirisc.coastappli.Database.DatabaseAssistant;
import osirisc.coastappli.Database.Marker;
import osirisc.coastappli.Database.MethodErosionDistance;


public class FullScreen extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Double markerLatitude = extras.getDouble("markerLatitude");
            Double markerLongitude = extras.getDouble("markerLongitude");
            String imageType = extras.getString("imageType");
            ImageView imageFullScreen = findViewById(R.id.imageFullScreen);
            if (imageType.equals("informationPhoto")) {
                DatabaseAssistant databaseAssistant = new DatabaseAssistant(this);
                Marker marker = databaseAssistant.findMarker(markerLatitude, markerLongitude);
                Bitmap bm = BitmapFactory.decodeByteArray(marker.getPhoto(), 0,marker.getPhoto().length);
                imageFullScreen.setImageBitmap(bm);
            }
            if (imageType.equals("MethodPhoto")) {
                DatabaseAssistant databaseAssistant = new DatabaseAssistant(this);
                MethodErosionDistance method = databaseAssistant.findMethodErosionDistance(markerLatitude, markerLongitude);
                Bitmap bm = BitmapFactory.decodeByteArray(method.getPhoto(), 0,method.getPhoto().length);
                imageFullScreen.setImageBitmap(bm);
            }
            if (imageType.equals("MethodPhotoPerson")) {
                DatabaseAssistant databaseAssistant = new DatabaseAssistant(this);
                MethodErosionDistance method = databaseAssistant.findMethodErosionDistance(markerLatitude, markerLongitude);
                Bitmap bm = BitmapFactory.decodeByteArray(method.getPhotoPerson(), 0,method.getPhotoPerson().length);
                imageFullScreen.setImageBitmap(bm);
            }
        }
    }
}