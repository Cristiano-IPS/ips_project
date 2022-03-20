package cm.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ButtonMapActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);
        if(useDarkTheme) {
            setTheme(R.style.Theme_AppCompat_Dialog_Alert);
        }
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.and);
        setContentView(R.layout.bottom_sheet_layout);
        setTitle(getResources().getString(R.string.title_bs));

        final TextView txtMain = findViewById(R.id.txtMain);
        String str = getIntent().getStringExtra("title");
        txtMain.setText(str);
        final TextView txtMore = findViewById(R.id.txtMore);
        txtMore.setText(getIntent().getStringExtra("tag"));

        Button btnRent = findViewById(R.id.btnRent);
        btnRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.helptype = txtMain.getText().toString();
                Intent mainActivity = new Intent(ButtonMapActivity.this, HelpActivity.class);
                startActivity(mainActivity);
            }
        });
    }
}