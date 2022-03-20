package cm.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    private DrawerLayout drawerLayout;
    private boolean mModeSilent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);
        if(useDarkTheme) {
            setTheme(R.style.Theme_AppCompat_Custom);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navView2);
        navigationView.setNavigationItemSelectedListener(this);

        TextView txtLang = findViewById(R.id.txtLang);
        Switch txtDark = findViewById(R.id.txtDark);
        TextView txtDelete = findViewById(R.id.txtDelete);
        TextView txtAbout = findViewById(R.id.txtAbout);

        txtDark.setChecked(useDarkTheme);

        txtLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String languageToLoad;
                if (Locale.getDefault().getLanguage().matches("pt"))
                    languageToLoad = "en";
                else languageToLoad = "pt";
                Locale.setDefault(new Locale(languageToLoad));
                getResources().updateConfiguration(new Configuration(), getResources().getDisplayMetrics());
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.change), Toast.LENGTH_SHORT).show();
                recreate();
                Intent mainActivity = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });


        txtDark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                editor.putBoolean(PREF_DARK_THEME, isChecked);
                editor.apply();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
              //  db.deleteUser(sharedPreferences.getInt("ID", 0));
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.delete), Toast.LENGTH_LONG).show();
                Intent loginActivity = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(loginActivity);
            }
        });
        txtAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aboutActivity = new Intent(SettingsActivity.this, AboutActivity.class);
                startActivity(aboutActivity);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_item_one: {
                Intent profileActivity = new Intent(SettingsActivity.this, ProfileActivity.class);
                profileActivity.putExtra("string", getResources().getText(R.string.profile));
                startActivity(profileActivity);
                break;
            }
            case R.id.nav_item_two: {
                Intent mapActivity = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(mapActivity);
                break;
            }
            case R.id.nav_item_three: {
                Intent historyActivity = new Intent(SettingsActivity.this, FreshNewsActivity.class);
                startActivity(historyActivity);
                break;
            }
            case R.id.nav_item_four: {
                Intent commentsActivity = new Intent(SettingsActivity.this, DonationsActivity.class);
                startActivity(commentsActivity);
                break;
            }
            case R.id.nav_item_five: {
                Toast.makeText(this, getResources().getString(R.string.current), Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.nav_item_six: {
                Toast.makeText(this, getResources().getString(R.string.logout), Toast.LENGTH_SHORT).show();
                getSharedPreferences("LOGIN", MODE_PRIVATE).edit().clear().apply();
                Intent loginActivity = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(loginActivity);
                onDestroy();
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
