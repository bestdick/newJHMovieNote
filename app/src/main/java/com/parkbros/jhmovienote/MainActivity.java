package com.parkbros.jhmovienote;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.Adapter.MainViewPagerAdapter;
import com.Function._ServerCommunicator;
import com.MainFragments.BoxOfficeFragment;
import com.MainFragments.MainFragment;
import com.MainFragments.SettingFragment;
import com.MainFragments.WriteFragment;
import com.MainFragments.YoutubeFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import org.json.JSONException;
import org.json.JSONObject;

import static com.StaticValues.StaticValues.LOGIN_TYPE_DEVICE;
import static com.StaticValues.StaticValues.baseURL;
import static com.StaticValues.StaticValues.youtubeAPIKey;



public class MainActivity extends AppCompatActivity implements BoxOfficeFragment.OnListFragmentInteractionListener<Object> , MainFragment.OnListFragmentInteractionListener<Object>,
        YoutubeFragment.OnListFragmentInteractionListener<Object>, WriteFragment.OnListFragmentInteractionListener<Object> , SettingFragment.OnListFragmentInteractionListener<Object> {


    FragmentManager fragmentManager;
//    FragmentTransaction fragmentTransaction;

    final private int REQUEST_WRITE_INTENT = 3670;
    int tabPosition;

    ProgressBar progressBar;
    LinearLayout container;
    ViewPager mViewPager;
    TabLayout mTabLayout;
    MainViewPagerAdapter mainViewPagerAdapter;
    TextView naviTextView;

    public static String LoginType;
    public static int uid;
    public static String deviceId;
    public static String deviceToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);



