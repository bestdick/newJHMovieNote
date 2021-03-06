package com.MainFragments;

import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Debug;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Beans.BoxOfficeBean;
import com.Beans.YoutubeChannelBean;
import com.Beans.YoutubeFragBean;
import com.Function.OnFragmentInteractionListener;
import com.Function._ServerCommunicator;
import com.ListViewAdapter.YoutubefragmentListAdapter;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.parkbros.jhmovienote.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.Inflater;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import static com.StaticValues.StaticValues.baseURL;
import static com.StaticValues.StaticValues.youtubeAPIKey;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link YoutubeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link YoutubeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YoutubeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static ProgressBar progressBar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public YoutubeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YoutubeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YoutubeFragment newInstance(String param1, String param2, ProgressBar _progressBar) {
        progressBar = _progressBar;
        YoutubeFragment fragment = new YoutubeFragment();
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
//    YouTubePlayerView youTubePlayerView;
//    Button btn;
//    YouTubePlayer.OnInitializedListener listener;

//    LinearLayout selectedChannellistLinearLayout;
//    ScrollView scrollView;

    SwipeRefreshLayout swipeRefreshLayout;
    View rootView;
    ListView listView;
    YoutubefragmentListAdapter listAdapter;
    ArrayList<YoutubeFragBean> list;

    String channelId;
    boolean hitBottom;
    public static int pg = 1 ;


    LinearLayout channelList_linearLayout ;
    HorizontalScrollView horizontalScrollView2 ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =inflater.inflate(R.layout.fragment_youtube, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);

        horizontalScrollView2 = ( HorizontalScrollView ) rootView.findViewById(R.id.horizontalScrollView2);
        channelList_linearLayout = ( LinearLayout ) rootView.findViewById(R.id.horizontalScrollView);

        channelId = null;
        list = new ArrayList<>();
        hitBottom = false;
        listView = (ListView)rootView.findViewById(R.id.youtubeListView);
        listAdapter = new YoutubefragmentListAdapter(getContext(), list, this );
        listView.setAdapter(listAdapter);

        /*initialLoader(rootView);
        FetchMoreItemToList();*/

        _initialLoader(  "rand", "");


//        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.e("click : ", String.valueOf(i));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("refresh","refresh");

                swipeRefreshLayout.setRefreshing( false );
            }
        });
        return rootView;
    }

    public void _initialLoader(final String _code , final String channel_id ){
        String url = baseURL;
        String mode = "youtube";
        String code = _code;
        JSONObject data = new JSONObject();
        if( !_code.equals("rand")){
            try {
                data.put("channel_id", channel_id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String stringified_data = data.toString();

        _ServerCommunicator serverCommunicator = new _ServerCommunicator(getContext(), url);
        serverCommunicator._Communicator(new _ServerCommunicator.VolleyCallback() {
            @Override
            public void onSuccess(String result, String connection) {
                Log.e("RESUT : ", result);
                if( connection == "success" ){
                    if( !_code.equals("rand")) {
                        if ( pg != 1) {

                            Log.e("size of list", String.valueOf( list.size() ) + " // " + String.valueOf( pg ) );
                            list.remove( list.size() - 1 );
                            listAdapter.notifyDataSetChanged();
                        }
                    }
                    Log.e("size of list", String.valueOf( list.size() ) + " // " + String.valueOf( pg ) );
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray channel_list = jsonObject.getJSONObject("msg").getJSONArray("channel_list");
                        draw_channel_list( _code, channel_list ); // drawing channellist ...
                        JSONArray movie_list = jsonObject.getJSONObject("msg").getJSONArray("video_list");
                        draw_vieo_list( movie_list );
                    }catch (JSONException e){
                        Log.e("JSON Err", e.toString() );
                    }
                    // --- add see more on if code is not rand
                    if( !_code.equals("rand")) {
                        YoutubeFragBean footer = new YoutubeFragBean(  // each channel header ....
                                1005, channel_id, "", "", "", "", "", "", null);
                        list.add(footer);
                    }
                    listAdapter.notifyDataSetChanged();
                    /*try {
                        JSONObject jsonObject = new JSONObject(result);
                        int res = jsonObject.getInt("res");


                        if( res == 0 ) {

                            JSONArray _channel_list = jsonObject.getJSONObject("msg").getJSONArray("channel_list");
                            ArrayList<YoutubeChannelBean> channel_list  = new ArrayList<YoutubeChannelBean>();
                            for( int i = 0 ; i < _channel_list.length(); i ++){
                                YoutubeChannelBean item = new YoutubeChannelBean(_channel_list.getJSONObject(i).getString("channelid"), _channel_list.getJSONObject(i).getString("channelname"), _channel_list.getJSONObject(i).getString("thumbnail"));
                                channel_list.add( item );
                            }

                            JSONObject random_video = jsonObject.getJSONObject("msg").getJSONObject("random_item");
                            JSONArray movie_list = jsonObject.getJSONObject("msg").getJSONArray("movie_list");

                            YoutubeFragBean randomItemHeader = new YoutubeFragBean(
                                    1001, null, null, null,
                                    null, null,null,null, null );

                            YoutubeFragBean randomItemMain = new YoutubeFragBean(
                                    1002, random_video.getString("channel_id"), random_video.getString("channel_thumbnail"),
                                    random_video.getString("title"), random_video.getString("description"),
                                    random_video.getString("video_thumbnail"), random_video.getString("channel_title"),
                                    random_video.getString("video_id"), null );

                            YoutubeFragBean  selectHeader = new YoutubeFragBean(  // each channel header ....
                                    1003, random_video.getString("channel_id"), random_video.getString("channel_thumbnail"),
                                    random_video.getString("title"), random_video.getString("description"),
                                    random_video.getString("video_thumbnail"), random_video.getString("channel_title"),
                                    random_video.getString("video_id") , channel_list );



                            list.add(randomItemHeader);
                            list.add( randomItemMain );
                            list.add( selectHeader ) ;

                            for( int i = 0 ; i < movie_list.length() ; i ++ ) {
                                YoutubeFragBean selectItem = new YoutubeFragBean(
                                        1004, movie_list.getJSONObject(i).getString("channel_id"), movie_list.getJSONObject(i).getString("channel_thumbnail"),
                                        movie_list.getJSONObject(i).getString("title"), movie_list.getJSONObject(i).getString("description"),
                                        movie_list.getJSONObject(i).getString("video_thumbnail"), movie_list.getJSONObject(i).getString("channel_title"),
                                        movie_list.getJSONObject(i).getString("video_id"), null );
                                list.add( selectItem ) ;
                            }
                            listAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                }
                progressBar.setVisibility(View.GONE);
            }

        }, mode, code, String.valueOf( pg ), stringified_data);
    }
    private void draw_vieo_list ( JSONArray movie_list ) throws JSONException {
        for( int i = 0 ; i < movie_list.length() ; i ++ ) {
            YoutubeFragBean selectItem = new YoutubeFragBean(
                    1004, movie_list.getJSONObject(i).getString("channel_id"), movie_list.getJSONObject(i).getString("channel_thumbnail"),
                    movie_list.getJSONObject(i).getString("title"), movie_list.getJSONObject(i).getString("description"),
                    movie_list.getJSONObject(i).getString("video_thumbnail"), movie_list.getJSONObject(i).getString("channel_title"),
                    movie_list.getJSONObject(i).getString("video_id"), null );
            list.add( selectItem ) ;
        }
    }
    private void draw_channel_list( String _code , JSONArray channel_list ) throws JSONException {
        // --- 추천 5
        channelList_linearLayout.removeAllViews();// set to initial;


        View channel_title_view  = getLayoutInflater().inflate(R.layout.container_youtube_channelid, null);
        TextView title_textView = (TextView) channel_title_view.findViewById(R.id.channelTitleTextView);
        ImageView channel_imageView = (ImageView) channel_title_view.findViewById(R.id.channelThumbnailImageView);

        title_textView.setText( " 추천 ");
        channelList_linearLayout.addView( channel_title_view );
        select_channel(channel_title_view, "rand" );

        if( _code.equals("rand") ) {
            channel_title_view.setBackground(getResources().getDrawable(R.drawable.channel_container_box));
        }


        for( int i = 0 ; i < channel_list.length() ; i ++ ){

            View _channel_title_view  = getLayoutInflater().inflate(R.layout.container_youtube_channelid, null);
            TextView _title_textView = (TextView) _channel_title_view.findViewById(R.id.channelTitleTextView);
            ImageView _channel_imageView = (ImageView) _channel_title_view.findViewById(R.id.channelThumbnailImageView);


            _title_textView.setText( channel_list.getJSONObject(i).getString("channelname") ) ;


            Picasso.get()
                    .load( channel_list.getJSONObject(i).getString("thumbnail") )
                    .transform(new CropCircleTransformation())
                    .into( _channel_imageView ) ;//채널 썸네일
            channelList_linearLayout.addView( _channel_title_view );

            String channel_id = channel_list.getJSONObject(i).getString("channel_id");
            select_channel( _channel_title_view, channel_id );

            if( !_code.equals("rand") ) {
                if( i == 0 ) {
                    _channel_title_view.setBackground(getResources().getDrawable(R.drawable.channel_container_box));
                }
            }
        }

        //channelList_linearLayout.scrollTo(0, 0 );
        horizontalScrollView2.scrollTo(0, 0 );

    }

    private void select_channel( View channel_title_view , final String channel_id){
        channel_title_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.e("channel_clicked :: ",  channel_id );
                String code = ( channel_id.equals("rand") ) ? "rand" : "select";
                pg = 1 ;
                list.clear();
                _initialLoader( code , channel_id );


            }
        });
    }





    private void FetchMoreItemToList(){
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if(hitBottom == false){
                    if (!view.canScrollList(View.SCROLL_AXIS_VERTICAL) && scrollState == SCROLL_STATE_IDLE) {
                        //When List reaches bottom and the list isn't moving (is idle)
                        Log.e("LAST ROW", "LAST :: " + channelId);
                        progressBar.setVisibility(View.VISIBLE);
                        hitBottom = true;
                        //getMoreListOfSelectedChannelId();

                    }
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                int lastIndex = view.getLastVisiblePosition() + 1;
//                if(hitBottom == false){
//                    if (lastIndex == totalItemCount) { //showing last row
//                        hitBottom = true;
//                        Log.e("LAST ROW", String.valueOf(totalItemCount) + " //// " + lastIndex);
//                    }
//                }
            }
        });
    }
    private String request_type(String data){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("request_type", data);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return jsonObject.toString();
        }
    }
    private String requestData(int type, JSONObject data){
        JSONObject jsonObject = new JSONObject();
        try {
            if(type == 1001){
                jsonObject.put("request_data", null);
                return jsonObject.toString();
            }else if(type == 1002){
                jsonObject.put("request_data", data);
                return jsonObject.toString();
            }else{
                jsonObject.put("request_data", data);
                return jsonObject.toString();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return jsonObject.toString();
        }
    }
  /*  private void initialLoader(final View rootView){

        String URL = baseURL+"/protocol/protocol_main.php";

        JSONObject jsonObject_rt = new JSONObject();
        try {
            jsonObject_rt.put("access", "jhmn");
            jsonObject_rt.put("request_type", 20000); //
            jsonObject_rt.put("value", 1); // update
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String request_type  = jsonObject_rt.toString();

        JSONObject jsonObject_rd = new JSONObject();
        try {
            jsonObject_rd.put("type", "a");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String request_data = jsonObject_rd.toString();

        _ServerCommunicator serverCommunicator = new _ServerCommunicator(getContext(), URL);
        serverCommunicator.Communicator(new _ServerCommunicator.VolleyCallback() {
            @Override
            public void onSuccess(String result, String connection) {
                Log.e("SELECT RANDOMVIDEO", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    YoutubeFragBean randomItemHeader = new YoutubeFragBean(
                            1001, null,null,null,null,null,null,null);
                    list.add(randomItemHeader);
                    YoutubeFragBean randomItemMain = new YoutubeFragBean(
                            1002,
                            jsonObject.getJSONObject("randomVideo").getString("j_y_s_channelid"),
                            jsonObject.getJSONObject("randomVideo").getString("channelThumbnail"),
                            jsonObject.getJSONObject("randomVideo").getJSONObject("j_y_s_information").getString("vTitle"),
                            jsonObject.getJSONObject("randomVideo").getJSONObject("j_y_s_information").getString("vDescription"),
                            jsonObject.getJSONObject("randomVideo").getJSONObject("j_y_s_information").getString("vThumbnailUrl"),
                            jsonObject.getJSONObject("randomVideo").getJSONObject("j_y_s_information").getString("vChannelTitle"),
                            jsonObject.getJSONObject("randomVideo").getJSONObject("j_y_s_information").getString("vVideoID")
                    );
                    list.add(randomItemMain);
//                    playYoutubeVideo(item);
//                    ImageView thumbnailImageView = (ImageView) rootView.findViewById(R.id.thumbnailImageView);
//                    TextView titleTextView = (TextView) rootView.findViewById(R.id.ytTitleTextView);
//                    TextView authorTextView = (TextView) rootView.findViewById(R.id.ytAuthorTextView);
//                    Picasso.get()
//                            .load(item.getChannelThumbnail())
//                            .transform(new CropCircleTransformation())
//                            .into(thumbnailImageView);
//                    titleTextView.setText(item.getTitle());
//                    authorTextView.setText(item.getChannelTitle());
                    // †††††††††††††† 여기까지는 Randome Channel 에 완한 JSON



                    YoutubeFragBean channelItemHeader = new YoutubeFragBean(
                            1003, null,null,null,null,null,null,null);
                    list.add(channelItemHeader);

                    JSONArray selectedChanneljsonArray = jsonObject.getJSONArray("selectedChannelList");
                    channelId = selectedChanneljsonArray.getJSONObject(0).getString("j_y_s_channelid");
                    for(int i = 0 ; i < selectedChanneljsonArray.length(); i++){
                        YoutubeFragBean youtubeFragBean = new YoutubeFragBean(
                                1004,
                                selectedChanneljsonArray.getJSONObject(i).getString("j_y_s_channelid"),
                                selectedChanneljsonArray.getJSONObject(i).getString("channelThumbnail"),
                                selectedChanneljsonArray.getJSONObject(i).getJSONObject("j_y_s_information").getString("vTitle"),
                                selectedChanneljsonArray.getJSONObject(i).getJSONObject("j_y_s_information").getString("vDescription"),
                                selectedChanneljsonArray.getJSONObject(i).getJSONObject("j_y_s_information").getString("vThumbnailUrl"),
                                selectedChanneljsonArray.getJSONObject(i).getJSONObject("j_y_s_information").getString("vChannelTitle"),
                                selectedChanneljsonArray.getJSONObject(i).getJSONObject("j_y_s_information").getString("vVideoID")
                        );
                        list.add(youtubeFragBean);


//
//                        View youtubethumbnailView  = getLayoutInflater().inflate(R.layout.container_youtubefrag_thumbnail, null);
//                        ImageView videothumbnailImageView = (ImageView) youtubethumbnailView.findViewById(R.id.youtubeImageView);
//                        ImageView channelThumbnailImageView = (ImageView) youtubethumbnailView.findViewById(R.id.channelThumbnailImageView);
//                        TextView videoTitleTextView = (TextView) youtubethumbnailView.findViewById(R.id.titleTextView);
//                        TextView channelTitleTextView = (TextView) youtubethumbnailView.findViewById(R.id.channelTitleTextView);
//                        float[] size =  ViewSize();
//                        Picasso.get()
//                                .load(selectedChannelList.get(i).getThumbnailUrl())
//                                .resize((int) size[0], (int) (size[1]))
//                                .into(videothumbnailImageView);
//                        Picasso.get()
//                                .load(selectedChannelList.get(i).getChannelThumbnail())
//                                .transform(new CropCircleTransformation())
//                                .into(channelThumbnailImageView);
//                        videoTitleTextView.setText(selectedChannelList.get(i).getTitle());
//                        videoTitleTextView.setText(selectedChannelList.get(i).getTitle());
//                        channelTitleTextView.setText(selectedChannelList.get(i).getChannelTitle());

//                        selectedChannellistLinearLayout.addView(youtubethumbnailView);
                    }
                    listAdapter.notifyDataSetChanged();

                    // †††††††††††††† 여기까지는  선택된 Channel의 정보를 가져오는 JSON

//                    Button button = new Button(getContext());
////                    selectedChannellistLinearLayout.addView(button);
//                    button.setText("qweqweqwe");
//                    button.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
////                            selectedChannellistLinearLayout.removeViewAt(selectedChannellistLinearLayout.getChildCount()-1);
//                        }
//                    });

                    //††††††††††††††footer
//                    fetChMoreList();


horizontalScrollView
                    LinearLayout channelTitleListLinearLayout = (LinearLayout) rootView.findViewById(R.id.youtuber_list_container);
                    ArrayList<YoutubeChannelBean> channelList = new ArrayList<>();
                    JSONArray AllChannelJsonArray = jsonObject.getJSONArray("allChannelIds");
                    for(int i = 0; i < AllChannelJsonArray.length(); i ++){
                        if(i == 0){
                            channelId = AllChannelJsonArray.getJSONObject(i).getString("j_y_c_channelid");
                        }
                        YoutubeChannelBean youtubeChannelBean = new YoutubeChannelBean(
                                AllChannelJsonArray.getJSONObject(i).getString("j_y_c_channelid"),
                                AllChannelJsonArray.getJSONObject(i).getString("j_y_c_channelname"),
                                AllChannelJsonArray.getJSONObject(i).getString("j_y_c_thumbnailurl")
                        );
                        channelList.add(youtubeChannelBean);

                        View youtubeChannelTitleView  = getLayoutInflater().inflate(R.layout.container_youtube_channelid, null);
                        TextView youtubeChannelTitleTextView = (TextView) youtubeChannelTitleView.findViewById(R.id.channelTitleTextView);
                        ImageView channelThumbnailImageView = (ImageView) youtubeChannelTitleView.findViewById(R.id.channelThumbnailImageView);
                        youtubeChannelTitleTextView.setText(channelList.get(i).getChannelTitle());
                        final String channelThumbnailUrl = channelList.get(i).getChannelThumbnailUrl();
                        Picasso.get()
                                .load(channelThumbnailUrl)
                                .transform(new CropCircleTransformation())
                                .into(channelThumbnailImageView);

                        //아이템을 클릭했을때 작동되는
                        final String channelid = channelList.get(i).getChannelId();
                        youtubeChannelTitleView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                channelId = channelid;
                                hitBottom = false;
                                getListOfSelectedChannelId();
                                progressBar.setVisibility(View.VISIBLE);
//                                Toast.makeText(getContext(), channelid, Toast.LENGTH_SHORT).show();
                            }
                        });

                        channelTitleListLinearLayout.addView(youtubeChannelTitleView);
                    }
                    // †††††††††††††† 여기까지는  모든 Channel 타이틀을 가져오는 JSON
                    progressBar.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, request_type, request_data);
    }*/

    /*private void getListOfSelectedChannelId(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("channelId", channelId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String URL = baseURL+"/youtubeFragment";
        String request_type = request_type("secondary");
        String request_data = requestData(1002, jsonObject);
        _ServerCommunicator serverCommunicator = new _ServerCommunicator(getContext(), URL);
        serverCommunicator.Communicator(new _ServerCommunicator.VolleyCallback() {
            @Override
            public void onSuccess(String result, String connection) {
                Log.e("LIST OF SELECTED ", result);
                try {
                    list.clear();
                    JSONObject jsonObject = new JSONObject(result);
                    YoutubeFragBean randomItemHeader = new YoutubeFragBean(
                            1001, null,null,null,null,null,null,null);
                    list.add(randomItemHeader);
                    YoutubeFragBean randomItemMain = new YoutubeFragBean(
                            1002,
                            jsonObject.getJSONObject("randomVideo").getString("j_y_s_channelid"),
                            jsonObject.getJSONObject("randomVideo").getString("channelThumbnail"),
                            jsonObject.getJSONObject("randomVideo").getJSONObject("j_y_s_information").getString("vTitle"),
                            jsonObject.getJSONObject("randomVideo").getJSONObject("j_y_s_information").getString("vDescription"),
                            jsonObject.getJSONObject("randomVideo").getJSONObject("j_y_s_information").getString("vThumbnailUrl"),
                            jsonObject.getJSONObject("randomVideo").getJSONObject("j_y_s_information").getString("vChannelTitle"),
                            jsonObject.getJSONObject("randomVideo").getJSONObject("j_y_s_information").getString("vVideoID")
                    );
                    list.add(randomItemMain);

                    YoutubeFragBean channelItemHeader = new YoutubeFragBean(
                            1003, null,null,null,null,null,null,null);
                    list.add(channelItemHeader);

                    JSONArray selectedChanneljsonArray = jsonObject.getJSONArray("selectedChannelList");
                    for(int i = 0 ; i < selectedChanneljsonArray.length(); i++){
                        YoutubeFragBean youtubeFragBean = new YoutubeFragBean(
                                1004,
                                selectedChanneljsonArray.getJSONObject(i).getString("j_y_s_channelid"),
                                selectedChanneljsonArray.getJSONObject(i).getString("channelThumbnail"),
                                selectedChanneljsonArray.getJSONObject(i).getJSONObject("j_y_s_information").getString("vTitle"),
                                selectedChanneljsonArray.getJSONObject(i).getJSONObject("j_y_s_information").getString("vDescription"),
                                selectedChanneljsonArray.getJSONObject(i).getJSONObject("j_y_s_information").getString("vThumbnailUrl"),
                                selectedChanneljsonArray.getJSONObject(i).getJSONObject("j_y_s_information").getString("vChannelTitle"),
                                selectedChanneljsonArray.getJSONObject(i).getJSONObject("j_y_s_information").getString("vVideoID")
                        );
                        list.add(youtubeFragBean);

                    }
                    listAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, request_type, request_data);
    }
    private void getMoreListOfSelectedChannelId( ){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("channelId", channelId);
            jsonObject.put("page", list.size()-3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String URL = baseURL+"/youtubeFragment";
        String request_type = request_type("secondary_more");
        String request_data = requestData(1003, jsonObject);
        _ServerCommunicator serverCommunicator = new _ServerCommunicator(getContext(), URL);
        serverCommunicator.Communicator(new _ServerCommunicator.VolleyCallback() {
            @Override
            public void onSuccess(String result, String connection) {
                Log.e("LIST OF SELECTED ", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);

                    JSONArray selectedChanneljsonArray = jsonObject.getJSONArray("selectedChannelList");
                    for(int i = 0 ; i < selectedChanneljsonArray.length(); i++){
                        YoutubeFragBean youtubeFragBean = new YoutubeFragBean(
                                1004,
                                selectedChanneljsonArray.getJSONObject(i).getString("j_y_s_channelid"),
                                selectedChanneljsonArray.getJSONObject(i).getString("channelThumbnail"),
                                selectedChanneljsonArray.getJSONObject(i).getJSONObject("j_y_s_information").getString("vTitle"),
                                selectedChanneljsonArray.getJSONObject(i).getJSONObject("j_y_s_information").getString("vDescription"),
                                selectedChanneljsonArray.getJSONObject(i).getJSONObject("j_y_s_information").getString("vThumbnailUrl"),
                                selectedChanneljsonArray.getJSONObject(i).getJSONObject("j_y_s_information").getString("vChannelTitle"),
                                selectedChanneljsonArray.getJSONObject(i).getJSONObject("j_y_s_information").getString("vVideoID")
                        );
                        list.add(youtubeFragBean);

                    }
                    listAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    hitBottom = false;

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, request_type, request_data);
    }*/
    private float[] ViewSize(){
        Display display = getActivity().getWindowManager().getDefaultDisplay();
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
    public void playYoutubeVideo(final YoutubeFragBean item){
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        youTubePlayerFragment.initialize(youtubeAPIKey, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                                youTubePlayer.setShowFullscreenButton(false);
                youTubePlayer.cueVideo(item.getVideoId());// cueVideo  == 지정한 동영상의 미리보기 이미지를 로드하고 플레이어가 동영상을 재생하도록 준비하지만 play()를 호출하기 전에는 동영상 스트림을 다운로드하지 않습니다.
                youTubePlayer.play();
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.e("Name of Screen", "초기화 실패");

            }
        });
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_layout, youTubePlayerFragment).commit();
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
        Log.e("YoutubeFragment :: ", "Attached");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.e("YoutubeFragment :: ", "Detach");
    }


//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }

    public interface OnListFragmentInteractionListener<T> {
        void onListFragmentInteraction(String tag, T data);
    }

}
