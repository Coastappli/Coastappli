package osirisc.coastappli.ui.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * This activity manages the data related to the history user interface.
 * At the moment, it only sets ans stores the default text that appears when the history interface is open.
 */

public class HistoryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("L'historique n'est pas encore implémenté");
    }

    public LiveData<String> getText() {
        return mText;
    }
}