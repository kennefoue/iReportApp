package com.example.hachemmasghouni.ireport1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;


public class DashboardActivity extends AppCompatActivity {
    private static JSONObject userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Get user Data
        String usrDataStr = getIntent().getExtras().getString("userData");
        try {
            userData = new JSONObject(usrDataStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), usrDataStr, Toast.LENGTH_LONG)
             .show();

        //final TextView tvWelcomeMessage = (TextView) findViewById(R.id.tv_test_welcome_messae);
        CircleMenu circleMenu = (CircleMenu) findViewById(R.id.circle_menu);

        //displayWelcomeMessage(tvWelcomeMessage);
        buildCircleMenu(circleMenu);
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
