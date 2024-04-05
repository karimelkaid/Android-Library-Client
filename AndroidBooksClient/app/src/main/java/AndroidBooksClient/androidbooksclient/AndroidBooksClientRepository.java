package AndroidBooksClient.androidbooksclient;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.Book;

public class AndroidBooksClientRepository {
    RequestQueue queue;
    private static final String adr_ip_pc_on_the_network = "192.168.210.235";     // The IP address of the PC on the network (the phone and the PC must be on the same network), it can change so use this command to get the IP address on the network : ip addr show
    String urlApi = "http://"+ this.adr_ip_pc_on_the_network +":3000";

    private Application application;
    public AndroidBooksClientRepository(Application application) {
        this.application = application;
        queue = Volley.newRequestQueue(application);
    }


    public void load_books_of_author(MutableLiveData<List<Book>> booksOfAuthor) {
        
    }
}
