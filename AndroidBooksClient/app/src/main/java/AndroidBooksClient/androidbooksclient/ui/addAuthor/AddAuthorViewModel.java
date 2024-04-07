package AndroidBooksClient.androidbooksclient.ui.addAuthor;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import AndroidBooksClient.androidbooksclient.Model.Author;
import AndroidBooksClient.androidbooksclient.Repository.AuthorsRepository;

public class AddAuthorViewModel extends AndroidViewModel {
    AuthorsRepository authorsRepository;
    private MutableLiveData<Author> authorAddedMutableLiveData;
    public AddAuthorViewModel(@NonNull Application application) {
        super(application);
        this.authorsRepository = new AuthorsRepository(application);
        this.authorAddedMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Author> getAuthorAddedMutableLiveData(){
        return this.authorAddedMutableLiveData;
    }
    public void setAuthorAddedMutableLiveData(Author authorAdded){
        this.authorAddedMutableLiveData.setValue(authorAdded);
    }
}
