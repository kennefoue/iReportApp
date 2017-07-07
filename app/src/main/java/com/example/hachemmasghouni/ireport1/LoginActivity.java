package com.example.hachemmasghouni.ireport1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.SignInButton;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    private AutoLogOn m_session;
    private SignInButton btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        m_session = new AutoLogOn(this);
//check if session is already onetime before registred then no need to show login screen
       /* if (m_session.loggedin()){
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }*/

        setContentView(R.layout.activity_login);

        final EditText etEmail = (EditText) findViewById(R.id.et_email);
        final EditText etPassword = (EditText) findViewById(R.id.et_password);
        Button bLogin = (Button) findViewById(R.id.bLogin);
        //google button
        SignInButton btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        TextView tRegisterLink = (TextView) findViewById(R.id.tv_register_link);

        tRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, SignupActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent googleIntent = new Intent(LoginActivity.this, Google.class);
                LoginActivity.this.startActivity(googleIntent);
            }
        });


        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                // Send user data to dashboard activity
                                Bundle mBundle = new Bundle();
                                String jsonResponseString = jsonResponse.toString();
                                Intent dashboardIntent = new Intent(LoginActivity.this, DashboardActivity.class);
                                dashboardIntent.putExtra("userData", jsonResponseString);

                                // Start dashboard activity
                                LoginActivity.this.startActivity(dashboardIntent);
                            }
                            else {
                                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(LoginActivity.this);
                                alertBuilder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch(JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });



    }

}
