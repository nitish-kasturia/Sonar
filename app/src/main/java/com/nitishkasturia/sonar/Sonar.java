package com.nitishkasturia.sonar;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import com.alexbbb.uploadservice.UploadService;
import com.facebook.FacebookSdk;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Nitish on 15-09-04.
 */
public class Sonar extends Application {

    private GoogleApiClient mGoogleApiClient = null;

    @Override
    public void onCreate() {
        super.onCreate();

        //Initialize SDK
        FacebookSdk.sdkInitialize(this);
        UploadService.NAMESPACE = "com.nitishkasturia.sonar";
    }

    public synchronized GoogleApiClient getGoogleApiClient() {
        if (mGoogleApiClient != null) {
            return mGoogleApiClient;
        }

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {

                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        Toast.makeText(getApplicationContext(), "ERROR: " + connectionResult.toString(), Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
        return mGoogleApiClient;
    }
}