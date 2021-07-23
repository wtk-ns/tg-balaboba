package io.wotkins.balabobaBot.utility;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Properties;

public class WebDriver {

    private static ChromeDriver chromeDriver;
    private static final Properties properties = PropertiesLoader.getProperties();

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
        chromeDriver.manage().window().setSize(new Dimension(1920,1080));
        chromeDriver.get(properties.getProperty("parsedURI"));
    }

    public static ChromeDriver getDriver(){
        setUpDriver();
        return chromeDriver;
    }
}
