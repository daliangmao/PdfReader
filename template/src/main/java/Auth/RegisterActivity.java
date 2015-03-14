package Auth;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.View;

import org.apache.http.NameValuePair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Helper.DialogActivity;
import Helper.Interface.LoadAsyncListener;
import Helper.UrlLoadAsync;

/**
 * Created by Andy on 3/8/15 AD.
 */
public abstract class RegisterActivity extends DialogActivity implements RegisterListener{

    public abstract String getUrlRegisteration();
    public abstract int minPasswordLength();
    public abstract List<NameValuePair> getPost(String username, String email, String password);

    @Override
    public View getViewContent() {
        return null;
    }

    @Override
    public void onRegister(String username, String email, String password, String confirm_password) {
        if (!password.equals(confirm_password)){
            new AlertDialog.Builder(this)
                    .setMessage("รหัสผ่านไม่ตรงกัน, กรุณาลองอีกครั้ง")
                    .setTitle("ผิดพลาด")
                    .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        }
        else if (username.equals("") || email.equals("") || password.equals("")) {
            new AlertDialog.Builder(this)
                    .setMessage("ข้อมูลไม่ครบถ้วน, กรุณาตรวจสอบ")
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
            final ProgressDialog progressDialog = ProgressDialog.show(this, "", "กำลังลงทะเบียน...", true);
            Map<String, String> mapUrl = new HashMap<String, String>();
            Map<String, List<? extends NameValuePair>> mapPost = new HashMap<String, List<? extends NameValuePair>>();
            mapPost.put("post", getPost(username.toLowerCase(), email.toLowerCase(), password));
            mapUrl.put("url", getUrlRegisteration());
            UrlLoadAsync request = new UrlLoadAsync();
            request.setOnLoadAsyncListener(new LoadAsyncListener() {
                @Override
                public void onLoadFinished(String result) {

                }
            });
            request.execute(mapUrl, mapPost, null);
        }
    }
}
