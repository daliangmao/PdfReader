package Helper;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by Andy on 1/30/15 AD.
 */
public class Screen {

    public static double displayInch(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels/dm.xdpi,2);
        double y = Math.pow(dm.heightPixels/dm.ydpi,2);
        double screenInches = Math.sqrt(x + y);
        return screenInches;
    }
}
