package com.devappcenter.theme;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Helper.Interface.LoadAsyncListener;
import Helper.UrlLoadAsync;

/**
 * Created by Andy on 2/26/15 AD.
 */
public abstract class SplashScreen extends Activity {

    public abstract Drawable getBackground();
    public abstract int getLogo();
    public abstract void onFinishLoading(String jsonString);
    public abstract String getLoadingUrl();
    public abstract List<NameValuePair> getPost();
    protected RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        container = (RelativeLayout) findViewById(R.id.splash_container);

        if (android.os.Build.VERSION.SDK_INT >= 16)
            container.setBackground(getBackground());
        else
            container.setBackgroundDrawable(getBackground());
        ImageView logo = (ImageView) findViewById(R.id.img_logo);
        logo.setImageResource(getLogo());

        Map<String, String> mapUrl = new HashMap<String, String>();
        Map<String, List<? extends NameValuePair>> mapPost = new HashMap<String, List<? extends NameValuePair>>();
        mapPost.put("post", getPost());
        mapUrl.put("url", getLoadingUrl());
        UrlLoadAsync loadAsync = new UrlLoadAsync();
        loadAsync.setOnLoadAsyncListener(new LoadAsyncListener() {
            @Override
            public void onLoadFinished(String result) {
                SplashScreen.this.onFinishLoading(result);
            }
        });
        loadAsync.execute(mapUrl, mapPost, null);
    }
}
