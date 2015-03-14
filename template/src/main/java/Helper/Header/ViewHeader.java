package Helper.Header;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.devappcenter.theme.R;

/**
 * Created by Andy on 2/24/15 AD.
 */
public class ViewHeader extends RelativeLayout {

    public static RelativeLayout instance;
    protected RelativeLayout leftPannel, rightPannel;

    public ViewHeader(Context context) {
        super(context);
        initViews();
    }

    public ViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        instance = this;
        initViews();
    }

    public ViewHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    protected void initViews() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.view_header, this, true);
        leftPannel = (RelativeLayout) v.findViewById(R.id.header_left_container);
        rightPannel = (RelativeLayout) v.findViewById(R.id.header_right_container);
    }

    public static RelativeLayout getInstance(Context context) {
        instance = new ViewHeader(context);
        return instance;
    }

    public static RelativeLayout getInstance() {
        return instance;
    }

    public static void addLeftPannelView(View view) {
        ((ViewHeader)instance).removeLeftPannelView();
        ((ViewHeader)instance).leftPannel.addView(view);
    }

    public void removeLeftPannelView() {
        leftPannel.removeAllViews();
    }

    public static void addRightPannelView(View view) {
        ((ViewHeader)instance).removeRightPannelView();
        if (view != null)
            ((ViewHeader)instance).rightPannel.addView(view);
    }

    public static void removeRightPannelView() {
        ((ViewHeader)instance).rightPannel.removeAllViews();
    }
}
