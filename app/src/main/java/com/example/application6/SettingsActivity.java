package com.example.application6;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences settings;
    TextView input;
    Switch switchTheme;

    public static final String APP_SETTINGS = "app_settings";
    public static final String LANG = "Language";
    public static final String THEME = "THEME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings = getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);

        input = findViewById(R.id.fav_lang_input);
        input.setText(settings.getString(LANG, ""));

        switchTheme = findViewById(R.id.switch_theme);
        switchTheme.setChecked(settings.getBoolean(THEME, false));

        Button fileButton = findViewById(R.id.save_settings_button);
        fileButton.setOnClickListener(v -> saveSettings());
    }

    private void saveSettings() {
        String lang = input.getText().toString();

        if (!lang.isEmpty()) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(LANG, lang);
            editor.putBoolean(THEME, switchTheme.isChecked());
            editor.apply();
        }
    }
}
