package com.nitishkasturia.sonar.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nitishkasturia.sonar.R;
import com.nitishkasturia.sonar.adapters.MusicStreamPagerAdapter;

/**
 * Created by Nitish on 15-09-05.
 */
public class MusicStreamFragment extends Fragment {

    TabLayout mTabLayout = null;
    ViewPager mViewPager = null;

    public static MusicStreamFragment newInstance() {
        return new MusicStreamFragment();
    }

    public MusicStreamFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_stream, container, false);

        mTabLayout = (TabLayout) view.findViewById(R.id.tab_container_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.music_stream_viewpager);

        mViewPager.setAdapter(new MusicStreamPagerAdapter(getActivity(), getChildFragmentManager()));

        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }
}
