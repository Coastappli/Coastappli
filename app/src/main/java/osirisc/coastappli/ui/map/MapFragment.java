package osirisc.coastappli.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import osirisc.coastappli.MainActivity;
import osirisc.coastappli.R;

/**
 * This fragment is launched when "Map" is clicked in the navigation bar, it displays the map.
 */

public class MapFragment extends Fragment implements View.OnClickListener {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //We start by creating the MapbokMap thanks to our createMapLocation function
        ((MainActivity)getActivity()).createMapLocation(savedInstanceState);
        //Inflating the layout
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        //We add a floating action button to the fragment, and set an on-click listener on it.
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        return root;
    }
    @Override
    public void onClick(View v) {
        //The centerMapOnMyLocation function is called when clicking the floating action button
        ((MainActivity)getActivity()).centerMapOnMyLocation();
    }
}