package com.example.cibilcheck.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/*
 * Created by ntf-9 on 27/12/17.
 */

public class MakeNetworkRequest {
    //  private static String URL = AppConstant.StartURL;
    private static RequestQueue requestQueue = null;
    private GetVolleyRequestCallback callback;
    private final String TAG = "MakeNetworkRequest";
    private static Context context;
    private String url;

    public MakeNetworkRequest(Context context, String url, GetVolleyRequestCallback callback) {
        this.callback = callback;
        MakeNetworkRequest.context = context;
        this.url=url;
    }

    public static RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context);

        return requestQueue;
    }


    public void sendRequest(RequestQueue requestQueue) {
        Log.d("MakeNetworkRequest ", "sendRequest called");

        requestQueue.getCache().clear();
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onGetResponse(response);
            }
            //next Parameter
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onGetError(error);
            }
        })
                //initializer Block
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", "AIzaSyBQG-zp-TcCk-WmmoDN0Douo0lNQSzsGjo");

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(1500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    public interface GetVolleyRequestCallback {
        void onGetResponse(JSONObject response);

        void onGetError(VolleyError error);
    }

}
