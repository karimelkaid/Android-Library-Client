package AndroidBooksClient.androidbooksclient.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.Author;
import AndroidBooksClient.androidbooksclient.R;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorViewHolder> {
    List<Author> authors;

    public AuthorAdapter(List<Author> authors) {
        this.authors = authors;
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_author_view_holder, parent, false);
        return new AuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, int position) {
        Author current_author = authors.get(position);

        Button btn_author = holder.get_btn_author_name();
        btn_author.setText(current_author.getLast_name());

        setUpAuthorInformationButton(btn_author);
    }

    public void setUpAuthorInformationButton(Button btn_author) {
        btn_author.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Display the author's information
                        Toast.makeText(v.getContext(), "to configure", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return authors.size();
    }


}
