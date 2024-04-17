package AndroidBooksClient.androidbooksclient.ui.books;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.Book;
import AndroidBooksClient.androidbooksclient.R;
import AndroidBooksClient.androidbooksclient.SharedViewModel;
import AndroidBooksClient.androidbooksclient.Utils;


public class BooksFragment extends Fragment {


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
        _sharedViewModel.get_reloadBooksLiveData().observe(getViewLifecycleOwner(), reloadBooks -> {
            if( reloadBooks ){
                _booksViewModel.loadData();
            }
        });

        // When the list of books changes (books are loaded or reloaded), refresh the list of books in the RecyclerView
        _booksViewModel.getBooks().observe(getViewLifecycleOwner(), books -> {
            if (books == null) {
                return; // Return immediately if the list of books is null
            }

            if (!_sharedViewModel.get_reloadBooksLiveData().getValue()) {
                setupBookAdapter(books); // Set up the adapter with books if reload is not needed
            } else {
                _sharedViewModel.setReloadBooks(false); // Reset the book reload state
                _sharedViewModel.set_loading(false);
                setupBookAdapter(books); // Set up the adapter again with the updated list of books

                if( _sharedViewModel.getPreviousFragmentIsBookInformation() ){
                    // To display a Toast that a book has been deleted only if the previous fragment is the details of a book (because books can also be deleted by deleting an author)
                    Toast.makeText(getContext(), "Book '"+_sharedViewModel.get_bookTitleToBeDeleted()+"' deleted !", Toast.LENGTH_SHORT).show();
                    _sharedViewModel.setPreviousFragmentIsBookInformation(false);   // Reset the state
                }
            }

        });


        // We check whether a book has been added in AddBookFragment to update the list of books
        _sharedViewModel.get_bookToAddMutableLiveData().observe(getViewLifecycleOwner(), newBook -> {
            if( newBook != null && _sharedViewModel.get_loading() ){
                _booksViewModel.addBookToList(newBook);
                _sharedViewModel.setBookToAddMutable(null);
                _sharedViewModel.set_loading(false);    // To prevent the code from re-triggering after returning to this fragment without having added a book
                Toast.makeText(getContext(), "Book '"+newBook.getTitle()+"' added !", Toast.LENGTH_SHORT).show();
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
        _bookAdapter = new BookAdapter(books, _sharedViewModel); // Instancie l'adaptateur avec la liste de livres
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
                        Utils.navigateTo(v.getContext(), R.id.action_navigation_books_to_navigation_addBook);
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


}
