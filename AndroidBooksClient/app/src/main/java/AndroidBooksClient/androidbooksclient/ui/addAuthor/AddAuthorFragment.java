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


    TextView tv_firstname;
    TextView tv_lastname;
    Button btn_add_author;
    AddAuthorViewModel addAuthorViewModel;
    SharedViewModel sharedViewModel;
    private boolean loading = false;    // To prevent unintentional execution of api actions

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_author, container, false);

        findComponents(view);
        addAuthorViewModel = new ViewModelProvider(requireActivity()).get(AddAuthorViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);


        setUpAddAuthorButton();

        addAuthorViewModel.getAuthorAddedMutableLiveData().observe(getViewLifecycleOwner(), authorAdded -> {
            if (authorAdded != null) {
                if(sharedViewModel.getLoading() && addAuthorViewModel.getThereIsAuthorToAdd()){
                    Toast.makeText(getContext(), "Author added successfully", Toast.LENGTH_SHORT).show();
                    sharedViewModel.setAuthorAddedMutableLiveData(authorAdded);
                    addAuthorViewModel.setThereIsAuthorToAdd(false);
                    navigateTo(getActivity(), R.id.action_navigation_add_author_fragment_to_navigation_authors);
                }
            } else {
                Toast.makeText(getContext(), "Author not added", Toast.LENGTH_SHORT).show();
            }
        });
        //Toast.makeText(getContext(), "Add Author Fragment", Toast.LENGTH_SHORT).show();

        return view;
    }

    private void setUpAddAuthorButton() {
        this.btn_add_author.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sharedViewModel.setLoading(true);
                        addAuthorViewModel.setThereIsAuthorToAdd(true);
                        JSONObject authorJSONObject = createAuthorJSONObject();
                        try {
                            addAuthorViewModel.addAuthor(authorJSONObject);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );
    }

    private JSONObject createAuthorJSONObject() {
        JSONObject authorJSONObject = new JSONObject();
        try {
            authorJSONObject.put("firstname", this.tv_firstname.getText().toString());
            authorJSONObject.put("lastname", this.tv_lastname.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authorJSONObject;
    }

    private void findComponents(View view) {
        this.tv_firstname = view.findViewById(R.id.tv_firstname);
        this.tv_lastname = view.findViewById(R.id.tv_lastname);
        this.btn_add_author = view.findViewById(R.id.btnAddAuthor);
    }
}
