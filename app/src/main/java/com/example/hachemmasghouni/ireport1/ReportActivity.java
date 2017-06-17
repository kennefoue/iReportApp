package com.example.hachemmasghouni.ireport1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;


//public class ReportActivity extends AppCompatActivity {

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
    }
}  */


public class ReportActivity extends AppCompatActivity implements CameraSurfaceViewFragment.onAllPictureTaked {


    private final int REQUEST_CODE_PLACEPICKER = 1;


    private ImageView getLocationIv;
    private TextView pickerResult;
    private ArrayList<byte[]> imageDataList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        /* find views by Ids */
        // Place Picker
        getLocationIv = (ImageView) findViewById(R.id.iv_get_location);
        pickerResult = (TextView) findViewById(R.id.result_text_location);
        // Camera fragment


        /* Google Place Picker */
        getLocationIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlacePickerActivity();
            }
        });

    }



    /* Start fragment transaction when all pictures a taked. */
    // get the taked image for the preview
    @Override
    public void getImageDataList(ArrayList<byte[]> dataList) {
        imageDataList = dataList;
    }

    // this implement a interface of the class CameraSurfaceViewFragment
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
        Place placeSelected = PlacePicker.getPlace(data, this);
        pickerResult.setText(placeSelected.getAddress().toString());
        pickerResult.setVisibility(View.VISIBLE);
    }



    /* TODO: The app crash when pressing the switch button; check this... FIXED... but */
    /* TODO: must desinstall app before runing to get camera worked.*/

}




