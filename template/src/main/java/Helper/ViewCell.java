package Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import org.json.JSONObject;

/**
 * Created by Andy on 8/15/14 AD.
 */
public abstract class ViewCell implements InterfaceItem {

    public JSONObject jsonItem;
    protected int layout;
    protected Context context;
    public abstract View getViewItem(LayoutInflater inflater, View convertView);

    public ViewCell(Context context, JSONObject json) {
        jsonItem = json;
        this.context = context;
    }

    @Override
    public int getViewType() {
        return 0;
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        //View view = inflater.inflate(layout, null);
        return getViewItem(inflater, convertView);
    }

    @Override
    public JSONObject getContent() {
        return jsonItem;
    }
}
