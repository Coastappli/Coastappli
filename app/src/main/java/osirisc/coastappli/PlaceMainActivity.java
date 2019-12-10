package osirisc.coastappli;


import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import osirisc.coastappli.Database.Marker;
import osirisc.coastappli.place.SectionsPagerAdapter;
import osirisc.coastappli.place.TabIndicatorsFragment;

public class PlaceMainActivity extends AppCompatActivity {

    private String nameBeach;
    private String nameTown;
    private String coastType;
    private String INEC;
    private int erosionDistanceMesure;

    public int getErosionDistanceMesure() {
        return erosionDistanceMesure;
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
            nameBeach = extras.getString("nameBeach");
            nameTown = extras.getString("nameTown");
            coastType = extras.getString("coastType");
            INEC = extras.getString("INEC");
            erosionDistanceMesure = extras.getInt("erosionDistanceMesure");

        }
    }

    public void distanceFunction(View view){
        Intent myIntent= new Intent(this, MethodMainActivity.class);
        PlaceMainActivity.this.startActivity(myIntent);
    }
}