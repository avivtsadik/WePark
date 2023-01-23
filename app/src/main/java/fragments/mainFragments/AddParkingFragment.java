package fragments.mainFragments;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import models.Interfaces.OnActionDoneListener;
import models.Parking;
import models.ParkingMock;
import services.LoginService;

public class AddParkingFragment extends Fragment {
    private String parkingId;
    private Button addParkingButton;
    private Button addImageButton;
    private Button removeImageButton;
    private Spinner sizeSpinner;
    private AutoCompleteTextView cityAutoComplete;
    private ImageView imageView;
    private String[] citiesArray;
    ActivityResultLauncher cameraAppLauncher;
    Boolean isUserAddImage;

    public AddParkingFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parkingId = UUID.randomUUID().toString();
        citiesArray = getCities();
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
        cityAutoComplete = view.findViewById(R.id.autoCompleteCity);
        imageView = view.findViewById(R.id.imageView);

        ArrayAdapter<CharSequence> sizeSpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.ParkingSize, R.layout.list_item);
        sizeSpinnerAdapter.setDropDownViewResource(R.layout.list_item);
        sizeSpinner.setAdapter(sizeSpinnerAdapter);

        ArrayAdapter<String> cityAutoCompleteAdapter = new ArrayAdapter(getContext(), R.layout.list_item, citiesArray);
        cityAutoComplete.setDropDownHeight(600);
        cityAutoComplete.setAdapter(cityAutoCompleteAdapter);

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
                String city = cityAutoComplete.getText().toString();

                if (!Arrays.stream(citiesArray).anyMatch(str -> str.equals(city))) {
                    cityAutoComplete.setError("choose from here");
                    throw new IOException("City not from list");
                }

                if (!isUserAddImage) throw new Exception("Did not added image");

                imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                Parking parking = new Parking(parkingId, userId, city, size, "");
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

    public String[] getCities() {
        List<String> items = new ArrayList();
        AssetManager am = getContext().getAssets();
        try {
            InputStream inputStream = am.open("cities.txt");
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String str_line;

            while ((str_line = buffer.readLine()) != null) {
                str_line = str_line.trim();
                if ((str_line.length() != 0)) {
                    items.add(str_line);
                }
            }

            return items.toArray(new String[items.size()]);

        } catch (IOException e) {
            return new String[0];
        }
    }
}
