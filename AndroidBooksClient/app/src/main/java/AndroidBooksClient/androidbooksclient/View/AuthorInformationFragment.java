package AndroidBooksClient.androidbooksclient.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import AndroidBooksClient.androidbooksclient.R;
import AndroidBooksClient.androidbooksclient.ViewModel.AuthorsViewModel;
import AndroidBooksClient.androidbooksclient.ViewModel.SharedViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AuthorInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuthorInformationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView tv_author_id;
    private TextView tv_author_first_name;
    private TextView tv_author_last_name;


    public AuthorInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AuthorInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AuthorInformationFragment newInstance(String param1, String param2) {
        AuthorInformationFragment fragment = new AuthorInformationFragment();
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
        View view = inflater.inflate(R.layout.fragment_author_information, container, false);

        findComponents(view);
        SharedViewModel sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);

        // Observing the selected author to display his information
        sharedViewModel.getSelectedAuthor().observe(getViewLifecycleOwner(), author -> {
            this.tv_author_id.setText("ID : " + author.getId());
            this.tv_author_first_name.setText("First Name : " + author.getFirst_name());
            this.tv_author_last_name.setText("Last Name : " + author.getLast_name());
        });

        return view;
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
        tv_author_id = view.findViewById(R.id.tv_author_id);

        tv_author_first_name = view.findViewById(R.id.tv_author_first_name);
        tv_author_last_name = view.findViewById(R.id.tv_author_last_name);
    }
}