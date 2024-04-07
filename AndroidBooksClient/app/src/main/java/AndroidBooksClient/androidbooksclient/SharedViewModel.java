package AndroidBooksClient.androidbooksclient;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import AndroidBooksClient.androidbooksclient.Model.Book;

public class SharedViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> selectedAuthor;
    private MutableLiveData<Integer> selectedBookId;
    private MutableLiveData<Book> selectedBook;
    private MutableLiveData<Book> bookToAddMutableLiveData;

    public SharedViewModel(@NonNull Application application) {
        super(application);
        selectedAuthor = new MutableLiveData<>();
        selectedBookId = new MutableLiveData<>();
        selectedBook = new MutableLiveData<>();
        bookToAddMutableLiveData = new MutableLiveData<>();
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
}
