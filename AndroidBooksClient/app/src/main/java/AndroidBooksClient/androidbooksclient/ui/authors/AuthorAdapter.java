package AndroidBooksClient.androidbooksclient.ui.authors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.Author;
import AndroidBooksClient.androidbooksclient.R;
import AndroidBooksClient.androidbooksclient.SharedViewModel;
import AndroidBooksClient.androidbooksclient.Utils;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorViewHolder> {
    List<Author> _authors;
    private SharedViewModel _sharedViewModel;

    public AuthorAdapter(List<Author> authors, SharedViewModel sharedViewModel) {
        this._authors = authors;
        this._sharedViewModel = sharedViewModel;
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_author_view_holder, parent, false);
        return new AuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, int position) {
        Author current_author = _authors.get(position); // Get the current author to display

        Button btn_author = holder.get_btn_author_name();   // Get the button to display the author's name
        btn_author.setText(current_author.getLast_name());  // Set the author's name in the button

        setUpAuthorInformationButton(btn_author, current_author);   // Set up the button to display the author's information
    }

    /*
        setUpAuthorInformationButton : proc :
            Set up the button to display the author's information when clicked on it to navigate
            to save the author ID select in the shared view model and navigate to the
            AuthorInformationFragment to display the author's informations
        Parameter(s) :
            Button btn_author : The button to display the author's information
            Author author : The author to display
        Return :
            void
     */
    public void setUpAuthorInformationButton(Button btn_author, Author author) {
        btn_author.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _sharedViewModel.setSelectedAuthor(author.getId());
                        Utils.navigateTo(v.getContext(), R.id.action_navigation_authors_to_navigation_author_informations);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return _authors.size();
    }


}
