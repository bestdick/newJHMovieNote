package com.SpinnerAdapter;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.Beans.YoutubeChannelBean;
import com.Beans.YoutubeFragBean;
import com.ListViewAdapter.YoutubefragmentListAdapter;
import com.parkbros.jhmovienote.MainActivity;
import com.parkbros.jhmovienote.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class channelSpinnerAdapter extends BaseAdapter {
    Context context;
    ArrayList<YoutubeChannelBean> list;
    YoutubefragmentListAdapter youtubefragmentListAdapter;

    public channelSpinnerAdapter(Context context, ArrayList<YoutubeChannelBean> list, YoutubefragmentListAdapter youtubefragmentListAdapter) {
        this.context = context;
        this.list = list;
        this.youtubefragmentListAdapter = youtubefragmentListAdapter;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.container_youtube_channelid, null);
        TextView channelName = ( TextView ) v.findViewById(R.id.channelTitleTextView);
        ImageView channeThumbnail = ( ImageView ) v.findViewById(R.id.channelThumbnailImageView);

        channelName.setText( list.get(i).getChannelTitle() );

        Picasso.get()
                .load(list.get(i).getChannelThumbnailUrl())
                .transform(new CropCircleTransformation())
                .into(channeThumbnail);

        return v ;
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
