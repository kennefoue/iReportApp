package com.example.hachemmasghouni.ireport1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn_next(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    public void button(View v) {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);

    }
}
