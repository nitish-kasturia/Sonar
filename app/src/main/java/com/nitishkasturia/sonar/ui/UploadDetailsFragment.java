package com.nitishkasturia.sonar.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nitishkasturia.sonar.R;

/**
 * Created by Nitish on 15-09-05.
 */
public class UploadDetailsFragment extends Fragment {

    public static UploadDetailsFragment newInstance() {
        return new UploadDetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_details, container, false);

        return view;
    }
}