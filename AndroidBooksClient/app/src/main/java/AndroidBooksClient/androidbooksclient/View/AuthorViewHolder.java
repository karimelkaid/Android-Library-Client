package AndroidBooksClient.androidbooksclient.View;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import AndroidBooksClient.androidbooksclient.R;

public class AuthorViewHolder extends RecyclerView.ViewHolder {
    private Button btn_author_name;


    public AuthorViewHolder(@NonNull View itemView) {
        super(itemView);
        btn_author_name = itemView.findViewById(R.id.btn_author_name);
    }

    public Button get_btn_author_name() {
        return btn_author_name;
    }
}
