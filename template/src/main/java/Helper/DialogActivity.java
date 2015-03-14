package Helper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.devappcenter.theme.R;
import org.apache.http.NameValuePair;

import java.util.List;


/**
 * Created by Andy on 8/16/14 AD.
 */
public abstract class DialogActivity extends FragmentActivity {

    public abstract View getViewContent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(this);
        FrameLayout mainLayout = (FrameLayout) inflater.inflate(R.layout.activity_dialog, null);
        mainLayout.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        View viewContent = getViewContent();
        if (viewContent != null) {
            FrameLayout frameLayout = (FrameLayout) mainLayout.findViewById(R.id.dialog);
            frameLayout.addView(viewContent);
            frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = (String) v.getTag();
                if (tag.equals("outbound")) {
                    finish();
                }
            }
        });
        setContentView(mainLayout);
    }
}
