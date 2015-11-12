package com.nitishkasturia.sonar.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nitishkasturia.sonar.R;
import com.nitishkasturia.sonar.ui.StreamFragment;

/**
 * Created by Nitish on 15-09-05.
 */
public class MusicStreamPagerAdapter extends FragmentPagerAdapter {

    Context mContext;

    public MusicStreamPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return StreamFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getResources().getString(R.string.hot);
            case 1:
                return mContext.getResources().getString(R.string.new_stream);
        }
        return null;
    }
}