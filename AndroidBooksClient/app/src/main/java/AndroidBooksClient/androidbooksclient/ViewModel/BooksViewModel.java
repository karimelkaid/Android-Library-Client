package AndroidBooksClient.androidbooksclient.ViewModel;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.Book;

public class BooksViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Book>> booksLiveData;
    //private static int nextId = 1;
    RequestQueue queue;

    public BooksViewModel(@NonNull Application application) {
        super(application);
        booksLiveData = new MutableLiveData<>(new ArrayList<>());
        queue = Volley.newRequestQueue(getApplication());
        loadData(getApplication());
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
    private void loadData(Context context) {
        String url = "http://192.168.1.9:3000/books";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() { // Utiliser JSONArray ici
                    @Override
                    public void onResponse(JSONArray response) {
                        handleResponse(context, response);
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
        handleResponse : proc :
            Handle the response from the server
        Parameter(s) :
            context : Context : The context of the application
            response : JSONArray : The response from the server
        Return :
            void
    */
    public void handleResponse(Context context, JSONArray response){
        Toast.makeText(context, "Response: " + response.toString(), Toast.LENGTH_SHORT).show();
        Log.d("BooksViewModel", "Response: " + response.toString());
        //booksLiveData.setValue(response);
        for( int i=0; i<response.length(); i++ ){
            try {
                JSONObject bookJsonObject = response.getJSONObject(i);
                // Create a new book object from the JSON object and add it to the list of books
                Book newBook = new Book(bookJsonObject.getInt("id"), bookJsonObject.getString("title"), bookJsonObject.getInt("publication_year"), bookJsonObject.getInt("authorId"));
                addBook(newBook);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
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
    }

    /*
        deleteBook : proc :
            Delete a book in the server and locally (locally to update the UI)
        Parameter(s) :
            bookId : int : The id of the book to delete
        Return :
            void
    */
    public void deleteBook(int bookId){
        deleteBookInServer(bookId);
        deleteBookLocally(bookId);  // To update the list of books in the UI
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
                    break;
                }
            }
        }

        // Update the LiveData with the modified books list
        this.booksLiveData.setValue(books);
    }

    /*
        deleteBookInServer : proc :
            Delete a book from the server
        Parameter(s) :
            bookId : int : The id of the book to delete
        Return :
            void
    */
    public void deleteBookInServer(int bookId) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE, "http://192.168.1.9:3000/books/"+bookId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplication(), "Response is: " + response, Toast.LENGTH_SHORT).show();
                        Log.d("BooksViewModel", "Response is: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("BooksViewModel", "Error: " + error.toString());
                    }
                }
        );
        queue.add(stringRequest);
    }
}
