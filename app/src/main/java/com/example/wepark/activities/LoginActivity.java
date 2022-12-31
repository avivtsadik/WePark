package com.example.wepark.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.wepark.R;

public class LoginActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        androidx.fragment.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameContainer,new LoginFragment(), "LOGIN_FRAGMENT");
        ft.commit();
    }

    @Override
    public void changeFragment() {
        androidx.fragment.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        LoginFragment myFragment = (LoginFragment)getSupportFragmentManager().findFragmentByTag("LOGIN_FRAGMENT");
        if (myFragment != null && myFragment.isVisible()) {
            ft.replace(R.id.frameContainer,new SignUpFragment(), "SIGNUP_FRAGMENT");
            ft.commit();
        }else{
            ft.replace(R.id.frameContainer,new LoginFragment(), "LOGIN_FRAGMENT");
            ft.commit();
        }
    }
}