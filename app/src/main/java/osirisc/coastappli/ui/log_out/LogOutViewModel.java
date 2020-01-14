package osirisc.coastappli.ui.log_out;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * This activity manages the data related to the log out interface.
 * At the moment, it only sets ans stores the default text that appears when the user clicks "log out".
 */

public class LogOutViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LogOutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("La page de déconnexion n'est pas encore implémentée");
    }

    public LiveData<String> getText() {
        return mText;
    }
}