package AndroidBooksClient.androidbooksclient.ui.book_informations;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.Tag;
import AndroidBooksClient.androidbooksclient.Repository.BooksRepository;

public class BookTagsViewModel extends AndroidViewModel {
    BooksRepository repository;
    MutableLiveData<List<Tag>> tags;
    public BookTagsViewModel(@NonNull Application application) {
        super(application);
        repository = new BooksRepository(application);
        tags = new MutableLiveData<>();
    }

    public void loadTags(int bookId) {
        repository.loadTags(bookId, tags);
    }

    public MutableLiveData<List<Tag>> getTagsMutableLiveData() {
        return tags;
    }
    public void setTagsMutableLiveData(List<Tag> tags) {
        this.tags.setValue(tags);
    }


}
