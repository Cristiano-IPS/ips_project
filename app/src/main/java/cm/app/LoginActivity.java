package cm.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class LoginActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";

    private static final String VALUE_ID = "ID";
    private static final String VALUE_NAME = "NAME";
    private static final String VALUE_CONTACT = "CONTACT";
    private static final String VALUE_PASS = "PASSWORD";

    private SharedPreferences sharedPreferences;

    private String contact, pass;
    private EditText editContact, editPass;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);
        if(useDarkTheme) {
            setTheme(R.style.Theme_AppCompat_Custom);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
        contact = sharedPreferences.getString(VALUE_CONTACT, "");
        pass = sharedPreferences.getString(VALUE_PASS, "");
        editContact = findViewById(R.id.input_contact);
        editPass = findViewById(R.id.input_password);
        editContact.setText(contact);
        editPass.setText(pass);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact = editContact.getText().toString();
                pass = editPass.getText().toString();
                boolean flag = true;
                if (contact.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.missing_contact, Toast.LENGTH_SHORT).show();
                } else if (pass.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.missing_password, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.login_ok), Toast.LENGTH_SHORT).show();
                    Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainActivity);
                    finish();
                }
                if(flag) Toast.makeText(getApplicationContext(), getResources().getString(R.string.login_not), Toast.LENGTH_SHORT).show();
            }
        });

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerActivity = new Intent(LoginActivity.this, ProfileActivity.class);
                registerActivity.putExtra("string", getResources().getText(R.string.register));
                startActivity(registerActivity);
                onStop();
            }
        });
    }
}
