package com.example.application6;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileActivity extends AppCompatActivity {
    TextView fileContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        fileContent = findViewById(R.id.file_textView);

        Button fileButton = findViewById(R.id.show_file_button);
        fileButton.setOnClickListener(v -> loadFileContents());
    }

    private void loadFileContents() {
        String result;
        StringBuilder stringBuilder = new StringBuilder();

        File downloadsDir = this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        File myFile = new File(downloadsDir, getString(R.string.log_file_name));

        try {
            BufferedReader reader = new BufferedReader(new FileReader(myFile));

            String ls = System.getProperty("line.separator");
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        result = stringBuilder.toString();
        fileContent.setText(result);
    }
}
