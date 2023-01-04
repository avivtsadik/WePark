package activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.wepark.R;
import fragments.mainFragments.HomeFragment;
import fragments.mainFragments.ManageFavoritesFragment;
import fragments.mainFragments.MyPostsFragment;
import fragments.mainFragments.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView logoutTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        loadFragment(new HomeFragment());

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                item.setChecked(true);
                switch (id) {
                    case R.id.optionHome: {
                        loadFragment(new HomeFragment());
                        toolbar.setTitle(R.string.app_name);
                        break;
                    }
                    case R.id.optionManageFavorites: {
                        loadFragment(new ManageFavoritesFragment());
                        toolbar.setTitle(R.string.manageFavorites);
                        break;
                    }
                    case R.id.optionMyPosts: {
                        loadFragment(new MyPostsFragment());
                        toolbar.setTitle(R.string.myPosts);
                        break;
                    }
                    case R.id.optionProfile: {
                        loadFragment(new ProfileFragment());
                        toolbar.setTitle(R.string.profile);
                        break;
                    }
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        logoutTextView = findViewById(R.id.logoutBtn);

        logoutTextView.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.container, fragment);
        ft.commit();

    }
}