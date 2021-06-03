package ru.mirea.chistyakov.mireaproject.ui.hardware;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import ru.mirea.chistyakov.mireaproject.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class FragmentHardware extends Fragment implements SensorEventListener {
    private TextView azimuthTextView;
    private TextView pitchTextView;
    private TextView rollTextView;
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private FragmentActivity activity;
    private Button button_take_picture;
    private Button button_save_picture;
    private static final int REQUEST_CODE_PERMISSION_CAMERA = 100;
    private ImageView imageView;
    private static final int CAMERA_REQUEST = 0;
    private boolean isWork = false;
    private Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hardware, container, false);
        activity = getActivity();
        sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        azimuthTextView = root.findViewById(R.id.textViewAzimuth);
        pitchTextView = root.findViewById(R.id.textViewPitch);
        rollTextView = root.findViewById(R.id.textViewRoll);
        button_take_picture = root.findViewById(R.id.button_take_picture);
        button_save_picture = root.findViewById(R.id.button_save_picture);
        imageView = root.findViewById(R.id.imageView);
        button_take_picture.setOnClickListener(v -> onClickTakePicture());
        button_save_picture.setOnClickListener(v -> onClickSavePicture());

        int cameraPermissionStatus =
                ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.CAMERA);
        int storagePermissionStatus = ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (cameraPermissionStatus == PackageManager.PERMISSION_GRANTED && storagePermissionStatus == PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {
            // Выполняется запрос к пользователь на получение необходимых разрешений
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION_CAMERA);
        }

        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float valueAzimuth = event.values[0];
            float valuePitch = event.values[1];
            float valueRoll = event.values[2];
            azimuthTextView.setText("Azimuth: " + valueAzimuth);
            pitchTextView.setText("Pitch: " + valuePitch);
            rollTextView.setText("Roll: " + valueRoll);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void onClickSavePicture() {
        boolean success = false;
        imageView.setDrawingCacheEnabled(true);
        Bitmap b = imageView.getDrawingCache();
        File sdCardDir = Environment.getExternalStorageDirectory();
        FileOutputStream outStream;
        try {
            Date currentTime = Calendar.getInstance().getTime();
            String imageName = currentTime.toString() + ".png";
            outStream = activity.openFileOutput(imageName, Context.MODE_PRIVATE);
//            outputStream.write(string.getBytes());
//            outputStream.close();
            b.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            /* 100 to keep full quality of the image */

            outStream.flush();
            outStream.close();
            success = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (success) {
            Toast.makeText(activity.getApplicationContext(), "Image saved with success",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity.getApplicationContext(),
                    "Error during image saving", Toast.LENGTH_LONG).show();
        }
    }


    private void onClickTakePicture() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // проверка на наличие разрешений для камеры
        ComponentName tmp = cameraIntent.resolveActivity(activity.getPackageManager());
        if (tmp != null && isWork == true) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // генерирование пути к файлу на основе authorities
            String authorities = activity.getApplicationContext().getPackageName() + ".fileProvider";
            imageUri = FileProvider.getUriForFile(activity.getApplicationContext(), authorities, photoFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            Fragment profileFlag = this;
            profileFlag.startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";
        File storageDirectory =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDirectory);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Если приложение камера вернула RESULT_OK, то производится установка изображению imageView
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            imageView.setImageURI(imageUri);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
// производится проверка полученного результата от пользователя на запрос разрешения Camera
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION_CAMERA) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission granted
                isWork = true;
            } else {
                isWork = false;
            }
        }
    }
}
