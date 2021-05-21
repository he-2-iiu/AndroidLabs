package ru.mirea.chistyakov.loadmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String> {
    public final String TAG = this.getClass().getSimpleName();
    private int LoaderID = 1234;
    private int LoaderID_for_string = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = new Bundle();
        bundle.putString(MyLoader.ARG_WORD, "mirea");
        getSupportLoaderManager().initLoader(LoaderID, bundle, this);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        Log.d(TAG, "onLoaderReset");
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        if (i == LoaderID) {
            Toast.makeText(this, "onCreateLoader:" + i, Toast.LENGTH_SHORT).show();
            return new MyLoader(this, bundle);
        }
        if (i == LoaderID_for_string) {
            Toast.makeText(this, "DEBUG", Toast.LENGTH_LONG).show();
            return new MyLoader(this, bundle);
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        if (loader.getId() == LoaderID) {
            Log.d(TAG, "onLoadFinished" + s);
            Toast.makeText(this, "onLoadFinished:" + s, Toast.LENGTH_SHORT).show();
        }
        if (loader.getId() == LoaderID_for_string) {
            TextView textView = findViewById(R.id.textView);

            String str = "";
            for (int i = s.length() - 1; i >= 0; i--) {
                str += s.charAt(i);
            }
            textView.setText(str);
        }
    }

    public void onClick(View view) {
        Bundle bundle = new Bundle();
        EditText editText = (EditText) findViewById(R.id.editTextTextNum1);
        String str_old = editText.getText().toString();
        bundle.putString(MyLoader.ARG_WORD, str_old);
        getSupportLoaderManager().restartLoader(LoaderID_for_string, bundle, this);
    }
}