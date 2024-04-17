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
import AndroidBooksClient.androidbooksclient.Utils;

public class AuthorsRepository {
    RequestQueue queue;
    Application application;
    private static final String adr_ip_pc_on_the_network = "localhost";     // The IP address of the PC on the network (the phone and the PC must be on the same network), it can change so use this command to get the IP address on the network : ip addr show
    String urlApi = "http://"+ this.adr_ip_pc_on_the_network +":3000";

    public AuthorsRepository(Application application) {
        this.application = application;
        queue = VolleySingleton.getInstance(application).getRequestQueue();    }

    /*
        load_authors_from_api : func :
            Load the authors from the API and update the LiveData with the authors to notify the AuthorsFragment that it can update the UI
        Parameter(s) :
            authorsLiveData : MutableLiveData<List<Author>> : The LiveData to update with the authors
        Return :
            void
    */
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

    /*
        handleResponseLoadDatas : func :
            Handle the response of the loadDatas request
        Parameter(s) :
            response : JSONArray : The response of the request
            authorsLiveData : MutableLiveData<List<Author>> : The LiveData to update with the authors
        Return :
            void
    */
    private void handleResponseLoadDatas(JSONArray response, MutableLiveData<List<Author>> authorsLiveData ) {
        Log.d("AuthorsViewModel", "Response: " + response.toString());
        List<Author> authors = new ArrayList<>();
        for( int i=0; i<response.length(); i++ ){
            try {
                JSONObject authorJsonObject = response.getJSONObject(i);
                // Create a new author object from the JSON object and add it to the list of authors
                Author author = new Author(authorJsonObject.getInt("id"), authorJsonObject.getString("firstname"), authorJsonObject.getString("lastname"), null);
                authors.add(author);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        Utils.sortAuhtorsByLastName(authors);
        authorsLiveData.setValue(authors);
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
        loadBooksOfAuthor : func :
            Load the books of an author from the API and update the LiveData with the books to notify the AuthorInformationFragment the RecyclerView which contains the books of the author selected
        Parameter(s) :
            authorId : int : The id of the author
            booksOfAuthorLiveData : MutableLiveData<List<Book>> : The LiveData to update with the books of the author
        Return :
            void
    */
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

    /*
        addAuthor : func :
            Add an author to the API and update the LiveData with the author to notify the AuthorsFragment that it can update the UI
        Parameter(s) :
            authorJSONObject : JSONObject : The JSON object of the author to add to be given to the API
            authorAddedMutableLiveData : MutableLiveData<Author> : The LiveData to update with the author added
        Return :
            void
    */
    public void addAuthor(JSONObject authorJSONObject, MutableLiveData<Author> authorAddedMutableLiveData) {
        String url = urlApi+"/authors";

        // Create a new JsonObjectRequest
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, authorJSONObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("AuthorsRepository", "Response: " + response.toString());
                        Author author = null;
                        try {
                            author = new Author(response.getInt("id"), response.getString("firstname"), response.getString("lastname"), new ArrayList<>());
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        authorAddedMutableLiveData.setValue(author);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(application, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("AuthorsRepository", "Error: " + error.toString());
                    }
                });

        // Add the request to the RequestQueue
        queue.add(jsonObjectRequest);
    }

    /*
    loadAuthorToDisplay : func :
        Load an author from the API and update the LiveData with the authorLiveData to notify the AuthorInformationFragment that it can update the UI
    Parameter(s) :
        authorId : int : The id of the author to load
        authorLiveData : MutableLiveData<Author> : The LiveData to update with the author to notify the AuthorFragment that it can update the UI
    Return :
        void
 */
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
    }

    public void deleteAuthor(int authorId, MutableLiveData<Boolean> authorIdDeletedLiveData) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE, "http://"+ this.adr_ip_pc_on_the_network +":3000/authors/"+authorId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Authors repository", "Response is: " + response);
                        authorIdDeletedLiveData.setValue(true);
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
