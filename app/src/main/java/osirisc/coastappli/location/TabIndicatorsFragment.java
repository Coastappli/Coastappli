package osirisc.coastappli.location;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import osirisc.coastappli.LocationMainActivity;
import osirisc.coastappli.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * This tab is one of the tabs of the LocationMainActivity
 * It's the tab where all the different indicators for each marker are shown
 */
public class TabIndicatorsFragment extends Fragment {

    // We have two objects scrollView which switch from being VISIBLE to GONE when the buttons Erosion or Submersion are clicked
    private ScrollView scrollViewErosion;
    private ScrollView scrollViewSubmersion;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        // We first inflate the layout
        final View root = inflater.inflate(R.layout.tab_indicators, container, false);

        // We then adapt the erosion and submersion buttons to fill the screen
        scrollViewErosion = root.findViewById(R.id.scrollViewErosion);
        scrollViewSubmersion = root.findViewById(R.id.scrollViewSubmersion);
        final RadioButton radioButtonErosion = root.findViewById(R.id.radioButtonErosion);
        final RadioButton radioButtonSubmersion = root.findViewById(R.id.radioButtonSubmersion);
        // We set each button's width to half the size of the screen
        radioButtonErosion.setWidth(((((LocationMainActivity)getActivity()).getWidth())/2));
        radioButtonSubmersion.setWidth(((((LocationMainActivity)getActivity()).getWidth())/2));

        // When launching the tab, the erosion scrollView is the one visible
        // The Erosion button is therefore the one activated
        // We set its text color to PrimaryLight, which is the color for the selected button
        // We set the Submersion button's text color to grey, color for the unselected button
        radioButtonErosion.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
        radioButtonSubmersion.setTextColor(getResources().getColor(R.color.grey));

        // We then add an OnCheckedChangeListener which controls which button from the radioGroup is clicked on
        RadioGroup radioGroupErosionSubmersion = root.findViewById(R.id.radioGroupErosionSubmersion);
        radioGroupErosionSubmersion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonErosion){
                    // If the Erosion button is selected its text color is ColorPrimaryLight and the Submersion Button's text color is Grey
                    // The Erosion scrollView is set to VISIBLE and the Submersion scrollView is set to GONE
                    radioButtonErosion.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                    radioButtonSubmersion.setTextColor(getResources().getColor(R.color.grey));
                    scrollViewErosion.setVisibility(VISIBLE);
                    scrollViewSubmersion.setVisibility(GONE);
                }
                if (checkedId == R.id.radioButtonSubmersion){
                    // If the Submersion button is selected its text color is ColorPrimaryLight and the Erosion Button's text color is Grey
                    // The Submersion scrollView is set to VISIBLE and the Erosion scrollView is set to GONE
                    radioButtonSubmersion.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                    radioButtonErosion.setTextColor(getResources().getColor(R.color.grey));
                    scrollViewErosion.setVisibility(GONE);
                    scrollViewSubmersion.setVisibility(VISIBLE);
                }
            }
        });

        // We then check which indicators are used for this marker
        // If the ErosionPhotoCaptureBool is set to 1, it means to indicator is used
        // If it's set to 0, it's not used so we set the layout containing the button and icons attached to it to GONE
        if (((LocationMainActivity)getActivity()).getErosionPhotoCaptureBool()== 0){
            LinearLayout erosionPhotoCaptureLayout = root.findViewById(R.id.erosionPhotoCaptureLayout);
            erosionPhotoCaptureLayout.setVisibility(GONE);
        }
        return root;
    }

}
