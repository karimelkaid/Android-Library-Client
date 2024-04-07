package AndroidBooksClient.androidbooksclient.ui.addAuthor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import AndroidBooksClient.androidbooksclient.R;

public class AddAuthorFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_author, container, false);

        //Toast.makeText(getContext(), "Add Author Fragment", Toast.LENGTH_SHORT).show();

        return view;
    }
}