package ru.mirea.chistyakov.mireaproject.ui.info;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ru.mirea.chistyakov.mireaproject.MainActivity;
import ru.mirea.chistyakov.mireaproject.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class InfoFragment extends Fragment {
    Button button_info;
    TextView text_view_time;
    private String TAG = MainActivity.class.getSimpleName();
    private String host = "time-a.nist.gov"; // или time-a.nist.gov or time-b.nist.gov
    private int port = 13;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_info, container, false);
        button_info = root.findViewById(R.id.button_info);
        button_info.setOnClickListener(v -> {
            GetTimeTask timeTask = new GetTimeTask();
            timeTask.execute();
        });
        text_view_time = root.findViewById(R.id.textViewTime);
        return root;
    }

    private class GetTimeTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String timeResult = "";
            try {
                Socket socket = new Socket(host, port);
                BufferedReader reader = SocketUtils.getReader(socket);
                reader.readLine(); // игнорируем первую строку
                timeResult = reader.readLine(); // считываем вторую строку
                Log.d(TAG,timeResult);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return timeResult;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            text_view_time.setText(result);
        }
    }
}
