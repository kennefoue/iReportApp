package com.example.hachemmasghouni.ireport1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;


public class Dashboard extends AppCompatActivity {

    String arrayName[] = {
            "Book",
            "Check",
            "Report",
            "eye",
            "myDesk"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final TextView tvWelcomeMessage = (TextView) findViewById(R.id.tv_test_welcome_messae);
        CircleMenu circleMenu = (CircleMenu) findViewById(R.id.circle_menu);

        displayWelcomeMessage(tvWelcomeMessage);
        buildCircleMenu(circleMenu);
    }

    private void buildCircleMenu(CircleMenu circleMenu) {
        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.drawable.ic_add, R.drawable.ic_remove)
                .addSubMenu(Color.parseColor("#258CFF"), R.drawable.ic_book)
                .addSubMenu(Color.parseColor("#6d4c41"), R.drawable.ic_check)
                .addSubMenu(Color.parseColor("#258CFF"), R.drawable.ic_eye)
                .addSubMenu(Color.parseColor("#258CFF"), R.drawable.ic_report)

                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int index) {

                        Toast.makeText(Dashboard.this, "You selected" + arrayName[index], Toast.LENGTH_SHORT)
                              .show();


                    }
                });
    }

    public void displayWelcomeMessage(TextView tWlcMsg) {
        Intent loginIntent = getIntent();
        String fullName = loginIntent.getStringExtra("fullName");
        String email = loginIntent.getStringExtra("email");
        String mobile = loginIntent.getStringExtra("mobile");

        String wlcMsg = "Welcome " + fullName + " to your Dashboard" +
                        "\n" + "Registred Email: " + email + "\n" +
                        "Mobile: " + mobile;

        tWlcMsg.setText(wlcMsg);
    }


    public void button7(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    public void button4(View v) {
        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
    }

    public void button6(View v) {
        Intent intent = new Intent(this, MyDeskActivity.class);
        startActivity(intent);
    }


}
