package cm.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import fr.ganfra.materialspinner.MaterialSpinner;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    /*services*/
    private static final String SERVICE_URL = "http://host/api/";
    /* Constantes para facilitar o acesso aos nomes dos valores a guardar */
    private static final String VALUE_ID = "ID";
    private static final String VALUE_NAME = "NAME";
    private static final String VALUE_CONTACT = "CONTACT";
    private static final String VALUE_PASS = "PASSWORD";

    private static MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static ArrayList<String> country, parentage;
    private final OkHttpClient client = new OkHttpClient();
    SharedPreferences.Editor editor;
    NavigationView navigationView;
    private SharedPreferences sharedPreferences;
    /*spinner*/
    private MaterialSpinner spinnerCountry, spinnerParentage;
    private HashMap<Integer, String> mapCountry, mapParentage;
    private ArrayAdapter<String> adapterCountry, adapterParentage;
    private EditText editName, editContact, editPass;
    private DatePicker date;
    private SimpleDateFormat dateFormat;
    private int countryPos, parentagePos, id;
    private String name, contact, pass, dateStr, selectedItemCountry, selectedItemParentage, str;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);
        if (useDarkTheme) {
            setTheme(R.style.Theme_AppCompat_Custom);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent n = getIntent();
        str = n.getStringExtra("string");
        TextView txt = findViewById(R.id.txtTitle);
        txt.setText(str);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        if (str.contentEquals(getResources().getText(R.string.profile))) {
            toggle.syncState();
        }
        navigationView = findViewById(R.id.navView2);
        navigationView.setNavigationItemSelectedListener(this);

        sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
        country = new ArrayList<>();
        parentage = new ArrayList<>();
        mapCountry = new HashMap<>();
        mapParentage = new HashMap<>();
        editName = findViewById(R.id.input_name);
        editContact = findViewById(R.id.input_contact);
        editPass = findViewById(R.id.input_password);

        if (str.contentEquals(getResources().getText(R.string.profile))) {
            id = sharedPreferences.getInt(VALUE_ID, 0);
            name = sharedPreferences.getString(VALUE_NAME, "");
            contact = sharedPreferences.getString(VALUE_CONTACT, "");
            pass = sharedPreferences.getString(VALUE_PASS, "");
            //Date l = new Date(sharedPreferences.getLong(VALUE_DATE, 0));
            editName.setText(name);
            editContact.setText(contact);
            editPass.setText(pass);
            //date.updateDate(l.getYear(), l.getMonth(), l.getDay());
        }

        Button btn = findViewById(R.id.buttonSave);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    Intent activity;
                    activity = new Intent(ProfileActivity.this, MainActivity.class);
                    startActivity(activity);

            }
        });
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_item_one: {
                Toast.makeText(this, getResources().getText(R.string.current), Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.nav_item_two: {
                Intent mainActivity = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(mainActivity);
                break;
            }
            case R.id.nav_item_three: {
                Intent historyActivity = new Intent(ProfileActivity.this, FreshNewsActivity.class);
                startActivity(historyActivity);
                break;
            }
            case R.id.nav_item_four: {
                Intent commentActivity = new Intent(ProfileActivity.this, DonationsActivity.class);
                startActivity(commentActivity);
                break;
            }
            case R.id.nav_item_five: {
                Intent settingsActivity = new Intent(ProfileActivity.this, SettingsActivity.class);
                startActivity(settingsActivity);
                break;
            }
            case R.id.nav_item_six: {
                Toast.makeText(this, getResources().getString(R.string.logout), Toast.LENGTH_SHORT).show();
                Intent loginActivity = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(loginActivity);
                onDestroy();
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}

