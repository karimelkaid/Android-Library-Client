package AndroidBooksClient.androidbooksclient.ui.author_informations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import AndroidBooksClient.androidbooksclient.Model.Book;
import AndroidBooksClient.androidbooksclient.R;
import AndroidBooksClient.androidbooksclient.SharedViewModel;
import AndroidBooksClient.androidbooksclient.Utils;

public class BookOfAuthorAdapter extends RecyclerView.Adapter<BookOfAuthorViewHolder> {
    List<Book> _booksOfAuthor;
    SharedViewModel _sharedViewModel;

    public BookOfAuthorAdapter(List<Book> books_of_author, SharedViewModel sharedViewModel) {
        _booksOfAuthor = books_of_author;
        _sharedViewModel = sharedViewModel;
    }

    @NonNull
    @Override
    public BookOfAuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_of_author_view_holder, parent, false);
        return new BookOfAuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookOfAuthorViewHolder holder, int position) {
        Book current_book = _booksOfAuthor.get(position);
        holder.get_tv_book_of_author_title().setText("#"+(position+1)+" : "+current_book.getTitle());

        // Once a book is clicked, save its informations in the shared preferences and navigate to the BookInformationFragment to display the book's informations
        holder.get_tv_book_of_author_title().setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _sharedViewModel.set_selectedBookId(current_book.getId());   // Save the book's id in the shared view model
                        _sharedViewModel.setPreviousFragmentIsAuthorInformation(true);
                        Utils.navigateTo(v.getContext(), R.id.action_navigation_author_informations_to_navigation_bookInformation); // Navigate to the BookInformationFragment
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return _booksOfAuthor.size();
    }
}
