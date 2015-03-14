package Helper;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Helper.Interface.LoadAsyncListener;

/**
 * Created by Andy on 2/28/15 AD.
 */
public abstract class SpyAdventure {

    public abstract void onResult(String result);

    public SpyAdventure(final JSONObject info) throws JSONException{
        String source = info.getString("source");
        final String destination = info.getString("dest");
        Map<String, String> mapUrl = new HashMap<String, String>();
        Map<String, List<? extends NameValuePair>> mapPost = new HashMap<String, List<? extends NameValuePair>>();
        List<NameValuePair> postValue = new ArrayList<NameValuePair>(2);
        mapUrl.put("url", info.getString("source"));
        mapPost.put("post", postValue);
        UrlLoadAsync loadAsync = new UrlLoadAsync();
        loadAsync.setOnLoadAsyncListener(new LoadAsyncListener() {
            @Override
            public void onLoadFinished(String result) {
                /*
                Map<String, String> mapUrl = new HashMap<String, String>();
                Map<String, List<? extends NameValuePair>> mapPost = new HashMap<String, List<? extends NameValuePair>>();
                List<NameValuePair> postValue = new ArrayList<NameValuePair>(2);
                mapUrl.put("url", destination);
                postValue.add(new BasicNameValuePair("action", "setinfo"));
                postValue.add(new BasicNameValuePair("data", result));
                mapPost.put("post", postValue);
                UrlLoadAsync loadAsync = new UrlLoadAsync();
                loadAsync.setOnLoadAsyncListener(new LoadAsyncListener() {
                    @Override
                    public void onLoadFinished(String result) {
                        onResult(result);
                    }
                });
                loadAsync.execute(mapUrl, mapPost, null);
                */
                onResult(result.substring(1, result.length()-2));
            }
        });
        loadAsync.execute(mapUrl, mapPost, null);
    }
}
