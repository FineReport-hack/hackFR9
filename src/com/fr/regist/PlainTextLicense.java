package com.fr.regist;

import com.fr.base.FRContext;
import com.fr.general.ComparatorUtils;
import com.fr.general.GeneralContext;
import com.fr.general.GeneralUtils;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.plugin.context.PluginMarker;
import com.fr.stable.ProductConstants;

import java.io.IOException;
import java.util.Calendar;

public class PlainTextLicense implements License {

    private static PlainTextLicense INSTANCE = new PlainTextLicense();

    private JSONObject licJSONObject;

    private PlainTextLicense() {
        licJSONObject = LicUtils.getJsonFromLic();
    }

    public static PlainTextLicense getInstance() {
        return INSTANCE;
    }

    private boolean contains(String s) {
        return licJSONObject != null && licJSONObject.has(s);
    }


    @Override
    public int getInt(String s, int i) {
        try {
            return contains(s) ? licJSONObject.getInt(s) : i;
        } catch (JSONException e) {
            e.printStackTrace();
            return i;
        }
    }

    @Override
    public long getLong(String s, long l) {
        try {
            return contains(s) ? licJSONObject.getLong(s) : l;
        } catch (JSONException e) {
            e.printStackTrace();
            return l;
        }
    }


    @Override
    public boolean getBoolean(String s, boolean b) {
        try {
            return contains(s) ? licJSONObject.getBoolean(s) : b;
        } catch (JSONException e) {
            e.printStackTrace();
            return b;
        }
    }

    @Override
    public String getString(String s, String s1) {
        try {
            return contains(s) ? licJSONObject.getString(s) : s1;
        } catch (JSONException e) {
            e.printStackTrace();
            return s1;
        }
    }

    @Override
    public boolean isOvertime() {
        FRContext.getLogger().info("isOvertime");
        return deadline() - Calendar.getInstance().getTimeInMillis() < 31536000000L;
    }

    @Override
    public boolean isOnTrial() {
        FRContext.getLogger().info("isOnTrial");
        return isOvertime();
    }

    @Override
    public boolean isTemp() {
        FRContext.getLogger().info("isTemp");
        return isOvertime();
    }

    @Override
    public long deadline() {
        FRContext.getLogger().info("deadline");
        return getLong(LicenseKey.DealLine.getKey(), -1);
    }

    @Override
    public int maxConcurrencyLevel() {
        FRContext.getLogger().info("maxConcurrencyLevel");
        return getInt(LicenseKey.MaxConcurrencyLevel.getKey(), -1);
    }

    @Override
    public int maxDataConnectionLevel() {
        FRContext.getLogger().info("maxDataConnectionLevel");
        return getInt(LicenseKey.MaxDataConnectionLevel.getKey(), -1);
    }

    @Override
    public int maxReportletLevel() {
        FRContext.getLogger().info("maxReportletLevel");
        return getInt(LicenseKey.MaxReportletLevel.getKey(), -1);
    }

    @Override
    public int maxDecisionUserLevel() {
        FRContext.getLogger().info("maxDecisionUserLevel");
        return getInt(LicenseKey.MaxDecisionUserLevel.getKey(), -1);
    }

    @Override
    public int maxMobileUserLevel() {
        FRContext.getLogger().info("maxMobileUserLevel");
        return getInt(LicenseKey.MaxMobileUserLevel.getKey(), -1);
    }

    @Override
    public String getVersion() {
        FRContext.getLogger().info("getVersion");
        return getString(LicenseKey.Version.getKey(), "");
    }

    @Override
    public boolean isVersionMatch() {
        FRContext.getLogger().info("ProductConstants.VERSION="+ProductConstants.VERSION);
        return ComparatorUtils.equals(ProductConstants.VERSION, getVersion());
    }

    @Override
    public String getAppName() {
        FRContext.getLogger().info("getAppName");
        return getString(LicenseKey.AppName.getKey(), "");
    }

    private String getWebAppName() {
        FRContext.getLogger().info("CurrentAppNameOfEnv="+GeneralContext.getCurrentAppNameOfEnv());
        return GeneralContext.getCurrentAppNameOfEnv();
    }

    @Override
    public boolean isAppNameMatch() {
        FRContext.getLogger().info("isAppNameMatch");
        return getWebAppName().equalsIgnoreCase(getAppName())
                || ComparatorUtils.equals(getAppName(), "");
    }

    @Override
    public String getAppContent() {
        FRContext.getLogger().info("getAppContent");
        return getString(LicenseKey.AppContent.getKey(), "");
    }

    @Override
    public boolean isAppContentMatch() {
        FRContext.getLogger().info("isAppContentMatch");
        return true;
    }

    @Override
    public String getUUID() {
        FRContext.getLogger().info("getUUID");
        return getString(LicenseKey.UUID.getKey(), "");
    }

    @Override
    public boolean isUUIDMatch() {
        FRContext.getLogger().info("isUUIDMatch");
        return GeneralUtils.isUUIDMatch(getUUID());
    }

    @Override
    public String getMacAddress() {
        FRContext.getLogger().info("getMacAddress");
        return getString(LicenseKey.MACAddress.getKey(), "");
    }

    @Override
    public boolean isMacAddressMatch() {
        FRContext.getLogger().info("isMacAddressMatch");
        try {
            return GeneralUtils.isMacAddressMatch(getMacAddress());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String templateEncryptionKey(String s) {
        FRContext.getLogger().info("templateEncryptionKey");
        return getString(LicenseKey.EncodeKey.getKey(), "");
    }

    @Override
    public String companyName(String s) {
        FRContext.getLogger().info("companyName");
        return getString(LicenseKey.CompanyName.getKey(), "");
    }

    @Override
    public String projectName(String s) {
        FRContext.getLogger().info("projectName");
        return getString(LicenseKey.ProjectName.getKey(), "");
    }

    @Override
    public boolean support(FunctionPoint functionPoint) {
        FRContext.getLogger().info("support -- FunctionPoint");
        return true;
    }

    @Override
    public boolean support(PluginMarker pluginMarker) {
        FRContext.getLogger().info("support -- PluginMarker");
        return true;
    }

    @Override
    public JSONObject getJSONObject() {
        FRContext.getLogger().info("getJSONObject");
        return licJSONObject;
    }
}
