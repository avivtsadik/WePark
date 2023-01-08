package services;


import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class GoogleLoginService {
    public static final GoogleLoginService _instance = new GoogleLoginService();
    public static GoogleLoginService instance() {
        return _instance;
    }

    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;

    public void SignInWithGoogle(Activity activity) {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(activity, gso);
    }

    public GoogleSignInAccount getGoogleAccount(Activity activity) {
        return GoogleSignIn.getLastSignedInAccount(activity);
    }

    public void SignOutFromGoogle() {
        gsc.signOut();
    }

    public Intent getSignInIntent() {
        return gsc.getSignInIntent();
    }

}
