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

public class BooksViewModel extends AndroidViewModel {
    BooksRepository repository;
    private final MutableLiveData<List<Book>> booksLiveData;
    //private static int nextId = 1;
    RequestQueue queue;
    private boolean api_loaded;
    private MutableLiveData<Book> bookUpdated;
    private boolean updateOrNot;

    public BooksViewModel(@NonNull Application application) {
        super(application);
        repository = new BooksRepository(application);
        booksLiveData = new MutableLiveData<>();
        bookUpdated = new MutableLiveData<>();
        api_loaded = false;
        updateOrNot = false;
        queue = Volley.newRequestQueue(getApplication());
        loadData();
    }

    /*public static int getNextId() {
        return nextId;
    }*/

    /*
        loadData : proc :
            Load the list of books from the server using API
        Parameter(s) :
            context : Context : The context of the application
        Return :
            void
    */
    public void loadData() {
        repository.loadAllBooks(booksLiveData);
    }


    /*
        addBookToList : proc :
            Add a book to the list of books
        Parameter(s) :
            newBook : Book : The book to add
        Return :
            void
    */
    public void addBookToList(Book newBook) {
        List<Book> books = booksLiveData.getValue();
        books.add(newBook);
        booksLiveData.setValue(books);
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
        return booksLiveData;
    }


    /*
    deleteBookLocally : procedure :
        Removes a book from the list of books (local) based on the book ID
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
                    this.bookUpdated.setValue(current_book);
                    break;
                }
            }
        }

        // Update the LiveData with the modified books list
        this.booksLiveData.setValue(books);
    }

    public void deleteBooksOfAuthor(Integer authorIdDeleted) {
        List<Book> books = booksLiveData.getValue();
        if( books != null ){
            for(int i = 0; i < books.size(); i++){
                if( books.get(i).getAuthorId() == authorIdDeleted ){
                    books.remove(i);
                    break;
                }
            }
        }
        booksLiveData.setValue(books);
    }
}
