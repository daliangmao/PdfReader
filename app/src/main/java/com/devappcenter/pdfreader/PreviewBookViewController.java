package com.devappcenter.pdfreader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.devappcenter.theme.Google.GoogleFragment;

/**
 * Created by Andy on 3/14/15 AD.
 */
public class PreviewBookViewController extends GoogleFragment {

    @Override
    public String setHeaderBackWithTitle() {
        return "Review";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        TextView txtTitle = new TextView(getActivity());
        txtTitle.setText("Preview Book");
        return txtTitle;
    }

}
