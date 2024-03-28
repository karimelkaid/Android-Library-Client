package AndroidBooksClient.androidbooksclient;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import AndroidBooksClient.androidbooksclient.Book;

public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {

    List<Book> books = new ArrayList<>();

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_view_holder, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        String bookTitle = books.get(position).getTitle();
        //Toast.makeText(holder.itemView.getContext(), bookTitle, Toast.LENGTH_SHORT).show();
        holder.getBookTitle().setText(bookTitle);

        holder.getBookTitle().setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(v.getContext(), "Hello, World!", Toast.LENGTH_SHORT).show();
                        navigateToBookInformation(v);   // Fragment change to BookInformation
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return books.size();
    }


    /*
        addBook : proc :
            Adds a book to the books attribute
        Parameter(s) :
            book : Book : The book to add to the books attribute
        Return :
            void
    */
    public void addBook(Book book) {
        books.add(book);
    }


    /*
        navigateToBookInformation : proc :
            Launch action to navigate from this fragment to the fragment containing information about a specific book
        Parameter(s) :
            v : View : The view that was clicked on to trigger this action
        Return :
            void
    */
    public void navigateToBookInformation(View v){
        NavController navController = Navigation.findNavController((Activity) v.getContext(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.action_navigation_books_to_navigation_bookInformation);
    }
}
