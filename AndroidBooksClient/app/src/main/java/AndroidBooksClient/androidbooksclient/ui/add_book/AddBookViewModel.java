package AndroidBooksClient.androidbooksclient.ui.add_book;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;

import AndroidBooksClient.androidbooksclient.AndroidBooksClientRepository;
import AndroidBooksClient.androidbooksclient.Model.Book;

public class AddBookViewModel extends AndroidViewModel {
    AndroidBooksClientRepository repository;
    private MutableLiveData<Book> bookToAddMutableLiveData;
    private boolean thereIsBookToAdd;
    public AddBookViewModel(@NonNull Application application) {
        super(application);
        repository = new AndroidBooksClientRepository(application);
        bookToAddMutableLiveData = new MutableLiveData<>();
        thereIsBookToAdd = false;
    }

    public void addBook(int authorId, JSONObject bookToAddJSONObject) throws JSONException {
        repository.addBook(authorId, bookToAddJSONObject, bookToAddMutableLiveData);
    }

    public MutableLiveData<Book> getBookToAddMutableLiveData(){
        return bookToAddMutableLiveData;
    }
    public void setBookToAddMutableLiveData(Book book){
        this.bookToAddMutableLiveData.setValue(book);
    }

    public boolean getThereIsBookToAdd(){
        return thereIsBookToAdd;
    }
    public void setThereIsBookToAdd(boolean b){
        this.thereIsBookToAdd = b;
    }
}
