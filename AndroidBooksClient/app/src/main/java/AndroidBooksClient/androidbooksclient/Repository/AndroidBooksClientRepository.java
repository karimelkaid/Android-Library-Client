package AndroidBooksClient.androidbooksclient.Repository;

import android.app.Application;
import android.content.Context;
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

public class AndroidBooksClientRepository {
    RequestQueue queue;
    Application application;
    private static final String adr_ip_pc_on_the_network = "192.168.1.9";     // The IP address of the PC on the network (the phone and the PC must be on the same network), it can change so use this command to get the IP address on the network : ip addr show
    String urlApi = "http://"+ this.adr_ip_pc_on_the_network +":3000";

    public AndroidBooksClientRepository(Application application) {
        this.application = application;
        queue = Volley.newRequestQueue(application);
    }

    public void loadAllBooks(MutableLiveData<List<Book>> booksLiveData){
        String url = urlApi+"/books";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        handleResponseLoadData(application, response, booksLiveData);
                        //api_loaded = true;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(application, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
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
    public void handleResponseLoadData(Context context, JSONArray response, MutableLiveData<List<Book>> booksLiveData){
        Log.d("BooksViewModel", "Response: " + response.toString());
        List<Book> books = booksLiveData.getValue();
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
        booksLiveData.setValue(books);
        //Toast.makeText(context, "Books loaded from the API !", Toast.LENGTH_SHORT).show();
    }


    public void load_books_of_author(MutableLiveData<List<Book>> booksOfAuthor) {
        
    }

    public void loadAuthorToDisplay(int authorId, MutableLiveData<Author> authorLiveData) {
        String url = urlApi + "/authors/" + authorId;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(application, "Response: " + response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            createAuthorFromJSONObject(response, authorLiveData);
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

    private void createAuthorFromJSONObject(JSONObject authorJSONObject, MutableLiveData<Author> authorLiveData) throws JSONException {

        Author author = new Author(authorJSONObject.getInt("id"), authorJSONObject.getString("firstname"), authorJSONObject.getString("lastname"), null);
        authorLiveData.setValue(author);
        /*String url = urlApi + "/authors/"+authorJSONObject.getInt("id")+"/books";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Book> books_of_author = new ArrayList<>();
                        // Add the books to the list of books of the author
                        for(int i=0; i<response.length(); i++){
                            try {
                                JSONObject current_json_object_book = response.getJSONObject(i);
                                Book current_book = translateJsonObjectToABookObject(current_json_object_book);
                                books_of_author.add(current_book);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        // Create a new author object from the JSON object and add it to the list of authors
                        //Author author = new Author(authorJSONObject.getInt("id"), authorJSONObject.getString("firstname"), authorJSONObject.getString("lastname"), books_of_author);
                        //authorLiveData.setValue(author);
                        //Toast.makeText(application, "Author ("+author.getId()+") loaded from the API !", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(application, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        queue.add(request);*/
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

    public void getBook(int bookId, MutableLiveData<Book> bookMutableLiveData) {
        String url = urlApi + "/books/" + bookId;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject bookJSONObject) {
                        //Toast.makeText(application, "Response: " + response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            Book book = translateJsonObjectToABookObject(bookJSONObject);
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

    public void addBook(int authorId, JSONObject bookToAddJSONObject, MutableLiveData<Book> bookToAddMutableLiveData) throws JSONException {
        String url = "http://"+ this.adr_ip_pc_on_the_network +":3000/authors/"+ authorId +"/books";

        // Create a new JsonObjectRequest
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, bookToAddJSONObject, new Response.Listener<JSONObject>() {

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
                        //addBookToList(newBook);
                        //bookUpdated.setValue(newBook);
                        bookToAddMutableLiveData.setValue(newBook);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(application, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("BooksViewModel", "Error: " + error.toString());
                    }
                });

        // Add the request to the RequestQueue
        queue.add(jsonObjectRequest);

    }

    public void deleteBook(MutableLiveData<Book> bookMutableLiveData, MutableLiveData<Integer> bookDeletedIdMutableLiveData) {
        Book bookToDelete = bookMutableLiveData.getValue();
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE, "http://"+ this.adr_ip_pc_on_the_network +":3000/books/"+bookToDelete.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Repository", "Response is: " + response);
                        bookDeletedIdMutableLiveData.setValue(bookToDelete.getId());
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

    public void loadTags(int bookId, MutableLiveData<List<Tag>> tags) {
        String url = urlApi + "/books/"+bookId+"/tags";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(application, "Response: " + response.toString(), Toast.LENGTH_SHORT).show();
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

    public void deleteAuthor(int authorId, MutableLiveData<Integer> authorIdDeletedLiveData) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE, "http://"+ this.adr_ip_pc_on_the_network +":3000/authors/"+authorId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Authors repository", "Response is: " + response);
                        authorIdDeletedLiveData.setValue(authorId);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(application, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("Authors repository", "Error: " + error.toString());
                    }
                }
        );
        queue.add(stringRequest);
    }
}
