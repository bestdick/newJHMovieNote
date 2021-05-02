package com.repository;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.Beans.Test2ActivityModel;
import com.Function.ServerCommunicator_v2;
import com.parkbros.jhmovienote.EnteranceActivity;
import com.parkbros.jhmovienote.Test2Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.StaticValues.EnvLib.__BASE_URL__;
import static com.StaticValues.EnvLib.__CONN_SUCCESS__;
import static com.StaticValues.EnvLib.fcm_idx;


/**
 * SingleTon pattern
 */
public class Test2ActivityRepository {

    private static Test2ActivityRepository instance;
    private ArrayList<Test2ActivityModel> dataSet = new ArrayList<>() ;

    public static Test2ActivityRepository getInstance() {
        if( instance == null ){
            instance  = new Test2ActivityRepository();
        }
        return instance ;
    }
    // --- database --- server ....etc ...
    public MutableLiveData<List<Test2ActivityModel>> getTest2ActivityModel(){

        MutableLiveData<List<Test2ActivityModel>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data ;
    }


}
