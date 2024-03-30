package AndroidBooksClient.androidbooksclient.View;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import AndroidBooksClient.androidbooksclient.R;

public class BookViewHolder extends RecyclerView.ViewHolder{
    Button bookTitle;
    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
        bookTitle = itemView.findViewById(R.id.btn_books);   // Retrieving the button from the book_view_holder layout
    }

    /*
        getBookTitle : func :
            This function returns the bookTitle button
        Parameter(s) :
            None
        Return :
            bookTitle : The bookTitle button
    */
    public Button getBookTitle(){
        return bookTitle;
    }
}
