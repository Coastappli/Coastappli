package osirisc.coastappli;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import osirisc.coastappli.method.SectionsPagerAdapterMethod;

public class MethodMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.method_main_activity);
        SectionsPagerAdapterMethod sectionsPagerAdapterMethod = new SectionsPagerAdapterMethod(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager_method);
        viewPager.setAdapter(sectionsPagerAdapterMethod);
        TabLayout tabs = findViewById(R.id.tabs_method);
        tabs.setupWithViewPager(viewPager);
    }
}