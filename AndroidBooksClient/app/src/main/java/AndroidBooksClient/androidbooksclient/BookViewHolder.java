package AndroidBooksClient.androidbooksclient;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookViewHolder extends RecyclerView.ViewHolder{
    TextView bookTitle;
    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
        bookTitle = itemView.findViewById(R.id.tv_book);
    }

    public TextView getBookTitle(){
        return bookTitle;
    }
}
