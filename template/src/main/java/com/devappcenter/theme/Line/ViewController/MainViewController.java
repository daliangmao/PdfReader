package com.devappcenter.theme.Line.ViewController;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import Helper.Header.ViewHeader;
import com.devappcenter.theme.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 2/24/15 AD.
 */
public class MainViewController extends Fragment {

    private MainControllerListener listener;
    private ScreenSlidePagerAdapter adapter;
    private TabHost tabHost;
    private TextView lblTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ScreenSlidePagerAdapter(getFragmentManager());
        lblTitle = new TextView(getActivity());
        lblTitle.setTextColor(Color.WHITE);
        lblTitle.setText("WebUB TV");
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.more_menu_item, null);
        ViewHeader.addLeftPannelView(lblTitle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_view_controller, container, false);
        final ViewPager pager = (ViewPager) view.findViewById(R.id.view_pager);
        tabHost = (TabHost) view.findViewById(R.id.tab_host);
        tabHost.setup();
        String[] titles = listener.TabTitles();//concatenate(listener.TabTitles(), defaultTitles);
        //Drawable[] images = listener.TabImages();
        for (int i=0; i<titles.length; i++) {
            String title = titles[i];
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(String.valueOf(i)).setIndicator(title).setContent(new EmptyTabFactory());
            tabHost.addTab(tabSpec);
        }
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                pager.setCurrentItem(Integer.valueOf(tabId), true);
            }
        });
        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.WHITE);
        }
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                OnPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //adapter.notifyDataSetChanged();
        pager.setCurrentItem(0);
        return view;
    }

    private void OnPageSelected(int position) {
        TabViewController viewController = (TabViewController) adapter.getItem(position);
        View menu = viewController.getMenuView();
        if (menu != null) {
            ViewHeader.addRightPannelView(menu);
        }
        else {
            ViewHeader.removeRightPannelView();
        }
        //lblTitle.setText(listener.TabTitles()[position]);
        tabHost.setCurrentTab(position);

    }

    public void setOnMainViewListener (MainControllerListener mainListener) {
        if (listener == null)
            listener = mainListener;
    }

    public <T> T[] concatenate (T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen+bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    private class EmptyTabFactory implements TabHost.TabContentFactory {

        @Override
        public View createTabContent(String tag) {
            return new View(getActivity());
        }

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (listener != null) {
                return listener.TabTitles()[position];
            }
            return null;
        }

        @Override
        public Fragment getItem(int position) {
            return listener.ControllerOfTab(position);
        }

        @Override
        public int getCount() {
            return listener!=null?listener.TabTotal():0;
        }
    }
}
