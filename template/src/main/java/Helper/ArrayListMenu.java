package Helper;

import java.util.ArrayList;

/**
 * Created by Andy on 3/7/15 AD.
 */
public class ArrayListMenu extends ArrayList<CellItem> {

    public CellItem getItem(int tag) {
        for (int i=0; i<this.size(); i++) {
            CellItem item = this.get(i);
            if (item.getTag() == tag)
                return item;
        }
        return null;
    }

    public int getIndex(int tag) {
        for (int i=0; i<this.size(); i++) {
            CellItem item = this.get(i);
            if (item.getTag() == tag)
                return i;
        }
        return -1;
    }
}
