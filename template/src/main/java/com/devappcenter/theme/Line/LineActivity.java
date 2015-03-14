package com.devappcenter.theme.Line;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.FrameLayout;

import com.devappcenter.theme.R;
import com.devappcenter.theme.Line.ViewController.MainViewController;

/**
 * Created by Andy on 2/24/15 AD.
 */
public class LineActivity extends FragmentActivity {

    protected FrameLayout controller_container;
    protected MainViewController mainViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_activity);
        controller_container = (FrameLayout) findViewById(R.id.view_controller_container);

        if (savedInstanceState == null) {
            mainViewController = new MainViewController();
        }
    }

}
