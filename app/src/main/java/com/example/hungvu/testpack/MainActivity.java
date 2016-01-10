package com.example.hungvu.testpack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private CallbackManager fbCallbackManager;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /// remember after setContentView
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        fbCallbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton)findViewById(R.id.login_fb);

        /// permission to get email from user
        loginButton.setReadPermissions(Arrays.asList("email"));

        loginButton.registerCallback(fbCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // login success

                /// get user info (must have access token)
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        // json user info
                        // {"id":"xxxxxxxxxxx","picture":{"data":{"url":"https:\/\/fbcdn-profile-a.akamaihd.net\/hprofile-ak-xpf1\/v\/t1.0-1\/p200x200\/12039505_124605024564769_2183097001496262180_n.jpg?oh=b6e2ee1c81655e7aedfa64e1211fec4c&oe=573E50E1&__gda__=1463803399_7a0115d5b2214ade64225af994c29c88","is_silhouette":false}},"email":"xxxxxx@gmail.com","name":"xxxx"}
                        Log.i("log_tag", "onCompleted: " + object.toString());
                    }
                });
                Bundle bundle = new Bundle();

                /// get name, email, avatar uri
                bundle.putString("fields", "name,email,picture.type(large)");
                request.setParameters(bundle);

                // start request
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // user cancel
            }

            @Override
            public void onError(FacebookException error) {
                // login error
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        fbCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}