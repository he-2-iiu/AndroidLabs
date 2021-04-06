package ru.mirea.chistyakov.intentfilter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button buttonBrowse;
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonBrowse = findViewById(R.id.buttonBrowse);
        buttonBrowse.setOnClickListener(oclBtnBr);

        buttonSend = findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(oclBtnSend);

    }


    View.OnClickListener oclBtnBr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Uri address = Uri.parse("http://www.mirea.ru/");
            Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, address);

            if (openLinkIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(openLinkIntent);
            } else {
                Log.d("Intent", "Не получается обработать намерение!");
            }
        }
    };

    View.OnClickListener oclBtnSend = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "MIREA");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "ФАМИЛИЯ ИМЯ ОТЧЕСТВО");
            startActivity(Intent.createChooser(shareIntent, "Мои ФИО"));
        }
    };
}