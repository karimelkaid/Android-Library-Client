package AndroidBooksClient.androidbooksclient;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.net.HttpCookie;

import AndroidBooksClient.androidbooksclient.Model.Author;
import AndroidBooksClient.androidbooksclient.Model.Book;

public class SharedViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> selectedAuthor;

    private MutableLiveData<Integer> selectedBookId;
    private MutableLiveData<Book> selectedBook;

    private MutableLiveData<Book> bookToAddMutableLiveData;
    private MutableLiveData<Author> authorAddedMutableLiveData;

    private boolean loading;

    private MutableLiveData<Boolean> reloadAuthorsLiveData;
    private MutableLiveData<Boolean> reloadBooksLiveData;

    public SharedViewModel(@NonNull Application application) {
        super(application);
        selectedAuthor = new MutableLiveData<>();
        selectedBookId = new MutableLiveData<>();
        selectedBook = new MutableLiveData<>();
        bookToAddMutableLiveData = new MutableLiveData<>();
        authorAddedMutableLiveData = new MutableLiveData<>();
        loading = false;
        reloadAuthorsLiveData = new MutableLiveData<>(false);
        reloadBooksLiveData = new MutableLiveData<>(false);
    }


    // Getters and setters
    public MutableLiveData<Integer> getSelectedAuthor() {
        return selectedAuthor;
    }

    public void setSelectedAuthor(int authorId) {
        selectedAuthor.setValue(authorId);
    }

    public MutableLiveData<Integer> getSelectedBookId() {
        return selectedBookId;
    }

    public void setSelectedBookId(Integer book) {
        selectedBookId.setValue(book);
    }

    public void setSelectedBook(Book book) {
        selectedBook.setValue(book);
    }

    public MutableLiveData<Book> getBookToAddMutableLiveData() {
        return bookToAddMutableLiveData;
    }

    public void setBookToAddMutable(Book bookToAdd) {
        bookToAddMutableLiveData.setValue(bookToAdd);
    }
    public void setAuthorAddedMutableLiveData(Author authorAdded) {
        this.authorAddedMutableLiveData.setValue(authorAdded);
    }
    public MutableLiveData<Author> getAuthorAddedMutableLiveData() {
        return this.authorAddedMutableLiveData;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }
    public boolean getLoading() {
        return loading;
    }

    public void setReloadAuthors(boolean b) {
        this.reloadAuthorsLiveData.setValue(b);
    }
    public MutableLiveData<Boolean> getReloadAuthorsLiveData() {
        return reloadAuthorsLiveData;
    }

    public void setReloadBooks(boolean b) {
        this.reloadBooksLiveData.setValue(b);
    }
    public MutableLiveData<Boolean> getReloadBooksLiveData() {
        return reloadBooksLiveData;
    }
}
