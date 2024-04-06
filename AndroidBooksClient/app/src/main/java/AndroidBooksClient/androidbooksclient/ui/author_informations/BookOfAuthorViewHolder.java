package AndroidBooksClient.androidbooksclient.ui.author_informations;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import AndroidBooksClient.androidbooksclient.R;

public class BookOfAuthorViewHolder extends RecyclerView.ViewHolder {
    private TextView tv_book_of_author_title;
    public BookOfAuthorViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_book_of_author_title = itemView.findViewById(R.id.tv_book_of_author);
    }

    public TextView get_tv_book_of_author_title(){
        return tv_book_of_author_title;
    }

}
