package com.devappcenter.theme.Google;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.devappcenter.theme.R;

import Helper.ArrayListMenu;
import Helper.Screen;

/**
 * Created by Andy on 2/25/15 AD.
 */
public abstract class GoogleActivity extends FragmentActivity implements ViewHeader.HeaderDelegate, DrawerMenu.MenuDelegate, GoogleDelegate{

    protected DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerMenu mLeftDrawerView;
    private DrawerContainer mRightDrawerView;
    protected FrameLayout frame;
    private float lastTranslate = 0.0f;
    protected abstract Fragment onFragmentAtMenu(int tag);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_activity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else {
            RelativeLayout mainlayout = (RelativeLayout) findViewById(R.id.header_extension);
            mainlayout.setVisibility(View.GONE);
        }
        if (Screen.displayInch(this) < 4.0)
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        frame = (FrameLayout) findViewById(R.id.content_frame);

        mLeftDrawerView = (DrawerMenu) findViewById(R.id.left_view_drawer);
        mRightDrawerView = (DrawerContainer) findViewById(R.id.right_view_drawer);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, mRightDrawerView);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View drawerView) {
                if (drawerView.equals(mLeftDrawerView)) {
                    //supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                    mDrawerToggle.syncState();
                }
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                if (drawerView.equals(mLeftDrawerView)) {
                    //supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                    mDrawerToggle.syncState();
                }
                else {

                }
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // Avoid normal indicator glyph behaviour. This is to avoid glyph movement when opening the right drawer
                //super.onDrawerSlide(drawerView, slideOffset);
                int index = drawerView.getId();
                float moveFactor = (drawerView.getWidth() * slideOffset) * (drawerView.getId()==R.id.left_view_drawer?1:-1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                {
                    frame.setTranslationX(moveFactor);
                }
                else
                {
                    TranslateAnimation anim = new TranslateAnimation(lastTranslate, moveFactor, 0.0f, 0.0f);
                    anim.setDuration(0);
                    anim.setFillAfter(true);
                    frame.startAnimation(anim);

                    lastTranslate = moveFactor;
                }
            }
            /*
            */
        };

    }

    @Override
    public void onBack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, mRightDrawerView);
    }

    @Override
    public void onLeftDrawOpen() {
        mDrawerLayout.openDrawer(mLeftDrawerView);
    }

    @Override
    public void onRightDrawOpen() {
        mDrawerLayout.openDrawer(mRightDrawerView);
    }

    @Override
    public void onRightDrawer(View view) {
        mRightDrawerView.removeAllViews();
        if (view != null) {
            mRightDrawerView.addView(view);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, mRightDrawerView);
        }
        else {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, mRightDrawerView);
        }

    }

    @Override
    public void didMenuSelectItem(int tag) {
        Fragment viewController = onFragmentAtMenu(tag);
        if (viewController != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction().replace(R.id.content_frame, viewController, viewController.getClass().getSimpleName()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            mDrawerLayout.closeDrawer(mLeftDrawerView);
        }
    }

    protected void setMenu(ArrayListMenu menu, int tag) {
        mLeftDrawerView.setMenu(menu, tag);
    }

    protected void refreshMenu() {
        mLeftDrawerView.Refresh();
    }

    protected void closeSidebarMenu() {
        mDrawerLayout.closeDrawer(mLeftDrawerView);
    }
}
