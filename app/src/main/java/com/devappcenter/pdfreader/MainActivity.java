package com.devappcenter.pdfreader;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.devappcenter.theme.Google.GoogleActivity;
import com.devappcenter.theme.Google.GoogleFragmentFeeder;


public class MainActivity extends GoogleActivity {

    @Override
    protected Fragment onFragmentAtMenu(int tag) {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle args = new Bundle();
        args.putString("preload", getIntent().getStringExtra("discover"));
        BookViewController viewController = new BookViewController();
        viewController.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.content_frame, viewController, "DISCOVER_FRAGMENT").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }


}
