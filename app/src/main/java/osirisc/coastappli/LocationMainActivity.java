package osirisc.coastappli;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.view.View;

import osirisc.coastappli.Database.DatabaseAssistant;
import osirisc.coastappli.Database.Marker;
import osirisc.coastappli.location.SectionsPagerAdapterLocation;

/**
 * Activity launched when a marker is clicked on from the map
 * It's the activity in which a user can find all the information about a marker,
 * what indicator is available and when was the last time measures were taken
 */
public class LocationMainActivity extends AppCompatActivity {

    private static LocationMainActivity instance;

    // These are the information of the marker which was clicked on
    private Double markerLatitude;
    private Double markerLongitude;
    private String nameBeach;
    private String nameTown;
    private String coastType;
    private String INEC;
    private int erosionPhotoCaptureBool;

    // This is the width of the screen, used to adapt to layout for all screen sizes
    private int width;


    protected void onCreate(Bundle savedInstanceState) {
        // We first create the activity with it's 3 tabs
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_main_activity);
        SectionsPagerAdapterLocation sectionsPagerAdapterLocation = new SectionsPagerAdapterLocation(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapterLocation);
        TabLayout tabs = findViewById(R.id.tabs);
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

        // We recover the width of the screen
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;

        // We initialize the instance
        instance=this;
    }

    public Double getMarkerLatitude() { return markerLatitude;    }

    public Double getMarkerLongitude() {  return markerLongitude;    }

    public int getErosionPhotoCaptureBool() {
        return erosionPhotoCaptureBool;
    }

    public String getINEC() {
        return INEC;
    }

    public String getCoastType() {
        return coastType;
    }

    public String getNameBeach() {
        return nameBeach;
    }

    public String getNameTown() {
        return nameTown;
    }

    public int getWidth(){ return width;}

    public static LocationMainActivity getInstance() { return instance; }

    /**
     *  The byteArray corresponding to the photo in the information fragment is too big to be stored
     *  in the extras part of the Intent
     *  Therefore, we have to get it by searching in the database
     */
    public byte[] getPhoto() {
        DatabaseAssistant databaseAssistant = new DatabaseAssistant(this);
        Marker marker = databaseAssistant.findMarker(this.getMarkerLatitude(), this.getMarkerLongitude());
        return marker.getPhoto();
    }

    /**
     * Function launched when the button "photo capture" in erosion in the indicators' fragment is clicked on
     * It starts the new activity with all the info to take the right photo
     *
     * We put all the marker's data in the Intent in order to reload the LocationMainActivity when the photo is taken
     * (Therefore the user can take data for an other indicator and the Activity is correctly updated with the new data)
     * @param view
     */
    public void PhotoCaptureFunction(View view){
        Intent myIntent= new Intent(this, PhotoCaptureErosionMainActivity.class);
        myIntent.putExtra("markerLatitude", markerLatitude);
        myIntent.putExtra("markerLongitude", markerLongitude);
        myIntent.putExtra("nameBeach", nameBeach);
        myIntent.putExtra("nameTown", nameTown);
        myIntent.putExtra("coastType", coastType);
        myIntent.putExtra("INEC", INEC);
        myIntent.putExtra("erosionPhotoCaptureBool", erosionPhotoCaptureBool);
        LocationMainActivity.this.startActivity(myIntent);
    }

    /**
     * Function launched when clicking on the camera icon in the indicators' fragment
     * It launches a dialog which indicates that a camera is needed
     * @param view
     */
    public void onClickCameraIcon(View view) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle(getResources().getString(R.string.cameraIcon));

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        }

    /**
     * Function launched when clicking on the location icon in the indicators' fragment
     * It launches a dialog which indicates that the location is needed
     * @param view
     */
    public void onClicklocationIcon(View view) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle(getResources().getString(R.string.locationIcon));

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    /**
     * Function launched when clicking on the metre icon in the indicators' fragment
     * It launches a dialog which indicates that a metre is needed
     * @param view
     */
    public void onClickMetreIcon(View view) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle(getResources().getString(R.string.meterIcon));

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    /**
     * Function launched when clicking on the bronze medal icon in the indicators' fragment
     * It launches a dialog which indicates that this is an easy task
     * @param view
     */
    public void onClickBronzeMedalIcon(View view) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle(getResources().getString(R.string.bronzeMedalIcon));

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    /**
     * Function launched when clicking on the silver medal icon in the indicators' fragment
     * It launches a dialog which indicates that this is an intermediate task
     * @param view
     */
    public void onClickSilverMedalIcon(View view) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle(getResources().getString(R.string.silverMedalIcon));

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    /**
     * Function launched when clicking on the gold medal icon in the indicators' fragment
     * It launches a dialog which indicates that this is a difficult task
     * @param view
     */
    public void onClickGoldMedalIcon(View view) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle(getResources().getString(R.string.goldMedalIcon));

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    /**
     * Function launched when clicking on the question icon in the indicators' fragment for the photo capture
     * It launches a dialog explaining what the task is
     * @param view
     */
    public void onClickQuestionIconPhotoCapture(View view) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle(getResources().getString(R.string.erosionDistance));
        alertDialogBuilder.setMessage(getResources().getString(R.string.questionIconPhotoCapture));

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    /**
     * Function launched when clicking on the question icon in the indicators' fragment for a task not yet implemented
     * It launches a dialog explaining that the task is not implemented
     * @param view
     */
    public void onClickQuestionIconNotImplemented(View view) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle(getResources().getString(R.string.notImplemented));

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}