package osirisc.coastappli.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * This activity manages the data related to the profile user interface.
 * At the moment, it only sets ans stores the default text that appears when the profile interface is open.
 */

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProfileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Les profils ne sont pas encore implémentés");
    }

    public LiveData<String> getText() {
        return mText;
    }
}