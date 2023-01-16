package fragments.mainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.wepark.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.UUID;

import models.Parking;
import models.ParkingMock;
import services.LoginService;

public class AddParkingFragment extends Fragment {
    private Button addParkingButton;
    private Spinner sizeSpinner;
    private Spinner citySpinner;
    private ImageView imageView;

    public AddParkingFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_parking, container, false);

        addParkingButton = view.findViewById(R.id.addParkingButton);
        sizeSpinner = view.findViewById(R.id.sizespinner);
        citySpinner = view.findViewById(R.id.cityspinner);
        imageView = view.findViewById(R.id.imageView);

        ArrayAdapter<CharSequence> sizeSpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.ParkingSize, android.R.layout.simple_spinner_item);
        sizeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sizeSpinner.setAdapter(sizeSpinnerAdapter);

        ArrayAdapter<CharSequence> citySpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.Cities, android.R.layout.simple_spinner_item);
        citySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        citySpinner.setAdapter(citySpinnerAdapter);

        addParkingButton.setOnClickListener(view1 -> {
            try {
                String id = UUID.randomUUID().toString();
                String userId = LoginService.instance().getLoginService().getUserId();
                String size = sizeSpinner.getSelectedItem().toString();
                String city = citySpinner.getSelectedItem().toString();

                Parking parking = new Parking(id, userId, city, size);
                ParkingMock.instance().addParkingLot(parking, () -> {
                    Toast.makeText(getContext(), "Parking Added Successfully", Toast.LENGTH_SHORT).show();
                });
            } catch (Exception e) {
                Toast.makeText(getContext(), "Missing values", Toast.LENGTH_SHORT).show();
            }

        });

        return view;
    }
}