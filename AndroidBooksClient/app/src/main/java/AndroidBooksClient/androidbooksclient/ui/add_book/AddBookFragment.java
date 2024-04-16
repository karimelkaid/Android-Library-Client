package AndroidBooksClient.androidbooksclient.ui.add_book;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import AndroidBooksClient.androidbooksclient.R;
import AndroidBooksClient.androidbooksclient.SharedViewModel;
import AndroidBooksClient.androidbooksclient.Utils;


public class AddBookFragment extends Fragment {
    private Button _btn_add_book;
    private EditText _et_book_title;
    private EditText _et_book_publication_year;
    private EditText _et_book_author_id;

    private SharedViewModel _sharedViewModel;
    private AddBookViewModel _addBookViewModel;
    private int _authorId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        // Getting ViewModels
        _sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        _addBookViewModel = new ViewModelProvider(requireActivity()).get(AddBookViewModel.class);

        findComponents(view);
        setUpAddBookButton();

        // Once a book has been added to the API, it can be added locally (in BooksViewModel) without reloading the list of books.
        _addBookViewModel.getBookToAddMutableLiveData().observe(getViewLifecycleOwner(), bookAdded -> {
            // Given that there are several fragments observing the same object, after adding an object, if you come to this fragment this code will be launched to add a new book similar to the previous one
            // To solve this problem, check that you are currently making changes (using getLoading)
            if( bookAdded != null && _sharedViewModel.getLoading() ){
                _sharedViewModel.setBookToAddMutable(bookAdded);
                Utils.navigateTo(getActivity(), R.id.action_navigation_addBook_to_navigation_books);; // Go to the books fragment because there is no need to stay in the add book fragment after adding a book
            }
        });

        return view;
    }



    /*
        setUpAddBookButton : proc :
            Sets up the add book button to add a book to the server and locally
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
                        _sharedViewModel.setLoading(true);

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
                        _authorId = Integer.parseInt(_et_book_author_id.getText().toString());

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

                        // Adding the book to the server
                        try {
                            _addBookViewModel.addBook(_authorId, book_json_object);
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
}
