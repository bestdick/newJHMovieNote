package com.NewMovieNote.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.MainFragments.BoxOfficeFragment;
import com.MainFragments.MainFragment;
import com.MainFragments.SettingFragment;
import com.MainFragments.YoutubeFragment;
import com.NewMovieNote.Fragments.BoxFragment;
import com.NewMovieNote.Fragments.YoutubeSuggestionFragment;
import com.NewMovieNote.ViewModel.BoxItemViewModel;
import com.google.android.material.tabs.TabLayout;
import com.parkbros.jhmovienote.R;
import com.parkbros.jhmovienote.WriteActivity;

import static com.StaticValues.StaticValues.__TOOLBAR_MENU_BOX_ONE;
import static com.StaticValues.StaticValues.__TOOLBAR_MENU_BOX_TWO;
import static com.StaticValues.StaticValues.__TOOLBAR_MENU_BOX_THREE;

public class MainActivity extends AppCompatActivity {
    final String TAG = "MainActivity" ;

    TabLayout mTabLayout ;
    TextView naviTextView ;
    Toolbar toolbar ;

    FragmentManager fragmentManager;

    private int tabPosition = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        InitializeUiComponents() ;
        InitializeResourceDatas() ;
        UpdateToolBarOptionMenu();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        BoxFragment boxFragment = new BoxFragment();
        boxFragment.newInstance(null, null);
        fragmentTransaction.replace(R.id.viewPager, boxFragment);
        fragmentTransaction.commitAllowingStateLoss();


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        //LogE("onPrepareOptionMenu");
        switch( tabPosition ){
            case 0 :

                menu.add(0,__TOOLBAR_MENU_BOX_ONE,0,"전체 영화");
                menu.add(0,__TOOLBAR_MENU_BOX_TWO,1,"한국 영화");
                menu.add(0,__TOOLBAR_MENU_BOX_THREE,2,"외국 영화");
                break;
            case 1:
                menu.add(0,0,0,"전체 영화");
                break;
            case 2:
                menu.add(0,0,0,"내 목록");
                menu.add(0,0,0,"전체 목록");
                break;
            case 3:
                menu.add(0,0,0,"전체 영화");
                break;
            case 4:
                menu.add(0,0,0,"전체 영화");
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //LogE("onCreateOptionMenu");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //LogE( "item selected  : " + item.getItemId() );
        switch( item.getItemId() ){
            case __TOOLBAR_MENU_BOX_ONE :
                break;
            case __TOOLBAR_MENU_BOX_TWO :
                break;
            case __TOOLBAR_MENU_BOX_THREE :
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void LogE(String msg ){
        Log.e(TAG, msg);
    }

    private void UpdateToolBarOptionMenu(){
        switch( tabPosition ){
            case 0:
                naviTextView.setText("Box");
                break;
            case 1 :
                naviTextView.setText("Youtube");
                break;
            case 2:
                naviTextView.setText("Main");
                break;
            case 3 :
                naviTextView.setText("Youtube");
                break;
            case 4 :
                naviTextView.setText("Setting");
                break;
            default:
                break;
        }
    }

    private void InitializeUiComponents(){
        fragmentManager = getSupportFragmentManager() ;
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        naviTextView = (TextView) findViewById(R.id.naviTextView);
        toolbar = (Toolbar) findViewById( R.id.toolbar ) ;

        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.box_menu);
        toolbar.setOverflowIcon(getDrawable(R.drawable.icon_toolbar_showmore));

        TabController() ;
    }

    private void InitializeResourceDatas(){

    }

    private void TabController(){
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                mViewPager.setCurrentItem(tab.getPosition());
                int page = tab.getPosition();
                if(page == 0){
                    // box fragment
                    Log.e("TAB", "1");
                    tabPosition=0;

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    BoxFragment boxFragment = new BoxFragment();
                    boxFragment.newInstance(null, null);
                    fragmentTransaction.replace(R.id.viewPager, boxFragment);
                    fragmentTransaction.commitAllowingStateLoss();


                    UpdateToolBarOptionMenu();
                }else if(page == 1){
                    Log.e("TAB", "2");
                    // youtube fragment
                    tabPosition=1;

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    YoutubeSuggestionFragment youtubeSuggestionFragment = new YoutubeSuggestionFragment();
                    youtubeSuggestionFragment.newInstance(null, null);
                    fragmentTransaction.replace(R.id.viewPager, youtubeSuggestionFragment);
                    fragmentTransaction.commitAllowingStateLoss();

                    UpdateToolBarOptionMenu();
                }else if (page == 2){
                    Log.e("TAB", "3");
                    // main fragment
                    tabPosition=2;
//                    naviTextView.setText("Main");
//                    progressBar.setVisibility(View.VISIBLE);
//                    FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
//                    MainFragment mainFragment = new MainFragment();
//                    //mainFragment.newInstance("null", "null", progressBar);
//                    fragmentTransaction.replace(R.id.viewPager, mainFragment);
//                    fragmentTransaction.commit();

                    UpdateToolBarOptionMenu();
                } else if(page == 3){
                    Log.e("TAB", "4");
//                    //setting fragment
//                    FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
//                    WriteFragment writeFragment = new WriteFragment();
//                    settingFragment.newInstance("null", "null");
//                    fragmentTransaction.replace(R.id.viewPager, settingFragment);
//                    fragmentTransaction.commit();
//                    Intent intent = new Intent(com.parkbros.jhmovienote.MainActivity.this, WriteActivity.class);
//                    intent.putExtra("tabPosition", tabPosition);
//                    startActivityForResult(intent, REQUEST_WRITE_INTENT);
                }else{
                    Log.e("TAB", "5");
                    //setting fragment
                    tabPosition=4;
//                    naviTextView.setText("Setting");
//                    FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
//                    SettingFragment settingFragment = new SettingFragment();
//                    settingFragment.newInstance("null", "null");
//                    fragmentTransaction.replace(R.id.viewPager, settingFragment);
//                    fragmentTransaction.commit();

                    UpdateToolBarOptionMenu();
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




}