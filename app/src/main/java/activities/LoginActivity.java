package activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.wepark.R;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fragments.loginFragments.LoginFragment;
import services.GoogleLoginService;
import services.LoginService;
import services.WeParkLoginService;

public class LoginActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser weParkUser = WeParkLoginService.instance().getWeParkUser();
        GoogleSignInAccount googleUser = GoogleLoginService.instance().getGoogleAccount(this);

        if (weParkUser != null || googleUser != null) {
            if (weParkUser != null) {
                LoginService.instance().setLoginService(WeParkLoginService.instance());
            } else {
                LoginService.instance().setLoginService(GoogleLoginService.instance());
            }
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WeParkLoginService.instance().setFirebaseInstance(FirebaseAuth.getInstance());
        setContentView(R.layout.activity_login);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameContainer, new LoginFragment(), "LOGIN_FRAGMENT");
        ft.commit();
    }

    @Override
    public void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.frameContainer, fragment);
        ft.commit();
    }
}