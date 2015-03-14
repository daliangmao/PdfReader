package Helper;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by Andy on 2/24/15 AD.
 */
public class Utils {

    private static final String REGEX_INPUT_BOUNDARY_BEGINNING = "\\A";

    public static JSONObject loadJsonFromAsset(InputStream inputStream) {
        String json = new Scanner(inputStream).useDelimiter(REGEX_INPUT_BOUNDARY_BEGINNING).next();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
