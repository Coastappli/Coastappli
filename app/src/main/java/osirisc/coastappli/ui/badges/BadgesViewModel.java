package osirisc.coastappli.ui.badges;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * This activity manages the data related to the bagdes user interface.
 * At the moment, it only sets ans stores the default text that appears when the badges interface is open.
 */

public class  BadgesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BadgesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Les badges ne sont pas encore implémentés");
    }

    public LiveData<String> getText() {
        return mText;
    }
}