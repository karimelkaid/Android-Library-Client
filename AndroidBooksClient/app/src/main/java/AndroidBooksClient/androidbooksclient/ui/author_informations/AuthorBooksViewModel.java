package AndroidBooksClient.androidbooksclient.ui.author_informations;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.Book;
import AndroidBooksClient.androidbooksclient.Repository.AuthorsRepository;

public class AuthorBooksViewModel extends AndroidViewModel {
    AuthorsRepository authorsRepository;
    private MutableLiveData<List<Book>> booksOfAuthorLiveData;
    public AuthorBooksViewModel(@NonNull Application application) {
        super(application);
        this.authorsRepository = new AuthorsRepository(application);
        this.booksOfAuthorLiveData = new MutableLiveData<>();
    }

    public void loadBooksOfAuthor(int authorId) {
        authorsRepository.loadBooksOfAuthor(authorId, booksOfAuthorLiveData);
    }
    public MutableLiveData<List<Book>> getBooksOfAuthorLiveData() {
        return booksOfAuthorLiveData;
    }
    public void setBooksOfAuthorLiveData(MutableLiveData<List<Book>> booksOfAuthorLiveData) {
        this.booksOfAuthorLiveData = booksOfAuthorLiveData;
    }
}
