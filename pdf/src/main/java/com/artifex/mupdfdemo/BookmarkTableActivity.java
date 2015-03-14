package com.artifex.mupdfdemo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.artifex.mupdfdemo.DB.Configure;
import com.artifex.mupdfdemo.Helper.Adapter;
import com.artifex.mupdfdemo.Helper.CellItem;
import com.artifex.mupdfdemo.Helper.DialogActivity;

import java.util.ArrayList;

/**
 * Created by Andy on 12/20/14 AD.
 */
public class BookmarkTableActivity extends DialogActivity {

    enum editmode{select, edit, delete};
    public static final int BOOKMARK_REQUEST_CODE = 101;

    private ViewBookmarkTable viewBookmarkTable;
    private String bookid, userid;
    private Integer editpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = this.getIntent().getExtras();
        bookid = bundle.getString("bookid");
        userid = bundle.getString("user");
        editpage = bundle.getInt("page");
        super.onCreate(savedInstanceState);
    }

    protected void getViewDialog(LayoutInflater inflater, FrameLayout containner) {

        viewBookmarkTable = new ViewBookmarkTable(this, editpage>0?editmode.select:editmode.select);
        containner.addView(viewBookmarkTable);
    }

    private class ViewBookmarkTable extends RelativeLayout implements EditItem.EditItemDelegate{

        private editmode mode;
        private Adapter adapter;
        private ArrayList listItems;
        private ImageButton btnEdit;
        private InputMethodManager inputMethodManager;

        public ViewBookmarkTable(Context context, editmode mode) {
            super(context);

            this.mode = mode;
            listItems = new ArrayList();
            inputMethodManager=(InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            Configure configure = Configure.getInstance();
            Cursor cursor = configure.bookmark.All(bookid, userid);
            if (cursor != null) {
                do {
                    String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                    Integer page = cursor.getInt(cursor.getColumnIndexOrThrow("page"));
                    Integer tag = cursor.getInt(cursor.getColumnIndexOrThrow("mark_id"));
                    EditItem editItem = new EditItem(title, page, tag);
                    editItem.delegate = this;
                    listItems.add(editItem);
                }while (cursor.moveToNext());
            }
            initViews();
            if (editpage > 0) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnEdit.performClick();

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                EditItem editItem = (EditItem)adapter.getItem(adapter.getCount()-1);
                                editItem.setEditMode(true);
                                inputMethodManager.toggleSoftInputFromWindow(editItem.txtTitle.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                            }
                        }, 200);
                    }
                }, 1000);
            }
        }

        void initViews() {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.modal_bookmark_content, this, true);
            ListView listView = (ListView) v.findViewById(R.id.listViewBookmark);
            adapter = new Adapter((android.app.Activity) getContext(), android.R.layout.simple_list_item_activated_1, listItems);
            listView.setAdapter(adapter);
            listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
            listView.setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (mode == editmode.select) {
                        EditItem editItem = (EditItem)adapter.getItem(position);
                        Intent resIntent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putInt("page", editItem.page);
                        resIntent.putExtras(bundle);
                        setResult(1, resIntent);
                        finish();
                    }
                    else if (mode == editmode.delete) {
                        EditItem editItem = (EditItem)adapter.getItem(position);
                        delete(editItem.getTag());
                        adapter.remove(editItem);
                        adapter.notifyDataSetChanged();
                    }
                    else if (mode == editmode.edit) {
                        EditItem editItem = (EditItem)adapter.getItem(position);
                        editItem.setEditMode(true);
                        inputMethodManager.toggleSoftInputFromWindow(editItem.txtTitle.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                    }
                }
            });
            btnEdit = (ImageButton) v.findViewById(R.id.btnEditBookmark);
            ImageButton btnDelete = (ImageButton) v.findViewById(R.id.btnDeleteBookmark);
            btnEdit.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mode = mode == editmode.edit?editmode.select:editmode.edit;
                    reload();
                }
            });
            btnDelete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mode = mode == editmode.delete ? editmode.select : editmode.delete;
                    reload();
                }
            });
        }

        private void reload() {
            for (int i=0; i<adapter.getCount(); i++) {
                EditItem editItem = (EditItem) adapter.getItem(i);
                editItem.editing = mode != editmode.select;
                editItem.edit = mode == editmode.edit;
            }
            adapter.notifyDataSetChanged();
        }

        private void delete(Integer index) {
            Configure configure = Configure.getInstance();
            configure.bookmark.Delete(index);
        }

        private void finidhedEdit() {

        }

        @Override
        public void OnFinishedItem(EditItem item) {
            Configure configure = Configure.getInstance();
            configure.bookmark.Update(item.getTag(), item.getTitle());
            if (editpage > 0)
                finish();
        }
    }


}
