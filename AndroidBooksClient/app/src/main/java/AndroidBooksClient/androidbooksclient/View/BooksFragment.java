package AndroidBooksClient.androidbooksclient.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import AndroidBooksClient.androidbooksclient.ViewModel.BooksViewModel;
import AndroidBooksClient.androidbooksclient.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BooksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BooksFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private BooksViewModel booksViewModel;
    private FloatingActionButton fab;


    public BooksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BooksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BooksFragment newInstance(String param1, String param2) {
        BooksFragment fragment = new BooksFragment();
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
        View view = inflater.inflate(R.layout.fragment_books, container, false);

        recyclerView = view.findViewById(R.id.rv_books);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));   // Setting the layout manager for the RecyclerView

        // ViewModel
        booksViewModel = new ViewModelProvider(requireActivity()).get(BooksViewModel.class);
        //bookAdapter = new BookAdapter(booksViewModel.getBooks().getValue());
        booksViewModel.getBooks().observe(getViewLifecycleOwner(), books -> {
            // Instanciate the adapter with the list of books when the list is updated (because request is asynchronous)
            bookAdapter = new BookAdapter(books);

            // Set the books in the adapter
            bookAdapter.setBooks(books);

            // Set the adapter to the RecyclerView
            recyclerView.setAdapter(bookAdapter);
        });
        /*
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
                        navigateTo(R.id.action_navigation_books_to_navigation_addBook);
                    }
                }
        );*/


        return view;
    }

    /*
        navigateTo : proc :
            Navigates to the specified action
        Parameter(s) :
            actionId : int : The id of the action to navigate to
        Return :
            void
    */
    public void navigateTo(int actionId) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(actionId);
    }

}
