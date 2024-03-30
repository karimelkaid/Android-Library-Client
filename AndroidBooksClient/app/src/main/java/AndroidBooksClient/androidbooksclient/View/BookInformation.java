package AndroidBooksClient.androidbooksclient.View;

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

import AndroidBooksClient.androidbooksclient.ViewModel.BooksViewModel;
import AndroidBooksClient.androidbooksclient.R;

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
    TextView tv_book_id;
    TextView tv_book_title;
    TextView tv_book_author;

    private int bookId;
    private String bookTitle;
    private String author;

    BooksViewModel booksViewModel;

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

        booksViewModel = new ViewModelProvider(requireActivity()).get(BooksViewModel.class);

        findComponents(view);
        getBookInformations();
        btn_back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                        navController.navigate(R.id.action_navigation_bookInformation_to_navigation_books);
                    }
                }
        );

        setUpDeleteBookButton(this.bookId);




        // Updating text views with the book information
        updateBookInformation();


        return view;
    }

    public void getBookInformations() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("BookInfo", Context.MODE_PRIVATE);

        this.bookId = sharedPreferences.getInt("id", -1);
        this.bookTitle = sharedPreferences.getString("title", "");
        this.author = sharedPreferences.getString("author", "");
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

        tv_book_id = view.findViewById(R.id.tv_bood_id);
        tv_book_title = view.findViewById(R.id.tv_book_title);
        tv_book_author = view.findViewById(R.id.tv_author_name);
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

        tv_book_id.setText( "ID : " + this.bookId );
        tv_book_title.setText("Title : "+ this.bookTitle);
        tv_book_author.setText( "Author\'s name : " + this.author );
    }

    public void setUpDeleteBookButton(int bookId){
        btn_delete_book.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        booksViewModel.deleteBook(bookId);
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
