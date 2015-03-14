package Helper;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.devappcenter.theme.R;

import org.json.JSONObject;

/**
 * Created by APPLE on 6/7/14.
 */
public class CellItem implements InterfaceItem {

    protected String mTitle;
    protected int mImage;
    private int mTag;
    public boolean select_enable;
    public boolean selected;

    public CellItem(String title, int imageResource, int tag){
        mTitle = title;
        mImage = imageResource;
        mTag = tag;
        selected = false;
        select_enable = true;
    }

    public int getTag(){
        return mTag;
    }

    @Override
    public int getViewType() {
        return Adapter.RowType.ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.sidebar_menu_item, null);
        }
        TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        ImageView imgMenu = (ImageView) view.findViewById(R.id.imgMenu);
        txtTitle.setText(mTitle);
        imgMenu.setImageResource(mImage);
        txtTitle.setTextColor(selected? Color.WHITE: Color.parseColor("#808080"));
        view.setBackgroundColor(selected? Color.parseColor("#6495ED"): Color.TRANSPARENT);
        return view;
    }

    @Override
    public JSONObject getContent() {
        return null;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
