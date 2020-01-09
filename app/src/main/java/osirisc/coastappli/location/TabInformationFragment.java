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

import osirisc.coastappli.LocationMainActivity;
import osirisc.coastappli.R;

/**
 * This tab is one of the tabs of the LocationMainActivity
 * It's the tab where all the different information of the selected marker are shown
 */
public class TabInformationFragment extends Fragment {

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        // We first inflate the layout
        View root = inflater.inflate(R.layout.tab_information, container, false);

        // We then fill the layout with the all the info from the marker
        TextView textViewNamePlace = root.findViewById(R.id.textViewNamePlace);
        TextView textViewCoastType = root.findViewById(R.id.textViewCoastType);
        TextView textViewINEC = root.findViewById(R.id.textViewINEC);
        // The data is stored in objects in the LocationMainActivity so we retrieve the data with the activity's getters
        textViewNamePlace.setText(((LocationMainActivity)getActivity()).getNameBeach()+"\n"+((LocationMainActivity)getActivity()).getNameTown());
        textViewCoastType.setText(((LocationMainActivity)getActivity()).getCoastType());
        textViewINEC.setText(((LocationMainActivity)getActivity()).getINEC());
        ImageView imageViewPlace = root.findViewById(R.id.imageViewPlace);
        Bitmap bm = BitmapFactory.decodeByteArray(((LocationMainActivity)getActivity()).getPhoto(), 0,((LocationMainActivity)getActivity()).getPhoto().length);
        imageViewPlace.setImageBitmap(bm);
        return root;
    }

    /**
     * This function is launched when the Activity is created and therefore when the layouts are correctly inflated
     * We can here add the onClickListeners to the information picture
     * It activates a new dialog allowing the user to zoom in the picture and move around in it
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // We retrieve the image view
        final ImageView imageViewPlace = getView().findViewById(R.id.imageViewPlace);

        // We set a onclick listener for when the image gets clicked
        imageViewPlace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // We create an AlertDialog.Builder
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());

                // We inflate a new layout composed of nothing apart from a PhotoView
                View mView = getLayoutInflater().inflate(R.layout.fullscreen, null);
                PhotoView photoView = mView.findViewById(R.id.photo_view);

                // In this PhotoView, we set the image we want from the object method
                Bitmap bm = BitmapFactory.decodeByteArray(((LocationMainActivity)getActivity()).getPhoto(), 0,((LocationMainActivity)getActivity()).getPhoto().length);
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