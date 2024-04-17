package AndroidBooksClient.androidbooksclient.ui.books;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import AndroidBooksClient.androidbooksclient.R;

public class BookViewHolder extends RecyclerView.ViewHolder{
    TextView _tvBookTitle;
    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
        _tvBookTitle = itemView.findViewById(R.id.tv_title_of_book);   // Retrieving the button from the book_view_holder layout
    }

    // Getter
    public TextView get_bookTitle(){
        return _tvBookTitle;
    }
}
