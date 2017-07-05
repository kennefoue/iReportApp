package com.example.hachemmasghouni.ireport1;

import android.util.Base64;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Blob;
import java.util.ArrayList;

/**
 * Created by kenne on 6/28/2017.
 */

public class ReportDataObject {
    private int userId;
    private double lat;
    private double lon;
    private String ref;
    private ArrayList<String> imagesNamesList;


    ReportDataObject(int userId, double lat, double lon, String ref, ArrayList<String> imagesNamesList) {
        this.userId = userId;
        this.lat = lat;
        this.lon = lon;
        this.ref = ref;
        this.imagesNamesList = imagesNamesList;

    }

    public JSONObject  getJsonObj() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("userId", userId);
            jsonObj.put("latitude", lat);
            jsonObj.put("longitude", lon);
            jsonObj.put("reference", ref);
            jsonObj.put("image1", imagesNamesList.get(0));
            jsonObj.put("image2", imagesNamesList.get(1));
            jsonObj.put("image3", imagesNamesList.get(2));
        } catch(JSONException e){
            e.printStackTrace();
        }
        return jsonObj;
    }

    public String getJsonString() {
        JSONObject jsonObj = getJsonObj();

        String jsonString;
        jsonString = jsonObj.toString();
        return jsonString;
    }

}
