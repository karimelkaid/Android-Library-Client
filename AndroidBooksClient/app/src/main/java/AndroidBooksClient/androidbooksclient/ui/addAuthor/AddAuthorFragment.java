package AndroidBooksClient.androidbooksclient.ui.addAuthor;

import static AndroidBooksClient.androidbooksclient.Utils.navigateTo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import AndroidBooksClient.androidbooksclient.R;
import AndroidBooksClient.androidbooksclient.SharedViewModel;

public class AddAuthorFragment extends Fragment {
    AddAuthorViewModel _addAuthorViewModel;
    SharedViewModel _sharedViewModel;

    TextView _tvFirstname;
    TextView _tvLastname;
    Button _btnAddAuthor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_author, container, false);

        findComponents(view);
        _addAuthorViewModel = new ViewModelProvider(requireActivity()).get(AddAuthorViewModel.class);
        _sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        setUpAddAuthorButton();

        // This piece of code will be executed when an author has been added to the API, to notify
        // the AuthorsFragment that an author has been added so that they can update their UI.
        _addAuthorViewModel.get_authorAddedMutableLiveData().observe(getViewLifecycleOwner(), authorAdded -> {
            if (_sharedViewModel.getLoading() && authorAdded != null) {
                Toast.makeText(getContext(), "Author added successfully", Toast.LENGTH_SHORT).show();
                _sharedViewModel.setAuthorAddedMutableLiveData(authorAdded);
                navigateTo(getActivity(), R.id.action_navigation_add_author_fragment_to_navigation_authors);
            } else {
                Toast.makeText(getContext(), "Author not added", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    /*
        setUpAddAuthorButton : proc :
            Calls the ViewModel method for adding a book, giving it the JSON to supply in the request
        Parameter(s) :
            void
        Return :
            void
     */
    private void setUpAddAuthorButton() {
        this._btnAddAuthor.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _sharedViewModel.setLoading(true);
                        JSONObject authorJSONObject = createAuthorJSONObject();
                        try {
                            _addAuthorViewModel.addAuthor(authorJSONObject);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );
    }

    /*
        createAuthorJSONObject : func :
            Creates a JSON object from the author's first name and last name (to be given in the request)
        Parameter(s) :
            void
        Return :
            JSONObject : The JSON object created
     */
    private JSONObject createAuthorJSONObject() {
        JSONObject authorJSONObject = new JSONObject();
        try {
            authorJSONObject.put("firstname", this._tvFirstname.getText().toString());
            authorJSONObject.put("lastname", this._tvLastname.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authorJSONObject;
    }

    /*
        findComponents : proc :
            Find the components in the view
        Parameter(s) :
            view : View : The view to find the components in
        Return :
            void
     */
    private void findComponents(View view) {
        this._tvFirstname = view.findViewById(R.id.tv_firstname);
        this._tvLastname = view.findViewById(R.id.tv_lastname);
        this._btnAddAuthor = view.findViewById(R.id.btnAddAuthor);
    }
}
