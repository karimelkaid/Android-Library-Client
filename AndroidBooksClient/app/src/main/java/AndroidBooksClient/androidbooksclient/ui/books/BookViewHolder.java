package AndroidBooksClient.androidbooksclient.ui.books;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import AndroidBooksClient.androidbooksclient.R;

public class BookViewHolder extends RecyclerView.ViewHolder{
    Button _bookTitle;
    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
        _bookTitle = itemView.findViewById(R.id.btn_books);   // Retrieving the button from the book_view_holder layout
    }

    /*
        getBookTitle : func :
            This function returns the bookTitle button
        Parameter(s) :
            None
        Return :
            bookTitle : The bookTitle button
    */
    public Button get_bookTitle(){
        return _bookTitle;
    }
}
