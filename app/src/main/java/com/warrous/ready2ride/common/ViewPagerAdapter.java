package com.warrous.ready2ride.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mPages;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> pages) {
        super(fm);
        this.mPages = pages;
    }

    @Override
    public Fragment getItem(int position) {
        return mPages.get(position);
    }

    @Override
    public int getCount() {
        return mPages == null ? 0 : mPages.size();
    }
}
