package com.fr.regist;

public enum LicenseKey {

    Version("VERSION"),
    MACAddress("MACADDRESS"),
    DealLine("DEADLINE"),
    AppName("APPNAME"),
    AppContent("APPCONTENT"),
    UUID("UUID"),
    ProjectName("PROJECTNAME"),
    CompanyName("COMPANYNAME"),
    EncodeKey("KEY"),
    MaxConcurrencyLevel("MAX_CONCURRENCY_LEVEL"),
    MaxDataConnectionLevel("MAX_DATA_CONNECTION_LEVEL"),
    MaxReportletLevel("MAX_REPORTLET_LEVEL"),
    MaxDecisionUserLevel("MAX_DECISION_USER_LEVEL"),
    MaxMobileUserLevel("MAX_MOBILE_USER_LEVEL");

    private String key;

    private LicenseKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public String toString() {
        return this.getKey();
    }

}
