package com.example.hachemmasghouni.ireport1;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kenne on 6/27/2017.
 */

public class SendReportRequest extends StringRequest{
    private static final String SEND_REPORT_REQUEST_URL = "http://kennefoue.000webhostapp.com/iReport_phps/report.php";
    private Map<String, String> params;

    public SendReportRequest(int userId, double lat, double lon, String ref, ArrayList<String> imagesNamesList, Response.Listener<String> listener) throws SQLException {
        super(Request.Method.POST, SEND_REPORT_REQUEST_URL, listener, null);
        params = new HashMap<>();
        ReportDataObject reportObject = new ReportDataObject(userId, lat, lon, ref, imagesNamesList);
        params.put("reportJsonObjString", reportObject.getJsonString());
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
