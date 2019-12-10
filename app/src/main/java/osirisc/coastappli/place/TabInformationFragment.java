package osirisc.coastappli.place;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
        return root;
    }

}
