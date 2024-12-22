package com.sahilkamat.config;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;

public class BrowserCapabilities {
    // Method to generate a unique build name using current timestamp
    private static String generateBuildName() {
        return "Build-" + System.currentTimeMillis();
    }

    public static DesiredCapabilities getDesktopCapabilities(String os, String osVersion, String browser, String browserVersion) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browserName", browser);
        caps.setCapability("browserVersion", browserVersion);

        // BrowserStack-specific options
        Map<String, Object> browserstackOptions = new HashMap<>();
        browserstackOptions.put("os", os);
        browserstackOptions.put("osVersion", osVersion);
        browserstackOptions.put("projectName", "NewsPipeline");

        // Generate unique build name
        String buildName = generateBuildName();
        browserstackOptions.put("buildName", buildName); // Set the unique build name for each session

        browserstackOptions.put("sessionName", "Desktop Test");

        // Attach BrowserStack options to capabilities
        caps.setCapability("bstack:options", browserstackOptions);

        return caps;
    }

    public static DesiredCapabilities getMobileCapabilities(String device, String osVersion) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browserName", "chrome"); // Set the browser for mobile (e.g., Chrome for Android)
        caps.setCapability("browserVersion", "latest");

        // BrowserStack-specific options
        Map<String, Object> browserstackOptions = new HashMap<>();
        browserstackOptions.put("deviceName", device);
        browserstackOptions.put("osVersion", osVersion);
        browserstackOptions.put("realMobile", "true");
        browserstackOptions.put("projectName", "NewsPipeline");

        // Generate unique build name
        String buildName = generateBuildName();
        browserstackOptions.put("buildName", buildName); // Set the unique build name for each session

        browserstackOptions.put("sessionName", "Mobile Test");

        // Attach BrowserStack options to capabilities
        caps.setCapability("bstack:options", browserstackOptions);

        return caps;
    }
}