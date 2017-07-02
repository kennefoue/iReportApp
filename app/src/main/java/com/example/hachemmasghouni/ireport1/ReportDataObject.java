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
    private ArrayList<byte[]> imagesDataList;


    ReportDataObject(int userId, double lat, double lon, String ref, ArrayList<byte[]> imagesDataList) {
        this.userId = userId;
        this.lat = lat;
        this.lon = lon;
        this.ref = ref;
        this.imagesDataList = imagesDataList;
    }

    public String getUserIdString() {
        return Integer.toString(userId);
    }

    public String getImgDataString(byte[] img) {
        return Base64.encodeToString(img, Base64.DEFAULT);
    }


    public String getLatString() {
        return Double.toString(this.lat);
    }

    public String getLonString() {
        return Double.toString(this.lon);
    }

    public String getRefString() {
        return this.ref;
    }

    public JSONObject  getJsonObj() {
        JSONObject jsonObj = new JSONObject();
        byte[] img1 = imagesDataList.get(0);
        byte[] img2 = imagesDataList.get(1);
        byte[] img3 = imagesDataList.get(2);
        try {
            jsonObj.put("userId", userId);
            jsonObj.put("latitude", lat);
            jsonObj.put("longitude", lon);
            jsonObj.put("reference", ref);
            jsonObj.put("image1", getImgDataString(img1));
            jsonObj.put("image2", getImgDataString(img2));
            jsonObj.put("image3", getImgDataString(img3));
        } catch(JSONException e){
            e.printStackTrace();
        }
        return jsonObj;
    }

    public String getJsonString() {
        JSONObject jsonObj = getJsonObj();
        String jsonString;
        /*
        jsonString = "{" +
                                "id:" + getUserIdString() +
                                "latitude:" + getLatString() +
                                "longitude:" + getLonString() +
                                "reference:" + getRefString() +
                                "image1:" + getImgDataString(0) +
                                "image2:" + getImgDataString(1) +
                                "image3:" + getImgDataString(2) +
                            "}";
        */
        jsonString = jsonObj.toString();
        return jsonString;
    }

}
