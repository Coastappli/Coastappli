package osirisc.coastappli.ui.badges;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import osirisc.coastappli.R;

/**
 * This activity allows the implementation of badges loyalty system
 * It launches the bagdes fragment when clicked in the navigation controller.
 */

public class BadgesFragment extends Fragment {

    // BadgesViewModel extends the ViewModel class, it stores and manages the data related to the badges interface
    private BadgesViewModel badgesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        badgesViewModel =
                ViewModelProviders.of(this).get(BadgesViewModel.class);
        // We inflate the layout
        View root = inflater.inflate(R.layout.fragment_badges, container, false);
        final TextView textView = root.findViewById(R.id.text_badges);
        badgesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}