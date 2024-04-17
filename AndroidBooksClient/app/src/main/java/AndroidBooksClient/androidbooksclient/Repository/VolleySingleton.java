package AndroidBooksClient.androidbooksclient.Repository;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import android.content.Context;

public class VolleySingleton {
    private static VolleySingleton instance;
    private RequestQueue requestQueue;

    private VolleySingleton(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    /*
        getInstance : func :
            Returns the instance of the VolleySingleton class if it exists, otherwise creates a new instance
        Parameter(s) :
            Context context : The context of the application
        Return :
            VolleySingleton : The instance of the VolleySingleton class
     */
    public static synchronized VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
