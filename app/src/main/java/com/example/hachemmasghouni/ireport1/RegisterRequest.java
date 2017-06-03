package com.example.hachemmasghouni.ireport1;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kenne on 5/29/2017.
 */

public class RegisterRequest extends StringRequest{
    private static final String REGISTER_REQUEST_URL = "http://kennefoue.000webhostapp.com/register.php";
    private Map<String, String> params;

    public RegisterRequest(String fullName, String email, String password, String mobile, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("fullName", fullName);
        params.put("email", email);
        params.put("password", password);
        params.put("mobile", mobile);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
