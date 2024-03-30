package AndroidBooksClient.androidbooksclient.View;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.Author;

public class AuthorsViewModel extends AndroidViewModel {
    MutableLiveData<List<Author>> authorsLiveData;
    public AuthorsViewModel(@NonNull Application application) {
        super(application);
        this.authorsLiveData = new MutableLiveData<>(new ArrayList<>());
    }


}
