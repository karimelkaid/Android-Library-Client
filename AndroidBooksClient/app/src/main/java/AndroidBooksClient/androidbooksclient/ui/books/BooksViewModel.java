package AndroidBooksClient.androidbooksclient.ui.books;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.Iterator;
import java.util.List;

import AndroidBooksClient.androidbooksclient.Repository.BooksRepository;
import AndroidBooksClient.androidbooksclient.Model.Book;
import AndroidBooksClient.androidbooksclient.Utils;

public class BooksViewModel extends AndroidViewModel {
    BooksRepository _repository;
    private final MutableLiveData<List<Book>> _booksLiveData;
    RequestQueue _queue;
    private MutableLiveData<Book> _bookUpdated;

    public BooksViewModel(@NonNull Application application) {
        super(application);
        _repository = new BooksRepository(application);
        _booksLiveData = new MutableLiveData<>();
        _bookUpdated = new MutableLiveData<>();
        _queue = Volley.newRequestQueue(getApplication());
        loadData();
    }

    /*
        loadData : proc :
            Load all the books from the API and update the LiveData to notify the observers when the data is ready
        Parameter(s) :
            void
        Return :
            void
    */
    public void loadData() {
        _repository.loadAllBooks(_booksLiveData);
    }


    /*
        addBookToList : proc :
            Adds a book to the list of books booksLiveData
        Parameter(s) :
            Book newBook : The book to be added to the list
        Return :
            void
    */
    public void addBookToList(Book newBook) {
        List<Book> books = _booksLiveData.getValue();
        books.add(newBook);
        Utils.sortBookByTitle(books);
        _booksLiveData.setValue(books);
    }

    /*
        getBooks : func :
            Get the list of books
        Parameter(s) :
            void
        Return :
            MutableLiveData<List<Book>> : The list of books
    */
    public MutableLiveData<List<Book>> getBooks() {
        return _booksLiveData;
    }


    /*
    deleteBookLocally : procedure :
        Removes a book from the list of books booksLiveData (local) based on the book ID
    Parameter(s) :
        bookId : int : The ID of the book to remove.
    Return :
        void
    */
    public void deleteBookLocally(int bookId){

        // Fetch the current list of books
        List<Book> books = this.getBooks().getValue();

        if( books != null ){
            // Use an iterator to safely modify the list while iterating
            Iterator<Book> iterator = books.iterator();

            while(iterator.hasNext()){
                Book current_book = iterator.next();

                if( current_book.getId() == bookId){
                    iterator.remove();  // Since the book to delete has been found and removed, break out of the loop
                    this._bookUpdated.setValue(current_book);
                    break;
                }
            }
        }

        // Update the LiveData with the modified books list
        this._booksLiveData.setValue(books);
    }

}
