package AndroidBooksClient.androidbooksclient.ui.author_informations;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import AndroidBooksClient.androidbooksclient.R;
import AndroidBooksClient.androidbooksclient.SharedViewModel;
import AndroidBooksClient.androidbooksclient.Utils;

public class AuthorInformationFragment extends Fragment {
    private AuthorInformationsViewModel _authorInformationsViewModel;
    private AuthorBooksViewModel _authorBooksViewModel;
    private SharedViewModel _sharedViewModel;

    private TextView _tvAuthorId;
    private TextView _tvAuthorFirstName;
    private TextView _tvAuthorLastName;
    private Button _btnDeleteAuthor;
    private Button _btnBackToAuthors;
    private RecyclerView _rvBooksOfAuthor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_author_information, container, false);
        findComponents(view);
        _rvBooksOfAuthor.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Retrieve ViewModels
        _authorInformationsViewModel = new ViewModelProvider(requireActivity()).get(AuthorInformationsViewModel.class);
        _sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        _authorBooksViewModel = new ViewModelProvider(requireActivity()).get(AuthorBooksViewModel.class);

        int authorId = _sharedViewModel.get_selectedAuthor().getValue(); // Get the author ID to display from the shared view model

        setUpBackToAuthorsButton();
        setUpDeleteButton(authorId);

        _authorInformationsViewModel.getAuthorDeleted().observe(getViewLifecycleOwner(), authorDeleted -> {
            if( _sharedViewModel.get_loading() ){
                if( authorDeleted == true ){
                    _sharedViewModel.setReloadAuthors(true);
                    _sharedViewModel.setReloadBooks(true);
                    _authorInformationsViewModel.setAuthorDeleted(false);
                      Utils.navigateTo(getContext(), R.id.action_navigation_author_informations_to_navigation_authors2);
                }
            }
        });

        // Load the author to display and his books
        _authorInformationsViewModel.loadAuthorToDisplay(authorId);
        _authorBooksViewModel.loadBooksOfAuthor(authorId);

        // Once the author has been loaded, display the author's information (without his books because they are loaded separately in a RecyclerView)
        _authorInformationsViewModel.get_authorLiveData().observe(getViewLifecycleOwner(), author -> {
            if (author != null) {
                _tvAuthorId.setText("ID: " + author.getId());
                _tvAuthorFirstName.setText("First Name: " + author.getFirst_name());
                _tvAuthorLastName.setText("Last Name: " + author.getLast_name());
            }
        });

        // Once the books of the author have been loaded, display them in the RecyclerView
        _authorBooksViewModel.get_booksOfAuthorLiveData().observe(getViewLifecycleOwner(), books -> {
            _rvBooksOfAuthor.setAdapter(new BookOfAuthorAdapter(books, _sharedViewModel));
        });

        return view;
    }

    /*
        setUpBackToAuthorsButton : proc :
            Sets up the back to authors button to navigate back to the authors fragment
        Parameter(s) :
            void
        Return :
            void
    */
    private void setUpBackToAuthorsButton() {
        _btnBackToAuthors.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.navigateTo(getContext(), R.id.action_navigation_author_informations_to_navigation_authors2);
                    }
                }
        );
    }

    /*
        setUpDeleteButton : proc :
            Sets up the delete button to delete an author
        Parameter(s) :
            int authorId : the ID of the author to delete
        Return :
            void
    */
    private void setUpDeleteButton(int authorId) {
        _btnDeleteAuthor.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _sharedViewModel.set_loading(true);
                        _sharedViewModel.set_authorLastNameToBeDeleted(_authorInformationsViewModel.get_authorLiveData().getValue().getLast_name());
                        _authorInformationsViewModel.deleteAuthor(authorId);
                    }
                }
        );
    }

    /*
        findComponents : proc :
            Find the components in the view
        Parameter(s) :
            View view : the view in which to find the components
        Return :
            void
    */
    private void findComponents(View view) {
        _tvAuthorId = view.findViewById(R.id.tv_author_id);
        _tvAuthorFirstName = view.findViewById(R.id.tv_author_first_name);
        _tvAuthorLastName = view.findViewById(R.id.tv_author_last_name);
        _rvBooksOfAuthor = view.findViewById(R.id.rv_books_of_author);
        _btnDeleteAuthor = view.findViewById(R.id.btn_delete_author);
        _btnBackToAuthors = view.findViewById(R.id.btn_back_to_authors);
    }
}
