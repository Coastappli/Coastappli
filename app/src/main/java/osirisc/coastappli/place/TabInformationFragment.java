package osirisc.coastappli.place;

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

import osirisc.coastappli.PlaceMainActivity;
import osirisc.coastappli.R;

public class TabInformationFragment extends Fragment {
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_information, container, false);
        TextView textViewNamePlace = root.findViewById(R.id.textViewNamePlace);
        TextView textViewCoastType = root.findViewById(R.id.textViewCoastType);
        TextView textViewINEC = root.findViewById(R.id.textViewINEC);
        textViewNamePlace.setText(((PlaceMainActivity)getActivity()).getNameBeach()+"\n"+((PlaceMainActivity)getActivity()).getNameTown());
        textViewCoastType.setText(((PlaceMainActivity)getActivity()).getCoastType());
        textViewINEC.setText(((PlaceMainActivity)getActivity()).getINEC());
        ImageView imageViewPlace = root.findViewById(R.id.imageViewPlace);
        Bitmap bm = BitmapFactory.decodeByteArray(((PlaceMainActivity)getActivity()).getPhoto(), 0,((PlaceMainActivity)getActivity()).getPhoto().length);
        imageViewPlace.setImageBitmap(bm);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // get the button view
        final ImageView imageViewPlace = getView().findViewById(R.id.imageViewPlace);
        imageViewPlace.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.fullscreen, null);
                PhotoView photoView = mView.findViewById(R.id.photo_view);
                Bitmap bm = BitmapFactory.decodeByteArray(((PlaceMainActivity)getActivity()).getPhoto(), 0,((PlaceMainActivity)getActivity()).getPhoto().length);
                photoView.setImageBitmap(bm);
                mBuilder.setView(mView);
                AlertDialog mDialog = mBuilder.create();
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialog.show();
            }
        });
    }
}
