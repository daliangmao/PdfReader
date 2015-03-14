package com.devappcenter.theme.Line.ViewController;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import Helper.Adapter;
import Helper.Utils;
import Helper.ViewCell;
import com.devappcenter.theme.R;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Andy on 2/24/15 AD.
 */
public class MoreViewController extends TabViewController {

    private MoreControllerListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        GridView view = (GridView) inflater.inflate(R.layout.grid_view_controller, container, false);
        Adapter adapter = new Adapter(getActivity(), android.R.layout.simple_list_item_1, new ArrayList());
        /*
        JSONObject menu = Utils.loadJsonFromAsset(getResources().openRawResource(R.raw.more_menu));
        Iterator<?> k = menu.keys();
        ArrayList<String> keys = new ArrayList<String>();
        while( k.hasNext() ){
            String key = (String)k.next();
            keys.add(key);
        }
        for (int i=0; i<keys.size(); i++) {
            try {
                JSONObject menuItem = menu.getJSONObject(keys.get(i));
                adapter.add(new MoreMenuItem(getActivity(), menuItem));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        */
        if (listener != null) {
            for (int i=0; i<listener.getNumOfItem(); i++) {
                try {
                    adapter.add(new MoreMenuItem(getActivity(), listener.getItemAtIndex(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        view.setColumnWidth(145);
        view.setAdapter(adapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
        return view;
    }

    public void setOnMoreControllerListener (MoreControllerListener listener) {
        this.listener = listener;
    }

    private class MoreMenuItem extends ViewCell {

        public MoreMenuItem(Context context, JSONObject json) {
            super(context, json);
        }

        @Override
        public View getViewItem(LayoutInflater inflater, View convertView) {
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.more_menu_item, null);
            }
            ImageView imgLogo = (ImageView)view.findViewById(R.id.icMenu);
            TextView lblTitle = (TextView) view.findViewById(R.id.lblMenuTitle);
            try {
                lblTitle.setText(jsonItem.getString("title"));
                String icon = jsonItem.getString("icon");
                if (icon.contains("http"))
                    Ion.with(imgLogo).load(icon);
                else
                    imgLogo.setImageResource(getResources().getIdentifier(icon, "drawable", context.getPackageName()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return view;
        }
    }
}
