package AndroidBooksClient.androidbooksclient.ui.book_informations;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.Tag;
import AndroidBooksClient.androidbooksclient.Repository.BooksRepository;

public class BookTagsViewModel extends AndroidViewModel {
    BooksRepository _repository;
    MutableLiveData<List<Tag>> _tags;
    public BookTagsViewModel(@NonNull Application application) {
        super(application);
        _repository = new BooksRepository(application);
        _tags = new MutableLiveData<>();
    }

    /*
        loadTags : proc :
            Calls a method in the repository to send a request to get the tags from the API
        Parameter(s) :
            int bookId : The id of the book to be fetched
        Return :
            void
    */
    public void loadTags(int bookId) {
        _repository.loadTags(bookId, _tags);
    }

    // Getter(s)
    public MutableLiveData<List<Tag>> getTagsMutableLiveData() {
        return _tags;
    }



}
