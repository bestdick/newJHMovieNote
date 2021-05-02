package com.Function;

import android.content.Context;

import com.StaticValues.EnvLib;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public class ServerCommunicator_v2 {
    Context context;
    String url;



    public ServerCommunicator_v2(Context context, String url) {
        this.context = context;
        this.url = url;

    }

    public interface VolleyCallback{
        void onSuccess(int res, String data);
    }

    public void _Communicator(final VolleyCallback callback, final Map<String, String> params ) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess( EnvLib.__CONN_SUCCESS__ , response );
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onSuccess( EnvLib.__CONN_FAIL__ , null );
            }
        }) {
            @Override
            protected Map<String, String> getParams(){

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}


