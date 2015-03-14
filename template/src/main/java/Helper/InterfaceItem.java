package Helper;

import android.view.LayoutInflater;
import android.view.View;

import org.json.JSONObject;

/**
 * Created by Andy on 3/7/15 AD.
 */
public interface InterfaceItem {
    public int getViewType();
    public View getView(LayoutInflater inflater, View convertView);
    public JSONObject getContent();
}
