package osirisc.coastappli.location;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.chrisbanes.photoview.PhotoView;

import osirisc.coastappli.Database.DatabaseAssistant;
import osirisc.coastappli.Database.MeasureErosionPhotoCapture;
import osirisc.coastappli.LocationMainActivity;
import osirisc.coastappli.R;

public class TabTraceFragment extends Fragment {

    // measureErosionPhotoCapture is the object containing all the data necessary to fill the Layout Erosion - Photo Capture
    private MeasureErosionPhotoCapture measureErosionPhotoCapture;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        // We first inflate the layout
        View root = inflater.inflate(R.layout.tab_trace, container, false);

        // We then have to retrieve the information from the database in order to fill the layout Erosion - Photo Capture
        // with to correct pictures and text from this marker's method
        DatabaseAssistant databaseAssistant = new DatabaseAssistant(getActivity());
        // We retrieve the marker's latitude and longitude from the PhotoCaptureErosionMainActivity in order to find the method for this marker
        measureErosionPhotoCapture = databaseAssistant.findMeasureErosionDistance(((LocationMainActivity)getActivity()).getMarkerLatitude(), ((LocationMainActivity)getActivity()).getMarkerLongitude());
        if (measureErosionPhotoCapture !=null) {
            // We fill the layout
            TextView textViewDateFill = root.findViewById(R.id.textViewDateFill);
            TextView textViewUserFill = root.findViewById(R.id.textViewUserFill);
            TextView textViewNoteFill = root.findViewById(R.id.textViewNoteFill);
            ImageView imageView2 = root.findViewById(R.id.imageView2);
            textViewDateFill.setText(measureErosionPhotoCapture.getDate() + "\n" + measureErosionPhotoCapture.getTime());
            textViewUserFill.setText(measureErosionPhotoCapture.getUser());
            textViewNoteFill.setText(measureErosionPhotoCapture.getNotes());
            imageView2.setImageBitmap(BitmapFactory.decodeByteArray( measureErosionPhotoCapture.getPhoto(), 0, measureErosionPhotoCapture.getPhoto().length));
        }
        return root;
    }

    /**
     * This function is launched when the Activity is created and therefore when the layouts are correctly inflated
     * We can here add the onClickListeners to the picture from the indicator Erosion - Photo Capture
     * It activates a new dialog allowing the user to zoom in the picture and move around in it
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        // We retrieve the image view
        final ImageView imageViewTrace = getView().findViewById(R.id.imageView2);

        // We set a onclick listener for when the image gets clicked
        imageViewTrace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // We create an AlertDialog.Builder
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());

                // We inflate a new layout composed of nothing apart from a PhotoView
                View mView = getLayoutInflater().inflate(R.layout.fullscreen, null);
                PhotoView photoView = mView.findViewById(R.id.photo_view);

                // In this PhotoView, we set the image we want from the object method
                Bitmap bm = BitmapFactory.decodeByteArray(measureErosionPhotoCapture.getPhoto(), 0, measureErosionPhotoCapture.getPhoto().length);
                photoView.setImageBitmap(bm);
                mBuilder.setView(mView);

                // We then create the Builder
                AlertDialog mDialog = mBuilder.create();

                // We set the background as TRANSPARENT in order to see to image in the builder like a pop up
                // (the background is still visible so the user is not lost)
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialog.show();
            }
        });
    }
}
