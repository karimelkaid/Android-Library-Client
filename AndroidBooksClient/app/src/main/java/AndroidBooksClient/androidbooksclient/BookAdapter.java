package AndroidBooksClient.androidbooksclient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import AndroidBooksClient.androidbooksclient.Book;

public class BookAdapter extends RecyclerView.Adapter {
    List<Book> books = new ArrayList<>();
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_view_holder, parent, false);

        books.add(new Book("manger", "Marc", "Une description"));
        books.add(new Book("manger", "Marc", "Une description"));

        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String bookTitle = books.get(position).getTitle();
        ((BookViewHolder)holder).getBookTitle().setText("#"+position+" : "+bookTitle);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
