package com.example.hachemmasghouni.ireport1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;


//public class ReportActivity extends AppCompatActivity {

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
    }hasg
}  */


public class ReportActivity extends AppCompatActivity implements CameraSurfaceViewFragment.onAllPictureTaked {

    /* Constant */
    // Google Place Picker
    private final int REQUEST_CODE_PLACEPICKER = 1;

    // Upload image

    Context applicationContext;



    private ImageView getLocationIv;
    private TextView pickerResult;
    Place placeSelected;
    private EditText etReference;
    private Button btnSendReport;
    private ArrayList<byte[]> imageDataList = new ArrayList<>();
    private String reportRef;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        applicationContext = getApplicationContext();

        /* find views by Ids */
        // Google Place Picker
        getLocationIv = (ImageView) findViewById(R.id.iv_get_location);
        pickerResult = (TextView) findViewById(R.id.result_text_location);
        // Report
        etReference = (EditText) findViewById(R.id.et_reference);
        btnSendReport = (Button) findViewById(R.id.btn_send_report);

        /* Set Listeners */
        // Google place picker
        getLocationIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlacePickerActivity();
            }
        });

        // Report
        btnSendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO impelement the logic after SendReportRequest - Done
                Double reportLat = placeSelected.getLatLng().latitude;
                Double reportLon = placeSelected.getLatLng().longitude;
                reportRef = etReference.getText().toString();

                new UploadImage("haha", "hoho", "hihi", imageDataList, applicationContext).execute();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponce = new JSONObject(response);
                            boolean success = jsonResponce.getBoolean("success");
                            if(success) {
                                Toast.makeText(getApplicationContext(), "Report Send", Toast.LENGTH_LONG)
                                     .show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed to Report", Toast.LENGTH_LONG)
                                     .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                try {
                    SendReportRequest reportRequest = new SendReportRequest(imageDataList, reportLat, reportLon, reportRef, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ReportActivity.this);
                    queue.add(reportRequest);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    /* Start fragment transaction when all pictures a taked. */
    // get the taked image for the preview
    @Override
    public void getImageDataList(ArrayList<byte[]> dataList) {
        imageDataList = dataList;
    }

    // implementation of the cameraSurfaceViewFragment interface for the communication
    @Override
    public void changeFragement() {
        Bundle imgBundle = new Bundle();
        imgBundle.putByteArray("img1", imageDataList.get(0));
        imgBundle.putByteArray("img2", imageDataList.get(1));
        imgBundle.putByteArray("img3", imageDataList.get(2));
        PicturesPreviewFragment picFragment = new PicturesPreviewFragment();
        picFragment.setArguments(imgBundle);
        android.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_report_activity, picFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
        placeSelected = PlacePicker.getPlace(data, this);
        pickerResult.setText(placeSelected.getAddress().toString());
        pickerResult.setVisibility(View.VISIBLE);
    }


    /* TODO: The app crash when pressing the switch button; check this... FIXED... but */
    /* TODO: must desinstall app before runing to get camera worked.*/

}




