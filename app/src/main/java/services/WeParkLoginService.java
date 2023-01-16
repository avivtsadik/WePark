package services;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WeParkLoginService extends AbstractLoginService {
    private FirebaseAuth mAuth;
    public static final WeParkLoginService _instance = new WeParkLoginService();

    public static WeParkLoginService instance() {
        return _instance;
    }

    private WeParkLoginService() {
    }

    public FirebaseUser getWeParkUser() {
        return mAuth.getCurrentUser();
    }

    public void setFirebaseInstance(FirebaseAuth mAuthInstance) {
        mAuth = mAuthInstance;
    }

    @Override
    public void signOut() {
        mAuth.signOut();
    }

    @Override
    public String getUserId() {
        return mAuth.getCurrentUser().getUid();
    }

    @Override
    public String getUserName() {
        return mAuth.getCurrentUser().getEmail().split("@")[0];
    }

    @Override
    public String getUserEmail() {
        return mAuth.getCurrentUser().getEmail();
    }
}
