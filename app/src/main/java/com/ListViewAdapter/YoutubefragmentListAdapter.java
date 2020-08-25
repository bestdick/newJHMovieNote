package com.ListViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.Beans.YoutubeFragBean;
import com.parkbros.jhmovienote.MainActivity;
import com.parkbros.jhmovienote.R;
import com.parkbros.jhmovienote._YoutubeActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class YoutubefragmentListAdapter extends BaseAdapter {
    Context context;
    ArrayList<YoutubeFragBean> list;

    public YoutubefragmentListAdapter(Context context, ArrayList<YoutubeFragBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = list.get(position).getType();
        switch (type){
            case 1001:
                View randomHeaderView = View.inflate(context, R.layout.container_mainfrag_subtitle_divider, null);
                TextView randomHeaderTextView = (TextView) randomHeaderView.findViewById(R.id.subtitleTextView);

                randomHeaderTextView.setText("오늘의 추천 영화");
                return randomHeaderView;
            case 1002:
                View youtubeRandomThumbnailView = View.inflate(context, R.layout.container_youtubefrag_thumbnail, null);
                ImageView randomVideothumbnailImageView = (ImageView) youtubeRandomThumbnailView.findViewById(R.id.youtubeImageView);
                ImageView randomChannelThumbnailImageView = (ImageView) youtubeRandomThumbnailView.findViewById(R.id.channelThumbnailImageView);
                TextView randomVideoTitleTextView = (TextView) youtubeRandomThumbnailView.findViewById(R.id.titleTextView);
                TextView randomChannelTitleTextView = (TextView) youtubeRandomThumbnailView.findViewById(R.id.channelTitleTextView);

                float[] size1002 =  ViewSize();
                Picasso.get()
                        .load(list.get(position).getThumbnailUrl())
                        .resize((int) size1002[0], (int) (size1002[1]))
                        .into(randomVideothumbnailImageView);
                Picasso.get()
                        .load(list.get(position).getChannelThumbnail())
                        .transform(new CropCircleTransformation())
                        .into(randomChannelThumbnailImageView);
                randomVideoTitleTextView.setText(list.get(position).getTitle());
                randomChannelTitleTextView.setText(list.get(position).getChannelTitle());


                OnClickView(youtubeRandomThumbnailView, position);
                return youtubeRandomThumbnailView;
            case 1003:
                View selectHeaderView = View.inflate(context, R.layout.container_youtube_channel_selection_header, null);
                ImageView selectHeaderImageView = (ImageView) selectHeaderView.findViewById(R.id.imageView);
                TextView selectHeaderTextView = (TextView) selectHeaderView.findViewById(R.id.subtitleTextView);

                String channelTitle = list.get(list.size()-1).getChannelTitle();
                String channelThumbnailUrl = list.get(list.size()-1).getChannelThumbnail();

                selectHeaderTextView.setText(channelTitle);
                Picasso.get()
                        .load(channelThumbnailUrl)
                        .transform(new CropCircleTransformation())
                        .into(selectHeaderImageView);

                return selectHeaderView;
            case 1004:
                View youtubeThumbnailView = View.inflate(context, R.layout.container_youtubefrag_thumbnail, null);
                ImageView videothumbnailImageView = (ImageView) youtubeThumbnailView.findViewById(R.id.youtubeImageView);
                ImageView channelThumbnailImageView = (ImageView) youtubeThumbnailView.findViewById(R.id.channelThumbnailImageView);
                TextView videoTitleTextView = (TextView) youtubeThumbnailView.findViewById(R.id.titleTextView);
                TextView channelTitleTextView = (TextView) youtubeThumbnailView.findViewById(R.id.channelTitleTextView);

                float[] size1004 =  ViewSize();
                Picasso.get()
                            .load(list.get(position).getThumbnailUrl())
                            .resize((int) size1004[0], (int) (size1004[1]))
                            .into(videothumbnailImageView);
                Picasso.get()
                        .load(list.get(position).getChannelThumbnail())
                        .transform(new CropCircleTransformation())
                        .into(channelThumbnailImageView);
                    videoTitleTextView.setText(list.get(position).getTitle());
                    channelTitleTextView.setText(list.get(position).getChannelTitle());

                OnClickView(youtubeThumbnailView, position);
                return youtubeThumbnailView;

                default:
                    return null;
        }


    }

    private void OnClickView(View view, final int position){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, _YoutubeActivity.class);
                intent.putExtra("videoId", list.get(position).getVideoId());
                ((MainActivity)context).startActivity(intent);
            }
        });
    }

    private float[] ViewSize(){
        Display display = ((MainActivity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;


        float newHeight = width / 1.618f;

        float[] returnSize = new float[2];
        returnSize[0] = (float) width;
        returnSize[1] = newHeight;
        return returnSize;
    }

}
