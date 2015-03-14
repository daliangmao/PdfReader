package Helper;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by APPLE on 6/7/14.
 */
public class Adapter extends ArrayAdapter<InterfaceItem> {

    private LayoutInflater mInflater;

    public Adapter(Activity ctx, int resource, ArrayList objects) {
        super(ctx, resource, objects);
        mInflater = LayoutInflater.from(ctx);
    }

    public enum RowType {
        ITEM, SECTION, HEADER, LOGIN
    }

    @Override
    public int getViewTypeCount() {
        return RowType.values().length;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItem(position).getView(mInflater, convertView);
    }

}
