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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.Author;
import AndroidBooksClient.androidbooksclient.Model.Book;

public class AuthorsRepository {
    RequestQueue queue;
    Application application;
    private static final String adr_ip_pc_on_the_network = "192.168.161.235";     // The IP address of the PC on the network (the phone and the PC must be on the same network), it can change so use this command to get the IP address on the network : ip addr show
    String urlApi = "http://"+ this.adr_ip_pc_on_the_network +":3000";

    public AuthorsRepository(Application application) {
        this.application = application;
        queue = Volley.newRequestQueue(application);
    }

    public void load_authors_from_api(MutableLiveData<List<Author>> authorsLiveData) {
        String url = urlApi + "/authors";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        handleResponseLoadDatas(response, authorsLiveData);
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

    private void handleResponseLoadDatas(JSONArray response, MutableLiveData<List<Author>> authorsLiveData ) {
        Log.d("AuthorsViewModel", "Response: " + response.toString());
        //booksLiveData.setValue(response);
        for( int i=0; i<response.length(); i++ ){
            try {
                JSONObject authorJsonObject = response.getJSONObject(i);
                // Create a new author object from the JSON object and add it to the list of authors
                translateJsonObjectToAnAuthorObject(authorJsonObject, authorsLiveData);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Toast.makeText(application, "Authors loaded from the API !", Toast.LENGTH_SHORT).show();
    }


    private void createAuthorFromJSONObject(JSONObject authorJSONObject, MutableLiveData<Author> authorLiveData) throws JSONException {
        String url = urlApi + "/authors/"+authorJSONObject.getInt("id")+"/books";

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
                            Author author = new Author(authorJSONObject.getInt("id"), authorJSONObject.getString("firstname"), authorJSONObject.getString("lastname"), books_of_author);
                            authorLiveData.setValue(author);
                            //Toast.makeText(application, "Author ("+author.getId()+") loaded from the API !", Toast.LENGTH_SHORT).show();

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

    private void translateJsonObjectToAnAuthorObject(JSONObject authorJsonObject, MutableLiveData<List<Author>> authorsLiveData) throws JSONException, InterruptedException {
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
                            //addAuthorToList(author);
                            // Adding in the livedata to notify AuthorsFragment that it can update UI
                            List<Author> authors = authorsLiveData.getValue();
                            authors.add(author);
                            authorsLiveData.setValue(authors);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(application, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
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


    public void loadBooksOfAuthor(int authorId, MutableLiveData<List<Book>> booksOfAuthorLiveData) {
        String url = "http://"+ this.adr_ip_pc_on_the_network +":3000/authors/"+ authorId +"/books";

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
                            /*
                            Author author = new Author(authorJsonObject.getInt("id"), authorJsonObject.getString("firstname"), authorJsonObject.getString("lastname"), books_of_author);
                            //addAuthorToList(author);
                            // Adding in the livedata to notify AuthorsFragment that it can update UI
                            List<Author> authors = authorsLiveData.getValue();
                            authors.add(author);
                            authorsLiveData.setValue(authors);
                            */
                         booksOfAuthorLiveData.setValue(books_of_author);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(application, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("AuthorsViewModel", "Error: " + error.toString());

                    }
                }
        );

        queue.add(request);
    }
}
