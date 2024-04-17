package AndroidBooksClient.androidbooksclient.ui.books;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.Book;
import AndroidBooksClient.androidbooksclient.R;
import AndroidBooksClient.androidbooksclient.SharedViewModel;
import AndroidBooksClient.androidbooksclient.Utils;

public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {

    List<Book> books;
    SharedViewModel sharedViewModel;

    public BookAdapter(List<Book> books, SharedViewModel sharedViewModel) {
        this.books = books;
        this.sharedViewModel = sharedViewModel;
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
        TextView tvBookTitle = holder.get_bookTitle();
        tvBookTitle.setText(bookTitle);

        // Set up the button to save the book ID to display and navigate to the BookInformation fragment
        tvBookTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedViewModel.set_selectedBookId(books.get(position).getId());
                sharedViewModel.setPreviousFragmentIsBooks(true);
                Utils.navigateTo(v.getContext(), R.id.action_navigation_books_to_navigation_bookInformation);
            }
        });
    }
    @Override
    public int getItemCount() {
        return books.size();
    }
}
