package com.example.hachemmasghouni.ireport1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


//public class Report extends AppCompatActivity {

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
    }
}  */


public class Report extends AppCompatActivity implements OnItemSelectedListener {

    ImageView img;
    private Button b_get;
    private TrackGPS gps;
    double longitude;
    double latitude;

    //LocationManager locationManager = (LocationManager);
    //getSystemService(Context.LOCATION_SERVICE);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        //camera element
        img=(ImageView)findViewById(R.id.imageView2);
        //map element
        b_get=(Button)findViewById(R.id.get);

        //button get click Listener
        b_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps = new TrackGPS(Report.this);


                if (gps.canGetLocation()){

                    longitude = gps.longitude;
                    latitude = gps.latitude;

                    Toast.makeText(getApplicationContext(), "Longitude: "+Double.toString(longitude)+
                            "\nLatitude:" +Double.toString(latitude), Toast.LENGTH_SHORT).show();
                }

                else{
                    gps.showSettingsAlert();
                }
            }
        });

        //protected void onDestroy(){
           // super.onDestroy();
           // gps.stopUsingGPS();

       // };

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner3);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Accident");
        categories.add("Broken trash");
        categories.add("Defect lights");
        categories.add("Wrong parked");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    //impleent btn camera to start action
    public void btn_camera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==100 && requestCode==RESULT_OK){

            img.setImageBitmap((Bitmap)data.getExtras().get("data"));
        }
    }

    public void button10(View v) {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }
}




