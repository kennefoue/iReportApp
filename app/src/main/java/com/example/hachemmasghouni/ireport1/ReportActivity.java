package com.example.hachemmasghouni.ireport1;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.melnykov.fab.FloatingActionButton;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.jar.*;


//public class ReportActivity extends AppCompatActivity {

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
    }
}  */


public class ReportActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    public static final int CAMERA_PERMISSION_REQUEST_CODE = 8675309;
    private final int REQUEST_CODE_PLACEPICKER = 1;

    private ImageView getLocationIv;
    private TextView pickerResult;

    Camera camera;
    SurfaceView cameraSurfaceView;
    SurfaceHolder cameraSurfaceHolder;
    FloatingActionButton btnTakePicture;
    ImageView ivFullScreenCamera;
    ImageView ivCameraPreview;
    boolean previewing = false;
    String stringPath = "/sdcard/sampleVideo.3gp";






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        /* find views by Ids */
        // Place Picker
        getLocationIv = (ImageView) findViewById(R.id.iv_get_location);
        pickerResult = (TextView) findViewById(R.id.result_text_location);
        // Camera Preview
        cameraSurfaceView = (SurfaceView) findViewById(R.id.srfcv_camera);
        btnTakePicture = (FloatingActionButton) findViewById(R.id.btn_take_photo);
        ivFullScreenCamera = (ImageView) findViewById(R.id.iv_fullscreen);
        ivCameraPreview = (ImageView) findViewById(R.id.iv_preview);

        /* Google Place Picker */
        getLocationIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlacePickerActivity();
            }
        });

        /* Camera surface View */
        getWindow().setFormat(PixelFormat.UNKNOWN);
        cameraSurfaceView = (SurfaceView) findViewById(R.id.srfcv_camera);
        cameraSurfaceHolder = cameraSurfaceView.getHolder();
        cameraSurfaceHolder.addCallback(this);
        cameraSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        ivFullScreenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    /* Camera surface view */
    public void startCameraPreview() {

        if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            cameraPreview();
        } else {
            String[] cameraPermissionRequest = {Manifest.permission.CAMERA};
            requestPermissions(cameraPermissionRequest, CAMERA_PERMISSION_REQUEST_CODE);
        }


    }

    private void cameraPreview() {
        if(!previewing) {
            camera = camera.open();
            if(camera != null) {
                try {
                    camera.setPreviewDisplay(cameraSurfaceHolder);
                    camera.startPreview();
                    previewing = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(!previewing) {
                    camera = camera.open();
                    if(camera != null) {
                        try {
                            camera.setPreviewDisplay(cameraSurfaceHolder);
                            camera.startPreview();
                            previewing = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), "Failed to get permission", Toast.LENGTH_LONG)
                     .show();
            }
        }
    }

    public void stopCameraPreview() {
        if(camera != null && previewing) {
            camera.stopPreview();
            camera.release();
            camera = null;
            previewing = false;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startCameraPreview();
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }

    /* Google place Picker */
    public void startPlacePickerActivity() {
        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
        try {
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, REQUEST_CODE_PLACEPICKER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_CODE_PLACEPICKER && resultCode == RESULT_OK){
            displaySelectedPlaceFromPlacePicker(data);
        }
    }

    private void displaySelectedPlaceFromPlacePicker(Intent data){
        Place placeSelected = PlacePicker.getPlace(data, this);
        pickerResult.setText(placeSelected.getAddress().toString());
        pickerResult.setVisibility(View.VISIBLE);
    }



}




