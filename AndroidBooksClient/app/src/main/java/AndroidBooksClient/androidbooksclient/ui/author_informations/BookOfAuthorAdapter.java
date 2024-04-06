package AndroidBooksClient.androidbooksclient.ui.author_informations;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.Book;
import AndroidBooksClient.androidbooksclient.R;
import AndroidBooksClient.androidbooksclient.SharedViewModel;

public class BookOfAuthorAdapter extends RecyclerView.Adapter<BookOfAuthorViewHolder> {
    List<Book> books_of_author;
    SharedViewModel sharedViewModel;

    public BookOfAuthorAdapter(List<Book> books_of_author, SharedViewModel sharedViewModel) {
        this.books_of_author = books_of_author;
        this.sharedViewModel = sharedViewModel;
    }

    @NonNull
    @Override
    public BookOfAuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_of_author_view_holder, parent, false);
        return new BookOfAuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookOfAuthorViewHolder holder, int position) {
        Book current_book = books_of_author.get(position);
        holder.get_tv_book_of_author_title().setText("#"+(position+1)+" : "+current_book.getTitle());
        holder.get_tv_book_of_author_title().setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sharedViewModel.setSelectedBook(current_book.getId());
                        navigateTo(R.id.action_navigation_author_informations_to_navigation_bookInformation, v);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return books_of_author.size();
    }

    public void saveBookInformations(Context context, Book book){
        // Retrieve the shared preferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("BookInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Save the book informations
        editor.putInt("id", book.getId());
        editor.putString("title", book.getTitle());
        editor.putInt("publication_year", book.getPublication_year());
        editor.putInt("author_id", book.getAuthorId());

        editor.apply();
    }

    public void navigateTo(int actionId, View v) {
        NavController navController = Navigation.findNavController((Activity) v.getContext(), R.id.nav_host_fragment_activity_main);
        navController.navigate(actionId);
    }


}
