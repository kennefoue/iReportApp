package com.example.hachemmasghouni.ireport1;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by kenne on 7/2/2017.
 */

public class UploadImage extends AsyncTask<Void, Void, Void> {

    private static final String HTTP_SERVER_ADRESS = "http://kennefoue.000webhostapp.com/iReport_phps/";

    ArrayList<byte[]> imgDataList = new ArrayList<>();
    ArrayList<String> imgBaosList = new ArrayList<>();
    ArrayList<String> imagesNames = new ArrayList<>();
    ArrayList<NameValuePair> dataToSend = new ArrayList<>();
    Context applicationContext;
    public UploadImage(ArrayList<String> imagesNames, ArrayList<byte[]> imgDataList, Context ctx) {
        this.imagesNames = imagesNames;
        this.imgDataList = imgDataList;
        this.applicationContext = ctx;
    }
    @Override
    protected Void doInBackground(Void... params) {
        // Encode the images
        for (int i = 0; i < this.imgDataList.size(); i++) {
            byte[] imgByte = imgDataList.get(i);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(imgByte, 0, imgByte.length);
            String baosString = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
            imgBaosList.add(baosString);
        }
        dataToSend.add(new BasicNameValuePair("image1Name", imagesNames.get(0)));
        dataToSend.add(new BasicNameValuePair("image2Name", imagesNames.get(1)));
        dataToSend.add(new BasicNameValuePair("image3Name", imagesNames.get(2)));
        dataToSend.add(new BasicNameValuePair("image1", imgBaosList.get(0)));
        dataToSend.add(new BasicNameValuePair("image2", imgBaosList.get(1)));
        dataToSend.add(new BasicNameValuePair("image3", imgBaosList.get(2)));

        // Configure Http Client
        HttpParams httpRequestParams = getHttpRequestParams();
        HttpClient client = new DefaultHttpClient(httpRequestParams);
        HttpPost post = new HttpPost(HTTP_SERVER_ADRESS + "uploadImages.php");

        try {
            post.setEntity(new UrlEncodedFormEntity(dataToSend));
            client.execute(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // TODO implement the loader when image are uploading

    @Override
    protected void onPostExecute(Void aVoid) {
        Toast.makeText(applicationContext, "Image Send To Server", Toast.LENGTH_LONG)
                .show();
        super.onPostExecute(aVoid);
    }

    private HttpParams getHttpRequestParams() {
        HttpParams httpRequestParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParams, 1000 * 30);
        HttpConnectionParams.setSoTimeout(httpRequestParams, 1000 * 30);
        return httpRequestParams;
    }
}

