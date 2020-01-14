package osirisc.coastappli.ui.manage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * This activity manages the data related to the app settings interface.
 * At the moment, it only sets ans stores the default text that appears when the settings interface is open.
 */

public class ManageViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ManageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Les paramètres ne sont pas encore implémentés");
    }

    public LiveData<String> getText() {
        return mText;
    }
}