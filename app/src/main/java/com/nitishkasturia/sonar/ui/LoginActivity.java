package com.nitishkasturia.sonar.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.nitishkasturia.sonar.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    LoginButton mLoginButton;
    CallbackManager mFacebookCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AccessToken facebookAccessToken = AccessToken.getCurrentAccessToken();

        if (facebookAccessToken != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else {
            setContentView(R.layout.activity_login);

            mLoginButton = (LoginButton) findViewById(R.id.facebook_login_button);

            mFacebookCallbackManager = CallbackManager.Factory.create();
            mLoginButton.registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                            try {
                                Ion.with(getApplicationContext()).load("https://microsoft-apiappec9a775993c04bddb23e9d3f9a9a0a65.azurewebsites.net/api/users")
                                        .setBodyParameter("id", (String) jsonObject.get("id"))
                                        .setBodyParameter("name", (String) jsonObject.get("name"))
                                        .setBodyParameter("description", "")
                                        .asString()
                                        .setCallback(new FutureCallback<String>() {
                                            @Override
                                            public void onCompleted(Exception e, String result) {
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    request.executeAsync();

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }

                @Override
                public void onCancel() {
                    //Do nothing
                }

                @Override
                public void onError(FacebookException e) {
                    Toast.makeText(getApplicationContext(), "ERROR: " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}