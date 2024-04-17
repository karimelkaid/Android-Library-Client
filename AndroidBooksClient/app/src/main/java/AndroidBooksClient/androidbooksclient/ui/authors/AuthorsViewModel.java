package AndroidBooksClient.androidbooksclient.ui.authors;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.Author;
import AndroidBooksClient.androidbooksclient.Model.Book;
import AndroidBooksClient.androidbooksclient.Repository.AuthorsRepository;
import AndroidBooksClient.androidbooksclient.Utils;

public class AuthorsViewModel extends AndroidViewModel {
    AuthorsRepository _authorsRepository;
    MutableLiveData<List<Author>> _authorsLiveData;
    RequestQueue _queue;
    private MutableLiveData<Author> _authorEdited;

    public AuthorsViewModel(@NonNull Application application) {
        super(application);
        this._authorsRepository = new AuthorsRepository(application);
        this._authorsLiveData = new MutableLiveData<>(new ArrayList<>());
        this._authorEdited = new MutableLiveData<>();
        _queue = Volley.newRequestQueue(getApplication());
        load_authors_from_api();
    }

    /*
        load_authors_from_api : proc :
            Calls a method in the repository to send a request to get the authors from the API
        Parameter(s) :
            None
        Return :
            void
    */
    public void load_authors_from_api() {
        _authorsRepository.load_authors_from_api(_authorsLiveData);
    }

    /*
        addAuthorToList : proc :
            Adds an author to the list of authors
        Parameter(s) :
            Author newAuthor : The author to be added to the list
        Return :
            void
    */
    public void addAuthorToList(Author newAuthor) {
        List<Author> authors = this._authorsLiveData.getValue();
        authors.add(newAuthor);
        Utils.sortAuhtorsByLastName(authors);
        _authorsLiveData.setValue(authors);
    }
    
    // Getter(s)
    public MutableLiveData<List<Author>> get_authors_live_data() {
        return _authorsLiveData;
    }

}
