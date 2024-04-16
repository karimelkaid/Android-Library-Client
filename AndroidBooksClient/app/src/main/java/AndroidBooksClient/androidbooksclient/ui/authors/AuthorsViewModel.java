package AndroidBooksClient.androidbooksclient.ui.authors;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.Author;
import AndroidBooksClient.androidbooksclient.Model.Book;
import AndroidBooksClient.androidbooksclient.Repository.AuthorsRepository;

public class AuthorsViewModel extends AndroidViewModel {
    AuthorsRepository _authorsRepository;
    MutableLiveData<List<Author>> _authorsLiveData;
    RequestQueue _queue;
    private MutableLiveData<Author> _authorEdited;

    public AuthorsViewModel(@NonNull Application application) {
        super(application);
        this._authorsRepository = new AuthorsRepository(application);
        this._authorsLiveData = new MutableLiveData<>(new ArrayList<>());
        this._authorEdited = new MutableLiveData<>();
        _queue = Volley.newRequestQueue(getApplication());
        load_authors_from_api();
    }

    public void load_authors_from_api() {
        _authorsRepository.load_authors_from_api(_authorsLiveData);
    }


    public void addAuthorToList(Author newAuthor) {
        List<Author> authors = this._authorsLiveData.getValue();
        authors.add(newAuthor);
        sortByLastName(authors);
        _authorsLiveData.setValue(authors);
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
        return _authorsLiveData;
    }

    public void updateAuthorsLiveData(int authorId, Book book) {
        List<Author> authors = _authorsLiveData.getValue();
        for( Author author : authors ){
            if( author.getId() == authorId ){
                author.addBook(book);
            }
        }
        _authorsLiveData.setValue(authors);
    }

    public void set_authorEdited(int authorId) {
        List<Author> authors = _authorsLiveData.getValue();
        for( Author author : authors ){
            if( author.getId() == authorId ){
                _authorEdited.setValue(author);
            }
        }
        _authorsLiveData.setValue(authors);
    }

    public MutableLiveData<Author> get_authorEdited() {
        return _authorEdited;
    }

    public Author getAuthor(int id) {
        List<Author> authors = _authorsLiveData.getValue();
        for( Author author : authors ){
            if( author.getId() == id ){
                return author;
            }
        }
        return null;
    }

    public void deleteBookOfAuthor(Book book) {
        List<Author> authors = _authorsLiveData.getValue();
        for( Author author : authors ){
            if( author.getId() == book.getAuthorId() ){
                // Remove the book from the list of books of the author
                author.removeBookById(book.getId());
            }
        }
        _authorsLiveData.setValue(authors);
    }

    public void addBookToAuthor(Book bookAdded) {
        List<Author> authors = _authorsLiveData.getValue();
        for( Author author : authors ){
            if( author.getId() == bookAdded.getAuthorId() ){
                author.addBook(bookAdded);
                Toast.makeText(getApplication(), "Book added to author "+bookAdded.getAuthorId(), Toast.LENGTH_SHORT).show();
            }
        }
        _authorsLiveData.setValue(authors);
    }

    public String deleteAuthor(Integer authorIdDeleted) {
        String lastNameOfAuthorDeleted = "";
        List<Author> authors = _authorsLiveData.getValue();
        for( Author author : authors ){
            if( author.getId() == authorIdDeleted ){
                lastNameOfAuthorDeleted = author.getLast_name();
                authors.remove(author);
                break;
            }
        }
        _authorsLiveData.setValue(authors);
        return lastNameOfAuthorDeleted;
    }
}
