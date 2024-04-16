package AndroidBooksClient.androidbooksclient.ui.author_informations;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import AndroidBooksClient.androidbooksclient.R;

public class BookOfAuthorViewHolder extends RecyclerView.ViewHolder {
    private TextView _tvBookOfAuthorTitle;
    public BookOfAuthorViewHolder(@NonNull View itemView) {
        super(itemView);
        _tvBookOfAuthorTitle = itemView.findViewById(R.id.tv_book_of_author);
    }

    // Getter(s)
    public TextView get_tv_book_of_author_title(){
        return _tvBookOfAuthorTitle;
    }

}
