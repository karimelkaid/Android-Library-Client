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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import AndroidBooksClient.androidbooksclient.Model.Author;
import AndroidBooksClient.androidbooksclient.Model.Book;

public class AuthorsViewModel extends AndroidViewModel {
    MutableLiveData<List<Author>> authorsLiveData;
    RequestQueue queue;
    private static final String adr_ip_pc_on_the_network = "192.168.241.235";     // The IP address of the PC on the network (the phone and the PC must be on the same network), it can change so use this command to get the IP address on the network : ip addr show
    private MutableLiveData<Author> authorEdited;

    public AuthorsViewModel(@NonNull Application application) {
        super(application);
        this.authorsLiveData = new MutableLiveData<>(new ArrayList<>());
        this.authorEdited = new MutableLiveData<>();
        queue = Volley.newRequestQueue(getApplication());
        load_authors_from_api(application);
    }

    public void load_authors_from_api(Context context) {
        String url = "http://"+ this.adr_ip_pc_on_the_network +":3000/authors";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        handleResponseLoadDatas(context, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("AuthorsViewModel", "Error: " + error.toString());
                    }
                }
        );

        queue.add(request);

    }

    private void handleResponseLoadDatas(Context context, JSONArray response) {
        Log.d("AuthorsViewModel", "Response: " + response.toString());
        //booksLiveData.setValue(response);
        for( int i=0; i<response.length(); i++ ){
            try {
                JSONObject authorJsonObject = response.getJSONObject(i);
                // Create a new author object from the JSON object and add it to the list of authors
                translateJsonObjectToAnAuthorObject(authorJsonObject);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Toast.makeText(context, "Authors loaded from the API !", Toast.LENGTH_SHORT).show();
    }

    private void addAuthorToList(Author newAuthor) {
        List<Author> authors = this.authorsLiveData.getValue();
        authors.add(newAuthor);
        authorsLiveData.setValue(authors);
    }

    private void translateJsonObjectToAnAuthorObject(JSONObject authorJsonObject) throws JSONException, InterruptedException {
        String url = "http://"+ this.adr_ip_pc_on_the_network +":3000/authors/"+ authorJsonObject.getInt("id") +"/books";

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
                        try {
                            Author author = new Author(authorJsonObject.getInt("id"), authorJsonObject.getString("firstname"), authorJsonObject.getString("lastname"), books_of_author);
                            addAuthorToList(author);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("AuthorsViewModel", "Error: " + error.toString());

                    }
                }
        );

        queue.add(request);
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



    public MutableLiveData<List<Author>> get_authors_live_data() {
        return authorsLiveData;
    }

    public void updateAuthorsLiveData(int authorId, Book book) {
        List<Author> authors = authorsLiveData.getValue();
        for( Author author : authors ){
            if( author.getId() == authorId ){
                author.addBook(book);
            }
        }
        authorsLiveData.setValue(authors);
    }

    public void setAuthorEdited(int authorId) {
        List<Author> authors = authorsLiveData.getValue();
        for( Author author : authors ){
            if( author.getId() == authorId ){
                authorEdited.setValue(author);
            }
        }
        authorsLiveData.setValue(authors);
    }

    public MutableLiveData<Author> getAuthorEdited() {
        return authorEdited;
    }

    public Author getAuthor(int id) {
        List<Author> authors = authorsLiveData.getValue();
        for( Author author : authors ){
            if( author.getId() == id ){
                return author;
            }
        }
        return null;
    }
}
