package com.devappcenter.theme.Google;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.devappcenter.theme.R;

import java.util.ArrayList;

import Helper.Adapter;
import Helper.ArrayListMenu;
import Helper.CellItem;
import Helper.ViewCell;


/**
 * Created by Andy on 8/18/14 AD.
 */
public class DrawerMenu extends DrawerContainer {

    private ArrayListMenu mMenu;
    private ListView mDrawerList;
    private Adapter AdapterSetting;
    private MenuDelegate delegate;
    private CellItem selectItem;

    public DrawerMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        delegate = (MenuDelegate)context;
    }

    @Override
    protected void initViews() {
        super.initViews();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.sidebar_menu, this, true);
        mDrawerList = (ListView) v.findViewById(R.id.list_menu_item);
        //ViewGroup.LayoutParams layoutParams = mDrawerList.getLayoutParams();
        //layoutParams.width = 200;
        //mDrawerList.setLayoutParams(layoutParams);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CellItem item = mMenu.get(position);
                if (item.select_enable) {
                    selectItem.selected = false;
                    selectItem = item;
                    selectItem.selected = true;
                    AdapterSetting.notifyDataSetChanged();
                }
                delegate.didMenuSelectItem(item.getTag());
            }
        });
    }

    public void setMenu(ArrayListMenu menu, int tag) {
        if (mMenu == null) {
            mMenu = menu;
            AdapterSetting = new Adapter((Activity) getContext(), android.R.layout.simple_list_item_activated_1, mMenu);
            mDrawerList.setAdapter(AdapterSetting);
            mDrawerList.setSelection(mMenu.getIndex(tag));
            setSelectItem(tag);
        }
    }

    public void setSelectItem(int tag) {
        if (selectItem != null)
            selectItem.selected = false;
        selectItem = mMenu.getItem(tag);
        selectItem.selected = true;
    }

    public int getSelectItem() {
        return selectItem.getTag();
    }

    public void Reload() {
        //mMenu.ReloadUserProfile();
    }

    public void Refresh() {
        AdapterSetting.notifyDataSetChanged();
    }

    public interface MenuDelegate {
        void didMenuSelectItem(int tag);
    }
}
