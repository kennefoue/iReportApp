package com.example.hachemmasghouni.ireport1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DashboardActivity extends AppCompatActivity {
    private static JSONObject userData;
    private int userId;
    private String userName;
    private String userMobile;
    private String userEmail;
    private String userIban;
    private  JSONObject userDashboardData;
    private PieChart userDashboardPieChart;
    private FloatingActionButton fbReport;
    private FloatingActionButton fbAdvices;
    private FloatingActionButton fbSearch;
    private TextView tvWelcom;
    private MenuView.ItemView menShowUserName;
    private MenuView.ItemView menShowUserMobile;
    private MenuView.ItemView menShowMoreOption;
    private TextView menShowUserEmail;
    private TextView menShowUserIban;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Get user Data From Extra
        String usrDataStr = getIntent().getExtras().getString("userData");
        try {
            userData = new JSONObject(usrDataStr);
            userId = userData.getInt("userId");
            userName = userData.getString("fullName");
            userMobile = userData.getString("mobile");
            userEmail = userData.getString("email");
            userIban = userData.getString("iban");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Get data for dashboard
        getUserDashboardData(userId);

        // Find Views
        tvWelcom = (TextView) findViewById(R.id.tv_welcom);
        userDashboardPieChart = (PieChart) findViewById(R.id.dashboard_pie_chart);
        fbReport = (FloatingActionButton) findViewById(R.id.report_button);
        fbAdvices = (FloatingActionButton) findViewById(R.id.button_advice);
        fbSearch = (FloatingActionButton) findViewById(R.id.button_search);
        menShowUserName = (MenuView.ItemView) findViewById(R.id.user_full_name);
        menShowUserMobile = (MenuView.ItemView) findViewById(R.id.user_mobile);
        // TODO set onclick listener on show more to show more user data
        menShowMoreOption = (MenuView.ItemView) findViewById(R.id.show_more);

        // Set Dashboard Text
        tvWelcom.setTextSize(20f);
        tvWelcom.setTypeface(null, Typeface.BOLD_ITALIC);
        tvWelcom.setText("Hi " + userName + "\n" + "Your Dashboard !");
        /*
        menShowUserName.setTitle(userName);
        menShowUserMobile.setTitle(userMobile);
        menShowUserEmail.setText(userEmail);
        menShowUserIban.setText(userIban);
        */

        // Floating button listeners
        setFloatingButtonsOnclickListener();
        // TODO TEST
        Toast.makeText(getApplicationContext(), usrDataStr, Toast.LENGTH_LONG)
                .show();

        // User dashboard pie chart; pie is drawed after string request response
        setUserDashboardPieChartProperties();

        // TODO NEXT add user data to navigation draw
    }

    // Build floation button
    private void setFloatingButtonsOnclickListener() {

        // Report button
        fbReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardActivity.this, "Report Clicked", Toast.LENGTH_SHORT).show();
                Intent i0 = new Intent(DashboardActivity.this, ReportActivity.class);
                i0.putExtra("userId", userId);
                startActivity(i0);
            }
        });

        // Advices button

        fbAdvices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardActivity.this, "Welcome to advices", Toast.LENGTH_SHORT).show();
                Intent i3 = new Intent(DashboardActivity.this, AdvicesActivity.class);
                startActivity(i3);
            }
        });
    }

    // TODO implement the onclick listener
    // Depend on string request response
    private void addUserDashboardDataSet() {

        // Create entry
        ArrayList<PieEntry> entrys = new ArrayList<>();
        try {
            entrys.add(new PieEntry(userDashboardData.getLong("acceptedReports"), "Accepted"));
            entrys.add(new PieEntry(userDashboardData.getLong("refusedReports"), "Refused"));
            entrys.add(new PieEntry(userDashboardData.getLong("waitingReports"), "Waiting"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Set pie entrys
        PieDataSet pieDataSet = new PieDataSet(entrys, "Reports");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(13);

        // Create colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);

        // Set Color
        pieDataSet.setColors(colors);

        // TODO LATER create custom legend
        // Create Legend
        Legend legend = userDashboardPieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(15f);
        legend.setYOffset(2);
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);

        // Create pie data object
        PieData pieData = new PieData(pieDataSet);
        userDashboardPieChart.setData(pieData);
        userDashboardPieChart.invalidate();
    }

    // TODO LATER make chart pie beatiful
    public void setUserDashboardPieChartProperties() {
        Description desc = new Description();
        desc.setTextSize(15);
        desc.setYOffset(0);
        desc.setXOffset(10f);
        desc.setText("Reports Summary");
        desc.setTextColor(Color.BLACK);
        userDashboardPieChart.setDescription(desc);
        userDashboardPieChart.setHoleRadius(30f);
        userDashboardPieChart.setTransparentCircleAlpha(0);
        // TODO LATER implement and get the balance
        userDashboardPieChart.setCenterText("Balance: 0");
        userDashboardPieChart.setCenterTextColor(Color.BLUE);
        userDashboardPieChart.setEntryLabelColor(Color.CYAN);
        userDashboardPieChart.animateY(2000);
        userDashboardPieChart.setCenterTextSize(15);
        userDashboardPieChart.setHoleRadius(35f);
        userDashboardPieChart.setUsePercentValues(true);
        userDashboardPieChart.setHoleColor(Color.LTGRAY);
        userDashboardPieChart.setDrawEntryLabels(true);
        userDashboardPieChart.setRotationEnabled(true);
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
                        userDashboardData = jsonResponse;
                        addUserDashboardDataSet();
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

}
