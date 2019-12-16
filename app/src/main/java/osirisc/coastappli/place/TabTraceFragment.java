package osirisc.coastappli.place;

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
import osirisc.coastappli.Database.MesureErosionDistance;
import osirisc.coastappli.FullScreen;
import osirisc.coastappli.PlaceMainActivity;
import osirisc.coastappli.R;

public class TabTraceFragment extends Fragment {


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_trace, container, false);
        DatabaseAssistant databaseAssistant = new DatabaseAssistant(getActivity());
        MesureErosionDistance mesure = databaseAssistant.findMesureErosionDistance(((PlaceMainActivity)getActivity()).getMarkerLatitude(), ((PlaceMainActivity)getActivity()).getMarkerLongitude());
        if (mesure!=null) {
            TextView textViewDateFill = root.findViewById(R.id.textViewDateFill);
            TextView textViewUserFill = root.findViewById(R.id.textViewUserFill);
            TextView textViewNoteFill = root.findViewById(R.id.textViewNoteFill);
            ImageView imageView2 = root.findViewById(R.id.imageView2);
            textViewDateFill.setText(mesure.getDate() + "\n" + mesure.getTime());
            textViewUserFill.setText(mesure.getUser());
            textViewNoteFill.setText(mesure.getNotes());
            imageView2.setImageBitmap(BitmapFactory.decodeByteArray( mesure.getPhoto(), 0,mesure.getPhoto().length));
        }
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        // get the button view
        final ImageView imageView2 = getView().findViewById(R.id.imageView2);
        // set a onclick listener for when the button gets clicked
        imageView2.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(getActivity(), FullScreen.class);
                mainIntent.putExtra("ImageId",imageView2.getId());
                startActivity(mainIntent);
            }
        });
    }
}
