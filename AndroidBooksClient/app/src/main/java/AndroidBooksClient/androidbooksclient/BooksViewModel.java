package AndroidBooksClient.androidbooksclient;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BooksViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Book>> booksLiveData;
    private static int nextId = 1;

    public BooksViewModel(@NonNull Application application) {
        super(application);
        booksLiveData = new MutableLiveData<>();
        loadData();
    }


    public static int getNextId() {
        return nextId;
    }

    /*
        loadData : proc :
            Load the data from the server
        Parameter(s) :
            void
        Return :
            void
    */
    private void loadData() {
        //
        List<Book> books = new ArrayList<>();



        // Ajoutez plus de livres si nécessaire
        booksLiveData.setValue(books);
    }

    public void fetchBooksAndSave(){
        
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
        addBook : proc :
            Add a book to the list of books
        Parameter(s) :
            newBook : Book : The book to add
        Return :
            void
    */
    public void addBook(Book newBook) {
        List<Book> books = booksLiveData.getValue();
        books.add(newBook);
        booksLiveData.setValue(books);
        nextId++;
    }

    /*
    deleteBook : procedure :
        Removes a book from the list of books based on the given book ID.
    Parameter(s) :
        bookId : int : The ID of the book to remove.
    Return :
        void
*/
    public void deleteBook(int bookId){
        // Fetch the current list of books
        List<Book> books = this.getBooks().getValue();

        if( books != null ){
            // Use an iterator to safely modify the list while iterating
            Iterator<Book> iterator = books.iterator();

            while(iterator.hasNext()){
                Book current_book = iterator.next();

                if( current_book.getId() == bookId){
                    iterator.remove();  // Since the book to delete has been found and removed, break out of the loop
                    break;
                }
            }
        }

        // Update the LiveData with the modified books list
        this.booksLiveData.setValue(books);
    }
}
