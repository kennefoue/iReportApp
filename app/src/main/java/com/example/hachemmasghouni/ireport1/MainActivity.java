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
    public void test(){

    }

    public void button8(View v){

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void button11(View v){

        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

}
