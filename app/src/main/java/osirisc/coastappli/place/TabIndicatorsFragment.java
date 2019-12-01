package osirisc.coastappli.place;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import osirisc.coastappli.MainActivity;
import osirisc.coastappli.MethodMainActivity;
import osirisc.coastappli.R;

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
                    scrollViewErosion.setVisibility(root.VISIBLE);
                    scrollViewSubmersion.setVisibility(root.GONE);
                }
                if (checkedId == R.id.radioButtonSubmersion){
                    scrollViewErosion.setVisibility(root.GONE);
                    scrollViewSubmersion.setVisibility(root.VISIBLE);
                }
            }
        });
        return root;
    }

}
