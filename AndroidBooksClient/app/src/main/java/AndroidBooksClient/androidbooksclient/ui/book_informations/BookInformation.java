package AndroidBooksClient.androidbooksclient.ui.book_informations;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import AndroidBooksClient.androidbooksclient.R;
import AndroidBooksClient.androidbooksclient.SharedViewModel;
import AndroidBooksClient.androidbooksclient.Utils;

public class BookInformation extends Fragment {

    SharedViewModel _sharedViewModel;
    BookInformationsViewModel _bookInformationsViewModel;
    BookTagsViewModel _bookTagsViewModel;

    Button _btnBack;
    Button _btnDeleteBook;
    TextView _tvId;
    TextView _tvTitle;
    TextView _tvPublicationYear;
    TextView _tvAuthorId;

    RecyclerView _rvTags;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_information, container, false);


        _sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        _bookInformationsViewModel = new ViewModelProvider(requireActivity()).get(BookInformationsViewModel.class);
        _bookTagsViewModel = new ViewModelProvider(requireActivity()).get(BookTagsViewModel.class);

        findComponents(view);
        resetTextViews(view);
        _rvTags.setLayoutManager(new LinearLayoutManager(getContext()));

        setUpBackButton();
        setUpDeleteBookButton(_sharedViewModel.get_selectedBookId().getValue());


        int bookId = _sharedViewModel.get_selectedBookId().getValue();
        _bookInformationsViewModel.loadBookToDisplay(bookId);
        _bookTagsViewModel.loadTags(bookId);

        // When the book is loaded, update the UI
        _bookInformationsViewModel.get_bookMutableLiveData().observe(getViewLifecycleOwner(), book -> {
            if( book == null ){
                return;
            }

            this._tvId.setText("ID : "+book.getId());
            this._tvTitle.setText("Title : "+book.getTitle());
            if(book.getPublication_year() != -1){
                this._tvPublicationYear.setText("Publication year : "+book.getPublication_year());
            }
            else{
                this._tvPublicationYear.setText("No specified publication year");
            }
            _sharedViewModel.set_loading(true);
            _bookInformationsViewModel.loadAuthor(book.getAuthorId());  // Load the author of the book to display his name and not his id
        });

        // When the author's name of the book is loaded, update the UI
        _bookInformationsViewModel.get_authorNameLiveData().observe(getViewLifecycleOwner(), authorOfBook -> {
            if( authorOfBook != null && _sharedViewModel.get_loading() ){
                this._tvAuthorId.setText("Author : " + Utils.toInitCap(authorOfBook.getFirst_name()) + " " + authorOfBook.getLast_name().toUpperCase());
                _sharedViewModel.set_loading(false);
            }
        });

        // When the tags are loaded, update the RecyclerView with the tags
        _bookTagsViewModel.getTagsMutableLiveData().observe(getViewLifecycleOwner(), tags -> {
            if(tags != null){
                TagAdapter tagAdapter = new TagAdapter(tags);
                _rvTags.setAdapter(tagAdapter);
            }
        });


        // When the book is deleted, save the book id which has been deleted (to update the UI in the Books fragment) and navigate to the Books fragment
        _bookInformationsViewModel.get_bookDeletedLiveData().observe(getViewLifecycleOwner(), bookDeleted -> {
            if(_sharedViewModel.get_loading() && bookDeleted){
                _sharedViewModel.setReloadBooks(true);
                Utils.navigateTo(getActivity(), R.id.action_navigation_bookInformation_to_navigation_books);
            }
        });

        return view;
    }

    private void resetTextViews(View view) {
        _tvId.setText("ID : ");
        _tvTitle.setText("Title : ");
        _tvPublicationYear.setText("Publication year : ");
        _tvAuthorId.setText("Author : ");
    }

    /*
        setUpBackButton : proc :
            Sets up the back button to navigate to the Books fragment
        Parameter(s) :
            None
        Return :
            void
    */
    public void setUpBackButton() {
        _btnBack.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _bookInformationsViewModel.set_authorNameLiveData(null);
                        _bookInformationsViewModel.set_bookMutableLiveData(null);
                        if( _sharedViewModel.getPreviousFragmentIsBooks() ){
                            Utils.navigateTo(v.getContext(), R.id.action_navigation_bookInformation_to_navigation_books);
                            _sharedViewModel.setPreviousFragmentIsBooks(false);
                        }
                        else if( _sharedViewModel.getPreviousFragmentIsAuthorInformation() ){
                            Utils.navigateTo(v.getContext(), R.id.action_navigation_bookInformation_to_navigation_author_informations);
                            _sharedViewModel.setPreviousFragmentIsAuthorInformation(false);
                        }
                        else{
                            Toast.makeText(getContext(), "Error : No previous fragment found", Toast.LENGTH_SHORT).show();
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
        _btnBack = view.findViewById(R.id.btn_back_book_information);

        _tvId = view.findViewById(R.id.tv_bood_id);
        _tvTitle = view.findViewById(R.id.tv_book_title);
        _tvPublicationYear = view.findViewById(R.id.tv_publication_year);
        _tvAuthorId = view.findViewById(R.id.tv_author_of_book_id);

        _btnDeleteBook = view.findViewById(R.id.btn_delete_book);
        _rvTags = view.findViewById(R.id.rv_tags_of_book);
    }

    /*
        setUpDeleteBookButton : proc :
            Sets up the delete book button to delete the book in the API
        Parameter(s) :
            bookId : int : The id of the book to delete
        Return :
            void
    */
    public void setUpDeleteBookButton(int bookId){
        _btnDeleteBook.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _sharedViewModel.set_loading(true);
                        _sharedViewModel.set_bookTitleToBeDeleted(_bookInformationsViewModel.get_bookMutableLiveData().getValue().getTitle());   // To show users the title of the book they have deleted.
                        _sharedViewModel.setPreviousFragmentIsBookInformation(true);    // To display a Toast that a book has been deleted only if the previous fragment is the details of a book (because books can also be deleted by deleting an author)
                        _bookInformationsViewModel.deleteBook(bookId);
                    }
                }
        );
    }
}
