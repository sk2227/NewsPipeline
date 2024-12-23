package com.sahilkamat.config;

import com.sahilkamat.utils.ConfigLoader;

public class BrowserStackConfig {

    public static final String USERNAME= ConfigLoader.get("browserstack.username");
    public static final String ACCESS_KEY=ConfigLoader.get("browserstack.access_key");
    public static final String HUB_URL = "https://"+USERNAME+":"+ACCESS_KEY+"@hub-cloud.browserstack.com/wd/hub";
}
