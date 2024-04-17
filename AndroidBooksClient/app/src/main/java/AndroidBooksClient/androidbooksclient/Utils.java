package AndroidBooksClient.androidbooksclient;

import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.Author;
import AndroidBooksClient.androidbooksclient.Model.Book;

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

    /*
        sortBooksByTitle : proc :
            Sorts the list of books by title
        Parameter(s) :
            List<Book> books : The list of books to sort
        Return :
            void
    */
    public static void sortBookByTitle(List<Book> books){
        books.sort((book1, book2) -> book1.getTitle().compareTo(book2.getTitle()));
    }

    /*
        sortAuhtorsByLastName : proc :
            Sorts the list of authors by last name
        Parameter(s) :
            List<Author> authors : The list of authors to sort
        Return :
            void
    */
    public static void sortAuhtorsByLastName(List<Author> authors) {
        authors.sort((author1, author2) -> author1.getLast_name().compareTo(author2.getLast_name()));
    }
}
