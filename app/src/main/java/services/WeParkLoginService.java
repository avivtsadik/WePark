package services;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fragments.loginFragments.LoginFragment;

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

    public interface SigningListener {
        void onSuccess();

        void onFailed();
    }

    public void signIn(String email, String password, SigningListener listener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("TAG", "user: " + user);
                            LoginService.instance().setLoginService(WeParkLoginService.instance());
                            listener.onSuccess();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            listener.onFailed();
                        }
                    }
                });
    }

    public void signUp(String email, String password, SigningListener listener) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "createUserWithEmail:success");
                            listener.onSuccess();
                        } else {
                            Log.d("TAG", "createUserWithEmail:failure", task.getException());
                            listener.onFailed();
                        }
                    }
                });
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
