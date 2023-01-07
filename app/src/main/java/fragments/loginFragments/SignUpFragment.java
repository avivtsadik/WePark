package fragments.loginFragments;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.wepark.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import activities.OnFragmentInteractionListener;

public class SignUpFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Auth
        Activity activity = getActivity();
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity
                    + " must implement OnFragmentInteractionListener");
        }
        FragmentActivity parentActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        TextView logInBtn = view.findViewById(R.id.textView2);
        logInBtn.setOnClickListener(view1 -> {
            mListener.loadFragment(new LoginFragment());
        });

        Button signupbtn = view.findViewById(R.id.signupbtn2);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                createUser(view);
            }
        });
        return view;
    }

    private void createUser(View view) {
        EditText emailObj = (EditText) view.findViewById(R.id.editTextLoginEmailAddress);
        EditText passwordObj = (EditText) view.findViewById(R.id.editTextLoginPassword);
        String email = emailObj.getText().toString();
        String password = passwordObj.getText().toString();
        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailObj.setError("Email is badly formatted");
            emailObj.requestFocus();
        } else if (password.length() < 6) {
            passwordObj.setError("Password should be at least 6 characters");
            passwordObj.requestFocus();
        } else {
            mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "createUserWithEmail:success");
                                Toast.makeText(view.getContext(), "Signup success",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                Log.d("TAG", "user: " + user);
                                mListener.loadFragment(new LoginFragment());
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.d("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(view.getContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                Log.d("TAG", "Failed: ");
                            }
                        }
                    });
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}