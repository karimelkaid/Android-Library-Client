package AndroidBooksClient.androidbooksclient.ui.book_informations;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import AndroidBooksClient.androidbooksclient.R;

public class TagViewHolder extends RecyclerView.ViewHolder{
    TextView tv_tag_name;
    public TagViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_tag_name = itemView.findViewById(R.id.tv_tag_name);
    }

    public void setTv_tag_name(String tag_name){
        tv_tag_name.setText(tag_name);
    }
}
