package com.nitishkasturia.sonar.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nitishkasturia.sonar.ui.UploadDetailsFragment;
import com.nitishkasturia.sonar.ui.UploadFragment;

/**
 * Created by Nitish on 15-09-05.
 */
public class UploadPagerAdapter extends FragmentPagerAdapter {

    Context mContext;

    public UploadPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return UploadFragment.newInstance();
            case 1:
                return UploadDetailsFragment.newInstance();
        }
        return new Fragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}