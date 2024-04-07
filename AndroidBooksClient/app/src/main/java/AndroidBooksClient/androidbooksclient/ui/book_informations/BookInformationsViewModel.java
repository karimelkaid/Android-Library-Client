package AndroidBooksClient.androidbooksclient.ui.book_informations;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import AndroidBooksClient.androidbooksclient.AndroidBooksClientRepository;
import AndroidBooksClient.androidbooksclient.Model.Book;

public class BookInformationsViewModel extends AndroidViewModel {
    private AndroidBooksClientRepository repository;
    private MutableLiveData<Book> bookMutableLiveData;
    public BookInformationsViewModel(@NonNull Application application) {
        super(application);
        repository = new AndroidBooksClientRepository(application);
        bookMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Book> getBookMutableLiveData(){
        return bookMutableLiveData;
    }
    public void setBookMutableLiveData(Book book){
        this.bookMutableLiveData.setValue(book);
    }

    public void loadBook(int bookId){
        repository.getBook(bookId, bookMutableLiveData);
    }
}
