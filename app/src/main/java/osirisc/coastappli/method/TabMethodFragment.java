package osirisc.coastappli.method;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import osirisc.coastappli.Database.DatabaseAssistant;
import osirisc.coastappli.Database.MethodErosionDistance;
import osirisc.coastappli.FullScreen;
import osirisc.coastappli.MethodMainActivity;
import osirisc.coastappli.R;

public class TabMethodFragment extends Fragment {
    private MethodErosionDistance method;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_method, container, false);
        DatabaseAssistant databaseAssistant = new DatabaseAssistant(getActivity());
        method = databaseAssistant.findMethodErosionDistance(((MethodMainActivity)getActivity()).getMarkerLatitude(), ((MethodMainActivity)getActivity()).getMarkerLongitude());
        if (method!=null) {
            TextView textViewMethodFillClue1 = root.findViewById(R.id.textViewMethodFillClue1);
            TextView textViewMethodFillClue2 = root.findViewById(R.id.textViewMethodFillClue2);
            TextView textViewMethodFillClue3 = root.findViewById(R.id.textViewMethodFillClue3);
            ImageView imageViewMethodPhoto = root.findViewById(R.id.imageViewMethodPhoto);
            ImageView imageViewMethodPhotoPerson = root.findViewById(R.id.imageViewMethodPhotoPerson);
            textViewMethodFillClue1.setText(method.getClue1());
            textViewMethodFillClue2.setText(method.getClue2());
            textViewMethodFillClue3.setText(method.getClue3());
            imageViewMethodPhoto.setImageBitmap(BitmapFactory.decodeByteArray(method.getPhoto(), 0,method.getPhoto().length));
            imageViewMethodPhotoPerson.setImageBitmap(BitmapFactory.decodeByteArray(method.getPhotoPerson(), 0,method.getPhotoPerson().length));
        }
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // get the button view
        final ImageView imageViewMethodPhoto = getView().findViewById(R.id.imageViewMethodPhoto);
        // set a onclick listener for when the button gets clicked
        imageViewMethodPhoto.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(getActivity(), FullScreen.class);
                mainIntent.putExtra("markerLatitude",(method.getMarkerLatitude()));
                mainIntent.putExtra("markerLongitude",(method.getMarkerLongitude()));
                mainIntent.putExtra("imageType", "MethodPhoto");
                startActivity(mainIntent);
            }
        });
        final ImageView imageViewMethodPhotoPerson = getView().findViewById(R.id.imageViewMethodPhotoPerson);
        // set a onclick listener for when the button gets clicked
        imageViewMethodPhotoPerson.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(getActivity(), FullScreen.class);
                mainIntent.putExtra("markerLatitude",(method.getMarkerLatitude()));
                mainIntent.putExtra("markerLongitude",(method.getMarkerLongitude()));
                mainIntent.putExtra("imageType", "MethodPhotoPerson");
                startActivity(mainIntent);
            }
        });
    }
}
