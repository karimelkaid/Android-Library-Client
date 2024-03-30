package AndroidBooksClient.androidbooksclient.View;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.Book;
import AndroidBooksClient.androidbooksclient.R;

public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {

    List<Book> books;

    public BookAdapter(List<Book> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_view_holder, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        String bookTitle = books.get(position).getTitle();
        Button btn_book = holder.getBookTitle();
        btn_book.setText(bookTitle);
        setUpBookInformationsButton(btn_book, books.get(position));
    }
    @Override
    public int getItemCount() {
        return books.size();
    }

    /*
        setUpBookInformationsButton : proc :
            Sets up the button to save the book informations and navigate to the BookInformation fragment
        Parameter(s) :
            btn_book : Button : The button to set up
            book : Book : The book to save the informations of
        Return :
            void
    */
    public void setUpBookInformationsButton(Button btn_book, Book book){
        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBookInformations(v.getContext(), book);
                navigateToBookInformation(v);   // Fragment change to BookInformation
            }
        });
    }

    /*
        addBook : proc :
            Adds a book to the books attribute
        Parameter(s) :
            book : Book : The book to add to the books attribute
        Return :
            void
    */
    /*public void addBook(Book book) {
        books.add(book);
    }*/

    /*
        saveBookInformations : proc :
            Saves the book informations in the shared preferences
        Parameter(s) :
            context : Context : The context of the application
            book : Book : The book to save the informations of
        Return :
            void
    */
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

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}