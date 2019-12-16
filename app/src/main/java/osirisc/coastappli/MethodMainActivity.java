package osirisc.coastappli;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import osirisc.coastappli.Database.DatabaseAssistant;
import osirisc.coastappli.Database.MesureErosionDistance;
import osirisc.coastappli.method.SectionsPagerAdapterMethod;

import static android.view.View.VISIBLE;

public class MethodMainActivity extends AppCompatActivity {
    private Double markerLatitude;
    private Double markerLongitude;
    private String nameBeach;
    private String nameTown;
    private String coastType;
    private String INEC;
    private int erosionDistanceMesureBool;
    private byte[] photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.method_main_activity);
        SectionsPagerAdapterMethod sectionsPagerAdapterMethod = new SectionsPagerAdapterMethod(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager_method);
        viewPager.setAdapter(sectionsPagerAdapterMethod);
        TabLayout tabs = findViewById(R.id.tabs_method);
        tabs.setupWithViewPager(viewPager);
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            markerLatitude = extras.getDouble("markerLatitude");
            markerLongitude = extras.getDouble("markerLongitude");
            nameBeach = extras.getString("nameBeach");
            nameTown = extras.getString("nameTown");
            coastType = extras.getString("coastType");
            INEC = extras.getString("INEC");
            erosionDistanceMesureBool = extras.getInt("erosionDistanceMesureBool");
            photo = extras.getByteArray("photo");
        }
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            Button buttonValidate = findViewById(R.id.buttonValidate);
            LinearLayout noteLayout = findViewById(R.id.NoteLayout);
            noteLayout.setVisibility(VISIBLE);
            buttonValidate.setVisibility(VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView imageViewPhoto = findViewById(R.id.imageViewPhoto);
            imageViewPhoto.setImageBitmap(imageBitmap);
            imageViewPhoto.setVisibility(VISIBLE);
        }
    }

    public void validate(View view){
        MesureErosionDistance mesure = new MesureErosionDistance();
        mesure.setMarkerLatitude(markerLatitude);
        mesure.setMarkerLongitude(markerLongitude);
        mesure.setDate(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
        mesure.setTime(new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
        TextView noteText = findViewById(R.id.editTextNote);
        mesure.setNotes((noteText.getText()).toString());
        mesure.setUser("");
        ImageView imageView2 = findViewById(R.id.imageViewPhoto);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ((BitmapDrawable)imageView2.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        mesure.setPhoto(byteArray);
        DatabaseAssistant databaseAssistant = new DatabaseAssistant(this);
        databaseAssistant.addMesureErosionDistance(mesure);
        MethodMainActivity.this.finish();

        Intent myPlaceIntent= new Intent(this, PlaceMainActivity.class);
        myPlaceIntent.putExtra("markerLatitude", markerLatitude);
        myPlaceIntent.putExtra("markerLongitude", markerLongitude);
        myPlaceIntent.putExtra("nameBeach", nameBeach);
        myPlaceIntent.putExtra("nameTown", nameTown);
        myPlaceIntent.putExtra("coastType", coastType);
        myPlaceIntent.putExtra("INEC", INEC);
        myPlaceIntent.putExtra("erosionDistanceMesureBool", erosionDistanceMesureBool);
        myPlaceIntent.putExtra("photo", photo);
        MethodMainActivity.this.startActivity(myPlaceIntent);
    }

    public Double getMarkerLatitude() {
        return markerLatitude;
    }

    public Double getMarkerLongitude() {
        return markerLongitude;
    }
}