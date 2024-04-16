package AndroidBooksClient.androidbooksclient.ui.book_informations;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import AndroidBooksClient.androidbooksclient.R;

public class TagViewHolder extends RecyclerView.ViewHolder{
    TextView _tvTagName;
    public TagViewHolder(@NonNull View itemView) {
        super(itemView);
        _tvTagName = itemView.findViewById(R.id.tv_tag_name);
    }

    public void set_tvTagName(String tag_name){
        _tvTagName.setText(tag_name);
    }
}
