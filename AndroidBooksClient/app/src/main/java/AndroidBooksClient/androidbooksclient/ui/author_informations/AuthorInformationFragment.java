package AndroidBooksClient.androidbooksclient.ui.author_informations;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import AndroidBooksClient.androidbooksclient.Model.Author;
import AndroidBooksClient.androidbooksclient.R;
import AndroidBooksClient.androidbooksclient.SharedViewModel;

public class AuthorInformationFragment extends Fragment {

    private TextView tvAuthorId;
    private TextView tvAuthorFirstName;
    private TextView tvAuthorLastName;
    private RecyclerView rvBooksOfAuthor;
    private AuthorInformationsViewModel viewModel;
    private SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_author_information, container, false);
        findComponents(view);
        rvBooksOfAuthor.setLayoutManager(new LinearLayoutManager(getActivity()));

        viewModel = new ViewModelProvider(requireActivity()).get(AuthorInformationsViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        //int authorId = 3;
        int authorId = sharedViewModel.getSelectedAuthor().getValue();

        //Toast.makeText(getContext(), "Author ID: " + authorId, Toast.LENGTH_SHORT).show();
        viewModel.loadAuthorToDisplay(authorId);

        viewModel.getAuthorLiveData().observe(getViewLifecycleOwner(), author -> {
            if (author != null) {
                Toast.makeText(getContext(), "author "+author.getId()+" selected change", Toast.LENGTH_SHORT).show();
                tvAuthorId.setText("ID: " + author.getId());
                tvAuthorFirstName.setText("First Name: " + author.getFirst_name());
                tvAuthorLastName.setText("Last Name: " + author.getLast_name());
                rvBooksOfAuthor.setAdapter(new BookOfAuthorAdapter(author.getBooks(), sharedViewModel));
            }
        });

        return view;
    }

    private void findComponents(View view) {
        tvAuthorId = view.findViewById(R.id.tv_author_id);
        tvAuthorFirstName = view.findViewById(R.id.tv_author_first_name);
        tvAuthorLastName = view.findViewById(R.id.tv_author_last_name);
        rvBooksOfAuthor = view.findViewById(R.id.rv_books_of_author);
    }
}
