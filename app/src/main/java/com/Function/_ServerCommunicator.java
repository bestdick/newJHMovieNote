package com.Function;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class _ServerCommunicator {
    Context context;
    String url;


    public _ServerCommunicator(Context context, String url) {
        this.context = context;
        this.url = url;

    }

    public interface VolleyCallback{
        void onSuccess(String result, String connection);
    }

    public void Communicator(final VolleyCallback callback, final String request_type,  final String request_data) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    callback.onSuccess(response, null);
            }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                NetworkResponse networkResponse = error.networkResponse;
//                Log.e("network response :: ", networkResponse.toString());
//                Log.e("volley err :: ", error.toString());
                callback.onSuccess(null, "connection_fail");
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params= new HashMap<>();

                params.put("reqest_type", request_type); //type of requesting..

                params.put("request_data", request_data);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void _Communicator(final VolleyCallback callback, final String request_type, final String request_value, final String request_data) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response, null);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                NetworkResponse networkResponse = error.networkResponse;
//                Log.e("network response :: ", networkResponse.toString());
//                Log.e("volley err :: ", error.toString());
                callback.onSuccess(null, "connection_fail");
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params= new HashMap<>();
                params.put("access", "jhmn");
                params.put("req", request_type); //type of requesting..
                params.put("code", request_value); //value of requesting....
                params.put("data", request_data);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
