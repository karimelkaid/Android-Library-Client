package AndroidBooksClient.androidbooksclient.ui.add_book;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;

import AndroidBooksClient.androidbooksclient.Repository.BooksRepository;
import AndroidBooksClient.androidbooksclient.Model.Book;

public class AddBookViewModel extends AndroidViewModel {
    BooksRepository repository;
    private MutableLiveData<Book> bookToAddMutableLiveData;
    public AddBookViewModel(@NonNull Application application) {
        super(application);
        repository = new BooksRepository(application);
        bookToAddMutableLiveData = new MutableLiveData<>();
    }

    /*
        addBook : proc :
            Calls a method in the repository to send a request to add a book to the API
        Parameter(s) :
            int authorId : The id of the author to whom the book will be added (Must not be given in the JSON to be sent to in the request)
            JSONObject bookToAddJSONObject : The book to be added in JSON format to be sent to the API
        Return :
            void
    */
    public void addBook(int authorId, JSONObject bookToAddJSONObject) throws JSONException {
        repository.addBook(authorId, bookToAddJSONObject, bookToAddMutableLiveData);
    }

    // Getter(s)
    public MutableLiveData<Book> getBookToAddMutableLiveData(){
        return bookToAddMutableLiveData;
    }
}
