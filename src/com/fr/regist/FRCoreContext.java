package com.fr.regist;

import java.util.*;
import java.io.*;

import com.fr.base.FRContext;
import com.fr.stable.*;
import java.util.concurrent.*;
import com.fr.plugin.manage.*;


public abstract class FRCoreContext
{
    private static final List<LicenseListener> LISTENERS;
    private static final String CONTEXT = "com.fr.license.selector.LicenseContext";

    private static void addShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                FRCoreContext.destroy();
            }
        });
    }

    public static void scheduleCheck() {
        FRContext.getLogger().info("should call --  com.fr.license.selector.LicenseContext::scheduleCheck");
    }

    public static void refresh() {
        FRContext.getLogger().info("should call --  com.fr.license.selector.LicenseContext::refresh");
    }

    public static License getLicense() {
        FRContext.getLogger().info("should call --  com.fr.license.selector.LicenseContext::currentLicense");
        return PlainTextLicense.getInstance();
    }

    @Deprecated
    public static License currentLicense() {
        FRContext.getLogger().info("should call --  com.fr.license.selector.LicenseContext::currentLicense");
        return PlainTextLicense.getInstance();
    }

    public static void init() {
        FRContext.getLogger().info("should call --  com.fr.license.selector.LicenseContext::init");
    }

    public static void destroy() {
        FRContext.getLogger().info("should call --  com.fr.license.selector.LicenseContext::destroy");
    }

    public static void listenLicense(final LicenseListener licenseListener) {
        if (licenseListener != null) {
            FRCoreContext.LISTENERS.add(licenseListener);
        }
    }

    @Deprecated
    public static void listenerLicense(final LicenseListener licenseListener) {
        listenLicense(licenseListener);
    }

    public static void fireLicenseChanged() {
        for (final LicenseListener licenseListener : getInOrder()) {
            try {
                licenseListener.onChange();
            }
            catch (Throwable t) {
                assert false;
                continue;
            }
        }
    }

    private static List<LicenseListener> getInOrder() {
        final ArrayList<LicenseListener> list = new ArrayList<LicenseListener>(FRCoreContext.LISTENERS);
        Collections.sort(list, (Comparator<LicenseListener>)new Comparator<LicenseListener>() {
            public int compare(final LicenseListener licenseListener, final LicenseListener licenseListener2) {
                return licenseListener2.getPriority() - licenseListener.getPriority();
            }
        });
        return (List<LicenseListener>)list;
    }

    @Deprecated
    public static String getLicenceString(final LicenseItem licenseItem) {
        return getLicenseItem(licenseItem);
    }

    public static String getLicenseItem(final LicenseItem licenseItem) {
        if (licenseItem == null) {
            return "";
        }
        return getLicense().getString(licenseItem.getKey(), "");
    }

    public static String getSoftLockContent(final String s) {
        final StringBuilder sb = new StringBuilder();
        final File file = new File(ProductConstants.getEnvHome() + File.separator + "System");
        if (file.exists()) {
            try {
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                for (String s2 = bufferedReader.readLine(); s2 != null; s2 = bufferedReader.readLine()) {
                    sb.append(s2);
                }
                bufferedReader.close();
            }
            catch (Exception ex) {}
        }
        if (!StringUtils.contains(sb.toString(), s)) {
            sb.append(s);
        }
        return sb.toString();
    }


    public static void stopLicense() {
        FRContext.getLogger().error("should call --  com.fr.license.selector.LicenseContext::stopLicense");
    }

    static {
        LISTENERS = new CopyOnWriteArrayList<LicenseListener>();
        PluginStartup.start();
        refresh();
        addShutDownHook();
    }
}