package com.example.hachemmasghouni.ireport1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        final TextView tWlcMsg = (TextView) findViewById(R.id.tWlcMsg);

        Intent loginIntent = getIntent();
        String fullName = loginIntent.getStringExtra("fullName");
        String email = loginIntent.getStringExtra("email");
        String mobile = loginIntent.getStringExtra("mobile");

        String wlcMsg = "Welcome " + fullName + " to your Dashboar" +
                        "\n" + "Registred Email: " + email + "\n" +
                        "Mobile: " + mobile;

        tWlcMsg.setText(wlcMsg);
    }








    public void button7(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    public void button4(View v) {
        Intent intent = new Intent(this, Report.class);
        startActivity(intent);
    }

    public void button6(View v) {
        Intent intent = new Intent(this, MyDesk.class);
        startActivity(intent);
    }


}
