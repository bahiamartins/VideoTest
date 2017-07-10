package com.jobconvo.www.videotest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION_RESULT = 0;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_RESULT = 1;

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startCamera();

            }
        });

        // permissions
        boolean storagePermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED);

        if (!storagePermission) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_RESULT);
        }

        boolean cameraPermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED);

        if (!cameraPermission) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO
                    }, REQUEST_CAMERA_PERMISSION_RESULT);
        }

    }

    // permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case REQUEST_CAMERA_PERMISSION_RESULT: {
                if(grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            "Application will not run without camera services", Toast.LENGTH_SHORT).show();

                }
                if(grantResults.length > 0 && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            "Application will not have audio on record", Toast.LENGTH_SHORT).show();


                }
            }

            case REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_RESULT: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,
                            "Permission successfully granted!", Toast.LENGTH_SHORT).show();

                    finish();
                    startActivity(getIntent());

                } else {
                    Toast.makeText(this,
                            "App needs to save video to run", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    private void startCamera() {

        Intent goToCameraActivity = new Intent(getApplicationContext(), NewCamActivity.class);
        startActivity(goToCameraActivity);

    }
}
