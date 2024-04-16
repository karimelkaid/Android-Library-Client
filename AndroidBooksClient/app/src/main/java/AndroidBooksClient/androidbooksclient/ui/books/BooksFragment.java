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

import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.Book;
import AndroidBooksClient.androidbooksclient.OnItemClickListener;
import AndroidBooksClient.androidbooksclient.R;
import AndroidBooksClient.androidbooksclient.SharedViewModel;


public class BooksFragment extends Fragment implements OnItemClickListener {


    private RecyclerView _recyclerView;
    private BookAdapter _bookAdapter;
    private BooksViewModel _booksViewModel;
    private SharedViewModel _sharedViewModel;
    private FloatingActionButton _fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_books, container, false);

        _recyclerView = view.findViewById(R.id.rv_books);
        _recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));   // Setting the layout manager for the RecyclerView

        findComponents(view);
        setUpFab();

        // ViewModels
        _sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        _booksViewModel = new ViewModelProvider(requireActivity()).get(BooksViewModel.class);

        // We check whether we need to send a request to retrieve all the books (if a book has been deleted)
        _sharedViewModel.getReloadBooksLiveData().observe(getViewLifecycleOwner(), reloadBooks -> {
            if( reloadBooks ){
                _booksViewModel.loadData();
            }
        });

        // When the list of books changes (books are loaded), refresh the list of books in the RecyclerView
        _booksViewModel.getBooks().observe(getViewLifecycleOwner(), books -> {
            if (books == null) {
                return; // Return immediately if the list of books is null
            }

            if (!_sharedViewModel.getReloadBooksLiveData().getValue()) {
                setupBookAdapter(books); // Set up the adapter with books if reload is not needed
            } else {
                _sharedViewModel.setReloadBooks(false); // Reset the book reload state
                _sharedViewModel.setLoading(false);
                setupBookAdapter(books); // Set up the adapter again with the updated list of books
            }

        });


        // We check whether a book has been added in AddBookFragment to update the list of books
        _sharedViewModel.getBookToAddMutableLiveData().observe(getViewLifecycleOwner(), newBook -> {
            if( newBook != null && _sharedViewModel.getLoading() ){
                _booksViewModel.addBookToList(newBook);
                _sharedViewModel.setBookToAddMutable(null);
                _sharedViewModel.setLoading(false);    // To prevent the code from re-triggering after returning to this fragment without having added a book
                Toast.makeText(getContext(), "Book added !", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    /*
        setupBookAdapter : proc :
            Sets up the book adapter and the RecyclerView with the list of books
        Parameter(s) :
            books : List<Book> : The list of books to set up the adapter with
        Return :
            void
     */
    private void setupBookAdapter(List<Book> books) {
        _bookAdapter = new BookAdapter(books, this, _sharedViewModel); // Instancie l'adaptateur avec la liste de livres
        _recyclerView.setAdapter(_bookAdapter); // Définit l'adaptateur pour le RecyclerView
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
        _fab.setOnClickListener(
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
        _fab = view.findViewById(R.id.fab);
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
        int bookSelectedId = _sharedViewModel.getSelectedBookId().getValue();
        Book book_selected = _bookAdapter.getBook(bookSelectedId);
        if( book_selected != null){
            _sharedViewModel.setSelectedBook(book_selected);
            navigateTo(R.id.action_navigation_books_to_navigation_bookInformation);
        }
        else{
            Toast.makeText(getContext(), "Book not found", Toast.LENGTH_SHORT).show();
        }
    }
}
