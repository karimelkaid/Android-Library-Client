package AndroidBooksClient.androidbooksclient;

import static java.security.AccessController.getContext;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

        //tvBooks.setText("Hello, World!");
        holder.getBookTitle().setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(v.getContext(), "Hello, World!", Toast.LENGTH_SHORT).show();

                        
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void addBook(Book book) {
        books.add(book);
    }
}
