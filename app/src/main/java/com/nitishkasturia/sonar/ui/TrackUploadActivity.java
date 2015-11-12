package com.nitishkasturia.sonar.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.nitishkasturia.sonar.R;
import com.nitishkasturia.sonar.ui.views.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

public class TrackUploadActivity extends AppCompatActivity {

    private static final int FILE_SELECT_CODE = 0;

    ProgressBar mUploadProgressBar = null;
    String fileName;

    // Define the connection-string with your values
    public static final String storageConnectionString =
            "DefaultEndpointsProtocol=http;" +
                    "AccountName=sonarapp;" +
                    "AccountKey=OQyP8P4TinFFqcKtKuFzaJqEoAxiL/ppgRd3V4MH5vP6sF81lni0aapPJ7FrxtPaSFoueHwMHdbmd+Irx1x3zg==";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_upload);

        fileName = AccessToken.getCurrentAccessToken().getUserId() + "-" + Calendar.getInstance().getTimeInMillis() + ".mp3";

        ImageButton uploadButton = (ImageButton) findViewById(R.id.upload_image);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        final EditText songName = (EditText) findViewById(R.id.song_name);
        final EditText songDescription = (EditText) findViewById(R.id.song_description);
        Button saveButton = (Button) findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ion.with(getApplicationContext()).load("https://microsoft-apiappec9a775993c04bddb23e9d3f9a9a0a65.azurewebsites.net/api/tracks/create")
                        .setBodyParameter("id", AccessToken.getCurrentAccessToken().getUserId())
                        .setBodyParameter("name", songName.getText().toString())
                        .setBodyParameter("description", songDescription.getText().toString())
                        .setBodyParameter("city", "toronto")
                        .setBodyParameter("state", "ontario")
                        .setBodyParameter("source", "https://sonarapp.blob.core.windows.net/audio-store/" + fileName)
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String result) {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        });
                finish();
            }
        });
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
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();

                    InputStream inputStream = null;
                    OutputStream outputStream = null;
                    final File audioFile;

                    try {
                        inputStream = getContentResolver().openInputStream(uri);
                        outputStream = new FileOutputStream(new File(getFilesDir(), "TEMPFILE"));

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

                    audioFile = new File(getFilesDir(), "TEMPFILE");

                    try {
                        // Retrieve storage account from connection-string.
                        CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

                        // Create the blob client.
                        CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

                        // Retrieve reference to a previously created container.
                        CloudBlobContainer container = blobClient.getContainerReference("audio-store");

                        // Create or overwrite the "myimage.jpg" blob with contents from a local file.
                        final CloudBlockBlob blob = container.getBlockBlobReference(fileName);

                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    blob.upload(new FileInputStream(audioFile), audioFile.length());
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (StorageException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        thread.start();

                    } catch (Exception e) {
                        // Output the stack trace.
                        e.printStackTrace();
                    }

//                    Ion.with(this).load("https://microsoft-apiappec9a775993c04bddb23e9d3f9a9a0a65.azurewebsites.net/api/tracks/create")
////                    Ion.with(this).load("POST", "https://posttestserver.com/post.php?dir=NITISH")
//                            .uploadProgress(new ProgressCallback() {
//                                @Override
//                                public void onProgress(long downloaded, long total) {
//                                    Log.d("UPLOAD", "Downloaded: " + downloaded + "/" + total);
//                                }
//                            })
//                            .setTimeout(60 * 60 * 1000)
//                            .uploadProgressBar(mUploadProgressBar)
//                            .setFileBody(audioFile)
//                            .asString()
//                            .setCallback(new FutureCallback<String>() {
//                                @Override
//                                public void onCompleted(Exception e, String result) {
//                                    Toast.makeText(getApplicationContext(), "RESULT: " + result, Toast.LENGTH_LONG).show();
//                                }
//                            });
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}