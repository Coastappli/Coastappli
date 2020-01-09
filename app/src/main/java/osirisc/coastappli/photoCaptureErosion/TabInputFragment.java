package osirisc.coastappli.photoCaptureErosion;


import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.chrisbanes.photoview.PhotoView;

import osirisc.coastappli.PhotoCaptureErosionMainActivity;
import osirisc.coastappli.R;

/**
 * This tab is one of the tabs of the PhotoCaptureErosionMainActivity
 * It's the tab where the user can take a picture, add notes and then validate to send the data to the database
 */
public class TabInputFragment extends Fragment {
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // We first inflate the layout
        View root = inflater.inflate(R.layout.tab_input_photo_capture_erosion, container, false);

        // We then add an onClickListener for when the user clicks on the picture he's taken
        // It activates a new dialog allowing the user to zoom in the picture and move around in it
        ImageView imageViewPhoto = root.findViewById(R.id.imageViewPhoto);
        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                // We create an AlertDialog.Builder
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());

                // We inflate a new layout composed of nothing apart from a PhotoView
                View mView = getLayoutInflater().inflate(R.layout.fullscreen, null);
                PhotoView photoView = mView.findViewById(R.id.photo_view);

                // In this PhotoView, we set the image we want
                photoView.setImageBitmap(((PhotoCaptureErosionMainActivity)getActivity()).getImageBitmap());
                mBuilder.setView(mView);

                // We then create the Builder
                AlertDialog mDialog = mBuilder.create();

                // We set the background as TRANSPARENT in order to see to image in the builder like a pop up
                // (the background is still visible so the user is not lost)
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialog.show();
            }
        });
        return root;
    }

}
