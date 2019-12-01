package osirisc.coastappli;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import osirisc.coastappli.method.SectionsPagerAdapterMethod;

import static android.view.View.VISIBLE;

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

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            Button buttonValidate = findViewById(R.id.buttonValidate);
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
        //Save the data in the Database
        MethodMainActivity.this.finish();
    }
}