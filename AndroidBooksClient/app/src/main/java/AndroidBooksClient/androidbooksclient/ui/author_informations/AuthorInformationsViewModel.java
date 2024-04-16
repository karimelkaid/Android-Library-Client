package AndroidBooksClient.androidbooksclient.ui.author_informations;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import AndroidBooksClient.androidbooksclient.Repository.AuthorsRepository;
import AndroidBooksClient.androidbooksclient.Model.Author;

public class AuthorInformationsViewModel extends AndroidViewModel {
    private AuthorsRepository _repository;
    private MutableLiveData<Author> _authorLiveData;
    private MutableLiveData<Boolean> _authorDeletedLiveData;

    public AuthorInformationsViewModel(@NonNull Application application) {
        super(application);
        _repository = new AuthorsRepository(application);
        _authorLiveData = new MutableLiveData<>();
        _authorDeletedLiveData = new MutableLiveData<>(false);
    }


    /*
        loadAuthorToDisplay : proc :
            Calls a method in the repository to send a request to get the author to display from the API
        Parameter(s) :
            int authorId : The id of the author to be fetched
        Return :
            void
    */
    public void loadAuthorToDisplay(int authorId) {
        _repository.loadAuthorToDisplay(authorId, _authorLiveData);
    }

    /*
        deleteAuthor : proc :
            Calls a method in the repository to send a request to delete an author from the API
        Parameter(s) :
            int authorId : The id of the author to be deleted
        Return :
            void
    */
    public void deleteAuthor(int authorId) {
        _repository.deleteAuthor(authorId, _authorDeletedLiveData);
    }

    // Getter(s) and setter(s)
    public MutableLiveData<Boolean> getAuthorDeleted() {
        return _authorDeletedLiveData;
    }
    public void setAuthorDeleted(boolean authorDeleted) {
        _authorDeletedLiveData.setValue(authorDeleted);
    }

    public LiveData<Author> get_authorLiveData() {
        return _authorLiveData;
    }

}
