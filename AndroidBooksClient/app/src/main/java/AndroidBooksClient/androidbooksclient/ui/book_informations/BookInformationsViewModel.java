package AndroidBooksClient.androidbooksclient.ui.book_informations;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import AndroidBooksClient.androidbooksclient.Repository.BooksRepository;
import AndroidBooksClient.androidbooksclient.Model.Book;

public class BookInformationsViewModel extends AndroidViewModel {
    private BooksRepository _repository;
    private MutableLiveData<Book> _bookMutableLiveData;
    private MutableLiveData<Boolean> _bookDeletedLiveData;
    public BookInformationsViewModel(@NonNull Application application) {
        super(application);
        _repository = new BooksRepository(application);
        _bookMutableLiveData = new MutableLiveData<>();
        _bookDeletedLiveData = new MutableLiveData<>(false);
    }

    /*
        loadBookToDisplay : proc :
            Calls a method in the repository to send a request to get the book to display from the API
        Parameter(s) :
            int bookId : The id of the book to be fetched
        Return :
            void
    */
    public void loadBookToDisplay(int bookId){
        _repository.getBook(bookId, _bookMutableLiveData);
    }

    /*
        deleteBook : proc :
            Calls a method in the repository to send a request to delete a book from the API
        Parameter(s) :
            int bookId : The id of the book to be deleted
        Return :
            void
    */
    public void deleteBook(int bookId){
        _repository.deleteBook(bookId, _bookDeletedLiveData);
    }

    // Getter(s)
    public MutableLiveData<Book> get_bookMutableLiveData(){
        return _bookMutableLiveData;
    }

    public MutableLiveData<Boolean> get_bookDeletedLiveData(){
        return _bookDeletedLiveData;
    }
}
