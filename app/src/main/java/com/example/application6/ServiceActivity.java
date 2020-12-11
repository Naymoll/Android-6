package com.example.application6;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ServiceActivity extends AppCompatActivity {
    private ServiceReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        Button start = findViewById(R.id.start_service_button);
        Button stop = findViewById(R.id.stop_service_button);

        start.setOnClickListener(v -> startService());
        stop.setOnClickListener(v -> stopService());
    }

    public void startService() {
        receiver = new ServiceReceiver(this);
        registerReceiver(receiver, new IntentFilter(ServiceReceiver.COUNTER_ACTION));

        Intent intent = new Intent(this, CounterService.class);
        startService(intent);
    }

    public void stopService() {
        unregisterReceiver(receiver);

        Intent intent = new Intent(this, CounterService.class);
        stopService(intent);
    }
}