/*
        Intent intent = getIntent();
        //LoginType = LOGIN_TYPE_DEVICE;
        //uid = intent.getIntExtra("uid", 0);
        deviceId = intent.getStringExtra("deviceId");
        deviceToken = intent.getStringExtra("deviceToken");

        Log.e("device id ", deviceId);
        Log.e("uid ", String.valueOf( uid ) );

        container = (LinearLayout) findViewById(R.id.viewPager);
        naviTextView = (TextView) findViewById(R.id.naviTextView);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.GONE);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);


        fragmentManager = getSupportFragmentManager();
        FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();

        tabPosition=0;
        BoxOfficeFragment boxOfficeFragment = new BoxOfficeFragment();
        boxOfficeFragment.newInstance("null", "null", progressBar);
        fragmentTransaction.add(R.id.viewPager, boxOfficeFragment);
        fragmentTransaction.commit();



*/

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                mViewPager.setCurrentItem(tab.getPosition());
                int page = tab.getPosition();
                if(page == 0){
                    Log.e("TAB", "1");
                    // box offfice fragment
                    tabPosition=0;
                    progressBar.setVisibility(View.VISIBLE);
                    naviTextView.setText("BoxOffice");
                    FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
                    BoxOfficeFragment boxOfficeFragment = new BoxOfficeFragment();
                    boxOfficeFragment.newInstance("null", "null", progressBar);
                    fragmentTransaction.replace(R.id.viewPager, boxOfficeFragment);
                    fragmentTransaction.commit();


                }else if(page == 1){
                    Log.e("TAB", "2");
                    // youtube fragment
                    tabPosition=1;
                    naviTextView.setText("Youtube");
                    progressBar.setVisibility(View.VISIBLE);
                    FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
                    YoutubeFragment youtubeFragment = new YoutubeFragment();
                    youtubeFragment.newInstance("null", "null", progressBar);
                    fragmentTransaction.replace(R.id.viewPager, youtubeFragment);
                    fragmentTransaction.commit();


                }else if (page == 2){
                    Log.e("TAB", "3");
                    // main fragment
                    tabPosition=2;
                    naviTextView.setText("Main");
                    progressBar.setVisibility(View.VISIBLE);
                    FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
                    MainFragment mainFragment = new MainFragment();
                    mainFragment.newInstance("null", "null", progressBar);
                    fragmentTransaction.replace(R.id.viewPager, mainFragment);
                    fragmentTransaction.commit();

                }
                else if(page == 3){
                    Log.e("TAB", "4");
//                    //setting fragment
//                    FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
//                    WriteFragment writeFragment = new WriteFragment();
//                    settingFragment.newInstance("null", "null");
//                    fragmentTransaction.replace(R.id.viewPager, settingFragment);
//                    fragmentTransaction.commit();
                    Intent intent = new Intent(MainActivity.this, WriteActivity.class);
                    intent.putExtra("tabPosition", tabPosition);
                    startActivityForResult(intent, REQUEST_WRITE_INTENT);
                }else{
                    Log.e("TAB", "5");
                    //setting fragment
                    tabPosition=4;
                    naviTextView.setText("Setting");
                    FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
                    SettingFragment settingFragment = new SettingFragment();
                    settingFragment.newInstance("null", "null");
                    fragmentTransaction.replace(R.id.viewPager, settingFragment);
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

    @Override
    public void onListFragmentInteraction(String tag, Object data) {
        //do some stuff with the data
        Log.e("Tag ::", tag.toString());
        Log.e("Object :: ", data.toString());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_WRITE_INTENT && resultCode == RESULT_OK){
            String result =data.getStringExtra("result");
            if(result.equals("success")){
                progressBar.setVisibility(View.VISIBLE);
                mTabLayout.getTabAt(2).select();
                naviTextView.setText("Main");

                /*FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
                MainFragment mainFragment = new MainFragment();
                mainFragment.newInstance("null", "null", progressBar);
                fragmentTransaction.replace(R.id.viewPager, mainFragment);
                fragmentTransaction.commit();*/
            }else
//                (result.equals("cancel"))
            {
                int tabPosition = data.getIntExtra("tabPosition", 2);
                Log.e("tabPosition", String.valueOf(tabPosition));
                progressBar.setVisibility(View.VISIBLE);
                mTabLayout.getTabAt(tabPosition).select();
//                previousTabPosition(tabPosition);
//                naviTextView.setText(tabPositionTitle(tabPosition));
//
//                FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
//                MainFragment mainFragment = new MainFragment();
//                mainFragment.newInstance("null", "null", progressBar);
//                fragmentTransaction.replace(R.id.viewPager, mainFragment);
//                fragmentTransaction.commit();

            }
        }else{

        }
    }

    private void previousTabPosition(int page){
        if(page == 0){
            Log.e("TAB", "1");
            // box offfice fragment

            progressBar.setVisibility(View.VISIBLE);
            naviTextView.setText("BoxOffice");
            FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
            BoxOfficeFragment boxOfficeFragment = new BoxOfficeFragment();
            boxOfficeFragment.newInstance("null", "null", progressBar);
            fragmentTransaction.replace(R.id.viewPager, boxOfficeFragment);
            fragmentTransaction.commit();


        }else if(page == 1){
            Log.e("TAB", "2");
            // youtube fragment
            naviTextView.setText("Youtube");
            progressBar.setVisibility(View.VISIBLE);
            FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
            YoutubeFragment youtubeFragment = new YoutubeFragment();
            youtubeFragment.newInstance("null", "null", progressBar);
            fragmentTransaction.replace(R.id.viewPager, youtubeFragment);
            fragmentTransaction.commit();


        }else if (page == 2){
            Log.e("TAB", "3");
            // main fragment
            naviTextView.setText("Main");
            progressBar.setVisibility(View.VISIBLE);
            FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
            MainFragment mainFragment = new MainFragment();
            mainFragment.newInstance("null", "null", progressBar);
            fragmentTransaction.replace(R.id.viewPager, mainFragment);
            fragmentTransaction.commit();

        }
        else if(page == 3){
            Log.e("TAB", "4");
//                    //setting fragment
//                    FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
//                    WriteFragment writeFragment = new WriteFragment();
//                    settingFragment.newInstance("null", "null");
//                    fragmentTransaction.replace(R.id.viewPager, settingFragment);
//                    fragmentTransaction.commit();
            Intent intent = new Intent(MainActivity.this, WriteActivity.class);
            intent.putExtra("tabPosition", tabPosition);
            startActivityForResult(intent, REQUEST_WRITE_INTENT);
        }else{
            Log.e("TAB", "5");
            //setting fragment
            progressBar.setVisibility(View.GONE);
            naviTextView.setText("Setting");
            FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
            SettingFragment settingFragment = new SettingFragment();
            settingFragment.newInstance("null", "null");
            fragmentTransaction.replace(R.id.viewPager, settingFragment);
            fragmentTransaction.commit();
        }

    }
}
