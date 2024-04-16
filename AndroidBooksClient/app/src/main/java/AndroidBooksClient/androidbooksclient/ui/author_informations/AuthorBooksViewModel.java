package AndroidBooksClient.androidbooksclient.ui.author_informations;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.Book;
import AndroidBooksClient.androidbooksclient.Repository.AuthorsRepository;

public class AuthorBooksViewModel extends AndroidViewModel {
    AuthorsRepository _authorsRepository;
    private MutableLiveData<List<Book>> _booksOfAuthorLiveData;
    public AuthorBooksViewModel(@NonNull Application application) {
        super(application);
        this._authorsRepository = new AuthorsRepository(application);
        this._booksOfAuthorLiveData = new MutableLiveData<>();
    }

    /*
        loadBooksOfAuthor : proc :
            Calls a method in the repository to send a request to get the books of an author from the API
        Parameter(s) :
            int authorId : The id of the author whose books will be fetched
        Return :
            void
    */
    public void loadBooksOfAuthor(int authorId) {
        _authorsRepository.loadBooksOfAuthor(authorId, _booksOfAuthorLiveData);
    }

    // Getter(s)
    public MutableLiveData<List<Book>> get_booksOfAuthorLiveData() {
        return _booksOfAuthorLiveData;
    }
}
