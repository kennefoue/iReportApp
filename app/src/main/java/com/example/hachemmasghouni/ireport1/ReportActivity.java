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


public class ReportActivity extends AppCompatActivity  {


    private final int REQUEST_CODE_PLACEPICKER = 1;

    private ImageView getLocationIv;
    private TextView pickerResult;








    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        /* find views by Ids */
        // Place Picker
        getLocationIv = (ImageView) findViewById(R.id.iv_get_location);
        pickerResult = (TextView) findViewById(R.id.result_text_location);


        /* Google Place Picker */
        getLocationIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlacePickerActivity();
            }
        });



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

    /* TODO: The app crash when pressing the switch button; check this... */

}




