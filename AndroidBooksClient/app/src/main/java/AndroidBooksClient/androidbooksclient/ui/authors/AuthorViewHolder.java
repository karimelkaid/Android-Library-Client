package AndroidBooksClient.androidbooksclient.ui.authors;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import AndroidBooksClient.androidbooksclient.R;

public class AuthorViewHolder extends RecyclerView.ViewHolder {
    private Button _btnAuthorName;


    public AuthorViewHolder(@NonNull View itemView) {
        super(itemView);
        _btnAuthorName = itemView.findViewById(R.id.btn_author_name);
    }

    public Button get_btnAuthorName() {
        return _btnAuthorName;
    }
}
