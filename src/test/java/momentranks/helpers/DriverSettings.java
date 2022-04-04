package momentranks.helpers;

import com.codeborne.selenide.Configuration;
import momentranks.config.Project;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;

public class DriverSettings {

    public static void configure() {
        Configuration.browser = Project.config.browser();
        Configuration.browserVersion = Project.config.browserVersion();
        Configuration.browserSize = Project.config.browserSize();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        ChromeOptions chromeOptions = new ChromeOptions();

        // https://peter.sh/experiments/chromium-command-line-switches/
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-infobars");
        // Disables pop-up blocking.
        chromeOptions.addArguments("--disable-popup-blocking");
        // Disables the Web Notification and the Push APIs.
        chromeOptions.addArguments("--disable-notifications");
        // The language file that we want to try to open. Of the form language[-country] where
        // language is the 2 letter code from ISO-639.
        chromeOptions.addArguments("--lang=en-en");

        if (Project.isWebMobile()) {
            Map<String, Object> mobileDevice = new HashMap<>();
            mobileDevice.put("deviceName", Project.config.browserMobileView());
            chromeOptions.setExperimentalOption("mobileEmulation", mobileDevice);
        }

        if (Project.isRemoteWebDriver()) {
            String login = System.getProperty("login");
            String pass = System.getProperty("pass");

            Configuration.remote = "https://" + login + ":" + pass + "@" + Project.config.remoteDriverUrl() + "/wd/hub";
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", true);
        }

        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        Configuration.browserCapabilities = capabilities;
    }
}