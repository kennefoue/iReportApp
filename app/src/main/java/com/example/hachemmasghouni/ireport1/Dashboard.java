package com.example.hachemmasghouni.ireport1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
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
