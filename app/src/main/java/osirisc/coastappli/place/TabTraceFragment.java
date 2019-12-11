package osirisc.coastappli.place;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import osirisc.coastappli.Database.DatabaseAssistant;
import osirisc.coastappli.Database.Mesure;
import osirisc.coastappli.PlaceMainActivity;
import osirisc.coastappli.R;

public class TabTraceFragment extends Fragment {

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_trace, container, false);
        DatabaseAssistant databaseAssistant = new DatabaseAssistant(getActivity());
        Mesure mesure = databaseAssistant.findMesureErosionDistance(((PlaceMainActivity)getActivity()).getMarkerLatitude(), ((PlaceMainActivity)getActivity()).getMarkerLongitude());
        if (mesure!=null) {
            TextView textViewDateFill = root.findViewById(R.id.textViewDateFill);
            TextView textViewUserFill = root.findViewById(R.id.textViewUserFill);
            TextView textViewNoteFill = root.findViewById(R.id.textViewNoteFill);
            textViewDateFill.setText(mesure.getDate() + "\n" + mesure.getTime());
            textViewUserFill.setText(mesure.getUser());
            textViewNoteFill.setText(mesure.getNotes());
        }
        return root;
    }
}
