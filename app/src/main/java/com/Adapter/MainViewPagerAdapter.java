package com.Adapter;

import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.MainFragments.BoxOfficeFragment;
import com.MainFragments.MainFragment;
import com.MainFragments.SettingFragment;
import com.MainFragments.WriteFragment;
import com.MainFragments.YoutubeFragment;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    private int mPageCount;
    ProgressBar progressBar;



    public MainViewPagerAdapter(FragmentManager fm, int pageCount, ProgressBar progressBar) {
        super(fm);
        this.mPageCount = pageCount;
        this.progressBar = progressBar;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                BoxOfficeFragment boxOfficeFragment = new BoxOfficeFragment();
                return boxOfficeFragment.newInstance("qwe0","qwe", progressBar);

            case 1:
                YoutubeFragment youtubeFragment = new YoutubeFragment();
                return youtubeFragment.newInstance("qwe0","qwe", progressBar);



            case 2:
                MainFragment mainFragment = new MainFragment();
                return mainFragment.newInstance("qwe0","qwe", progressBar);
//            case 3:
//                WriteFragment writeFragment = new WriteFragment();
//                return writeFragment.newInstance("qwe0","qwe");
            case 3:
                SettingFragment settingFragment = new SettingFragment();
                return settingFragment.newInstance("qwe0","qwe");



            default:

                return null;

        }

    }



    @Override

    public int getCount() {

        return mPageCount;

    }

}