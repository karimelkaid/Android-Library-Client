package AndroidBooksClient.androidbooksclient.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import AndroidBooksClient.androidbooksclient.Model.Author;

public class SharedViewModel extends AndroidViewModel {
    private MutableLiveData<Author> selectedAuthor;
    public SharedViewModel(@NonNull Application application) {
        super(application);
        selectedAuthor = new MutableLiveData<>();
    }

    public MutableLiveData<Author> getSelectedAuthor() {
        return selectedAuthor;
    }

    public void setSelectedAuthor(Author author) {
        selectedAuthor.setValue(author);
    }
}
