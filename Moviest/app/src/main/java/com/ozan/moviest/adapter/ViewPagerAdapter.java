package com.ozan.moviest.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ozan.moviest.R;
import com.ozan.moviest.ui.activities.MainActivity;
import com.ozan.moviest.ui.fragments.NowPlayingFragment;
import com.ozan.moviest.ui.fragments.TopRatedFragment;
import com.ozan.moviest.ui.fragments.UpComingFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    MainActivity mainActivity;

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (mainActivity.topRatedFragment == null)
                    mainActivity.topRatedFragment = new TopRatedFragment();
                return mainActivity.topRatedFragment;
            case 1:
                if (mainActivity.upComingFragment == null)
                    mainActivity.upComingFragment = new UpComingFragment();
                return mainActivity.upComingFragment;
            case 2:
                if (mainActivity.nowPlayingFragment == null)
                    mainActivity.nowPlayingFragment = new NowPlayingFragment();
                return mainActivity.nowPlayingFragment;
            default:
                return null;
        }
    }

    public ViewPagerAdapter(FragmentManager fm, int numOfTabs, Context context) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
        mainActivity = (MainActivity) context;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return mainActivity.getString(R.string.first_tab);
        }else if (position == 1){
            return mainActivity.getString(R.string.second_tab);
        }else if (position == 2){
            return mainActivity.getString(R.string.third_tab);
        }else {
            return mainActivity.getString(R.string.first_tab);
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
