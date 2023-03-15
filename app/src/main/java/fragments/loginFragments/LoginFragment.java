package fragments.loginFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wepark.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.LinkedList;

import activities.MainActivity;
import activities.OnFragmentInteractionListener;
import models.Interfaces.OnActionDoneListener;
import models.User;
import models.UserMock;
import services.GoogleLoginService;
import services.LoginService;
import services.WeParkLoginService;

public class LoginFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Button signupBtn = view.findViewById(R.id.signupBtn);
        SignInButton googleSignIn = view.findViewById(R.id.googleSignIn);

        signupBtn.setOnClickListener(view1 -> {
            mListener.loadFragment(new SignUpFragment());
        });

        Button loginBtn = view.findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                loginUser(view);
            }
        });

        GoogleLoginService.instance().signIn(getActivity());

        googleSignIn.setOnClickListener(view1 -> {
            this.SignInWithGoogle();
        });

        return view;
    }

    private void loginUser(View view) {
        EditText emailObj = view.findViewById(R.id.editTextLoginEmailAddress);
        EditText passwordObj = view.findViewById(R.id.editTextLoginPassword);
        String email = emailObj.getText().toString();
        String password = passwordObj.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailObj.setError("Email cannot be empty");
            emailObj.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            passwordObj.setError("Password cannot be empty");
            passwordObj.requestFocus();
        } else {
            WeParkLoginService.instance().signIn(email, password, new WeParkLoginService.SigningListener() {
                @Override
                public void onSuccess() {
                    fetchUserData(data -> {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    });
                }

                @Override
                public void onFailed() {
                    Toast.makeText(view.getContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void SignInWithGoogle() {
        Intent intent = GoogleLoginService.instance().getSignInIntent();
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                LoginService.instance().setLoginService(GoogleLoginService.instance());
                fetchUserData(data1 -> {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                });
            } catch (ApiException e) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void fetchUserData(OnActionDoneListener<User> listener) {
        String userId = LoginService.instance().getLoginService().getUserId();

        UserMock.instance().getExistingUser(userId, user -> {
            UserMock.instance().updateFavorites(user, data -> {
                listener.onComplete(user);
            });
        });
    }
}