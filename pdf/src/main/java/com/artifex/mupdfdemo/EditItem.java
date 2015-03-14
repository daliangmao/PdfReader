package com.artifex.mupdfdemo;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.artifex.mupdfdemo.DB.Configure;
import com.artifex.mupdfdemo.Helper.CellItem;

/**
 * Created by Andy on 12/23/14 AD.
 */
public class EditItem extends CellItem {

    private TextView lblTitle;
    private String title;
    public EditText txtTitle;
    public boolean editing;
    public boolean edit;
    public EditItemDelegate delegate;
    Integer page;

    public EditItem(String title, Integer page, int tag) {
        super(title, 0, tag);
        this.page = page;
        editing = false;
        edit = true;
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.bookmark_edit_item, null);
        }

        lblTitle = (TextView) view.findViewById(R.id.lblEditTitle);
        lblTitle.setText(mTitle);
        txtTitle = (EditText) view.findViewById(R.id.txtEditTitle);
        TextView lblPage = (TextView) view.findViewById(R.id.lblPage);
        lblPage.setText(String.valueOf(page));

        ImageView imgStatus = (ImageView) view.findViewById(R.id.imgEditStatus);
        imgStatus.setVisibility(editing ? View.VISIBLE : View.GONE);
        imgStatus.setSelected(edit);

        txtTitle.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            title = title.equals("")?"Untitled":title;
                            txtTitle.clearFocus();
                            txtTitle.setVisibility(View.GONE);
                            lblTitle.setVisibility(View.VISIBLE);
                            lblTitle.setText(title);
                            mTitle = title;
                            delegate.OnFinishedItem(EditItem.this);
                        }
                    }, 1000);
                    return true;
                }
                return false;
            }
        });
        txtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                title = editable.toString();
            }
        });
        setEditMode(false);
        return view;
    }

    public void setEditMode(boolean editmode) {
        lblTitle.setVisibility(editmode?View.GONE:View.VISIBLE);
        txtTitle.setVisibility(!editmode?View.GONE:View.VISIBLE);
        if (editmode) {
            txtTitle.setText(mTitle.equals("Untitled")?"":mTitle);
            if (!mTitle.equals("Untitled"))
                txtTitle.setSelection(mTitle.length());
            txtTitle.requestFocus();
        }
    }

    public interface EditItemDelegate {
        void OnFinishedItem(EditItem item);
    }
}