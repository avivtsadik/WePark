package fragments.mainFragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wepark.R;

import java.io.IOException;
import java.util.Arrays;

import models.Interfaces.OnActionDoneListener;
import models.Parking;
import models.ParkingMock;
import models.User;
import models.UserMock;
import services.LoginService;

public class ProfileFragment extends Fragment {
    private Button addImageButton;
    private Button removeImageButton;
    private Button saveButton;
    private Button cancelButton;
    private ImageView imageView;
    private TextView displayNameTextView;
    private User user;
    ActivityResultLauncher cameraAppLauncher;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraAppLauncher = registerForActivityResult(new
                ActivityResultContracts.TakePicturePreview(), new
                ActivityResultCallback<Bitmap>() {
                    @Override
                    public void onActivityResult(Bitmap result) {
                        imageView.setImageBitmap(result);
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        addImageButton = view.findViewById(R.id.addImageButton);
        removeImageButton = view.findViewById(R.id.removeImageButton);

        saveButton = view.findViewById(R.id.saveButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        displayNameTextView = view.findViewById(R.id.displayNameTextView);

        UserMock.instance().getUser().observe(getViewLifecycleOwner(), user -> {
            this.user = user;
            displayNameTextView.setText(user.getDisplayName());
        });

        addImageButton.setOnClickListener(view2 -> {
            cameraAppLauncher.launch(null);
        });

        removeImageButton.setOnClickListener(view2 -> {
            imageView.setImageBitmap(null);
        });

        cancelButton.setOnClickListener(view1 -> {
            Navigation.findNavController(view).popBackStack();
        });

        saveButton.setOnClickListener(view1 -> {
            try {
                String userId = LoginService.instance().getLoginService().getUserId();
                user.setDisplayName(displayNameTextView.getText().toString());

//                imageView.setDrawingCacheEnabled(true);
//                imageView.buildDrawingCache();
//                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//
//                UserMock.instance().uploadProfileImage(bitmap, userId, new OnActionDoneListener<String>() {
//                    @Override
//                    public void onComplete(String uri) {
//                        if (uri != null) {
//                            user.setAvatarUrl(uri);
//                        }
                        UserMock.instance().updateUserData(user, (unused) -> {
                            Toast.makeText(getContext(), "Profiled saved", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view).popBackStack();
                        });
//                    }
//                });
            } catch (Exception e) {
                Toast.makeText(getContext(), "Missing values", Toast.LENGTH_SHORT).show();
            }

        });

        return view;
    }
}