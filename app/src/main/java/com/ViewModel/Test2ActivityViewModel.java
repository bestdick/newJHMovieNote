package com.ViewModel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.Beans.Test2ActivityModel;
import com.Function.ServerCommunicator_v2;
import com.repository.Test2ActivityRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.StaticValues.EnvLib.__BASE_URL__;
import static com.StaticValues.EnvLib.__CONN_SUCCESS__;

public class Test2ActivityViewModel extends ViewModel {
    Context context ;

    private MutableLiveData<List<Test2ActivityModel>> mTest2ActivityModel ;

    private Test2ActivityRepository mRepo;
    public void init(Context context){
        this.context = context ;
        if (mTest2ActivityModel != null){
            return;
        }
        mRepo = Test2ActivityRepository.getInstance() ;
        //mRepo.setContext( this.context );
        mTest2ActivityModel = mRepo.getTest2ActivityModel() ;
    }

    public MutableLiveData<List<Test2ActivityModel>> getTest2ActivityModel(){
        return mTest2ActivityModel;
    }


    // --- retive data from ur desire places  such server ,  databases .... etc
    public void setTest2ActivityModel(){
        Map<String, String> params = new HashMap<>();
        params.put("mode", "30021" );
        ServerCommunicator_v2 serverCommunicator = new ServerCommunicator_v2( context , __BASE_URL__ );
        serverCommunicator._Communicator(new ServerCommunicator_v2.VolleyCallback() {
            @Override
            public void onSuccess(int res, String data) {
                if( res == __CONN_SUCCESS__ ){
                    //Log.e("Test2ActivityRepo : " , data );
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        if( jsonObject.getInt("res") == 0 ) {
                            JSONArray jsonArray = jsonObject.getJSONArray("msg");
                            List<Test2ActivityModel> list = new ArrayList<>();
                            for( int i = 0 ; i < jsonArray.length() ; i++ ){
                                list.add( new Test2ActivityModel(jsonArray.getJSONObject(i).getString("channel_id") , jsonArray.getJSONObject(i).getString("channelname")));

                            }
                            mTest2ActivityModel.postValue( list );
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, params );


    }
}
