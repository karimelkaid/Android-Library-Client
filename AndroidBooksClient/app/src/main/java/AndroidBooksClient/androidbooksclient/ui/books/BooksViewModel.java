package AndroidBooksClient.androidbooksclient.ui.books;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
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
    private static final String adr_ip_pc_on_the_network = "192.168.1.9";     // The IP address of the PC on the network (the phone and the PC must be on the same network), it can change so use this command to get the IP address on the network : ip addr show
    private boolean api_loaded;
    private MutableLiveData<Book> bookUpdated;
    private boolean updateOrNot;

    public BooksViewModel(@NonNull Application application) {
        super(application);
        booksLiveData = new MutableLiveData<>(new ArrayList<>());
        bookUpdated = new MutableLiveData<>();
        api_loaded = false;
        updateOrNot = false;
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
        String url = "http://"+ this.adr_ip_pc_on_the_network +":3000/books";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        handleResponseLoadData(context, response);
                        api_loaded = true;
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
        handleResponseLoadData : proc :
            Handle the response from the server
        Parameter(s) :
            context : Context : The context of the application
            response : JSONArray : The response from the server
        Return :
            void
    */
    public void handleResponseLoadData(Context context, JSONArray response){
        Log.d("BooksViewModel", "Response: " + response.toString());
        //booksLiveData.setValue(response);
        for( int i=0; i<response.length(); i++ ){
            try {
                JSONObject bookJsonObject = response.getJSONObject(i);
                // Create a new book object from the JSON object and add it to the list of books
                Book newBook = translateJsonObjectToABookObject(bookJsonObject);
                addBookToList(newBook);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        Toast.makeText(context, "Books loaded from the API !", Toast.LENGTH_SHORT).show();
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
        addBook : proc :
            Add a book to the server and locally
        Parameter(s) :
            author_id : int : The id of the author of the book to add
            book_json_object : JSONObject : The JSON object representing the book
        Return :
            void
    */
    public void addBook(int author_id, JSONObject book_json_object) {
        String url = "http://"+ this.adr_ip_pc_on_the_network +":3000/authors/"+ author_id +"/books";

        // Create a new JsonObjectRequest
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, book_json_object, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("BooksViewModel", "Response: " + response.toString());
                        // Add the book to the local list and update the LiveData
                        Book newBook = null;
                        try {
                            newBook = translateJsonObjectToABookObject(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        addBookToList(newBook);

                        if(api_loaded){
                            /*if( newBook.getPublication_year() == -1 ){
                                bookUpdated.setValue(new Book(newBook.getId(), newBook.getTitle(), newBook.getAuthorId()));
                            }
                            else{
                                bookUpdated.setValue(new Book(newBook.getId(), newBook.getTitle(), newBook.getPublication_year(), newBook.getAuthorId()));
                            }*/
                            bookUpdated.setValue(newBook);
                        }

                        Toast.makeText(getApplication(), "Book added !", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("BooksViewModel", "Error: " + error.toString());
                    }
                });

        // Add the request to the RequestQueue
        queue.add(jsonObjectRequest);
    }

    /*
        translateJsonObjectToABookObject : func :
            Translate a JSON object to a Book object
        Parameter(s) :
            book_json_object : JSONObject : The JSON object to translate
        Return :
            Book : The Book object
    */
    public Book translateJsonObjectToABookObject(JSONObject book_json_object) throws JSONException {
        Book newBook = null;
        if(book_json_object.isNull("publication_year") )
        {
            // Create a new book object from the JSON object with the constructor that does not take the publication year
            newBook = new Book(book_json_object.getInt("id"), book_json_object.getString("title"), book_json_object.getInt("authorId"));
        }
        else{
            // Create a new book object from the JSON object with the constructor that takes the publication year
            newBook = new Book(book_json_object.getInt("id"), book_json_object.getString("title"), book_json_object.getInt("publication_year"), book_json_object.getInt("authorId"));
        }
        return newBook;
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
        deleteBookFromServer(bookId);
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
                    this.bookUpdated.setValue(current_book);
                    break;
                }
            }
        }

        // Update the LiveData with the modified books list
        this.booksLiveData.setValue(books);
    }

    /*
        deleteBookFromServer : proc :
            Delete a book from the server based on the book ID (using the API)
        Parameter(s) :
            bookId : int : The id of the book to delete
        Return :
            void
    */
    public void deleteBookFromServer(int bookId) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE, "http://"+ this.adr_ip_pc_on_the_network +":3000/books/"+bookId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("BooksViewModel", "Response is: " + response);
                        // We must to verify (not doing here) the code status of the response to know if the book has been deleted or not
                        Toast.makeText(getApplication(), "Book deleted !" + response, Toast.LENGTH_SHORT).show();
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

    public MutableLiveData<Book> getBookUpdated() {
        return bookUpdated;
    }

    public void setBookAdded(Object object) {
        bookUpdated.setValue(null);
    }

    public boolean getUpdateOrNot() {
        return this.updateOrNot;
    }

    public void setUpdateOrNot(boolean b) {
        this.updateOrNot = b;
    }
}
