package fragments.mainFragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.wepark.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import models.Interfaces.OnActionDoneListener;
import models.Parking;
import models.ParkingMock;
import services.LoginService;

public class EditParkingFragment extends Fragment {
    private String parkingId;
    private Button addImageButton;
    private Button removeImageButton;
    private Spinner sizeSpinner;
    private TextView cityTextView;
    private ImageView imageView;
    private Parking parking;
    ActivityResultLauncher cameraAppLauncher;
    ArrayAdapter<CharSequence> sizeSpinnerAdapter;
    Boolean isUserAddImage;

    public EditParkingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_parking, container, false);

        addImageButton = view.findViewById(R.id.addImageButton);
        removeImageButton = view.findViewById(R.id.removeImageButton);
        sizeSpinner = view.findViewById(R.id.sizespinner);
        cityTextView = view.findViewById(R.id.cityTextView);
        imageView = view.findViewById(R.id.imageView);
        Button saveParkingBtn = view.findViewById(R.id.savebtn);
        Button cancelbtn = view.findViewById(R.id.cancelbtn);

        parkingId = EditParkingFragmentArgs.fromBundle(getArguments()).getParkingId();

        ParkingMock.instance().getParkingLot(parkingId, newParking -> {
            parking = newParking;
            refreshFrame();
        });

        sizeSpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.ParkingSize, R.layout.list_item);
        sizeSpinnerAdapter.setDropDownViewResource(R.layout.list_item);
        sizeSpinner.setAdapter(sizeSpinnerAdapter);

        addImageButton.setOnClickListener(view2 -> {
            cameraAppLauncher.launch(null);
        });

        removeImageButton.setOnClickListener(view2 -> {
            imageView.setImageBitmap(null);
            isUserAddImage = false;
        });

        cancelbtn.setOnClickListener(view1 -> {
            Navigation.findNavController(view).popBackStack();
        });

        saveParkingBtn.setOnClickListener(view1 -> {
            try {
                String userId = LoginService.instance().getLoginService().getUserId();
                String size = sizeSpinner.getSelectedItem().toString();
                String city = cityTextView.getText().toString();

                if (!isUserAddImage) throw new Exception("Did not edit image");

                imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                parking = new Parking(parkingId, userId, city, size, "");
                ParkingMock.instance().uploadParkingLotImage(bitmap, parkingId, new OnActionDoneListener<String>() {
                    @Override
                    public void onComplete(String uri) {
                        if (uri != null) {
                            parking.setAvatarUrl(uri);
                        }
                        ParkingMock.instance().addParkingLot(parking, (unused) -> {
                            Toast.makeText(getContext(), "Parking Added Successfully", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view).popBackStack();
                        });
                    }
                });

            } catch (IOException e) {

            } catch (Exception e) {
                Toast.makeText(getContext(), "Missing values", Toast.LENGTH_SHORT).show();
            }

        });

        return view;
    }

    public void refreshFrame() {
        int sizePosition = sizeSpinnerAdapter.getPosition(parking.getSize());
        sizeSpinner.setSelection(sizePosition);
        cityTextView.setText(parking.getCity());
        Picasso.get().load(parking.getAvatarUrl()).placeholder(R.drawable.avatar).into(imageView);
        isUserAddImage = true;
    }

}