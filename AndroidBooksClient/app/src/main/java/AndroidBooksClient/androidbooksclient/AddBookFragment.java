package AndroidBooksClient.androidbooksclient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddBookFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button _btn_add_book;
    private EditText _et_book_title;
    private EditText _et_book_author;

    private BooksViewModel booksViewModel;

    public AddBookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddBookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddBookFragment newInstance(String param1, String param2) {
        AddBookFragment fragment = new AddBookFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        // Getting the ViewModel
        booksViewModel = new ViewModelProvider(requireActivity()).get(BooksViewModel.class);

        findComponents(view);
        /*_btn_add_book.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Retrieving information from the EditTexts
                        String title = _et_book_title.getText().toString();
                        String author = _et_book_author.getText().toString();

                        // Creating a new book object
                        Book newBook = new Book(BooksViewModel.getNextId(), title, author, "a description");

                        // Adding the book to the list of books
                        booksViewModel.addBook(newBook);

                        // Back to the previous fragment
                        navigateTo(R.id.action_navigation_addBook_to_navigation_books);
                    }
                }
        );*/

        return view;
    }

    /*
    findComponents : proc :
        Finds the components of the layout
    Parameter(s) :
        view : View : The view to find the components in
    Return :
        void
    */
    private void findComponents(View view) {
        // Retrieving the layout components for this fragment
        _btn_add_book = view.findViewById(R.id.btn_add_book);
        _et_book_title = view.findViewById(R.id.et_title);
        _et_book_author = view.findViewById(R.id.et_author);
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
