package osirisc.coastappli.ui.badges;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BadgesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BadgesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Les badges ne sont pas encore implémentés");
    }

    public LiveData<String> getText() {
        return mText;
    }
}