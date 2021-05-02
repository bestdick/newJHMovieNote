package com.NewMovieNote.ViewModel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.Function.ServerCommunicator_v2;
import com.NewMovieNote.Repos.YoutubeItemRepo;
import com.NewMovieNote.models.BoxItemModel;
import com.NewMovieNote.models.YoutubeChannelItemModel;
import com.NewMovieNote.models.YoutubeSuggestionItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.StaticValues.EnvLib.__BASE_URL__;
import static com.StaticValues.EnvLib.__CONN_SUCCESS__;

public class YoutubeItemViewModel extends ViewModel {
    private final String TAG = "YoutubeItemViewModel";
    Context context ;
    private MutableLiveData<List<YoutubeChannelItemModel>> mYoutubeChannelItemModel;
    private MutableLiveData<List<YoutubeSuggestionItemModel>> mYoutubeSuggestionItemModel;

    private YoutubeItemRepo mRepo;

    public void InitContext( Context context ){
        this.context = context ;
        mRepo = YoutubeItemRepo.getInstance() ;
    }
    public void InitYoutubeChannel(){
        if( mYoutubeChannelItemModel  != null ){
            Log.e( TAG ,  "mYoutubeChannelItemModel is null" );
        }
        if( mYoutubeSuggestionItemModel != null ){
            Log.e( TAG ,  "mYoutubeSuggestionItemModel is null" );
        }

        mYoutubeSuggestionItemModel = mRepo.getYoutubeSuggestionItemModel() ;
        mYoutubeChannelItemModel = mRepo.getYoutubeChannelItemModel() ;
    }

    public void InitYoutubeSuggestion( ){
    }

    public MutableLiveData<List<YoutubeChannelItemModel>> getYoutubeChannelItemModel(){
        return mYoutubeChannelItemModel ;
    }
    public MutableLiveData<List<YoutubeSuggestionItemModel>> getYoutubeSuggestionItemModel(){
        return mYoutubeSuggestionItemModel ;
    }


    public void fetchYoutubeFragmentItem(){
        Map<String, String> params = new HashMap<>();
        params.put("mode", "30021" );
        ServerCommunicator_v2 serverCommunicator = new ServerCommunicator_v2( context , __BASE_URL__ );
        serverCommunicator._Communicator(new ServerCommunicator_v2.VolleyCallback() {
            @Override
            public void onSuccess(int res, String data) {
                if( res == __CONN_SUCCESS__ ){
                    //Log.e("BoxItemViewModel : " , data );
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        if( jsonObject.getInt("res") == 0 ) {
                            JSONArray channel_jsonArray = jsonObject.getJSONObject("msg").getJSONArray("channel_list");
                            List<YoutubeChannelItemModel> channelList = new ArrayList<>();
                            for( int i = 0 ; i < channel_jsonArray.length() ; i++ ){
                                channelList.add( new YoutubeChannelItemModel(
                                        null, null, null
                                        )
                                );
                            }
                            mYoutubeChannelItemModel.setValue( channelList );


                            JSONArray suggestion_jsonArray = jsonObject.getJSONObject("msg").getJSONArray("youtube_suggestion_list");
                            List<YoutubeSuggestionItemModel> suggestionList = new ArrayList<>();
                            for( int i = 0 ; i < suggestion_jsonArray.length(); i++ ){
                                suggestionList.add( new YoutubeSuggestionItemModel(
                                                null, null, null,
                                                null, null, null
                                        )
                                );
                            }
                            mYoutubeSuggestionItemModel.setValue( suggestionList );

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, params );
    }

}
