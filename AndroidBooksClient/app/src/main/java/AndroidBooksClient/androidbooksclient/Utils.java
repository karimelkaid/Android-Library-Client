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
        Navigates to the specified action
    Parameter(s) :
        actionId : int : The id of the action to navigate to
    Return :
        void
 */
    public static void navigateTo(Context context, int actionId) {
        NavController navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment_activity_main);
        navController.navigate(actionId);
    }


    public static void saveAuthorInformations(Context context, String preferencies_name, Author author) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferencies_name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", author.getId());
        editor.putString("first_name", author.getFirst_name());
        editor.putString("last_name", author.getLast_name());
    }

}
