package com.parkbros.jhmovienote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.Function.ServerCommunicator_v2;
import com.Function._ServerCommunicator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.parkbros.jhmovienote.databinding.ActivityEnteranceBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.StaticValues.EnvLib.__BASE_URL__;
import static com.StaticValues.EnvLib.__CONN_FAIL__;
import static com.StaticValues.EnvLib.__CONN_SUCCESS__;
import static com.StaticValues.EnvLib.fcm_idx;
import static com.StaticValues.StaticValues.baseURL;
import static com.StaticValues.StaticValues.localURL;
import static com.StaticValues.StaticValues.remoteURL;

public class EnteranceActivity extends AppCompatActivity {

    ProgressBar progressBar;
    EnteranceVariable enteranceVariable ;
    final String TAG = "EnteranceActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ActivityEnteranceBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_enterance);

        setContentView(R.layout.activity_enterance);

        //binding.setUser(new EnteranceVariable("test1", "test2"));
        //enteranceVariable = new EnteranceVariable(null, null);


        DeviceInfoManager_v3();

        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //DeviceInfoManager();

        //DeviceInfoManager_v2();

        //GotoLoung("test", "test")
        //check_server();
/*
        Observable<Bundle> ob = Observable
                .create(new ObservableOnSubscribe<Bundle>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Bundle> emitter) throws Throwable {
                        __BASE_URL__ = remoteURL;
                        Map<String, String> params = new HashMap<>();
                        ServerCommunicator_v2 serverCommunicator_v2 = new ServerCommunicator_v2(EnteranceActivity.this, __BASE_URL__  );
                        serverCommunicator_v2._Communicator(new ServerCommunicator_v2.VolleyCallback() {
                            @Override
                            public void onSuccess(int res, String data) {
                                Log.e( TAG, res + " / " + data);
                                Bundle bundle = new Bundle();
                                bundle.putString("0", "hahahaha");
                                emitter.onNext(bundle);
                                emitter.onComplete();
                            }
                        }, params );



                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        ob.subscribe(new Observer<Bundle>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }
            @Override
            public void onNext(@NonNull Bundle bundle) {
                Log.e(TAG , "onNext " );
                String tmp = bundle.getString("0");
                enteranceVariable.setTest1( tmp );
                enteranceVariable.setTest2( "test2333333333333333331222221");

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
            @Override
            public void onComplete() {
                Log.e(TAG , "Complete");
                binding.setUser(enteranceVariable);
            }
        });

 */
    }

    private void ServerChechk(){
        Map<String, String> params = new HashMap<>();
        String url = "http://192.168.219.155:80/p_jhmovienote/protocol/main.ptc.php";
        //String url = "http://122.46.245.107:12188/p_jhmovienote/protocol/main.ptc.php";
        ServerCommunicator_v2 serverCommunicator = new ServerCommunicator_v2( this ,  url );
        serverCommunicator._Communicator(new ServerCommunicator_v2.VolleyCallback() {
            @Override
            public void onSuccess(int  res, String data )  {
                Log.e( TAG , res +" / " +data);
                if( res == __CONN_SUCCESS__ ){
                    __BASE_URL__ = "http://192.168.219.155:80/p_jhmovienote/protocol/main.ptc.php";
                }else{
                    __BASE_URL__ =  "http://122.46.245.107:12188/p_jhmovienote/protocol/main.ptc.php";

                }
                DeviceInfoManager_v3();
            }
        }, params );
    }



    private void DeviceInfoManager_v3(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "Fetching FCM registration token failed", task.getException());
                            // --- do nothing ---
                        }else{
                            // ---- update server ----
                            String token = task.getResult();

                            Map<String, String> params = new HashMap<>();
                            params.put("mode", "20001" );
                            params.put( "deviceToken", token );
                            ServerCommunicator_v2 serverCommunicator = new ServerCommunicator_v2(EnteranceActivity.this, __BASE_URL__ );
                            serverCommunicator._Communicator(new ServerCommunicator_v2.VolleyCallback() {
                                @Override
                                public void onSuccess(int res, String data) {

                                    if( res == __CONN_SUCCESS__ ){
                                        Log.e(TAG, data);
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if( jsonObject.getInt("res") == 0 ) {
                                                fcm_idx = jsonObject.getJSONObject("msg").getInt("fcm_idx");
                                                Intent intent = new Intent( EnteranceActivity.this , com.NewMovieNote.activities.MainActivity.class);
                                                intent.putExtra( "fcm_id" , token.split(":")[0] );
                                                intent.putExtra( "fcm_token" , token.split(":")[1] );
                                                startActivity( intent );
                                                finish();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }else if ( res == __CONN_FAIL__ ){
                                        Toast.makeText( EnteranceActivity.this , "Connection Fail" , Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, params );
                        }

                    }
                });
    }


    private void check_server(){
        _ServerCommunicator serverCommunicator = new _ServerCommunicator(EnteranceActivity.this, remoteURL);
        serverCommunicator._Communicator(new _ServerCommunicator.VolleyCallback() {
            @Override
            public void onSuccess(String result, String connection) {
                Log.e("connection", connection);
                if ( connection.equals("success") ){
                    baseURL = remoteURL;
                    DeviceInfoManager_v2();
                }else{
                    baseURL = localURL;
                    DeviceInfoManager_v2();
                }
            }
        }, "urlcheck", "", "","" );
    }

    private void DeviceInfoManager_v2(){
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( EnteranceActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String mToken = instanceIdResult.getToken();
                String[] temp = mToken.split(":");
                final String device_id = temp[0].trim();
                final String device_token = temp[1].trim();
                //Log.e("FCM TOKEN DEVICE ID ", device_id);
                //Log.e("FCM TOKEN ID", mToken);

                String mode ="firebase";
                String code ="";
                String pg = "";
                String data ="";
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("device_id", device_id);
                    jsonObject.put("device_token", device_token);
                    data = jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                    _ServerCommunicator serverCommunicator = new _ServerCommunicator(EnteranceActivity.this, baseURL);
                    serverCommunicator._Communicator(new _ServerCommunicator.VolleyCallback() {
                        @Override
                        public void onSuccess(String result, String connection) {
                            if( connection.equals("success")){
                                Log.e("result :" , result );
                                try {
                                    JSONObject jsonObject1 = new JSONObject( result ) ;
                                    int res = jsonObject1.getInt("res");
                                    if ( res  == 0 ){
                                        String uid = jsonObject1.getJSONObject("msg").getString("uid");
                                        GotoLoung(Integer.parseInt(uid), device_id, device_token);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }



                            }
                        }
                    }, mode, code, pg, data );


            }
        });
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
                                    GotoLoung(1, deviceID, deviceToken);
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
                    }, req, code, "0",data);
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
                                        GotoLoung(1, deviceID, deviceToken);
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
                        }, req, code, "0", request_data);

                }
            }
        });
    }




    private void OffProgressBar(){
        progressBar.setVisibility(View.GONE);
    }

    private void GotoLoung(int uid, String deviceID, String deviceToken){
        Intent intent = new Intent(EnteranceActivity.this, MainActivity.class);
        intent.putExtra("uid", uid);
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

    public class EnteranceVariable{
        String test1;
        String test2;

        public EnteranceVariable(String test1, String test2) {
            this.test1 = test1;
            this.test2 = test2;
        }

        public String getTest1() {
            return test1;
        }

        public void setTest1(String test1) {
            this.test1 = test1;
        }

        public String getTest2() {
            return test2;
        }

        public void setTest2(String test2) {
            this.test2 = test2;
        }
    }
}
