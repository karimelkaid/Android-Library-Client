package AndroidBooksClient.androidbooksclient.ui.book_informations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.Tag;
import AndroidBooksClient.androidbooksclient.R;

public class TagAdapter extends RecyclerView.Adapter<TagViewHolder>{

    List<Tag> tags;
    public TagAdapter(List<Tag> tags) {
        this.tags = tags;
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_view_holder, parent, false);
        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        holder.setTv_tag_name(tags.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return this.tags.size();
    }
}
