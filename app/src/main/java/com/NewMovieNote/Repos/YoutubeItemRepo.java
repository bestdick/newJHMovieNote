package com.NewMovieNote.Repos;

import androidx.lifecycle.MutableLiveData;

import com.NewMovieNote.models.YoutubeChannelItemModel;
import com.NewMovieNote.models.YoutubeSuggestionItemModel;

import java.util.ArrayList;
import java.util.List;

public class YoutubeItemRepo {
    private static YoutubeItemRepo instance;
    private ArrayList<YoutubeChannelItemModel> channelList = new ArrayList<>();
    private ArrayList<YoutubeSuggestionItemModel> youtubeList = new ArrayList<>();

    public static YoutubeItemRepo getInstance(){
        if( instance == null ){
            instance = new YoutubeItemRepo() ;
        }
        return instance;
    }

    public MutableLiveData<List<YoutubeChannelItemModel>> getYoutubeChannelItemModel(){
        MutableLiveData<List<YoutubeChannelItemModel>> data = new MutableLiveData<>();
        data.setValue(channelList);
        return data;
    }

    public MutableLiveData<List<YoutubeSuggestionItemModel>> getYoutubeSuggestionItemModel(){
        MutableLiveData<List<YoutubeSuggestionItemModel>> data = new MutableLiveData<>();
        data.setValue(youtubeList);
        return data;
    }
}
