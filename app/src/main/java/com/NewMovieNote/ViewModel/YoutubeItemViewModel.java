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

    public void InitResources( Context context ){
        this.context = context ;
        mRepo = YoutubeItemRepo.getInstance() ;
    }
    public void InitYoutubeChannel(){
        if( mYoutubeChannelItemModel  != null ){
            LogE("mYoutubeChannelItemModel is null" );
            return;
        }
        if( mYoutubeSuggestionItemModel != null ){
            LogE("mYoutubeSuggestionItemModel is null" );
            return;
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


    public void fetchYoutubeChannelList(){
        Map<String, String> params = new HashMap<>();
        params.put("mode", "30021" );
        ServerCommunicator_v2 serverCommunicator = new ServerCommunicator_v2( context , __BASE_URL__ );
        serverCommunicator._Communicator(new ServerCommunicator_v2.VolleyCallback() {
            @Override
            public void onSuccess(int res, String data) {
                if( res == __CONN_SUCCESS__ ){
                    //LogE( data );
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        if( jsonObject.getInt("res") == 0 ) {
                            JSONArray channel_jsonArray = jsonObject.getJSONArray("msg");
                            List<YoutubeChannelItemModel> channelList = new ArrayList<>();
                            for( int i = 0 ; i < channel_jsonArray.length() ; i++ ){
                                String channelId = channel_jsonArray.getJSONObject(i).getString("channel_id");
                                String channelName = channel_jsonArray.getJSONObject(i).getString("channelname");
                                String thumbnailUrl = channel_jsonArray.getJSONObject(i).getString("thumbnail");
                                channelList.add( new YoutubeChannelItemModel(
                                        channelId, channelName, thumbnailUrl
                                        )
                                );
                            }
                            mYoutubeChannelItemModel.setValue( channelList );


//                            JSONArray suggestion_jsonArray = jsonObject.getJSONObject("msg").getJSONArray("youtube_suggestion_list");
//                            List<YoutubeSuggestionItemModel> suggestionList = new ArrayList<>();
//                            for( int i = 0 ; i < suggestion_jsonArray.length(); i++ ){
//                                suggestionList.add( new YoutubeSuggestionItemModel(
//                                                null, null, null,
//                                                null, null, null
//                                        )
//                                );
//                            }
//                            mYoutubeSuggestionItemModel.setValue( suggestionList );

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, params );
    }

    public void fetchYoutubeSuggestionList( int page , String channelId ){
        Map<String, String> params = new HashMap<>();
        params.put("mode", "30022" );
        params.put("pg" , String.valueOf(page) );
        params.put("youtube_selection" , channelId );
        ServerCommunicator_v2 serverCommunicator = new ServerCommunicator_v2( context , __BASE_URL__ );
        serverCommunicator._Communicator(new ServerCommunicator_v2.VolleyCallback() {
            @Override
            public void onSuccess(int res, String data) {
                if( res == __CONN_SUCCESS__ ){
                    //LogE( data );
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        if( jsonObject.getInt("res") == 0 ) {
                            JSONArray suggestion_jsonArray = jsonObject.getJSONArray("msg");
                            if( page == 0 ) {
                                List<YoutubeSuggestionItemModel> suggestionList = new ArrayList<>();
                                for( int i = 0 ; i < suggestion_jsonArray.length(); i++ ){
                                    String channelId = suggestion_jsonArray.getJSONObject(i).getString("channel_id");
                                    String channelName = suggestion_jsonArray.getJSONObject(i).getString("channel_title");
                                    String channelThumbnail = suggestion_jsonArray.getJSONObject(i).getString("thumbnail");

                                    String videoId = suggestion_jsonArray.getJSONObject(i).getString("video_id");
                                    String videoTitle = suggestion_jsonArray.getJSONObject(i).getString("title");
                                    String videoThumbnail = suggestion_jsonArray.getJSONObject(i).getString("video_thumbnail");

                                    suggestionList.add( new YoutubeSuggestionItemModel(
                                            channelId, channelName, channelThumbnail,
                                            videoId, videoTitle, videoThumbnail
                                            )
                                    );
                                }
                                mYoutubeSuggestionItemModel.setValue( suggestionList );
                            }



                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, params );
    }



    private void LogE( String msg ){
        Log.e( TAG, msg );
    }
}
