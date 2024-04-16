package AndroidBooksClient.androidbooksclient.ui.authors;

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
import AndroidBooksClient.androidbooksclient.Repository.AuthorsRepository;

public class AuthorsViewModel extends AndroidViewModel {
    AuthorsRepository authorsRepository;
    MutableLiveData<List<Author>> authorsLiveData;
    RequestQueue queue;
    private static final String adr_ip_pc_on_the_network = "192.168.161.235";     // The IP address of the PC on the network (the phone and the PC must be on the same network), it can change so use this command to get the IP address on the network : ip addr show
    private MutableLiveData<Author> authorEdited;

    public AuthorsViewModel(@NonNull Application application) {
        super(application);
        this.authorsRepository = new AuthorsRepository(application);
        this.authorsLiveData = new MutableLiveData<>(new ArrayList<>());
        this.authorEdited = new MutableLiveData<>();
        queue = Volley.newRequestQueue(getApplication());
        load_authors_from_api();
    }

    public void load_authors_from_api() {
        authorsRepository.load_authors_from_api(authorsLiveData);
    }


    public void addAuthorToList(Author newAuthor) {
        List<Author> authors = this.authorsLiveData.getValue();
        authors.add(newAuthor);
        sortByLastName(authors);
        authorsLiveData.setValue(authors);
    }

    private void sortByLastName(List<Author> authors) {
        for( int i = 0; i < authors.size(); i++ ){
            for( int j = i+1; j < authors.size(); j++ ){
                if( authors.get(i).getLast_name().compareTo(authors.get(j).getLast_name()) > 0 ){
                    Author aux = authors.get(i);
                    authors.set(i, authors.get(j));
                    authors.set(j, aux);
                }
            }
        }
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

    public void deleteBookOfAuthor(Book book) {
        List<Author> authors = authorsLiveData.getValue();
        for( Author author : authors ){
            if( author.getId() == book.getAuthorId() ){
                // Remove the book from the list of books of the author
                author.removeBookById(book.getId());
            }
        }
        authorsLiveData.setValue(authors);
    }

    public void addBookToAuthor(Book bookAdded) {
        List<Author> authors = authorsLiveData.getValue();
        for( Author author : authors ){
            if( author.getId() == bookAdded.getAuthorId() ){
                author.addBook(bookAdded);
                Toast.makeText(getApplication(), "Book added to author "+bookAdded.getAuthorId(), Toast.LENGTH_SHORT).show();
            }
        }
        authorsLiveData.setValue(authors);
    }

    public String deleteAuthor(Integer authorIdDeleted) {
        String lastNameOfAuthorDeleted = "";
        List<Author> authors = authorsLiveData.getValue();
        for( Author author : authors ){
            if( author.getId() == authorIdDeleted ){
                lastNameOfAuthorDeleted = author.getLast_name();
                authors.remove(author);
                break;
            }
        }
        authorsLiveData.setValue(authors);
        return lastNameOfAuthorDeleted;
    }
}
