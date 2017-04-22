package com.example.android.facebookloginsample;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;
import com.steelkiwi.instagramhelper.InstagramHelper;
import com.steelkiwi.instagramhelper.InstagramHelperConstants;
import com.steelkiwi.instagramhelper.model.InstagramUser;
import com.steelkiwi.instagramhelper.utils.SharedPrefUtils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends Activity {
    private CallbackManager callbackManager;
    private TextView loginButton;
    private LoginButton btnLogin;
    private ProgressDialog progressDialog;
    private Button authInsgtagram;
    private ImageView userPhoto;
    private InstagramHelper instagramHelper;
    private String access_token;
    private Context context;
    User user;
    private final String hashtag_expo = "ucl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        authInsgtagram = (Button) findViewById(R.id.buttonInstagram);
        userPhoto = (ImageView) findViewById(R.id.user_photo);
        CustomInstagram ct = new CustomInstagram();
        ct.onCreate();
        context = this.getApplicationContext(); //save context
        instagramHelper = ct.getInstagramHelper();

        if(PrefUtils.getCurrentUser(LoginActivity.this) != null){

            Intent homeIntent = new Intent(LoginActivity.this, LogoutActivity.class);

            startActivity(homeIntent);

            finish();
        }
        authInsgtagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instagramHelper.loginFromActivity(LoginActivity.this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();


        callbackManager=CallbackManager.Factory.create();


        btnLogin= (LoginButton) findViewById(R.id.btnLogin);
        btnLogin.setReadPermissions("public_profile", "email","user_friends");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                btnLogin.registerCallback(callbackManager, mCallBack);



            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == InstagramHelperConstants.INSTA_LOGIN && resultCode == RESULT_OK) {
            InstagramUser user = instagramHelper.getInstagramUser(this);
            Picasso.with(this).load(user.getData().getProfilePicture()).into(userPhoto);
            Log.w("TAG", user.getData().getUsername() + "\n"
                    + user.getData().getFullName() + "\n"
                    + user.getData().getWebsite() + "\n"
                    + user.getData().getId()
            );
            String st = SharedPrefUtils.getToken(this);
            this.access_token = st;
            Log.w("token", st);
            new GetPointsByHashtag().execute();


        } else {
            Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show();
        }
    }


    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            progressDialog.dismiss();

            // App code
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {

                            Log.e("response: ", response + "");
                                try {
                                    user = new User();
                                    user.facebookID = object.getString("id").toString();
                                    user.email = object.getString("email").toString();
                                    user.name = object.getString("name").toString();
                                    user.gender = object.getString("gender").toString();
                                    PrefUtils.setCurrentUser(user,LoginActivity.this);

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                              Toast.makeText(LoginActivity.this,"welcome "+user.name,Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(LoginActivity.this,LogoutActivity.class);
                                startActivity(intent);
                                finish();

                        }

                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender, birthday");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
            progressDialog.dismiss();
        }

        @Override
        public void onError(FacebookException e) {
            progressDialog.dismiss();
        }
    };

    //call instagram api
    //search for all media
    //check hashtag for each media, count only once.
    private class GetPointsByHashtag extends AsyncTask<Void, Void, Void> {
        private boolean errorHappened;
        public static final String GET = "GET";
        public String parsed = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(CustomInstagram.MEDIA_API + "?access_token="
                        + access_token);
                Log.w("API_call", CustomInstagram.MEDIA_API + "?access_token="
                        + access_token + "&count=100");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod(GET);

                conn.setDoInput(true);
                conn.connect();

                int response = conn.getResponseCode();
                String message = conn.getResponseMessage();
                if (response == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    parsed += sb;
                   // Gson gson = new Gson();

                    //InstagramUser user = gson.fromJson(sb.toString(), InstagramUser.class);
                    //SharedPrefUtils.saveInstagramUser(InstagramLoginActivity.this,user);
                }else{
                    errorHappened = true;
                    Log.w("Error", message);

                    //finishWithError(ERROR);
                }

            } catch (Exception e) {
                errorHappened = true;
                //finishWithError(ERROR);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(!errorHappened){
                try {
                    JSONObject json = new JSONObject(parsed);
                    int count = 0;
                    JSONArray array=json.getJSONArray("data");
                    for(int i=0;i < array.length();i++){
                        JSONObject post = array.getJSONObject(i);
                        JSONArray tags = post.getJSONArray("tags");
                        for (int j = 0; j < tags.length(); j++) {
                            String nameTag  = tags.get(j).toString().trim().toLowerCase();
                            if (nameTag.equals(hashtag_expo)) {
                                count += 1;
                                break; //only find one per photo
                            }
                        }
                    }

                    Toast.makeText(context, "Hashtags " + count, Toast.LENGTH_LONG).show();

                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
