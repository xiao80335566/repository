package xydproject.pluginutil.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/6/1.
 */

public class StringUtil {
    /**
     * 判断是否是json结构
     */
    public static boolean isJson(String value) {
        try {
            new JSONObject(value);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

}
