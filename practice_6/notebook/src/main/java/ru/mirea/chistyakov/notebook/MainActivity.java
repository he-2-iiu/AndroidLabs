package ru.mirea.chistyakov.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private SharedPreferences preferences;
    private final String SAVED_FILENAME = "saved_filename";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getPreferences(MODE_PRIVATE);
        String last_filename = preferences.getString(SAVED_FILENAME, null);
        if (last_filename != null) {
            String text = getTextFromFile(last_filename);
            EditText editText_filename = (EditText) findViewById(R.id.edit_text_filename);
            EditText editText_text = (EditText) findViewById(R.id.edit_text_text);
            editText_filename.setText(last_filename);
            editText_text.setText(text);
        }
    }

    public void onSaveNote(View view) {
        String filename = ((EditText) findViewById(R.id.edit_text_filename)).getText().toString();
        String text = ((EditText) findViewById(R.id.edit_text_text)).getText().toString();
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(text.getBytes());
            outputStream.close();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(SAVED_FILENAME, filename);
            editor.apply();
            Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public String getTextFromFile(String fileName) {
        FileInputStream fin = null;
        try {
            fin = openFileInput(fileName);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            Log.d(LOG_TAG, text);
            return text;
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }
}

