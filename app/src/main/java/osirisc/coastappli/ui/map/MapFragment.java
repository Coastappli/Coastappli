package osirisc.coastappli.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import osirisc.coastappli.MainActivity;
import osirisc.coastappli.R;

public class MapFragment extends Fragment implements View.OnClickListener {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity)getActivity()).createMapLocation(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        return root;
    }
    @Override
    public void onClick(View v) {
        ((MainActivity)getActivity()).centerMapOnMyLocation();

    }
}