package com.artifex.mupdfdemo;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.artifex.mupdfdemo.DB.Config;
import com.artifex.mupdfdemo.DB.Configure;
import com.artifex.mupdfdemo.Helper.DialogActivity;

/**
 * Created by Andy on 12/20/14 AD.
 */
public class SettingActivity extends DialogActivity{

    enum theme{NORMAL, INVERT};
    private ViewSetting viewSetting;
    private TextView lblTheme;
    private ImageButton btnThemeNormal, btnThemeInvert;
    //Variable to store brightness value
    private int brightness;
    //Content resolver used as a handle to the system's settings
    private ContentResolver cResolver;
    //Window object, that will store a reference to the current window
    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Get the content resolver
        cResolver = getContentResolver();

        //Get the current window
        window = getWindow();
        try
        {
            // To handle the auto
            Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            //Get the current system brightness
            brightness = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS);
        }
        catch (Settings.SettingNotFoundException e)
        {
            //Throw an error case it couldn't be retrieved
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
    }

    protected void getViewDialog(LayoutInflater inflater, FrameLayout containner) {
        viewSetting = new ViewSetting(this);
        containner.addView(viewSetting);
    }

    private class ViewSetting extends RelativeLayout {

        public ViewSetting(Context context) {
            super(context);
            initViews();
        }

        void initViews() {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Configure configure = Configure.getInstance();
            View v = inflater.inflate(R.layout.modal_setting, this, true);
            final TextView lblBrightness = (TextView) v.findViewById(R.id.lblBrightness);
            lblTheme = (TextView) v.findViewById(R.id.lblTheme);
            lblBrightness.setText(String.format("Brightness %d%%", brightness));
            lblTheme.setText(String.format("Theme %s", configure.confg.theme==0?"Normal":"Night"));
            SeekBar seekBrightness = (SeekBar) v.findViewById(R.id.seekBar);
            seekBrightness.setProgress(brightness);
            btnThemeNormal = (ImageButton) v.findViewById(R.id.btnSun);
            btnThemeInvert = (ImageButton) v.findViewById(R.id.btnMoon);
            btnThemeNormal.setSelected(configure.confg.theme==0);
            btnThemeInvert.setSelected(configure.confg.theme==1);
            btnThemeNormal.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setTheme(theme.NORMAL);
                }
            });
            btnThemeInvert.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setTheme(theme.INVERT);
                }
            });
            seekBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    lblBrightness.setText(String.format("Brightness %d%%", seekBar.getProgress()));
                    //Set the system brightness using the brightness variable value
                    Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, seekBar.getProgress());
                    //Get the current window attributes
                    WindowManager.LayoutParams layoutpars = window.getAttributes();
                    //Set the brightness of this window
                    layoutpars.screenBrightness = brightness / (float)255;
                    //Apply attribute changes to this window
                    window.setAttributes(layoutpars);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }

        private void setTheme(theme theme) {

            Configure configure = Configure.getInstance();
            configure.confg.theme = theme==theme.NORMAL?0:1;
            configure.confg.update();

            btnThemeNormal.setSelected(theme==theme.NORMAL);
            btnThemeInvert.setSelected(theme==theme.INVERT);

            lblTheme.setText(String.format("Theme %s", theme == theme.NORMAL ? "Normal" : "Night"));
        }
    }

}
