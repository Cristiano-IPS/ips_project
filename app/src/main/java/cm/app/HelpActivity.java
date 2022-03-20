package cm.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class HelpActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
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
        setContentView(R.layout.activity_help);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navView2);
        navigationView.setNavigationItemSelectedListener(this);
        TextView txtHelp = findViewById(R.id.helpid);
        txtHelp.setText(MainActivity.helptype);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_item_one: {
                Intent profileActivity = new Intent(HelpActivity.this, ProfileActivity.class);
                profileActivity.putExtra("string", getResources().getText(R.string.profile));
                startActivity(profileActivity);
                break;
            }
            case R.id.nav_item_two: {
                Intent mapActivity = new Intent(HelpActivity.this, MainActivity.class);
                startActivity(mapActivity);
                break;
            }
            case R.id.nav_item_three: {
                Intent historyActivity = new Intent(HelpActivity.this, FreshNewsActivity.class);
                startActivity(historyActivity);
                break;
            }
            case R.id.nav_item_four: {
                Intent settingsActivity = new Intent(HelpActivity.this, DonationsActivity.class);
                startActivity(settingsActivity);
                break;
            }
            case R.id.nav_item_five: {
                Intent settingsActivity = new Intent(HelpActivity.this, SettingsActivity.class);
                startActivity(settingsActivity);
                break;
            }
            case R.id.nav_item_six: {
                Toast.makeText(this, getResources().getString(R.string.logout), Toast.LENGTH_SHORT).show();
                getSharedPreferences("LOGIN", MODE_PRIVATE).edit().clear().apply();
                Intent loginActivity = new Intent(HelpActivity.this, LoginActivity.class);
                startActivity(loginActivity);
                onDestroy();
                break;
            }


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
