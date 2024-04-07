package AndroidBooksClient.androidbooksclient.ui.addAuthor;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;

import AndroidBooksClient.androidbooksclient.Model.Author;
import AndroidBooksClient.androidbooksclient.Repository.AuthorsRepository;

public class AddAuthorViewModel extends AndroidViewModel {
    AuthorsRepository authorsRepository;
    private MutableLiveData<Author> authorAddedMutableLiveData;
    private boolean thereIsAuthorToAdd;
    public AddAuthorViewModel(@NonNull Application application) {
        super(application);
        this.authorsRepository = new AuthorsRepository(application);
        this.authorAddedMutableLiveData = new MutableLiveData<>();
        this.thereIsAuthorToAdd = false;
    }

    public MutableLiveData<Author> getAuthorAddedMutableLiveData(){
        return this.authorAddedMutableLiveData;
    }
    public void setAuthorAddedMutableLiveData(Author authorAdded){
        this.authorAddedMutableLiveData.setValue(authorAdded);
    }

    public void addAuthor(JSONObject authorJSONObject) throws JSONException {
        this.authorsRepository.addAuthor(authorJSONObject, authorAddedMutableLiveData);
    }

    public boolean getThereIsAuthorToAdd() {
        return thereIsAuthorToAdd;
    }

    public void setThereIsAuthorToAdd(boolean b) {
        this.thereIsAuthorToAdd = b;
    }
}
