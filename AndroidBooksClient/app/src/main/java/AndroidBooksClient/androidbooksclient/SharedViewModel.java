package AndroidBooksClient.androidbooksclient;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import AndroidBooksClient.androidbooksclient.Model.Author;

public class SharedViewModel extends AndroidViewModel {
    private MutableLiveData<Author> selectedAuthor;
    private MutableLiveData<Integer> selectedBook;
    public SharedViewModel(@NonNull Application application) {
        super(application);
        selectedAuthor = new MutableLiveData<>();
        selectedBook = new MutableLiveData<>();
    }

    public MutableLiveData<Author> getSelectedAuthor() {
        return selectedAuthor;
    }

    public void setSelectedAuthor(Author author) {
        selectedAuthor.setValue(author);
    }

    public MutableLiveData<Integer> getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(Integer book) {
        selectedBook.setValue(book);
    }
}
