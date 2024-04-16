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
    private MutableLiveData<Integer> bookDeletedIdMutableLiveData;  // When I delete a book, I set its attribute to null, but I need its id to delete it from the other ViewModels too
    private MutableLiveData<Author> authorAddedMutableLiveData;
    private MutableLiveData<Integer> authorIdDeletedLiveData;
    private boolean loading;
    private MutableLiveData<Boolean> reloadAuthorsLiveData;
    private MutableLiveData<Boolean> reloadBooksLiveData;
    private MutableLiveData<Boolean> authorDeletedLiveData;

    public SharedViewModel(@NonNull Application application) {
        super(application);
        selectedAuthor = new MutableLiveData<>();
        selectedBookId = new MutableLiveData<>();
        selectedBook = new MutableLiveData<>();
        bookToAddMutableLiveData = new MutableLiveData<>();
        bookDeletedIdMutableLiveData = new MutableLiveData<>();
        authorAddedMutableLiveData = new MutableLiveData<>();
        authorIdDeletedLiveData = new MutableLiveData<>();
        loading = false;
        reloadAuthorsLiveData = new MutableLiveData<>(false);
        reloadBooksLiveData = new MutableLiveData<>(false);
        authorDeletedLiveData = new MutableLiveData<>(false);
    }

    public MutableLiveData<Boolean> getAuthorDeleted() {
        return authorDeletedLiveData;
    }
    public void setAuthorDeleted(boolean authorDeleted) {
        authorDeletedLiveData.setValue(authorDeleted);
    }


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

    public MutableLiveData<Book> getSelectedBook() {
        return selectedBook;
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

    public MutableLiveData<Integer> getBookDeletedIdMutableLiveData() {
        return bookDeletedIdMutableLiveData;
    }

    public void setBookDeletedIdMutableLiveData(int bookDeletedID) {
        bookDeletedIdMutableLiveData.setValue(bookDeletedID);
    }

    public void setBookDeletedIdMutable( int deletedId ) {
        bookDeletedIdMutableLiveData.setValue(deletedId);
    }

    public void setAuthorAddedMutableLiveData(Author authorAdded) {
        this.authorAddedMutableLiveData.setValue(authorAdded);
    }
    public MutableLiveData<Author> getAuthorAddedMutableLiveData() {
        return this.authorAddedMutableLiveData;
    }

    public void setAuthorIdDeleted(Integer authorIdDeleted) {
        authorIdDeletedLiveData.setValue(authorIdDeleted);
    }
    public MutableLiveData<Integer> getAuthorIdDeletedLiveData() {
        return authorIdDeletedLiveData;
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
