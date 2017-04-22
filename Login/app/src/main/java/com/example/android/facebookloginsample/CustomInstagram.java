package com.example.android.facebookloginsample;

/**
 * Created by SDX on 4/21/17.
 */


import android.app.Application;

import com.steelkiwi.instagramhelper.InstagramHelper;

public class CustomInstagram extends Application {
    public static final String CLIENT_ID     = "e37c69a0f62e49dcb16f874a1dab4592";
    public static final String REDIRECT_URL  = "https://expouvg2017.azurewebsites.net/";
    public static final String MEDIA_API     = "https://api.instagram.com/v1/users/self/media/recent/";

    private static InstagramHelper instagramHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        instagramHelper = new InstagramHelper.Builder()
                .withClientId(CLIENT_ID)
                .withRedirectUrl(REDIRECT_URL)
                .withScope("basic")
                .build();

    }

    public static InstagramHelper getInstagramHelper() {
        return instagramHelper;
    }
}