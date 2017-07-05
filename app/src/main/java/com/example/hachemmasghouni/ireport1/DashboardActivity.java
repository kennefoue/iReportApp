package com.example.hachemmasghouni.ireport1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

import org.json.JSONException;
import org.json.JSONObject;


public class DashboardActivity extends AppCompatActivity {
    private static JSONObject userData;
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Get user Data
        String usrDataStr = getIntent().getExtras().getString("userData");
        try {
            userData = new JSONObject(usrDataStr);
            userId = userData.getInt("userId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // TODO TEST
        Toast.makeText(getApplicationContext(), usrDataStr, Toast.LENGTH_LONG)
             .show();

        // TODO user scalified library instead; see github for dokumentation
        // Circle Menu
        CircleMenu circleMenu = (CircleMenu) findViewById(R.id.circle_menu);
        buildCircleMenu(circleMenu);

        // Get data for dashboard
        getUserDashboardData(userId);
    }

    public void getUserDashboardData(int userId) {
        final ProgressDialog pgrDiag = new ProgressDialog(DashboardActivity.this);
        pgrDiag.setCancelable(false);
        pgrDiag.setTitle("Loading");
        pgrDiag.setMessage("Loading Dashboard Data");
        pgrDiag.show();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pgrDiag.cancel();
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success) {
                        // TODO  NEXT implement the method for android pie chart
                        Toast.makeText(getApplicationContext(), jsonResponse.toString(), Toast.LENGTH_LONG)
                             .show();
                    } else {
                        // TODO LATER implement a method to handle Error
                        Toast.makeText(getApplicationContext(), "Failed To Load Data", Toast.LENGTH_SHORT)
                             .show();
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        UserDashboardDataRequest userDataDashboardDataRequest = new UserDashboardDataRequest(userId, responseListener);
        RequestQueue queue = new Volley().newRequestQueue(DashboardActivity.this);
        queue.add(userDataDashboardDataRequest);
    }

    private void buildCircleMenu(CircleMenu circleMenu) {
        circleMenu.setMainMenu(Color.parseColor("#235e11"), R.drawable.ic_add, R.drawable.ic_remove)
                .addSubMenu(Color.parseColor("#EC190D"), R.drawable.ic_eye)
                .addSubMenu(Color.parseColor("#FDA334"), R.drawable.ic_report)
                .addSubMenu(Color.parseColor("#EDEA18"), R.drawable.ic_event_note_black_48dp)
                .addSubMenu(Color.parseColor("#19F212"), R.drawable.ic_nature_people_black_48dp)

                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int index) {

                        switch (index) {
                            case 0:
                                Toast.makeText(DashboardActivity.this, "Report Clicked", Toast.LENGTH_SHORT).show();
                                Intent i0 = new Intent(DashboardActivity.this, ReportActivity.class);
                                i0.putExtra("userId", userId);
                                startActivity(i0);
                                break;

                            //other cases (1,2) need to be implemented depending on what we're going to define

                            case 3:
                                Toast.makeText(DashboardActivity.this, "Welcome to advices", Toast.LENGTH_SHORT).show();
                                Intent i3 = new Intent(DashboardActivity.this, AdvicesActivity.class);
                                startActivity(i3);
                                break;

                        }

                    }
                });

    }







}
