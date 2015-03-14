package com.devappcenter.pdfreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.artifex.mupdfdemo.MuPDFActivity;
import com.devappcenter.theme.Google.GoogleFragmentFeeder;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import Helper.ImageLoader;
import Helper.ViewCell;

/**
 * Created by Andy on 3/14/15 AD.
 */
public class BookViewController extends GoogleFragmentFeeder {

    private ProgressDialog progressDialog;

    @Override
    public String setHeaderMenuWithTitle() {
        return "รายการหนังสือ";
    }

    @Override
    public String getUrl() {
        return "http://www.lightoflovenovel.com/ebook/ios/discover.php";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, new ListView(getActivity()), savedInstanceState);
    }

    @Override
    public ViewCell cellForItemAtIndex(JSONObject obj, Integer position) {
        return new ViewItem(getActivity(), obj);
    }

    @Override
    public JSONArray arrayOfItem(String result) throws JSONException{
        return new JSONArray(result).getJSONArray(0);
    }

    @Override
    protected List<NameValuePair> getPostValue() {
        List<NameValuePair> postValue = super.getPostValue();
        postValue.add(new BasicNameValuePair("filter", "new_entry"));
        return postValue;
    }

    @Override
    public void OnItemClick(Integer position) {
        /*
        ViewItem item = (ViewItem) adapter.getItem(position);
        progressDialog = ProgressDialog.show(getActivity(),"","Loading..", true);
        String location = getActivity().getDir("book_preview", android.content.Context.MODE_PRIVATE).getAbsolutePath() + "/preview.pdf";
        new DownloadFileAsync().execute(item.getPreview(), location, item.getTitle());
        */
        FragmentManager fragmentManager = getFragmentManager();
        PreviewBookViewController viewController = new PreviewBookViewController();
        fragmentManager.beginTransaction().replace(R.id.content_frame, viewController, "PREVIEW").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
    }

    private class ViewItem extends ViewCell {

        public ViewItem(Context context, JSONObject json) {
            super(context, json);
        }

        @Override
        public View getViewItem(LayoutInflater inflater, View convertView) {
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.cart_item, null);
            }
            ImageView imgCover = (ImageView)view.findViewById(R.id.img_cart_cover);
            TextView lblTitle = (TextView) view.findViewById(R.id.lbl_cart_title);
            TextView lblPrice = (TextView) view.findViewById(R.id.lbl_cart_price);
            ImageLoader imageLoader = ImageLoader.getInstance(null);
            try {
                Integer price = jsonItem.getInt("price");
                lblTitle.setText(jsonItem.getString("title"));
                lblPrice.setText(String.format("%d บาท", price));
                imageLoader.DisplayImage(jsonItem.getString("thumb"), imgCover);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return view;
        }

        public String getTitle() {
            String title = null;
            try {
                title = jsonItem.getString("title");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return title;
        }

        public String getPreview() {
            String preview = null;
            try {
                preview = jsonItem.getString("preview");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return preview;
        }
    }

    class DownloadFileAsync extends AsyncTask<String, String, Void>
    {
        String location, title;

        @Override
        protected Void doInBackground(String... params){
            int count;
            String strUrl = params[0];
            location = params[1];
            title = params[2];
            try {
                //URL url = new URL(params[0].get("url"));
                URL url = new URL(strUrl);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(location);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1){
                    total += count;
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {}
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            Bundle bundle = new Bundle();
            Uri uri = Uri.parse(location);
            progressDialog.cancel();
            bundle.putString("title", title);
            bundle.putBoolean("preview", true);
            Intent intent = new Intent(getActivity(), MuPDFActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
