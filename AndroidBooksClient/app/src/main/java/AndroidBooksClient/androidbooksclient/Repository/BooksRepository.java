package AndroidBooksClient.androidbooksclient.Repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

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
import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.Author;
import AndroidBooksClient.androidbooksclient.Model.Book;
import AndroidBooksClient.androidbooksclient.Model.Tag;
import AndroidBooksClient.androidbooksclient.Utils;

public class BooksRepository {
    RequestQueue queue;
    Application application;
    private static final String adr_ip_pc_on_the_network = "localhost";     // The IP address of the PC on the network (the phone and the PC must be on the same network), it can change so use this command to get the IP address on the network : ip addr show
    String urlApi = "http://"+ this.adr_ip_pc_on_the_network +":3000";

    public BooksRepository(Application application) {
        this.application = application;
        queue = Volley.newRequestQueue(application);
    }

    /*
    loadAllBooks : func :
        Load all the books from the API and update the LiveData with the booksLiveData to notify the BooksFragment that it can update the UI
    Parameter(s) :
        booksLiveData : MutableLiveData<List<Book>> : The LiveData to update with the books to notify the BooksFragment that it can update the UI
    Return :
        void
     */
    public void loadAllBooks(MutableLiveData<List<Book>> booksLiveData){
        String url = urlApi+"/books";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        handleResponseLoadData(response, booksLiveData);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(application, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("Books repository", "Error: " + error.toString());
                    }
                }
        );

        queue.add(request);
    }

    /*
        handleResponseLoadData : func :
            Handle the response of the load data request
        Parameter(s) :
            response : JSONArray : The response of the request
            booksLiveData : MutableLiveData<List<Book>> : The LiveData to update with the books
        Return :
            void
    */
    public void handleResponseLoadData(JSONArray response, MutableLiveData<List<Book>> booksLiveData){
        Log.d("BooksViewModel", "Response: " + response.toString());
        List<Book> books = new ArrayList<>();
        for( int i=0; i<response.length(); i++ ){
            try {
                JSONObject bookJsonObject = response.getJSONObject(i);
                // Create a new book object from the JSON object and add it to the list of books
                Book newBook = translateJsonObjectToABookObject(bookJsonObject);
                books.add(newBook);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        // When all the books have been added, I setValue in the livedata of the book list to
        // notify the observers that the books have been added in order to update the UI.
        Utils.sortBookByTitle(books);
        booksLiveData.setValue(books);
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
        addBook : func :
            Add a book to the API and update the LiveData with the book added to notify the fragments that they can update their UI
        Parameter(s) :
            authorId : int : The id of the author of the book
            bookToAddJSONObject : JSONObject : The JSON object of the book to add
            bookToAddMutableLiveData : MutableLiveData<Book> : The LiveData to update with the book added to notify the fragments that they can update their UI
        Return :
            void
    */
    public void addBook(int authorId, JSONObject bookToAddJSONObject, MutableLiveData<Book> bookToAddMutableLiveData) {
        String url = "http://"+ this.adr_ip_pc_on_the_network +":3000/authors/"+ authorId +"/books";

        // Create a new JsonObjectRequest
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, bookToAddJSONObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Add the book to the local list and update the LiveData
                        Book newBook = null;
                        try {
                            newBook = translateJsonObjectToABookObject(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        bookToAddMutableLiveData.setValue(newBook);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(application, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("Books repository", "Error: " + error.toString());
                    }
                });

        // Add the request to the RequestQueue
        queue.add(jsonObjectRequest);

    }

    /*
        deleteBook : func :
            Delete a book from the API and update the LiveData with the book deleted to notify the fragments that they can update their UI
        Parameter(s) :
            bookId : int : The id of the book to delete
            bookDeletedIdMutableLiveData : MutableLiveData<Boolean> : The LiveData to update with the book deleted to notify the fragments that they can update their UI
        Return :
            void
    */
    public void deleteBook(int bookId, MutableLiveData<Boolean> bookDeletedIdMutableLiveData) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE, "http://"+ this.adr_ip_pc_on_the_network +":3000/books/"+bookId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Books repository", "Response is: " + response);
                        bookDeletedIdMutableLiveData.setValue(true);
                        //bookMutableLiveData.setValue(null); // To warn observers that I need to delete the book locally as well

                        //Toast.makeText(application, "Book deleted !" + response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(application, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("Repository", "Error: " + error.toString());
                    }
                }
        );
        queue.add(stringRequest);
    }

    /*
        loadTags : func :
            Load the tags of a book from the API and update the LiveData with the tags to notify the fragments that they can update their UI
        Parameter(s) :
            bookId : int : The id of the book to get the tags
            tags : MutableLiveData<List<Tag>> : The LiveData to update with the tags to notify the fragments that they can update their UI
        Return :
            void
    */
    public void loadTags(int bookId, MutableLiveData<List<Tag>> tags) {
        String url = urlApi + "/books/"+bookId+"/tags";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Tag> tagsList = new ArrayList<>();
                        for( int i=0; i<response.length(); i++ ){
                            try {
                                JSONObject tagJsonObject = response.getJSONObject(i);
                                Tag tag = new Tag(tagJsonObject.getInt("id"), tagJsonObject.getString("name"));
                                tagsList.add(tag);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        tags.setValue(tagsList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(application, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        queue.add(request);
    }

    public void getBook(int bookId, MutableLiveData<Book> bookMutableLiveData) {
        String url = urlApi + "/books/"+bookId;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Book book = translateJsonObjectToABookObject(response);
                            bookMutableLiveData.setValue(book);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(application, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        queue.add(request);
    }

    public void getAuthor(int authorId, MutableLiveData<Author> authorOfBookLiveData) {
        String url = urlApi + "/authors/"+authorId;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Author author = new Author(response.getInt("id"), response.getString("firstname"), response.getString("lastname"), null);
                            authorOfBookLiveData.setValue(author);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(application, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        queue.add(request);
    }
}
