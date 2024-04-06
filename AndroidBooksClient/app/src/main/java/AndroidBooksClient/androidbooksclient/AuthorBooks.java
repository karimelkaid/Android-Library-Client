package AndroidBooksClient.androidbooksclient;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import AndroidBooksClient.androidbooksclient.AndroidBooksClientRepository;
import AndroidBooksClient.androidbooksclient.Model.Book;

public class AuthorBooks extends AndroidViewModel {
    AndroidBooksClientRepository repository;
    MutableLiveData<List<Book>> booksOfAuthor;
    public AuthorBooks(@NonNull Application application) {
        super(application);
        this.repository = new AndroidBooksClientRepository(application);
        this.booksOfAuthor = new MutableLiveData<>();
        load_books_of_author();
    }

    private void load_books_of_author() {
        this.repository.load_books_of_author(this.booksOfAuthor);
    }

    public void setBooksOfAuthor(List<Book> books) {
        this.booksOfAuthor.setValue(books);
    }
    public MutableLiveData<List<Book>> getBooksOfAuthor() {
        return this.booksOfAuthor;
    }
}
