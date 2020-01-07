package osirisc.coastappli.location;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import osirisc.coastappli.LocationMainActivity;
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
        final RadioButton radioButtonErosion = root.findViewById(R.id.radioButtonErosion);
        radioButtonErosion.setWidth(((((LocationMainActivity)getActivity()).getWidth())/2));
        final RadioButton radioButtonSubmersion = root.findViewById(R.id.radioButtonSubmersion);
        radioButtonSubmersion.setWidth(((((LocationMainActivity)getActivity()).getWidth())/2));
        radioButtonErosion.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
        radioButtonSubmersion.setTextColor(getResources().getColor(R.color.grey));

        radioGroupErosionSubmersion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonErosion){
                    radioButtonErosion.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                    radioButtonSubmersion.setTextColor(getResources().getColor(R.color.grey));
                    scrollViewErosion.setVisibility(VISIBLE);
                    scrollViewSubmersion.setVisibility(GONE);
                }
                if (checkedId == R.id.radioButtonSubmersion){
                    radioButtonSubmersion.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                    radioButtonErosion.setTextColor(getResources().getColor(R.color.grey));
                    scrollViewErosion.setVisibility(GONE);
                    scrollViewSubmersion.setVisibility(VISIBLE);
                }
            }
        });
        if (((LocationMainActivity)getActivity()).getErosionPhotoCaptureBool()== 0){
            Button distanceButton = root.findViewById(R.id.erosionPhotoCaptureButton);
            distanceButton.setVisibility(GONE);
        }
        return root;
    }

}
