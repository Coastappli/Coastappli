package osirisc.coastappli.method;

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
import osirisc.coastappli.Database.MethodErosionDistance;
import osirisc.coastappli.MethodMainActivity;
import osirisc.coastappli.R;

public class TabMethodFragment extends Fragment {
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_method, container, false);
        DatabaseAssistant databaseAssistant = new DatabaseAssistant(getActivity());
        MethodErosionDistance method = databaseAssistant.findMethodErosionDistance(((MethodMainActivity)getActivity()).getMarkerLatitude(), ((MethodMainActivity)getActivity()).getMarkerLongitude());
        if (method!=null) {
            TextView textViewMethodFillClue1 = root.findViewById(R.id.textViewMethodFillClue1);
            TextView textViewMethodFillClue2 = root.findViewById(R.id.textViewMethodFillClue2);
            TextView textViewMethodFillClue3 = root.findViewById(R.id.textViewMethodFillClue3);
            ImageView imageViewMethodPhoto = root.findViewById(R.id.imageViewMethodPhoto);
            ImageView imageViewMethodPhotoPerson = root.findViewById(R.id.imageViewMethodPhotoPerson);
            textViewMethodFillClue1.setText(method.getClue1());
            textViewMethodFillClue2.setText(method.getClue2());
            textViewMethodFillClue3.setText(method.getClue3());
            imageViewMethodPhoto.setImageBitmap(BitmapFactory.decodeByteArray(method.getPhoto(), 0,method.getPhoto().length));
            imageViewMethodPhotoPerson.setImageBitmap(BitmapFactory.decodeByteArray(method.getPhotoPerson(), 0,method.getPhotoPerson().length));

        }
        return root;
    }

}
