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
import android.widget.Button;

import osirisc.coastappli.Database.DatabaseAssistant;
import osirisc.coastappli.Database.Marker;
import osirisc.coastappli.place.SectionsPagerAdapter;

public class PlaceMainActivity extends AppCompatActivity {
    private Double markerLatitude;
    private Double markerLongitude;
    private String nameBeach;
    private String nameTown;
    private String coastType;
    private String INEC;
    private int erosionDistanceMesureBool;
    private static PlaceMainActivity instance;

    private int width;

    public Double getMarkerLatitude() { return markerLatitude;    }

    public Double getMarkerLongitude() {  return markerLongitude;    }

    public int getErosionDistanceMesureBool() {
        return erosionDistanceMesureBool;
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

    public byte[] getPhoto() {
        DatabaseAssistant databaseAssistant = new DatabaseAssistant(this);
        Marker marker = databaseAssistant.findMarker(this.getMarkerLatitude(), this.getMarkerLongitude());
        return marker.getPhoto();
    }

    public int getWidth(){ return width;}

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_main_activity);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
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
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        instance=this;
    }


    public void distanceFunction(View view){
        Intent myIntent= new Intent(this, MethodMainActivity.class);
        myIntent.putExtra("markerLatitude", markerLatitude);
        myIntent.putExtra("markerLongitude", markerLongitude);
        myIntent.putExtra("nameBeach", nameBeach);
        myIntent.putExtra("nameTown", nameTown);
        myIntent.putExtra("coastType", coastType);
        myIntent.putExtra("INEC", INEC);
        myIntent.putExtra("erosionDistanceMesureBool", erosionDistanceMesureBool);
        PlaceMainActivity.this.startActivity(myIntent);
    }

    public static PlaceMainActivity getInstance() {
        return instance;
    }

    public void onClickPhotoIcon(View view) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("L'appareil photo est requis");

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