package AndroidBooksClient.androidbooksclient.ui.book_informations;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import AndroidBooksClient.androidbooksclient.Model.Author;
import AndroidBooksClient.androidbooksclient.R;
import AndroidBooksClient.androidbooksclient.SharedViewModel;
import AndroidBooksClient.androidbooksclient.ui.authors.AuthorsViewModel;
import AndroidBooksClient.androidbooksclient.ui.books.BooksViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookInformation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookInformation extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button btn_back;
    Button btn_delete_book;
    TextView tv_id;
    TextView tv_title;
    TextView tv_publication_year;
    TextView tv_author_id;

    private int book_id;
    private String book_title;
    private int publication_year;
    private int author_id;

    BooksViewModel booksViewModel;
    AuthorsViewModel authorsViewModel;
    SharedViewModel sharedViewModel;
    BookInformationsViewModel bookInformationsViewModel;

    public BookInformation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookInformation.
     */
    // TODO: Rename and change types and number of parameters
    public static BookInformation newInstance(String param1, String param2) {
        BookInformation fragment = new BookInformation();
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
        View view = inflater.inflate(R.layout.fragment_book_information, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        findComponents(view);
        //getBookInformations();
        setUpBackButton();

        setUpDeleteBookButton(sharedViewModel.getSelectedBookId().getValue());
        // Updating text views with the book information
        bookInformationsViewModel = new ViewModelProvider(requireActivity()).get(BookInformationsViewModel.class);
        //int bookId = sharedViewModel.getSelectedBookId().getValue();
        bookInformationsViewModel.setBookMutableLiveData(sharedViewModel.getSelectedBook().getValue());
        //bookInformationsViewModel.loadBook(bookId);
        bookInformationsViewModel.getBookMutableLiveData().observe(getViewLifecycleOwner(), book -> {
            this.tv_id.setText("ID : "+book.getId());
            this.tv_title.setText("Title : "+book.getTitle());
            if(book.getPublication_year() != -1){
                this.tv_publication_year.setText("Publication year : "+book.getPublication_year());
            }
            else{
                this.tv_publication_year.setText("No specified publication year");
            }
            this.tv_author_id.setText("Author ID : "+book.getAuthorId());
        });

        //updateBookInformation();


        return view;
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
        btn_back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        navigateTo(R.id.action_navigation_bookInformation_to_navigation_books);
                    }
                }
        );
    }

    /*
        getBookInformations : proc :
            Retrieves the book informations from the shared preferences
        Parameter(s) :
            None
        Return :
            void
    */
    public void getBookInformations() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("BookInfo", Context.MODE_PRIVATE);
        //Integer idSelectedBook = sharedViewModel.getSelectedBook().getValue();


        this.book_id = sharedPreferences.getInt("id", -1);
        this.book_title = sharedPreferences.getString("title", "");
        this.publication_year = sharedPreferences.getInt("publication_year", -1);
        this.author_id = sharedPreferences.getInt("author_id", -1);
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
        btn_back = view.findViewById(R.id.btn_back_book_information);

        tv_id = view.findViewById(R.id.tv_bood_id);
        tv_title = view.findViewById(R.id.tv_book_title);
        tv_publication_year = view.findViewById(R.id.tv_publication_year);
        tv_author_id = view.findViewById(R.id.tv_author_of_book_id);

        btn_delete_book = view.findViewById(R.id.btn_delete_book);
    }

    /*
        updateBookInformation : proc :
            Updates the book information in the text views
        Parameter(s) :
            None
        Return :
            void
    */
    public void updateBookInformation() {
        tv_id.setText( "ID : " + this.book_id );
        tv_title.setText("Title : "+ this.book_title);
        tv_publication_year.setText( "Publication year : " + this.publication_year );
        tv_author_id.setText( "Author ID : " + this.author_id );
    }

    /*
        setUpDeleteBookButton : proc :
            Sets up the delete book button to delete the book
        Parameter(s) :
            bookId : int : The id of the book to delete
        Return :
            void
    */
    public void setUpDeleteBookButton(int bookId){
        btn_delete_book.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*booksViewModel.deleteBook(bookId);
                        booksViewModel.getBookUpdated().observe(getViewLifecycleOwner(), book -> {
                            if(book != null){
                                authorsViewModel.deleteBookOfAuthor(book);

                                Author author = authorsViewModel.getAuthor(book.getAuthorId());
                                sharedViewModel.setSelectedAuthor(author.getId());
                            }

                        });*/
                        bookInformationsViewModel.deleteBook(sharedViewModel.getBookDeletedIdMutableLiveData());    // 
                        navigateTo(R.id.action_navigation_bookInformation_to_navigation_books);
                    }
                }
        );
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
