package fragments.mainFragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.wepark.R;

import java.util.UUID;

import models.FirebaseModel;
import models.Parking;
import models.ParkingMock;
import services.LoginService;

public class AddParkingFragment extends Fragment {
    private String parkingId;
    private Button addParkingButton;
    private Button addImageButton;
    private Button removeImageButton;
    private Spinner sizeSpinner;
    private Spinner citySpinner;
    private ImageView imageView;
    ActivityResultLauncher cameraAppLauncher;
    Boolean isUserAddImage;

    public AddParkingFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parkingId = UUID.randomUUID().toString();
        isUserAddImage = false;
        cameraAppLauncher = registerForActivityResult(new
                ActivityResultContracts.TakePicturePreview(), new
                ActivityResultCallback<Bitmap>() {
                    @Override
                    public void onActivityResult(Bitmap result) {
                        imageView.setImageBitmap(result);
                        isUserAddImage = true;
                    }
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_parking, container, false);

        addParkingButton = view.findViewById(R.id.addParkingButton);
        addImageButton = view.findViewById(R.id.addImageButton);
        removeImageButton = view.findViewById(R.id.removeImageButton);
        sizeSpinner = view.findViewById(R.id.sizespinner);
        citySpinner = view.findViewById(R.id.cityspinner);
        imageView = view.findViewById(R.id.imageView);

        ArrayAdapter<CharSequence> sizeSpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.ParkingSize, android.R.layout.simple_spinner_item);
        sizeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sizeSpinner.setAdapter(sizeSpinnerAdapter);

        ArrayAdapter<CharSequence> citySpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.Cities, android.R.layout.simple_spinner_item);
        citySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        citySpinner.setAdapter(citySpinnerAdapter);

        addImageButton.setOnClickListener(view2 -> {
            cameraAppLauncher.launch(null);
        });

        removeImageButton.setOnClickListener(view2 -> {
            imageView.setImageBitmap(null);
            isUserAddImage = false;
        });

        addParkingButton.setOnClickListener(view1 -> {
            try {
                String userId = LoginService.instance().getLoginService().getUserId();
                String size = sizeSpinner.getSelectedItem().toString();
                String city = citySpinner.getSelectedItem().toString();

                if (!isUserAddImage) throw new Exception("Did not added image");

                imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                Parking parking = new Parking(parkingId, userId, city, size, "");
                ParkingMock.instance().uploadParkingLotImage(bitmap, parkingId, new FirebaseModel.UploadImageListener() {
                    @Override
                    public void onComplete(String uri) {
                        if (uri != null) {
                            parking.setAvatarUrl(uri);
                        }
                        ParkingMock.instance().addParkingLot(parking, (unused) -> {
                            Toast.makeText(getContext(), "Parking Added Successfully", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view).navigate(R.id.action_addParkingFragmentNav_to_homeFragmentNav);
                        });
                    }
                });

            } catch (Exception e) {
                Toast.makeText(getContext(), "Missing values", Toast.LENGTH_SHORT).show();
            }

        });

        return view;
    }
}