package fragments.mainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.wepark.R;

public class EditParkingFragment extends Fragment {
    public EditParkingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_parking, container, false);

        String parkingId = EditParkingFragmentArgs.fromBundle(getArguments()).getParkingId();

        Spinner spinnerLanguages= view.findViewById(R.id.cityspinner);
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(view.getContext(), R.array.Cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerLanguages.setAdapter(adapter);

        Spinner spinnerLanguages3= view.findViewById(R.id.sizespinner);
//        ArrayAdapter<CharSequence>adapter3=ArrayAdapter.createFromResource(view.getContext(), R.array.city, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerLanguages3.setAdapter(adapter);

        Button saveBtn = view.findViewById(R.id.savebtn);

        saveBtn.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_editParkingFragment_to_fragment_my_posts_nav_graph);
        });

        return view;
    }
}