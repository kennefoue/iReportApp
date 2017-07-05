package com.example.hachemmasghouni.ireport1;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kenne on 7/4/2017.
 */

public class UserDashboardDataRequest extends StringRequest {
    private static final String GET_USER_DASHBOARD_DATA_REQUEST_URL = "http://kennefoue.000webhostapp.com/iReport_phps/userDashboardData.php";
    private Map<String, String> params;
    private static int userId ;

    public UserDashboardDataRequest(int userId, Response.Listener<String> listener) {
        super(Request.Method.POST, GET_USER_DASHBOARD_DATA_REQUEST_URL, listener, null);
        this.userId = userId;
        params = new HashMap<>();
        params.put("userId", Integer.toString(userId));
    }

    @Override
    protected Map<String, String> getParams() {
        return params;
    }
}
