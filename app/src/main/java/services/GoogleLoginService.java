package services;


import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class GoogleLoginService extends AbstractLoginService {
    public static final GoogleLoginService _instance = new GoogleLoginService();
    public static GoogleLoginService instance() {
        return _instance;
    }

    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private Activity currentActivity;

    private GoogleLoginService() {
    }

    public void signIn(Activity activity) {
        currentActivity = activity;
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(activity, gso);
    }

    public GoogleSignInAccount getGoogleAccount(Activity activity) {
        this.currentActivity = activity;
        return GoogleSignIn.getLastSignedInAccount(activity);
    }

    public void signOut() {
        gsc.signOut();
    }

    public Intent getSignInIntent() {
        return gsc.getSignInIntent();
    }

    @Override
    public String getUserId() {
        return GoogleSignIn.getLastSignedInAccount(this.currentActivity).getId();
    }

    @Override
    public String getUserName() {
        return GoogleSignIn.getLastSignedInAccount(this.currentActivity).getDisplayName();
    }

    @Override
    public String getUserEmail() {
        return GoogleSignIn.getLastSignedInAccount(this.currentActivity).getEmail();
    }
}
