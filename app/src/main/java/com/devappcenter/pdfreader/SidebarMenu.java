package com.devappcenter.pdfreader;

import android.content.Context;

import Helper.ArrayListMenu;
import Helper.CellItem;

/**
 * Created by Andy on 2/22/15 AD.
 */

public class SidebarMenu extends ArrayListMenu {

    private Integer selected;
    private CellItem logoutItem;

    public SidebarMenu(Context context) {
        selected = 0;
        int imgPreview = context.getResources().getIdentifier("ic_table", "drawable", context.getPackageName());
        int imgLottery = context.getResources().getIdentifier("ic_lotto_table", "drawable", context.getPackageName());

        this.add(new CellItem("รายการหนังสือ", imgPreview, AppMenu.PREVIEW.ordinal()));
        this.add(new CellItem("ทดสอบ Fragment", imgLottery, AppMenu.FRAGMENT.ordinal()));

    }

}
