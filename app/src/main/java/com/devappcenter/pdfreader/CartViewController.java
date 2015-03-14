package com.devappcenter.pdfreader;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devappcenter.theme.Google.GoogleFragment;
import com.devappcenter.theme.Google.GoogleFragmentFeeder;
import com.devappcenter.theme.Google.ViewHeader;
import Helper.ClearableEditText;

/**
 * Created by Andy on 3/14/15 AD.
 */
public class CartViewController extends GoogleFragment {

    @Override
    public String setHeaderMenuWithTitle() {
        return "ตะกร้า";
    }
    /*
    @Override
    public View getRightViewHeader() {
        final ClearableEditText txtSearch = new ClearableEditText(getActivity());
        txtSearch.setOnClearableSearchListener(new ClearableEditText.ClearableSearchListener() {
            @Override
            public void OnShrink() {
                ViewHeader.setHiddenMenuButton(false);
            }

            @Override
            public void OnExpand() {
                ViewHeader.setHiddenMenuButton(true);
            }
        });
        txtSearch.edit_text.setTextColor(getResources().getColor(R.color.white));
        txtSearch.edit_text.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //listener.onSearch(txtSearch.getText().toString());
                    ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(txtSearch.getWindowToken(), 0);
                    txtSearch.shrink();
                    return true;
                }
                return false;
            }
        });
        return txtSearch;
    }
    */
    @Override
    public void setRightViewHeader(View view) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cart_buttons, (ViewGroup) view, true);
        final ImageButton btnExpand = (ImageButton) getActivity().findViewById(R.id.btn_tool);
        btnExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnExpand.setSelected(!btnExpand.isSelected());
                ViewHeader.SubOpen(btnExpand.isSelected());
            }
        });
        final ClearableEditText txtSearch = (ClearableEditText) getActivity().findViewById(R.id.search_view);
        txtSearch.setOnClearableSearchListener(new ClearableEditText.ClearableSearchListener() {
            @Override
            public void OnShrink() {
                ViewHeader.setHiddenMenuButton(false);
            }

            @Override
            public void OnExpand() {
                ViewHeader.setHiddenMenuButton(true);
            }
        });
        txtSearch.edit_text.setTextColor(getResources().getColor(R.color.white));
        txtSearch.edit_text.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //listener.onSearch(txtSearch.getText().toString());
                    ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(txtSearch.getWindowToken(), 0);
                    txtSearch.shrink();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public View getSubViewHeader() {
        RelativeLayout layout = new RelativeLayout(getActivity());
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120));
        layout.setBackgroundColor(getResources().getColor(R.color.CadetBlue));
        return layout;
    }

    @Override
    public View getDrawerRightView() {
        RelativeLayout layout = new RelativeLayout(getActivity());
        layout.setLayoutParams(new ViewGroup.LayoutParams(300, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setBackgroundColor(getResources().getColor(R.color.Khaki));
        return layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, new ListView(getActivity()), savedInstanceState);
        //ViewHeader.SubOpen(true);

        TextView txtTitle = new TextView(getActivity());
        txtTitle.setText("Hello world");
        return txtTitle;
    }


}
