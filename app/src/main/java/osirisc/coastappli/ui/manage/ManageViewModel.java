package osirisc.coastappli.ui.manage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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