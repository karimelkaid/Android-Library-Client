package AndroidBooksClient.androidbooksclient;

import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import AndroidBooksClient.androidbooksclient.Model.Author;

public class Utils {
        /*
        navigateTo : proc :
            Navigates to the specified fragment
        Parameter(s) :
            actionId : int : The action identifier for navigating to the fragment
        Return :
            void
     */
    public static void navigateTo(Context context, int actionId) {
        NavController navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment_activity_main);
        navController.navigate(actionId);
    }
}
