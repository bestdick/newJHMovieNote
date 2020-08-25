package com.parkbros.jhmovienote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import static com.StaticValues.StaticValues.youtubeAPIKey;

public class TestActivity extends YouTubeBaseActivity {
    YouTubePlayerView youTubePlayerView;
    Button btn;
    YouTubePlayer.OnInitializedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        btn = findViewById(R.id.youtubeBtn);
        youTubePlayerView = findViewById(R.id.youtubeView);
        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("uerxZZDa7pI");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.e("Error Initialize", youTubeInitializationResult.toString());
            }
        };

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubePlayerView.initialize(youtubeAPIKey, listener);
            }
        });
    }
}