package AndroidBooksClient.androidbooksclient;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.Iterator;
import java.util.List;

public class BooksViewModel extends AndroidViewModel {
    private final MutableLiveData<JSONArray> booksLiveData;
    private static int nextId = 1;

    public BooksViewModel(@NonNull Application application) {
        super(application);
        booksLiveData = new MutableLiveData<>();
        loadData(getApplication());
    }


    public static int getNextId() {
        return nextId;
    }

    /*
        loadData : proc :
            Load the list of books from the server using API
        Parameter(s) :
            context : Context : The context of the application
        Return :
            void
    */
    private void loadData(Context context) {
        String url = "http://192.168.1.9:3000/books";

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() { // Utiliser JSONArray ici
                    @Override
                    public void onResponse(JSONArray response) {
                        Toast.makeText(context, "Response: " + response.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("BooksViewModel", "Response: " + response.toString());
                        booksLiveData.setValue(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("BooksViewModel", "Error: " + error.toString());
                    }
                }
        );

        queue.add(request);
    }

    /*
        getBooks : func :
            Get the list of books
        Parameter(s) :
            void
        Return :
            MutableLiveData<List<Book>> : The list of books
    */
    public MutableLiveData<JSONArray> getBooks() {
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
        /*List<Book> books = booksLiveData.getValue();
        books.add(newBook);
        booksLiveData.setValue(books);
        nextId++;*/
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
        /*
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
        this.booksLiveData.setValue(books);*/
    }
}
