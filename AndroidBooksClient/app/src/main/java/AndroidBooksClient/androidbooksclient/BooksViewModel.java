package AndroidBooksClient.androidbooksclient;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
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
        // Simulate loading data from a server
        List<Book> books = new ArrayList<>();
        books.add(new Book(nextId, "War and Peace", "Leo Tolstoy", "Historical Novel"));
        nextId++;
        books.add(new Book(nextId, "It", "Stephen King", "Horror"));
        nextId++;
        // Ajoutez plus de livres si nécessaire
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
}
