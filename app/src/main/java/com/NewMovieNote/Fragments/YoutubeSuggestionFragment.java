package com.NewMovieNote.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.NewMovieNote.ViewModel.YoutubeItemViewModel;
import com.NewMovieNote.models.BoxItemModel;
import com.NewMovieNote.models.YoutubeChannelItemModel;
import com.NewMovieNote.models.YoutubeSuggestionItemModel;
import com.parkbros.jhmovienote.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YoutubeSuggestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YoutubeSuggestionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public YoutubeSuggestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YoutubeSuggestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YoutubeSuggestionFragment newInstance(String param1, String param2) {
        YoutubeSuggestionFragment fragment = new YoutubeSuggestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    final private String TAG = "YoutubeSuggestionFrag";

    private int page = 0 ;
    private String channelId = "rand";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_youtube_suggestion, container, false);


        YoutubeItemViewModel youtubeItemViewModel = new YoutubeItemViewModel();
        youtubeItemViewModel.InitResources( getContext() );
        youtubeItemViewModel.InitYoutubeChannel();
        youtubeItemViewModel.getYoutubeChannelItemModel().observe(this, new Observer<List<YoutubeChannelItemModel>>() {
            @Override
            public void onChanged(List<YoutubeChannelItemModel> youtubeChannelItemModels) {
                LogE( "youtubechannelItemModels changed : " + youtubeChannelItemModels.toString() );
            }
        });

        youtubeItemViewModel.getYoutubeSuggestionItemModel().observe(this, new Observer<List<YoutubeSuggestionItemModel>>() {
            @Override
            public void onChanged(List<YoutubeSuggestionItemModel> youtubeSuggestionItemModels) {
                LogE( "youtubeSuggestionItemModels changed" + youtubeSuggestionItemModels .toString() );
            }
        });

        youtubeItemViewModel.fetchYoutubeChannelList();
        youtubeItemViewModel.fetchYoutubeSuggestionList(page, channelId);

        return rootView ;
    }
    private void LogE( String msg ){
        Log.e( TAG, msg );
    }

}