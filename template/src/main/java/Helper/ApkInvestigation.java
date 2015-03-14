package Helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Andy on 2/26/15 AD.
 */
public class ApkInvestigation {

    JSONArray investigate;
    Context mContext;

    public ApkInvestigation(Context context, String info) {
        JSONArray allApk = null;
        mContext = context;
        investigate = new JSONArray();
        try {
            allApk = new JSONArray(info);
            for (int i=0; i<allApk.length(); i++) {
                JSONObject apk = allApk.getJSONObject(i);
                //JSONObject item = new JSONObject();
                //item.put("title", apk.getString("title"));
                //item.put("icon", apk.getString("icon"));
                PackageInfo packageInfo = packageExisted(apk.getString("package"));
                apk.put("exist", packageInfo!=null);
                apk.put("update", packageInfo!=null?packageInfo.versionCode<apk.getInt("version")?true:false:false);
                investigate.put(apk);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        return investigate.length();
    }

    public JSONObject getItem(int index) throws JSONException{
        return investigate.getJSONObject(index);
    }

    public void launchAppAtIndex(int index) {
        try {
            JSONObject apk = getItem(index);
            final String packagename = apk.getString("package");
            if (apk.getBoolean("exist")) {
                if (apk.getBoolean("update")) {
                    new AlertDialog.Builder(mContext)
                            .setMessage("เวอร์ชั่นใหม่ออกแล้ว คุณต้องการอัพเดทหรือไม่")
                            .setTitle("อัพเดทแอพ")
                            .setPositiveButton("อัพเดท", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse("market://details?id=" + packagename));
                                    mContext.startActivity(intent);
                                }
                            })
                            .setNegativeButton("เปิด", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent LaunchIntent = mContext.getPackageManager().getLaunchIntentForPackage(packagename);
                                    mContext.startActivity( LaunchIntent );
                                }
                            })
                            .show();
                }
                else {
                    Intent LaunchIntent = mContext.getPackageManager().getLaunchIntentForPackage(packagename);
                    mContext.startActivity( LaunchIntent );
                }
            }
            else {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id="+packagename));
                mContext.startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private PackageInfo packageExisted(String targetPackage){
        List<PackageInfo> packages;
        PackageManager pm = mContext.getPackageManager();
        packages = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : packages) {
            if(packageInfo.packageName.equals(targetPackage)) return packageInfo;
        }
        return null;
    }
}
