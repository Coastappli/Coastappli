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
import osirisc.coastappli.Database.MeasureErosionPhotoCapture;
import osirisc.coastappli.photoCaptureErosion.SectionsPagerAdapterPhotoCaptureErosion;

import static android.view.View.VISIBLE;

/**
 * Activity launched when to erosion photo capture button in the indicators' fragment is clicked on
 * It's the activity in which the user can find information about how to take the photo and send it
 */
public class PhotoCaptureErosionMainActivity extends AppCompatActivity {

    // These are the information of the marker which was clicked on
    private Double markerLatitude;
    private Double markerLongitude;
    private String nameBeach;
    private String nameTown;
    private String coastType;
    private String INEC;
    private int erosionPhotoCaptureBool;

    private Bitmap bitmap;
    private String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // We first create the activity with it's 2 tabs
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_capture_erosion_main_activity);
        SectionsPagerAdapterPhotoCaptureErosion sectionsPagerAdapterPhotoCaptureErosion = new SectionsPagerAdapterPhotoCaptureErosion(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager_photo_capture_erosion);
        viewPager.setAdapter(sectionsPagerAdapterPhotoCaptureErosion);
        TabLayout tabs = findViewById(R.id.tabs_photo_capture_erosion);
        tabs.setupWithViewPager(viewPager);

        // We recover the data of the marker which stored in the extras part of the Intent used to launch this activity
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            markerLatitude = extras.getDouble("markerLatitude");
            markerLongitude = extras.getDouble("markerLongitude");
            nameBeach = extras.getString("nameBeach");
            nameTown = extras.getString("nameTown");
            coastType = extras.getString("coastType");
            INEC = extras.getString("INEC");
            erosionPhotoCaptureBool = extras.getInt("erosionPhotoCaptureBool");
        }
    }

    /**
     * This Function is launched when clicking in the take photo button
     * It launches a new camera activity
     * @param view
     */
    public void takePhoto(View view) {
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
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    /**
     * This function is called when creating the image file as to photo is taken
     *
     * We have to create a path in which to store the photo on the device until it's pushed to the database
     * @return File
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // We create an image file and store it on the device under a time stamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // We save the path for future deletion
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    /**
     * This function is launched when the camera activity is done
     * It makes the photo appear on the screen as well as the validate button and the notes
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If a photo was taken
        if (requestCode == 1 && resultCode == RESULT_OK) {

            // The validate button and the notes appear (they were previously in GONE mode)
            Button buttonValidate = findViewById(R.id.buttonValidate);
            LinearLayout noteLayout = findViewById(R.id.NoteLayout);
            noteLayout.setVisibility(VISIBLE);
            buttonValidate.setVisibility(VISIBLE);

            // We then want to display the photo in the right size for the screen of the device

            // We therefore recover the width and the height of the photo
            ImageView imageViewPhoto = findViewById(R.id.imageViewPhoto);
            int targetW = imageViewPhoto.getWidth();
            int targetH = imageViewPhoto.getHeight();

            // We get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;

            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // We determine how much to scale down the image
            int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

            // We decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
            imageViewPhoto.setImageBitmap(bitmap);

            // We then set the view to VISIBLE mode
            imageViewPhoto.setVisibility(VISIBLE);
        }
    }

    /**
     * This function is launched when the validate button is clicked on
     * It creates and pushes the new mesure to the database
     * @param view
     */
    public void validate(View view){
        // We create the new measure
        MeasureErosionPhotoCapture mesure = new MeasureErosionPhotoCapture();
        mesure.setMarkerLatitude(markerLatitude);
        mesure.setMarkerLongitude(markerLongitude);
        mesure.setDate(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
        mesure.setTime(new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
        TextView noteText = findViewById(R.id.editTextNote);
        mesure.setNotes((noteText.getText()).toString());
        mesure.setUser(getResources().getString(R.string.notImplemented));


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //Photo is compressed to 60% in order to be a smaller byte[]: it can't be stored if it's too big
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60 , baos);
        byte[] b = baos.toByteArray();
        mesure.setPhoto(b);

        // We push the ne measure in the database
        DatabaseAssistant databaseAssistant = new DatabaseAssistant(this);
        databaseAssistant.addMeasureErosionDistance(mesure);

        // We then finish this activity and reload (finish and restart) the previous LocationMainActivity in order to update it with the new data
        PhotoCaptureErosionMainActivity.this.finish();
        LocationMainActivity.getInstance().finish();

        Intent myPlaceIntent= new Intent(this, LocationMainActivity.class);
        myPlaceIntent.putExtra("markerLatitude", markerLatitude);
        myPlaceIntent.putExtra("markerLongitude", markerLongitude);
        myPlaceIntent.putExtra("nameBeach", nameBeach);
        myPlaceIntent.putExtra("nameTown", nameTown);
        myPlaceIntent.putExtra("coastType", coastType);
        myPlaceIntent.putExtra("INEC", INEC);
        myPlaceIntent.putExtra("erosionPhotoCaptureBool", erosionPhotoCaptureBool);
        PhotoCaptureErosionMainActivity.this.startActivity(myPlaceIntent);

        // The photo stored in the device is then deleted is it's not useful anymore
        new File(currentPhotoPath).delete();
    }

    public Double getMarkerLatitude() { return markerLatitude; }

    public Double getMarkerLongitude() { return markerLongitude; }

    public Bitmap getImageBitmap() { return bitmap; }
}