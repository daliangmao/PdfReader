package com.devappcenter.theme.Google;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devappcenter.theme.R;

import org.w3c.dom.Text;

/**
 * Created by Andy on 3/3/15 AD.
 */
public class ViewHeader extends Helper.Header.ViewHeader {

    protected RelativeLayout subHeader;
    private Button btnMenu;
    private HeaderDelegate delegate;

    public ViewHeader(Context context) {
        super(context);
        delegate = (HeaderDelegate) context;
    }

    public ViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        delegate = (HeaderDelegate) context;
    }

    public ViewHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        delegate = (HeaderDelegate) context;
    }

    @Override
    protected void initViews() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.view_header_google, this, true);
        //leftPannel = (RelativeLayout) v.findViewById(R.id.header_left_container);
        btnMenu = (Button) v.findViewById(R.id.btn_header_menu);
        rightPannel = (RelativeLayout) v.findViewById(R.id.header_right_container);
        subHeader = (RelativeLayout) v.findViewById(R.id.sub_header_container);
        btnMenu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnMenu.isSelected())
                    delegate.onLeftDrawOpen();
                else
                    delegate.onBack();
            }
        });
    }

    public void setOnBackWithTitle(String title) {
        if (title != null) {
            btnMenu.setSelected(false);
            btnMenu.setText(title);
        }
    }

    public void setOnMenuWithTitle(String title) {
        if (title != null) {
            btnMenu.setSelected(true);
            btnMenu.setText(title);
        }
    }

    public static void addSubMenu(View view) {
        ((ViewHeader)instance).removeSubMenu();
        if (view != null)
            ((ViewHeader)instance).subHeader.addView(view);
    }
    public static View addRightPannel() {
        ((ViewHeader)instance).removeRightPannelView();
        return ((ViewHeader)instance).rightPannel;
    }

    public static void removeSubMenu() {
        ((ViewHeader)instance).subHeader.removeAllViews();
    }

    public static void SubOpen(boolean open) {
        ViewGroup.LayoutParams layoutParams = ((ViewHeader)instance).subHeader.getLayoutParams();
        layoutParams.height = open?LayoutParams.WRAP_CONTENT:0;
        ((ViewHeader) instance).subHeader.setLayoutParams(layoutParams);
        //((ViewHeader) instance).scaleView(((ViewHeader) instance).subHeader, open?48:100, open?100:48);
    }

    public static void setHiddenMenuButton(boolean hidden) {
        ((ViewHeader)instance).btnMenu.setVisibility(hidden?GONE:VISIBLE);
    }

    private void scaleView(View v, float startScale, float endScale) {
        Animation anim = new ScaleAnimation(
                1f, 1f, // Start and end values for the X axis scaling
                startScale, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        v.startAnimation(anim);
    }

    private void expand(View summary) {
        //set Visible
        summary.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        summary.measure(widthSpec, 300);

        ValueAnimator mAnimator = slideAnimator(0, 300, summary);
        mAnimator.start();
    }

    private ValueAnimator slideAnimator(int start, int end, final View summary) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);


        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = summary.getLayoutParams();
                layoutParams.height = value;
                summary.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    interface HeaderDelegate {
        void onBack();
        void onLeftDrawOpen();
        void onRightDrawOpen();
    }
}
