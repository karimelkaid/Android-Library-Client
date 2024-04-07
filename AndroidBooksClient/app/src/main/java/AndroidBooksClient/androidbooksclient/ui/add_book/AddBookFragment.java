package AndroidBooksClient.androidbooksclient.ui.add_book;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import AndroidBooksClient.androidbooksclient.Model.Book;
import AndroidBooksClient.androidbooksclient.R;
import AndroidBooksClient.androidbooksclient.SharedViewModel;
import AndroidBooksClient.androidbooksclient.ui.authors.AuthorsViewModel;
import AndroidBooksClient.androidbooksclient.ui.books.BooksViewModel;

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
    private EditText _et_book_publication_year;
    private EditText _et_book_author_id;

    private SharedViewModel sharedViewModel;
    private AddBookViewModel addBookViewModel;
    private Observer<Book> bookUpdateObserver;
    private int authorId;

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
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        addBookViewModel = new ViewModelProvider(requireActivity()).get(AddBookViewModel.class);

        findComponents(view);
        setUpAddBookButton();

        /*bookUpdateObserver = book -> {
            if (book != null) {
                authorsViewModel.updateAuthorsLiveData(authorId, book);

                booksViewModel.getBookUpdated().removeObserver(this.bookUpdateObserver);
                booksViewModel.setBookAdded(null);
                navigateTo(R.id.action_navigation_addBook_to_navigation_books);
            }
        };

        booksViewModel.getBookUpdated().observe(getViewLifecycleOwner(), bookUpdateObserver);*/

        addBookViewModel.getBookToAddMutableLiveData().observe(getViewLifecycleOwner(), bookAdded -> {
            // Given that there are several fragments observing the same object, after adding an object, if you come to this fragment this code will be launched to add a new book similar to the previous one
            // To solve this problem, check that you really want to add a book
            if( addBookViewModel.getThereIsBookToAdd() ){
                // To Edit the list of books in BooksViewModel
                Toast.makeText(getContext(), "book to add changed", Toast.LENGTH_SHORT).show();
                sharedViewModel.setSelectedBook(bookAdded);
                addBookViewModel.setThereIsBookToAdd(false);
                navigateTo(R.id.action_navigation_addBook_to_navigation_books);
            }
        });


        return view;
    }



    /*
        setUpAddBookButton : proc :
            Sets up the add book button
        Parameter(s) :
            none
        Return :
            void
    */
    public void setUpAddBookButton() {
        _btn_add_book.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addBookViewModel.setThereIsBookToAdd(true);
                        // Retrieving information from the EditTexts
                        String title = _et_book_title.getText().toString();
                        int publicationYear;
                        if( !_et_book_publication_year.getText().toString().equals("") )
                        {
                            publicationYear = Integer.parseInt(_et_book_publication_year.getText().toString());
                        }
                        else{
                            publicationYear = -1;
                        }
                        authorId = Integer.parseInt(_et_book_author_id.getText().toString());

                        // Creating a JSON object to send to the server
                        JSONObject book_json_object = new JSONObject();
                        try {
                            book_json_object.put("title", title);
                            // If the publication year is not -1, it is specified by the user so we add it to the JSON object
                            if( publicationYear != -1 ){
                                book_json_object.put("publication_year", publicationYear);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        //booksViewModel.setUpdateOrNot(true);

                        // Adding the book to the server and locally
                       // booksViewModel.addBook(authorId, book_json_object);

                        try {
                            addBookViewModel.addBook(authorId, book_json_object);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
        );
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
        _et_book_publication_year = view.findViewById(R.id.et_publication_year);
        _et_book_author_id = view.findViewById(R.id.et_author_id);
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
