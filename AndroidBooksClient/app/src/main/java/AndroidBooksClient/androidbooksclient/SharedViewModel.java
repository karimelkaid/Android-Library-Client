package AndroidBooksClient.androidbooksclient;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import AndroidBooksClient.androidbooksclient.Model.Author;
import AndroidBooksClient.androidbooksclient.Model.Book;

public class SharedViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> _selectedAuthor;

    private MutableLiveData<Integer> _selectedBookId;
    private MutableLiveData<Book> _selectedBook;

    private MutableLiveData<Book> _bookToAddMutableLiveData;
    private MutableLiveData<Author> _authorAddedMutableLiveData;

    private boolean _loading;

    private MutableLiveData<Boolean> _reloadAuthorsLiveData;
    private MutableLiveData<Boolean> _reloadBooksLiveData;

    private String _bookTitleToBeDeleted;
    private String _authorLastNameToBeDeleted;

    private Boolean _previousFragmentIsBookInformation; // To display a Toast that a book has been deleted only if the previous fragment is the details of a book (because books can also be deleted by deleting an author)

    // To find out which fragment to navigate to, the BookInformation fragment can be accessed via 2 paths
    private Boolean _previousFragmentIsAuthorInformation;
    private Boolean _previousFragmentIsBooks;

    public SharedViewModel(@NonNull Application application) {
        super(application);
        _selectedAuthor = new MutableLiveData<>();
        _selectedBookId = new MutableLiveData<>();
        _selectedBook = new MutableLiveData<>();
        _bookToAddMutableLiveData = new MutableLiveData<>();
        _authorAddedMutableLiveData = new MutableLiveData<>();
        _loading = false;
        _reloadAuthorsLiveData = new MutableLiveData<>(false);
        _reloadBooksLiveData = new MutableLiveData<>(false);
        _bookTitleToBeDeleted = "";
        _authorLastNameToBeDeleted = "";
        _previousFragmentIsBookInformation = false;
        _previousFragmentIsAuthorInformation = false;
        _previousFragmentIsBooks = false;
    }


    // Getters and setters
    public MutableLiveData<Integer> get_selectedAuthor() {
        return _selectedAuthor;
    }

    public void set_selectedAuthor(int authorId) {
        _selectedAuthor.setValue(authorId);
    }

    public MutableLiveData<Integer> get_selectedBookId() {
        return _selectedBookId;
    }

    public void set_selectedBookId(Integer book) {
        _selectedBookId.setValue(book);
    }

    public void set_selectedBook(Book book) {
        _selectedBook.setValue(book);
    }

    public MutableLiveData<Book> get_bookToAddMutableLiveData() {
        return _bookToAddMutableLiveData;
    }

    public void setBookToAddMutable(Book bookToAdd) {
        _bookToAddMutableLiveData.setValue(bookToAdd);
    }
    public void set_authorAddedMutableLiveData(Author authorAdded) {
        _authorAddedMutableLiveData.setValue(authorAdded);
    }
    public MutableLiveData<Author> get_authorAddedMutableLiveData() {
        return _authorAddedMutableLiveData;
    }

    public void set_loading(boolean loading) {
        _loading = loading;
    }
    public boolean get_loading() {
        return _loading;
    }

    public void setReloadAuthors(boolean b) {
        _reloadAuthorsLiveData.setValue(b);
    }
    public MutableLiveData<Boolean> get_reloadAuthorsLiveData() {
        return _reloadAuthorsLiveData;
    }

    public void setReloadBooks(boolean b) {
        _reloadBooksLiveData.setValue(b);
    }
    public MutableLiveData<Boolean> get_reloadBooksLiveData() {
        return _reloadBooksLiveData;
    }

    public void set_bookTitleToBeDeleted(String title) {
        _bookTitleToBeDeleted = title;
    }

    public String get_bookTitleToBeDeleted() {
        return _bookTitleToBeDeleted;
    }

    public void set_authorLastNameToBeDeleted(String lastName) {
        _authorLastNameToBeDeleted = lastName;
    }
    public String getAuthorLastNameToBeDeleted() {
        return _authorLastNameToBeDeleted;
    }

    public void setPreviousFragmentIsBookInformation(Boolean b) {
        _previousFragmentIsBookInformation = b;
    }
    public Boolean getPreviousFragmentIsBookInformation() {
        return _previousFragmentIsBookInformation;
    }

    public void setPreviousFragmentIsAuthorInformation(Boolean b) {
        _previousFragmentIsAuthorInformation = b;
    }
    public Boolean getPreviousFragmentIsAuthorInformation() {
        return _previousFragmentIsAuthorInformation;
    }

    public void setPreviousFragmentIsBooks(Boolean b) {
        _previousFragmentIsBooks = b;
    }
    public Boolean getPreviousFragmentIsBooks() {
        return _previousFragmentIsBooks;
    }
}
