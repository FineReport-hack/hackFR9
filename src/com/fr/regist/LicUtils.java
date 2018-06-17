package com.fr.regist;

import com.fr.base.Env;
import com.fr.base.FRContext;
import com.fr.base.Utils;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;

import java.io.InputStream;

public abstract class LicUtils {

    public static JSONObject getJsonFromLic() {
        JSONObject localJSONObject = null;
        try {
            localJSONObject = new JSONObject(getLicAsStr());
        } catch (JSONException e) {
        }
        return localJSONObject;
    }

    public static String getLicAsStr() {
        String result = "";
        try {
            InputStream localInputStream = getLicFileStream();
            result = Utils.inputStream2String(localInputStream, "UTF-8");
        } catch (Exception e) {
        }
        return result;
    }

    private static InputStream getLicFileStream() {
        try
        {
            Env localEnv = FRContext.getCurrentEnv();
            String str = localEnv.getLicName();
            InputStream localInputStream = localEnv.readBean(str, "resources");
            return localInputStream;
        }
        catch (Exception localException) {
            return null;
        }
    }
}
