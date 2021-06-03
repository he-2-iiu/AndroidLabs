package ru.mirea.chistyakov.mireaproject;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import ru.mirea.chistyakov.mireaproject.AppPreference.Preferences;

import java.lang.reflect.Field;

import static ru.mirea.chistyakov.mireaproject.AppPreference.*;
import static ru.mirea.chistyakov.mireaproject.AppPreference.Preferences.*;

public class PlayerService extends Service {
    private MediaPlayer mediaPlayer;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate(){
        String music = "music_" + getPreference(MUSIC_CHOICE);
        int id = getResId(music, R.raw.class);
        mediaPlayer= MediaPlayer.create(this, id);
        mediaPlayer.setLooping(true);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        mediaPlayer.start();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        mediaPlayer.stop();
    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}