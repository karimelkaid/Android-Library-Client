package AndroidBooksClient.androidbooksclient.ui.author_informations;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import AndroidBooksClient.androidbooksclient.AndroidBooksClientRepository;
import AndroidBooksClient.androidbooksclient.Model.Author;

public class AuthorInformationsViewModel extends AndroidViewModel {
    private AndroidBooksClientRepository repository;
    private MutableLiveData<Author> authorLiveData = new MutableLiveData<>();

    public AuthorInformationsViewModel(@NonNull Application application) {
        super(application);
        repository = new AndroidBooksClientRepository(application);
    }

    public LiveData<Author> getAuthorLiveData() {
        return authorLiveData;
    }

    public void loadAuthorToDisplay(int authorId) {
        repository.loadAuthorToDisplay(authorId, authorLiveData);
    }
}
