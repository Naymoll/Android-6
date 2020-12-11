package com.example.application6;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {
    private int state;
    private final int INT_STATE = 1;
    private final int STRING_STATE = 2;

    public static final String STATE_KEY = "State";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            state = savedInstanceState.getInt(STATE_KEY);
        }

        FrameLayout fragment_place = findViewById(R.id.fragment_place);

        if (fragment_place != null) {
            setFragment(new IntSum());
            state = INT_STATE;

            Button switch_fragment_button = findViewById(R.id.switch_fragment_button);
            switch_fragment_button.setOnClickListener(v -> {
                if (state == INT_STATE) {
                    setFragment(new StringSum());
                    state = STRING_STATE;
                } else if (state == STRING_STATE) {
                    setFragment(new IntSum());
                    state = INT_STATE;
                }
            });
        }
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_place, fragment).commit();
    }

    public void addToHistory(HistoryItem item) {
        DatabaseManager databaseManager = new DatabaseManager(this);
        databaseManager.open();
        databaseManager.insert(item);
        databaseManager.close();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_KEY, state);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.open_history_item:
                Intent intent = new Intent(this, HistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.open_service_item:
                intent = new Intent(this, ServiceActivity.class);
                startActivity(intent);
                break;
            case R.id.open_browser_item:
                intent = new Intent(this, BrowserCallActivity.class);
                startActivity(intent);
                break;
            case R.id.open_settings_item:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.open_file_activity:
                intent = new Intent(this, FileActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}