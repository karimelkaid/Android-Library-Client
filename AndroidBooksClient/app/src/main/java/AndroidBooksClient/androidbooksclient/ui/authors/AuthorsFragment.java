package AndroidBooksClient.androidbooksclient.ui.authors;

import static AndroidBooksClient.androidbooksclient.Utils.navigateTo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import AndroidBooksClient.androidbooksclient.R;
import AndroidBooksClient.androidbooksclient.SharedViewModel;

public class AuthorsFragment extends Fragment {

    private SharedViewModel _sharedViewModel;
    private AuthorsViewModel _authorsViewModel;

    private AuthorAdapter _authorAdapter;
    private FloatingActionButton _fab;
    private RecyclerView _recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_authors, container, false);

        findComponents(view);
        // Retrieving ViewModels
        _authorsViewModel = new ViewModelProvider(requireActivity()).get(AuthorsViewModel.class);
        _sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        _recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // To display the authors in a grid

        setUpFab(); // Set up the floating action button to add an author

        // We check whether we need to send a request to retrieve all the authors (if an author has been deleted)
        _sharedViewModel.get_reloadAuthorsLiveData().observe(getViewLifecycleOwner(), reloadAuthors -> {
            if( reloadAuthors ){
                _authorsViewModel.load_authors_from_api();
            }
        });

        // When the list of authors changes, refresh the list of authors in the RecyclerView
        _authorsViewModel.get_authors_live_data().observe(getViewLifecycleOwner(), authors -> {
            _authorAdapter = new AuthorAdapter(authors, _sharedViewModel);
            _recyclerView.setAdapter(_authorAdapter);
            if( _sharedViewModel.get_reloadAuthorsLiveData().getValue() ){
                _sharedViewModel.setReloadAuthors(false); // We stop the reload of authors
                _sharedViewModel.set_loading(false); // We stop the loading
                Toast.makeText(getContext(), "Author '"+_sharedViewModel.getAuthorLastNameToBeDeleted()+"' deleted", Toast.LENGTH_SHORT).show();   // Display a toast message to inform the user that the author has been deleted
            }
        });

        // We check whether an author has been added in AddAuthorFragment to update the list of authorsLiveData
        _sharedViewModel.get_authorAddedMutableLiveData().observe(getViewLifecycleOwner(), authorAdded -> {
            if (authorAdded != null && _sharedViewModel.get_loading()) {
                _authorsViewModel.addAuthorToList(authorAdded);
                _sharedViewModel.set_loading(false);
                Toast.makeText(getContext(), "Author '"+authorAdded.getLast_name()+"' added", Toast.LENGTH_SHORT).show();   // Display a toast message to inform the user that the author has been added
            }
        });



        return view;
    }

    /*
        findComponents : proc :
            Find the components in the view
        Parameter(s) :
            View view : The view in which we need to find the components
        Return :
            void
     */
    private void findComponents(View view) {
        this._fab = view.findViewById(R.id.fabAddAuthor);
        _recyclerView = view.findViewById(R.id.rv_authors);
    }

    /*
        setUpFab : proc :
            Set up the floating action button to navigate to the AddAuthorFragment when clicked on it
        Parameter(s) :
            void
        Return :
            void
     */
    public void setUpFab() {
        _fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        navigateTo(getActivity(), R.id.action_navigation_authors_to_navigation_add_author_fragment3);
                    }
                }
        );
    }

}
