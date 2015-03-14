package Auth;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Helper.DialogActivity;
import Helper.Interface.LoadAsyncListener;
import Helper.UrlLoadAsync;

/**
 * Created by Andy on 8/16/14 AD.
 */
public abstract class LoginActivity extends DialogActivity  implements LoginListener{

    public abstract String getUrlAuthentication();
    public abstract int minPasswordLength();
    public abstract List<NameValuePair> getPost(String username, String password);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View getViewContent() {
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLogin(String username, String password) {
        if (username.equals("") || password.equals("")) {
            new AlertDialog.Builder(this)
                    .setMessage("กรอกข้อมูลไม่ครบถ้วน, กรุณาลองอีกครั้ง")
                    .setTitle("ผิดพลาด")
                    .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        }
        else if (password.length() < minPasswordLength()) {
            new AlertDialog.Builder(this)
                    .setMessage("รหัสผ่านอย่างน้อย 5 ตัวอักษร")
                    .setTitle("ผิดพลาด")
                    .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        }
        else {
            final ProgressDialog progressDialog = ProgressDialog.show(this, "", "กำลังลอกอิน..", true);
            Map<String, String> mapUrl = new HashMap<String, String>();
            Map<String, List<? extends NameValuePair>> mapPost = new HashMap<String, List<? extends NameValuePair>>();
            mapPost.put("post", getPost(username.toLowerCase(), password));
            mapUrl.put("url", getUrlAuthentication());
            UrlLoadAsync request = new UrlLoadAsync();
            request.setOnLoadAsyncListener(new LoadAsyncListener() {
                @Override
                public void onLoadFinished(String result) {
                    progressDialog.cancel();
                    try {
                        JSONObject response = new JSONArray(result).getJSONObject(0);
                        Boolean status = response.getBoolean("status");
                        if (status == true) {
                            Intent resIntent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString("info", response.toString());
                            resIntent.putExtras(bundle);
                            setResult(AuthResponse.LOGIN.ordinal(), resIntent);
                            finish();
                        }
                        else {
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setMessage(response.getString("message"))
                                    .setTitle("ผิดพลาด")
                                    .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                        }
                                    })
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            request.execute(mapUrl, mapPost, null);
        }
    }

}
