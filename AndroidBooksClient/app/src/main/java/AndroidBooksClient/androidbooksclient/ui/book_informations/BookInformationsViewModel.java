package AndroidBooksClient.androidbooksclient.ui.book_informations;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import AndroidBooksClient.androidbooksclient.Repository.BooksRepository;
import AndroidBooksClient.androidbooksclient.Model.Book;

public class BookInformationsViewModel extends AndroidViewModel {
    private BooksRepository repository;
    private MutableLiveData<Book> bookMutableLiveData;
    private MutableLiveData<Boolean> bookDeletedLiveData;
    public BookInformationsViewModel(@NonNull Application application) {
        super(application);
        repository = new BooksRepository(application);
        bookMutableLiveData = new MutableLiveData<>();
        bookDeletedLiveData = new MutableLiveData<>(false);
    }

    public MutableLiveData<Book> getBookMutableLiveData(){
        return bookMutableLiveData;
    }
    public void setBookMutableLiveData(Book book){
        this.bookMutableLiveData.setValue(book);
    }

    public MutableLiveData<Boolean> getBookDeletedLiveData(){
        return bookDeletedLiveData;
    }
    public void setBookDeletedLiveData(boolean b){
        this.bookDeletedLiveData.setValue(b);
    }

    public void deleteBook(int bookId){
        repository.deleteBook(bookId, bookDeletedLiveData);
    }
}
