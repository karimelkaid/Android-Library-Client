package AndroidBooksClient.androidbooksclient;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.Author;
import AndroidBooksClient.androidbooksclient.Model.Book;

public class AndroidBooksClientRepository {
    RequestQueue queue;
    private static final String adr_ip_pc_on_the_network = "192.168.210.235";     // The IP address of the PC on the network (the phone and the PC must be on the same network), it can change so use this command to get the IP address on the network : ip addr show
    String urlApi = "http://"+ this.adr_ip_pc_on_the_network +":3000";

    private Application application;
    public AndroidBooksClientRepository(Application application) {
        this.application = application;
        queue = Volley.newRequestQueue(application);
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
                        Toast.makeText(application, "Response: " + response.toString(), Toast.LENGTH_SHORT).show();
                        createAuthorFromJSONObject(response);

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

    private void createAuthorFromJSONObject(JSONObject response) throws JSONException {
        String url = urlApi + "/authors/"+response.getInt("id")+"/books";

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
                        Toast.makeText(application, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
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

}
