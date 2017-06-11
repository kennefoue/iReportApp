package com.example.hachemmasghouni.ireport1;

import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import java.text.SimpleDateFormat;
import java.util.Date;


//public class ReportActivity extends AppCompatActivity {

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
    }
}  */


public class ReportActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private final int REQUEST_CODE_PLACEPICKER = 1;

    private ImageView getLocationIv;
    private TextView pickerResult;

    Camera camera;
    SurfaceView cameraSurfaceView;
    FloatingActionButton btnTakePicture;
    SurfaceHolder cameraSurfaceHolder;
    //Camera.PictureCallback jpegCallback;
    Camera.ShutterCallback shutterCallback;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        /* find views by Ids */
        getLocationIv = (ImageView) findViewById(R.id.iv_get_location);
        pickerResult = (TextView) findViewById(R.id.result_text_location);

        cameraSurfaceView = (SurfaceView) findViewById(R.id.srfcv_camera);
        btnTakePicture = (FloatingActionButton) findViewById(R.id.btn_take_photo);

        /* Google Place Picker */
        getLocationIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlacePickerActivity();
            }
        });

        /* Camera surface View */
        cameraSurfaceHolder = cameraSurfaceView.getHolder();

        // avoid report layout to open:
        cameraSurfaceHolder.addCallback(this);
        // app crash when clicking on btnTakePicture;
        cameraSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        btnTakePicture.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraImage();
            }
        });

    }

    private  File getDir() {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        return new File (dir, "iReport_Photo");
    }

    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            FileOutputStream outputStream = null;
            File imageFile = getDir();
            if(!imageFile.exists() && !imageFile.mkdirs()) {
                Toast.makeText(getApplicationContext(), "cant't create directory to save Image.", Toast.LENGTH_LONG)
                .show();
                return;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmddhhmmss");
            String date = simpleDateFormat.format(new Date());
            String pictureFile = "Ireport" + date + ".jpg";
            String pictureFileName = imageFile.getAbsolutePath() + "/" + pictureFile;
            File picFile = new File(pictureFileName);
            try {
                outputStream = new FileOutputStream(picFile);
                outputStream.write(data);
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
            Toast.makeText(getApplicationContext(), "Picture saved", Toast.LENGTH_LONG)
                 .show();
            refreshCamera();
            refreshGallery(picFile);
        }
    };

    // refresh gallery
    private void refreshGallery (File file) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        sendBroadcast(intent);
    }

    public void refreshCamera () {
        if(cameraSurfaceHolder.getSurface() == null) {
            // Preview surface does not exist
            return;
        }

        // Stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Start preview with new settings
        try {
            camera.setPreviewDisplay(cameraSurfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cameraImage() {
        camera.takePicture(null, null, jpegCallback);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Open the camera
        try {
            camera.open();
        } catch(RuntimeException e) {
            e.printStackTrace();
        }
        Camera.Parameters cameraParameters;
        cameraParameters = camera.getParameters();
        //cameraParameters.setPreviewFrameRate(20);
        cameraParameters.setPreviewSize(100, 100);
        camera.setParameters(cameraParameters);
        camera.setDisplayOrientation(90);

        try {
            camera.setPreviewDisplay(cameraSurfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        refreshCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

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




