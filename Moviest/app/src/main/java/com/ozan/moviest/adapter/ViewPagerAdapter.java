package com.ozan.moviest.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ozan.moviest.R;
import com.ozan.moviest.ui.activities.MainActivity;
import com.ozan.moviest.ui.fragments.FavoritiesFragment;
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
                    mainActivity.topRatedFragment = TopRatedFragment.newInstance();
                return mainActivity.topRatedFragment;
            case 1:
                if (mainActivity.upComingFragment == null)
                    mainActivity.upComingFragment = UpComingFragment.getInstance();
                return mainActivity.upComingFragment;
            case 2:
                if (mainActivity.nowPlayingFragment == null)
                    mainActivity.nowPlayingFragment = NowPlayingFragment.newInstance();
                return mainActivity.nowPlayingFragment;
            case 3:
                if (mainActivity.favoritiesFragment == null)
                    mainActivity.favoritiesFragment = FavoritiesFragment.newInstance();
                return mainActivity.favoritiesFragment;
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
        }else if (position == 3){
            return mainActivity.getString(R.string.forth_tab);
        }else {
            return mainActivity.getString(R.string.first_tab);
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
