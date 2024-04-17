package AndroidBooksClient.androidbooksclient.ui.add_book;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.SpinnerItem;
import AndroidBooksClient.androidbooksclient.R;
import AndroidBooksClient.androidbooksclient.SharedViewModel;
import AndroidBooksClient.androidbooksclient.Utils;


public class AddBookFragment extends Fragment {
    private Button _btn_add_book;
    private EditText _et_book_title;
    private EditText _et_book_publication_year;
    private Spinner _spAuthors;

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
            if( bookAdded != null && _sharedViewModel.get_loading() ){
                _sharedViewModel.setBookToAddMutable(bookAdded);
                Utils.navigateTo(getActivity(), R.id.action_navigation_addBook_to_navigation_books);; // Go to the books fragment because there is no need to stay in the add book fragment after adding a book
            }
        });

        _sharedViewModel.loadAuthors();
        // When the authors are loaded, we fill the Spinner
        _sharedViewModel.get_authorsLiveData().observe(getViewLifecycleOwner(), authors -> {
            if( authors != null ){
                setUpSpinnerAuthors();
            }
        });

        return view;
    }

    private void setUpSpinnerAuthors() {

        List<SpinnerItem> spinnerItems = new ArrayList<>();
        for( int i = 0; i < _sharedViewModel.get_authorsLiveData().getValue().size(); i++ ){
            SpinnerItem spinnerItem = new SpinnerItem(_sharedViewModel.get_authorsLiveData().getValue().get(i).getLast_name(), _sharedViewModel.get_authorsLiveData().getValue().get(i).getId());
            spinnerItems.add(spinnerItem);
        }

        ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _spAuthors.setAdapter(adapter);

        _spAuthors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItem item = (SpinnerItem) parent.getSelectedItem();
                int value = item.getValue();
                _authorId = value;  // We save the author id to add the book to this author who is selected in the spinner
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
                        _sharedViewModel.set_loading(true);

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
        _spAuthors = view.findViewById(R.id.sp_authors);
    }
}
