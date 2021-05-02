package com.NewMovieNote.ViewModel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.Beans.Test2ActivityModel;
import com.Function.ServerCommunicator_v2;
import com.NewMovieNote.Repos.BoxItemRepo;
import com.NewMovieNote.models.BoxItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.StaticValues.EnvLib.__BASE_URL__;
import static com.StaticValues.EnvLib.__CONN_SUCCESS__;

public class BoxItemViewModel extends ViewModel {
    Context context ;

    private MutableLiveData<List<BoxItemModel>> mBoxItemModel ;

    private BoxItemRepo mRepo ;

    public void init( Context context ) {
        if( mBoxItemModel != null ){
            return ;
        }
        this.context = context ;
        mRepo = BoxItemRepo.getInstance() ;
        mBoxItemModel = mRepo.getBoxItemModel() ;
    }

    public MutableLiveData<List<BoxItemModel>> getBoxItemModel(){
        return mBoxItemModel ;
    }

    public void fetchBoxInfo( String type ){
        Map<String, String> params = new HashMap<>();
        params.put("mode", "30020" );
        params.put("movie_type", type );
        ServerCommunicator_v2 serverCommunicator = new ServerCommunicator_v2( context , __BASE_URL__ );
        serverCommunicator._Communicator(new ServerCommunicator_v2.VolleyCallback() {
            @Override
            public void onSuccess(int res, String data) {
                if( res == __CONN_SUCCESS__ ){
                    //Log.e("BoxItemViewModel : " , data );
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        if( jsonObject.getInt("res") == 0 ) {
                            JSONArray jsonArray = jsonObject.getJSONArray("msg");
                            List<BoxItemModel> list = new ArrayList<>();
                            for( int i = 0 ; i < jsonArray.length() ; i++ ){
                                  list.add( new BoxItemModel( jsonArray.getJSONObject(i).getInt("rank") ,
                                          jsonArray.getJSONObject(i).getString("title"),
                                          jsonArray.getJSONObject(i).getString("imageUrl"))
                                  );
                            }

                            mBoxItemModel.setValue( list );
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, params );


    }

}
