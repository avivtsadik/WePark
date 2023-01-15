package fragments.mainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.wepark.R;
import com.google.firebase.auth.FirebaseAuth;

import models.Parking;
import models.ParkingMock;
import models.ParkingSize;

public class AddParkingFragment extends Fragment {
    private Button addParkingButton;
    private FirebaseAuth mAuth;

    public AddParkingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_parking, container, false);

        addParkingButton = view.findViewById(R.id.addParkingButton);
        addParkingButton.setOnClickListener(view1 -> {
            mAuth = FirebaseAuth.getInstance();
            Parking parking = new Parking(123, mAuth.getUid(), "HOLON", ParkingSize.MEDIUM);
            ParkingMock.instance().addParkingLot(parking, () -> {
                Toast.makeText(getContext(), "Parking Added Successfully", Toast.LENGTH_SHORT).show();
            });
        });

        return view;
    }
}