package com.example.hachemmasghouni.ireport1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;


public class Dashboard extends AppCompatActivity {

    String arrayName[] = {
            "Report",
            "Last reports",
            "Approved reports",
            "Advices",

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

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
                                Toast.makeText(Dashboard.this, "Report Clicked", Toast.LENGTH_SHORT).show();
                                Intent i0 = new Intent(Dashboard.this, ReportActivity.class);
                                startActivity(i0);
                                break;

                            //other cases (1,2) need to be implemented depending on what we're going to define

                            case 3:
                                Toast.makeText(Dashboard.this, "Welcome to advices", Toast.LENGTH_SHORT).show();
                                Intent i3 = new Intent(Dashboard.this, AdvicesActivity.class);
                                startActivity(i3);
                                break;

                        }

                    }
                });

    }




//was desactivated cuz no need th show the username and email everytime in the background

  /*  public void displayWelcomeMessage(TextView tWlcMsg) {
        Intent loginIntent = getIntent();
        String fullName = loginIntent.getStringExtra("fullName");
        String email = loginIntent.getStringExtra("email");
        String mobile = loginIntent.getStringExtra("mobile");

        String wlcMsg = "Welcome " + fullName + " to your Dashboard" +
                        "\n" + "Registred Email: " + email + "\n" +
                        "Mobile: " + mobile;

        tWlcMsg.setText(wlcMsg);
    }
*/


}
