package cm.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class FreshNewsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);
        if(useDarkTheme) {
            setTheme(R.style.Theme_AppCompat_Custom);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresh_news);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navView2);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_item_one: {
                Intent profileActivity = new Intent(FreshNewsActivity.this, ProfileActivity.class);
                profileActivity.putExtra("string", getResources().getText(R.string.profile));
                startActivity(profileActivity);
                break;
            }
            case R.id.nav_item_two: {
                Intent mapActivity = new Intent(FreshNewsActivity.this, MainActivity.class);
                startActivity(mapActivity);
                break;
            }
            case R.id.nav_item_three: {
                Toast.makeText(this, getResources().getString(R.string.current), Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.nav_item_four: {
                Intent commentsActivity = new Intent(FreshNewsActivity.this, DonationsActivity.class);
                startActivity(commentsActivity);
                break;
            }
            case R.id.nav_item_five: {
                Intent settingsActivity = new Intent(FreshNewsActivity.this, SettingsActivity.class);
                startActivity(settingsActivity);
                break;
            }
            case R.id.nav_item_six: {
                Toast.makeText(this, getResources().getString(R.string.logout), Toast.LENGTH_SHORT).show();
                getSharedPreferences("LOGIN", MODE_PRIVATE).edit().clear().apply();
                Intent loginActivity = new Intent(FreshNewsActivity.this, LoginActivity.class);
                startActivity(loginActivity);
                onDestroy();
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
