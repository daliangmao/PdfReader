package com.devappcenter.theme.Google;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.devappcenter.theme.R;

import org.json.JSONObject;

import Helper.Adapter;
import Helper.CellItem;
import Helper.ImageLoader;
import Helper.InterfaceItem;

/**
 * Created by Andy on 8/15/14 AD.
 */
public abstract class ProfileItem extends CellItem {

    public abstract String getImageUrl();

    public ProfileItem(String title, int imageResource, int tag) {
        super(title, imageResource, tag);
    }

    @Override
    public int getViewType() {
        return Adapter.RowType.ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.sidebar_login_item, null);
        }
        ImageView imgUser = (ImageView) view.findViewById(R.id.img_user);
        TextView lblTitle = (TextView) view.findViewById(R.id.lbl_Title);
        lblTitle.setText(mTitle);
        String imageUrl = getImageUrl();
        if (imageUrl == null) {
            imgUser.setImageResource(mImage);
        }
        else {
            ImageLoader imageLoader = ImageLoader.getInstance(null);
            imageLoader.DisplayImage(imageUrl, imgUser);
        }
        return view;
    }

    @Override
    public JSONObject getContent() {
        return null;
    }
}
