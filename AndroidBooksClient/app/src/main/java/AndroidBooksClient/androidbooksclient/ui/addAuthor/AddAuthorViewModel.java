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
    AuthorsRepository _authorsRepository;
    private MutableLiveData<Author> _authorAddedMutableLiveData;
    public AddAuthorViewModel(@NonNull Application application) {
        super(application);
        this._authorsRepository = new AuthorsRepository(application);
        this._authorAddedMutableLiveData = new MutableLiveData<>();
    }

    /*
        addAuthor : proc :
            Calls a method in the repository to send a request to add an author to the API
        Parameter(s) :
            JSONObject authorJSONObject : The author to be added in JSON format to be sent to the API
        Return :
            void
    */
    public void addAuthor(JSONObject authorJSONObject) throws JSONException {
        this._authorsRepository.addAuthor(authorJSONObject, _authorAddedMutableLiveData);
    }

    // Getter(s)
    public MutableLiveData<Author> get_authorAddedMutableLiveData(){
        return this._authorAddedMutableLiveData;
    }
}
