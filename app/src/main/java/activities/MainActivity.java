package activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.wepark.R;

import fragments.mainFragments.HomeFragment;
import fragments.mainFragments.ManageFavoritesFragment;
import fragments.mainFragments.MyPostsFragment;
import fragments.mainFragments.ProfileFragment;
import services.LoginService;

import com.google.android.material.navigation.NavigationView;

import java.util.Set;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    AppBarConfiguration appBarConfiguration;
    NavController navController;
    Toolbar toolbar;
    TextView logoutTextView;
    TextView userName;
    TextView userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_navhost);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.optionHome);

        View navigationHeader = navigationView.getHeaderView(0);
        userName = navigationHeader.findViewById(R.id.userName);
        userEmail = navigationHeader.findViewById(R.id.userEmail);

        userName.setText(LoginService.instance().getLoginService().getUserName());
        userEmail.setText(LoginService.instance().getLoginService().getUserEmail());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        logoutTextView = findViewById(R.id.logoutBtn);

        logoutTextView.setOnClickListener(view -> {
            LoginService.instance().getLoginService().signOut();

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id != navigationView.getCheckedItem().getItemId()) {
            NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.fragment_home_nav_graph, false).build();
            item.setChecked(true);
            switch (id) {
                case R.id.optionHome: {
                    navController.navigate(R.id.fragment_home_nav_graph, null, navOptions);
                    break;
                }
                case R.id.optionManageFavorites: {
                    navController.navigate(R.id.fragment_manage_favorites_nav_graph, null, navOptions);
                    break;
                }
                case R.id.optionMyPosts: {
                    navController.navigate(R.id.fragment_my_posts_nav_graph, null, navOptions);
                    break;
                }
                case R.id.optionProfile: {
                    navController.navigate(R.id.fragment_profile_nav_graph, null, navOptions);
                    break;
                }
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.main_navhost, fragment);
        ft.commit();
    }
}
