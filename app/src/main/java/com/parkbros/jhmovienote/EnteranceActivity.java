package com.parkbros.jhmovienote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.Function._ServerCommunicator;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import static com.StaticValues.StaticValues.baseURL;

public class EnteranceActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterance);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        DeviceInfoManager();
    }
    private void DeviceInfoManager(){
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( EnteranceActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {

                String mToken = instanceIdResult.getToken();
                Log.e("FCM TOKEN ID", mToken);
                String[] temp = mToken.split(":");
                final String deviceID = temp[0].trim();
                final String deviceToken = temp[1].trim();
                if(getDeviceInfo()==null){
                    //처음 앱을 설치하여 device info 가 존재 하지 않을떄....
                    //디비에 업로드 한다.
                    Log.e("Device info :: ",  "null");
                    setDeviceInfo(deviceID, deviceToken);
                    String url = baseURL + "/protocol/protocol_main.php";
                    String req = "auth";
                    String code ="upload_deviceinfo";
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("deviceId", deviceID);
                        jsonObject.put("deviceToken", deviceToken);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String data = jsonObject.toString();
                    _ServerCommunicator serverCommunicator = new _ServerCommunicator(EnteranceActivity.this, url);
                    serverCommunicator._Communicator(new _ServerCommunicator.VolleyCallback() {
                        @Override
                        public void onSuccess(String result, String connection) {
                            Log.e("connection response", result);
                            try {
                                JSONObject resultJsonObject = new JSONObject(result);
                                boolean res = resultJsonObject.getBoolean("res");
                                if(res) {
                                    OffProgressBar();
//                                    GotoRegister(deviceID, deviceToken);
                                    GotoLoung(deviceID, deviceToken);
                                }

//                                int response_result = resultJsonObject.getInt("response");
//                                if(response_result ==1){
//                                    OffProgressBar();
////                                    GotoRegister(deviceID, deviceToken);
//                                    GotoLoung(deviceID, deviceToken);
//                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, req, code, data);
                } else{
                    //deviceinfo 가 존재하지만 toke 값의 변화가 없을떄나 변화가 있을때나 무조건 업데이트 한다....
                    Bundle deviceInfo = getDeviceInfo();
                    String g_deviceID = deviceInfo.getString("deviceId").trim();
                    String g_deviceToken = deviceInfo.getString("deviceToken").trim();
                    //Log.e("Device Token :: ", deviceToken);
                    //Log.e("G Device Token :: ", g_deviceToken);
                        Log.e("Device Info", "현재 디바이스 id 와 token의 변경값이 없다");

                        //    String url = baseURL+ "/fcm/upload";
                        String url = baseURL + "/protocol/protocol_main.php";
                        String req = "auth";
                        String code ="update_deviceinfo";
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("deviceId", deviceID);
                            jsonObject.put("deviceToken", deviceToken);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String request_data = jsonObject.toString();
                        _ServerCommunicator serverCommunicator = new _ServerCommunicator(EnteranceActivity.this, url);
                        serverCommunicator._Communicator(new _ServerCommunicator.VolleyCallback() {
                            @Override
                            public void onSuccess(String result, String connection) {
                                Log.e("connection response", result);
                                try {
                                    JSONObject resultJsonObject = new JSONObject(result);
                                    boolean res = resultJsonObject.getBoolean("res");
                                    if(res) {
                                        OffProgressBar();
//                                    GotoRegister(deviceID, deviceToken);
                                        GotoLoung(deviceID, deviceToken);
                                    }else{
                                        Log.e("Enterance ERR::", resultJsonObject.toString());
                                    }

//                                    int response_result = resultJsonObject.getInt("response");
//                                    if(response_result ==1){
//                                        OffProgressBar();
////                                        GotoLoung(deviceID, deviceToken);
//                                        GotoLoung(deviceID, deviceToken);
//                                    }else if(response_result == -1){
//                                        OffProgressBar();
////                                        GotoRegister(deviceID, deviceToken);
//                                        GotoLoung(deviceID, deviceToken);
//                                    }else{
//                                        // fail
//                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, req, code, request_data);

                }
            }
        });
    }




    private void OffProgressBar(){
        progressBar.setVisibility(View.GONE);
    }

    private void GotoLoung(String deviceID, String deviceToken){
        Intent intent = new Intent(EnteranceActivity.this, MainActivity.class);
        intent.putExtra("deviceId", deviceID);
        intent.putExtra("deviceToken", deviceToken);
        startActivity(intent);
        finish();
    }
    //Preference 읽기
    private Bundle getDeviceInfo() {
        SharedPreferences pref = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        if(pref.getString("deviceId", null) == null){
//            Toast.makeText(this, "device id not exist", Toast.LENGTH_LONG).show();
            return null;
        }else{
//            Toast.makeText(this, "device id already exist", Toast.LENGTH_LONG).show();
            Bundle temp = new Bundle();
            temp.putString("deviceId", pref.getString("deviceId", null));
            temp.putString("deviceToken", pref.getString("deviceToken", null));
            return temp;
        }

    }
    // Preference 쓰기
    private void setDeviceInfo(String value1, String value2){
        SharedPreferences pref = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("deviceID", value1);
        editor.putString("deviceToken", value2);
        editor.commit();
    }






    //-------------------below deprecated ---------------------------------
//    private void DeviceInfoManager(){
//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( EnteranceActivity.this,  new OnSuccessListener<InstanceIdResult>() {
//            @Override
//            public void onSuccess(InstanceIdResult instanceIdResult) {
//
//                String mToken = instanceIdResult.getToken();
//                Log.e("FCM TOKEN ID", mToken);
//                String[] temp = mToken.split(":");
//                final String deviceID = temp[0].trim();
//                final String deviceToken = temp[1].trim();
//                if(getDeviceInfo()==null){
//                    Log.e("Device info :: ",  "null");
//                    setDeviceInfo(deviceID, deviceToken);
//
//                    //            String url = baseURL+ "/fcm/upload";
//                    //          String request_type = "fcm_token_upload";
//                    // this is initial uploader
//                    String url = baseURL + "/protocol/protocol_main.php";
//                    JSONObject jsonObject_rt = new JSONObject();
//                    try {
//                        jsonObject_rt.put("access", "jhmn");
//                        jsonObject_rt.put("request_type", 50000); //
//                        jsonObject_rt.put("value", 1); // upload
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    String request_type  = jsonObject_rt.toString();
//                    JSONObject jsonObject = new JSONObject();
//                    try {
//                        jsonObject.put("deviceID", deviceID);
//                        jsonObject.put("deviceToken", deviceToken);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    String request_data = jsonObject.toString();
//                    _ServerCommunicator serverCommunicator = new _ServerCommunicator(EnteranceActivity.this, url);
//                    serverCommunicator.Communicator(new _ServerCommunicator.VolleyCallback() {
//                        @Override
//                        public void onSuccess(String result, String connection) {
//                            Log.e("connection response", result);
//                            try {
//                                JSONObject resultJsonObject = new JSONObject(result);
//                                int response_result = resultJsonObject.getInt("response");
//                                if(response_result ==1){
//                                    OffProgressBar();
////                                    GotoRegister(deviceID, deviceToken);
//                                    GotoLoung(deviceID, deviceToken);
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    }, request_type, request_data);
//                } else{
//                    Bundle deviceInfo = getDeviceInfo();
//                    String g_deviceID = deviceInfo.getString("deviceID").trim();
//                    String g_deviceToken = deviceInfo.getString("deviceToken").trim();
//                    //Log.e("Device Token :: ", deviceToken);
//                    //Log.e("G Device Token :: ", g_deviceToken);
//                    if(deviceID.equals(g_deviceID) && deviceToken.equals(g_deviceToken)){
//                        Log.e("Device Info", "현재 디바이스 id 와 token의 변경값이 없다");
//
//                        //    String url = baseURL+ "/fcm/upload";
//                        String url = baseURL + "/protocol/protocol_main.php";
//                        JSONObject jsonObject_rt = new JSONObject();
//                        try {
//                            jsonObject_rt.put("access", "jhmn");
//                            jsonObject_rt.put("request_type", 50000); //
//                            jsonObject_rt.put("value", 2); // update
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        String request_type  = jsonObject_rt.toString();
//                        JSONObject jsonObject = new JSONObject();
//                        try {
//                            jsonObject.put("deviceID", deviceID);
//                            jsonObject.put("deviceToken", deviceToken);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        String request_data = jsonObject.toString();
//                        _ServerCommunicator serverCommunicator = new _ServerCommunicator(EnteranceActivity.this, url);
//                        serverCommunicator.Communicator(new _ServerCommunicator.VolleyCallback() {
//                            @Override
//                            public void onSuccess(String result, String connection) {
//                                Log.e("connection response", result);
//                                try {
//                                    JSONObject resultJsonObject = new JSONObject(result);
//                                    int response_result = resultJsonObject.getInt("response");
//                                    if(response_result ==1){
//                                        OffProgressBar();
////                                        GotoLoung(deviceID, deviceToken);
//                                        GotoLoung(deviceID, deviceToken);
//                                    }else if(response_result == -1){
//                                        OffProgressBar();
////                                        GotoRegister(deviceID, deviceToken);
//                                        GotoLoung(deviceID, deviceToken);
//                                    }else{
//                                        // fail
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        }, request_type, request_data);
//                    }else{
//                        //token update
//                        Log.e("Device Info", "현재 디바이스 id 와 token 의 변경값이 있다.");
//
//                        setDeviceInfo(deviceID, deviceToken);
//                        //String url = baseURL+ "/fcm/upload";
//                        String url = baseURL + "/protocol/protocol_main.php";
//                        JSONObject jsonObject_rt = new JSONObject();
//                        try {
//                            jsonObject_rt.put("access", "jhmn");
//                            jsonObject_rt.put("request_type", 50000); //
//                            jsonObject_rt.put("value", 2); // update
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        String request_type  = jsonObject_rt.toString();
//                        JSONObject jsonObject = new JSONObject();
//                        try {
//                            jsonObject.put("deviceID", deviceID);
//                            jsonObject.put("deviceToken", deviceToken);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        String request_data = jsonObject.toString();
//                        _ServerCommunicator serverCommunicator = new _ServerCommunicator(EnteranceActivity.this, url);
//                        serverCommunicator.Communicator(new _ServerCommunicator.VolleyCallback() {
//                            @Override
//                            public void onSuccess(String result, String connection) {
//                                Log.e("connection response", result);
//                                try {
//                                    JSONObject resultJsonObject = new JSONObject(result);
//                                    int response_result = resultJsonObject.getInt("response");
//                                    if(response_result ==1){
//                                        OffProgressBar();
//                                        GotoLoung(deviceID, deviceToken);
//                                    }else if(response_result ==-1){
//                                        OffProgressBar();
////                                        GotoRegister(deviceID, deviceToken);
//                                        GotoLoung(deviceID, deviceToken);
//                                    }else{
//                                        // fail
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }, request_type, request_data);
//                    }
//                }
//            }
//        });
//    }
}
