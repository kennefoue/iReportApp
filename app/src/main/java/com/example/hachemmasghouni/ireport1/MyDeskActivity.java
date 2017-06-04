package com.example.hachemmasghouni.ireport1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MyDeskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_desk);

        final TextView tWlcMsg = (TextView) findViewById(R.id.tv_test_welcome_messae);

        Intent loginIntent = getIntent();
        String fullName = loginIntent.getStringExtra("fullName");
        String email = loginIntent.getStringExtra("email");
        String mobile = loginIntent.getStringExtra("mobile");

        String wlcMsg = "Welcome " + fullName + " to your Dashboard" +
                "\n" + "Registred Email: " + email + "\n" +
                "Mobile: " + mobile;

        tWlcMsg.setText(wlcMsg);
    }
}
