package osirisc.coastappli.place;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import osirisc.coastappli.PlaceMainActivity;
import osirisc.coastappli.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class TabIndicatorsFragment extends Fragment {

    private ScrollView scrollViewErosion;
    private ScrollView scrollViewSubmersion;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.tab_indicators, container, false);
        RadioGroup radioGroupErosionSubmersion = root.findViewById(R.id.radioGroupErosionSubmersion);
        scrollViewErosion = root.findViewById(R.id.scrollViewErosion);
        scrollViewSubmersion = root.findViewById(R.id.scrollViewSubmersion);
        radioGroupErosionSubmersion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonErosion){
                    scrollViewErosion.setVisibility(VISIBLE);
                    scrollViewSubmersion.setVisibility(GONE);
                }
                if (checkedId == R.id.radioButtonSubmersion){
                    scrollViewErosion.setVisibility(GONE);
                    scrollViewSubmersion.setVisibility(VISIBLE);
                }
            }
        });
        if (((PlaceMainActivity)getActivity()).getErosionDistanceMesure()== 0){
            Button distanceButton = root.findViewById(R.id.distanceButton);
            distanceButton.setVisibility(GONE);
        }
        return root;
    }

}
