package Helper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.devappcenter.theme.Google.GoogleFragment;
import com.devappcenter.theme.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Helper.Adapter;
import Helper.Interface.LoadAsyncListener;
import Helper.LoadMoreScrollListener;
import Helper.UrlLoadAsync;
import Helper.ViewCell;

/**
 * Created by Andy on 3/4/15 AD.
 */
public abstract class FeedViewController extends Fragment {

    private Integer page;
    protected Adapter adapter;
    private String preload;
    protected ProgressBar loadingIndicator;
    private boolean isInit, isReady;

    public abstract String getUrl();
    public abstract ViewCell cellForItemAtIndex(JSONObject obj, Integer position);
    public abstract void OnItemClick(Integer position);
    public abstract JSONArray arrayOfItem(String result) throws JSONException;
    public abstract void OnInitialize();
    protected LoadMoreScrollListener loadMoreScrollListener;
    private LoadAsyncListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            if (getArguments() != null)
                preload = getArguments().getString("preload");
            isReady = true;
            page = 1;
        }
        else {
            isReady = savedInstanceState.getBoolean("ready");
            page = savedInstanceState.getInt("page");
            preload = savedInstanceState.getString("preload");
        }
        loadMoreScrollListener = getOnLoadListener();
        isInit = true;
        adapter = new Adapter(getActivity(), 0, new ArrayList());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.feed_view_controller, null, false);
        FrameLayout listViewContainer = (FrameLayout) view.findViewById(R.id.list_view_containner);
        loadingIndicator = (ProgressBar) view.findViewById(R.id.loading_indicator);
        AbsListView listView = (AbsListView)container;
        listView.setAdapter(adapter);
        listView.setOnScrollListener(loadMoreScrollListener);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OnItemClick(position);
            }
        });
        listViewContainer.addView(listView);
        if (isInit) {
            if (preload != null){
                if (isReady && loadMoreScrollListener != null) {
                    loadMoreScrollListener.page = this.page;
                    loadMoreScrollListener.ready();
                }
                try {
                    //JSONObject p = new JSONArray(preload).getJSONObject(0);
                    //JSONArray c = p.getJSONArray("Result");
                    addItem(arrayOfItem(preload));
                    preload = null;
                    //addItem(c);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else{
                adapter.setNotifyOnChange(true);
                Feed(0);
            }
        }
        isInit = false;
        return view;
    }

    protected LoadMoreScrollListener getOnLoadListener() {
        return new LoadMoreScrollListener() {
            @Override
            public void onLoadMore(Integer page) {
                Feed(page);
            }
        };
    }

    protected List<NameValuePair> getPostValue() {
        List<NameValuePair> postValue = new ArrayList<NameValuePair>(2);
        postValue.add(new BasicNameValuePair("page", String.valueOf(page)));
        postValue.add(new BasicNameValuePair("platform", "android"));
        return postValue;
    }

    protected void addItem(JSONArray jsonArray) throws JSONException {
        for (int i=0; i<jsonArray.length(); i++){
            JSONObject d = jsonArray.getJSONObject(i);
            adapter.add(cellForItemAtIndex(d, i));
        }
        if (loadMoreScrollListener != null)
            loadMoreScrollListener.ready();
    }

    protected void Feed(Integer page) {
        this.page = page;
        Map<String, String> mapUrl = new HashMap<String, String>();
        Map<String, List<NameValuePair>> mapPost = new HashMap<String, List<NameValuePair>>();
        mapPost.put("post", getPostValue());
        mapUrl.put("url", getUrl());
        UrlLoadAsync loadAsync = new UrlLoadAsync();
        loadAsync.setOnLoadAsyncListener(new LoadAsyncListener() {
            @Override
            public void onLoadFinished(String result) {
                if (result != null){
                    try {
                        //JSONObject p = new JSONArray(result).getJSONObject(0);
                        //JSONArray c = p.getJSONArray("Result");
                        addItem(arrayOfItem(result));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.setNotifyOnChange(true);

            }
        });
        loadAsync.execute(mapUrl, mapPost, null);
    }

    protected void Reload() {
        loadMoreScrollListener.reset();
        adapter.clear();
        Feed(0);
    }

    protected void ReloadWithFeedDisable() {
        loadMoreScrollListener.stop = true;
        adapter.clear();
        Feed(0);
    }
}
