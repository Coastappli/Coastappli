package osirisc.coastappli;


import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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
    private Bitmap bitmap;

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
        }
    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "osirisc.coastappli.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            Button buttonValidate = findViewById(R.id.buttonValidate);
            LinearLayout noteLayout = findViewById(R.id.NoteLayout);
            noteLayout.setVisibility(VISIBLE);
            buttonValidate.setVisibility(VISIBLE);

            ImageView imageViewPhoto = findViewById(R.id.imageViewPhoto);
            int targetW = imageViewPhoto.getWidth();
            int targetH = imageViewPhoto.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;

            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
            imageViewPhoto.setImageBitmap(bitmap);
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
        mesure.setUser(getResources().getString(R.string.notImplemented));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        //Photo is compressed to 60% in order to be a smaller byte[]: it doesn't work if it's too big
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60 , baos);
        byte[] b = baos.toByteArray();
        mesure.setPhoto(b);
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
        PlaceMainActivity.getInstance().finish();
        MethodMainActivity.this.startActivity(myPlaceIntent);
        new File(currentPhotoPath).delete();
    }

    public Double getMarkerLatitude() {
        return markerLatitude;
    }

    public Double getMarkerLongitude() {
        return markerLongitude;
    }

    public Bitmap getImageBitmap() {
        return bitmap;
    }
}