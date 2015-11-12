package com.nitishkasturia.sonar.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.nitishkasturia.sonar.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

/**
 * Created by Nitish on 15-09-05.
 */
public class UploadFragment extends Fragment {

    private static final int FILE_SELECT_CODE = 0;

    public static UploadFragment newInstance() {
        return new UploadFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload, container, false);

        ImageButton uploadButton = (ImageButton) view.findViewById(R.id.upload_image);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        return view;
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        String fileName = AccessToken.getCurrentAccessToken().getUserId() + "-" + Calendar.getInstance().toString();

        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();

                    InputStream inputStream = null;
                    OutputStream outputStream = null;
                    File audioFile;

                    try {
                        inputStream = getActivity().getContentResolver().openInputStream(uri);
                        outputStream = new FileOutputStream(new File(getActivity().getFilesDir(), "TEMPFILE"));

                        int read = 0;
                        byte[] bytes = new byte[1024];

                        while ((read = inputStream.read(bytes)) != -1) {
                            outputStream.write(bytes, 0, read);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    audioFile = new File(getActivity().getFilesDir(), "TEMPFILE");
//                    Ion.with(getActivity()).load("https://microsoft-apiappec9a775993c04bddb23e9d3f9a9a0a65.azurewebsites.net/api/tracks/upload")
                    Ion.with(getActivity()).load("POST", "https://posttestserver.com/post.php?dir=NITISH")
                            .uploadProgress(new ProgressCallback() {
                                @Override
                                public void onProgress(long downloaded, long total) {
                                    Log.d("UPLOAD", "Downloaded: " + downloaded + "/" + total);
                                }
                            })
                            .setTimeout(60 * 60 * 1000)
                            .uploadProgressBar(((TrackUploadActivity) getActivity()).mUploadProgressBar)
                            .setMultipartParameter("name", "media")
                            .setMultipartFile(fileName, audioFile)
                            .asString()
                            .setCallback(new FutureCallback<String>() {
                                @Override
                                public void onCompleted(Exception e, String result) {
                                    Toast.makeText(getActivity(), "RESULT: " + result, Toast.LENGTH_LONG).show();
                                }
                            });
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}