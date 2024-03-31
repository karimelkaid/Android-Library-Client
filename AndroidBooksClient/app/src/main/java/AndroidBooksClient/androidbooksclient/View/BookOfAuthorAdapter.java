package AndroidBooksClient.androidbooksclient.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.Book;
import AndroidBooksClient.androidbooksclient.R;

public class BookOfAuthorAdapter extends RecyclerView.Adapter<BookOfAuthorViewHolder> {
    List<Book> books_of_author;

    public BookOfAuthorAdapter(List<Book> books_of_author) {
        this.books_of_author = books_of_author;
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
    }

    @Override
    public int getItemCount() {
        return books_of_author.size();
    }
}
