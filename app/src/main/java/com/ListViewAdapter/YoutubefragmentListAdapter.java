package com.ListViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.Beans.YoutubeChannelBean;
import com.Beans.YoutubeFragBean;
//import com.MainFragments.ChannelList;
import com.MainFragments.YoutubeFragment;
import com.SpinnerAdapter.channelSpinnerAdapter;
import com.parkbros.jhmovienote.MainActivity;
import com.parkbros.jhmovienote.R;
import com.parkbros.jhmovienote._YoutubeActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import static com.MainFragments.YoutubeFragment.pg;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class YoutubefragmentListAdapter extends BaseAdapter {
    Context context;
    ArrayList<YoutubeFragBean> list;
    YoutubeFragment youtubeFragment;



    public YoutubefragmentListAdapter(Context context, ArrayList<YoutubeFragBean> list, YoutubeFragment youtubeFragment ) {
        this.context = context;
        this.list = list;
        this.youtubeFragment = youtubeFragment;

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

                final ProgressBar pb = (ProgressBar) youtubeRandomThumbnailView.findViewById(R.id.progressBar);
                ImageView randomVideothumbnailImageView = (ImageView) youtubeRandomThumbnailView.findViewById(R.id.youtubeImageView);
                final ImageView randomChannelThumbnailImageView = (ImageView) youtubeRandomThumbnailView.findViewById(R.id.channelThumbnailImageView);
                TextView randomVideoTitleTextView = (TextView) youtubeRandomThumbnailView.findViewById(R.id.titleTextView);
                TextView randomChannelTitleTextView = (TextView) youtubeRandomThumbnailView.findViewById(R.id.channelTitleTextView);

                float[] size1002 =  ViewSize();
                Picasso.get()
                        .load(list.get(position).getThumbnailUrl())
                        .resize((int) size1002[0], (int) (size1002[1]))
                        .into(randomVideothumbnailImageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                pb.setVisibility( View.GONE );
                            }

                            @Override
                            public void onError(Exception e) {
                                Log.e( "load image err: ", e.toString() );
                            }
                        });
                Picasso.get()
                        .load(list.get(position).getChannelThumbnail())
                        .transform(new CropCircleTransformation())
                        .into(randomChannelThumbnailImageView);//채널 썸네일
                randomVideoTitleTextView.setText(list.get(position).getTitle());
                randomChannelTitleTextView.setText(list.get(position).getChannelTitle());


                OnClickView(youtubeRandomThumbnailView, position);
                return youtubeRandomThumbnailView;
            case 1003:
                // ----- channel list ------
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

                Spinner channelListSpinner  = ( Spinner ) selectHeaderView.findViewById(R.id.dropdownList);
                channelSpinnerAdapter _channelSpinnerAdapter = new channelSpinnerAdapter( context, list.get( position ).getChannelList() , this );
                channelListSpinner.setAdapter( _channelSpinnerAdapter );
                channelListSpinner.setSelection(0);

                channelListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.e("item select", "select : " +list.size() );
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }) ;

                return selectHeaderView;
            case 1004:
                View youtubeThumbnailView = View.inflate(context, R.layout.container_youtubefrag_thumbnail, null);
                final ProgressBar pb4 = (ProgressBar) youtubeThumbnailView.findViewById(R.id.progressBar);
                ImageView videothumbnailImageView = (ImageView) youtubeThumbnailView.findViewById(R.id.youtubeImageView);
                ImageView channelThumbnailImageView = (ImageView) youtubeThumbnailView.findViewById(R.id.channelThumbnailImageView);
                TextView videoTitleTextView = (TextView) youtubeThumbnailView.findViewById(R.id.titleTextView);
                TextView channelTitleTextView = (TextView) youtubeThumbnailView.findViewById(R.id.channelTitleTextView);

                float[] size1004 =  ViewSize();
                Picasso.get()
                            .load(list.get(position).getThumbnailUrl())
                            .resize((int) size1004[0], (int) (size1004[1]))
                            .into(videothumbnailImageView, new Callback() {
                                @Override
                                public void onSuccess() {
                                    pb4.setVisibility( View.GONE );
                                }

                                @Override
                                public void onError(Exception e) {

                                }
                            });
                Picasso.get()
                        .load(list.get(position).getChannelThumbnail())
                        .transform(new CropCircleTransformation())
                        .into(channelThumbnailImageView);
                    videoTitleTextView.setText(list.get(position).getTitle());
                    channelTitleTextView.setText(list.get(position).getChannelTitle());

                OnClickView(youtubeThumbnailView, position);
                return youtubeThumbnailView;
            case 1005 :

                View seemore = View.inflate(context, R.layout.container_youtube_channel_seemore, null);
                seemore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pg ++ ;
                        ( (YoutubeFragment) youtubeFragment )._initialLoader("select", list.get(0).getChannelId());
                    }
                });
                return seemore;
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
