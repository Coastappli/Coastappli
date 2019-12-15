package osirisc.coastappli;


import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import osirisc.coastappli.place.SectionsPagerAdapter;

public class PlaceMainActivity extends AppCompatActivity {
    private Double markerLatitude;
    private Double markerLongitude;
    private String nameBeach;
    private String nameTown;
    private String coastType;
    private String INEC;
    private int erosionDistanceMesureBool;
    private byte[] photo;

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
        return photo;
    }


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
            photo = extras.getByteArray("photo");
        }
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
        myIntent.putExtra("photo", photo);
        PlaceMainActivity.this.startActivity(myIntent);
        PlaceMainActivity.this.finish();
    }
}