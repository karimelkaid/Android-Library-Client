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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AuthorsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuthorsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AuthorAdapter authorAdapter;
    private SharedViewModel sharedViewModel;
    private AuthorsViewModel authorsViewModel;
    private FloatingActionButton fab;

    public AuthorsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AuthorsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AuthorsFragment newInstance(String param1, String param2) {
        AuthorsFragment fragment = new AuthorsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_authors, container, false);

        findComponents(view);
        // Retrieving the AuthorsViewModel
        authorsViewModel = new ViewModelProvider(requireActivity()).get(AuthorsViewModel.class);

        // Retrieving the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.rv_authors);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Retrieving the SharedViewModel to give for the AuthorAdapter
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        setUpFab();

        // Setting the adapter when the authorsLiveData changes
        authorsViewModel.get_authors_live_data().observe(getViewLifecycleOwner(), authors -> {
            authorAdapter = new AuthorAdapter(authors, sharedViewModel);
            recyclerView.setAdapter(authorAdapter);
        });

        //To update the list of books by an author when a book is added (using the book's authorId)
        sharedViewModel.getBookToAddMutableLiveData().observe(getViewLifecycleOwner(), bookAdded -> {
            if( bookAdded !=null ){
                if( !authorsViewModel.getAuthor(bookAdded.getAuthorId()).getBooks().contains(bookAdded) ){
                    authorsViewModel.addBookToAuthor(bookAdded);
                }
            }
        });

        // We check whether an author has been added in AddAuthorFragment to update the list of livedata authors.
        sharedViewModel.getAuthorAddedMutableLiveData().observe(getViewLifecycleOwner(), authorAdded -> {
            if (authorAdded != null) {
                authorsViewModel.addAuthorToList(authorAdded);
            }
        });

        return view;
    }

    private void findComponents(View view) {
        this.fab = view.findViewById(R.id.fabAddAuthor);
    }

    public void setUpFab() {
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
                        navigateTo(getActivity(), R.id.action_navigation_authors_to_navigation_add_author_fragment3);
                    }
                }
        );
    }

}
