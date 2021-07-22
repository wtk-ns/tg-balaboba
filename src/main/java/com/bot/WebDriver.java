package com.bot;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriver {

    private static ChromeDriver chromeDriver;

    private WebDriver(){
    }

    private static void setUpDriver(){
        System.setProperty("webdriver.chrome.driver", "src/chromedriver/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("-no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        chromeDriver = new ChromeDriver(options);
    }

    public static ChromeDriver getDriver(){
        setUpDriver();
        return chromeDriver;
    }
}
