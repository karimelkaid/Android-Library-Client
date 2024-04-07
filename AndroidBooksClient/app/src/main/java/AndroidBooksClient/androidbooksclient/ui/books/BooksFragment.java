package AndroidBooksClient.androidbooksclient.ui.books;

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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import AndroidBooksClient.androidbooksclient.Model.Book;
import AndroidBooksClient.androidbooksclient.OnItemClickListener;
import AndroidBooksClient.androidbooksclient.R;
import AndroidBooksClient.androidbooksclient.SharedViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BooksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BooksFragment extends Fragment implements OnItemClickListener {

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
    private SharedViewModel sharedViewModel;
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
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        //bookAdapter = new BookAdapter(booksViewModel.getBooks().getValue());
        booksViewModel.getBooks().observe(getViewLifecycleOwner(), books -> {
            //onItemClicked(0);
            // Instanciate the adapter with the list of books when the list is updated (because request is asynchronous)
            bookAdapter = new BookAdapter(books, this, sharedViewModel);
            // Set the books in the adapter
            //bookAdapter.setBooks(books);

            // Set the adapter to the RecyclerView
            recyclerView.setAdapter(bookAdapter);
        });

        sharedViewModel.getBookToAddMutableLiveData().observe(getViewLifecycleOwner(), newBook -> {
            if( newBook != null ){
                booksViewModel.addBookToList(newBook);
                sharedViewModel.setBookToAddMutable(null);  // To prevent the code from re-triggering after returning to this fragment without having added a book
                Toast.makeText(getContext(), "Book added !", Toast.LENGTH_SHORT).show();
            }
        });

        sharedViewModel.getBookDeletedIdMutableLiveData().observe(getViewLifecycleOwner(), bookDeletedId -> {
            if( bookDeletedId != -1 ){
                booksViewModel.deleteBookLocally(bookDeletedId);
                sharedViewModel.setBookDeletedIdMutable(-1);
                Toast.makeText(getContext(), "Book deleted !", Toast.LENGTH_SHORT).show();
            }
        });

        findComponents(view);
        setUpFab();

        return view;
    }

    /*
        setUpFab : proc :
            Sets up the floating action button
        Parameter(s) :
            None
        Return :
            void
     */
    public void setUpFab() {
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
                        navigateTo(R.id.action_navigation_books_to_navigation_addBook);
                    }
                }
        );
    }

    /*
        findComponents : proc :
            Find the components in the view
        Parameter(s) :
            view : View : The view to find the components in
        Return :
            void
     */
    public void findComponents(View view){
        fab = view.findViewById(R.id.fab);
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

    @Override
    public void onItemClicked(int position) {
        //Toast.makeText(getContext(), "Book clicked : "+sharedViewModel.getSelectedBook().getValue(), Toast.LENGTH_SHORT).show();
        int bookSelectedId = sharedViewModel.getSelectedBookId().getValue();
        Book book_selected = bookAdapter.getBook(bookSelectedId);
        if( book_selected != null){
            sharedViewModel.setSelectedBook(book_selected);
            navigateTo(R.id.action_navigation_books_to_navigation_bookInformation);
        }
        else{
            Toast.makeText(getContext(), "Book not found", Toast.LENGTH_SHORT).show();
        }
    }
}
