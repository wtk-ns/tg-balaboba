package com.bot;


import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class Bot extends TelegramLongPollingBot {

    private boolean isParse = false;
    private ChromeDriver chromeDriver;
    private static final Map<String, String> getenv = System.getenv();

    public Bot(){
        chromeDriver = WebDriver.getDriver();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage().getText().equals("/start")){
            sendMsg(update.getMessage().getChatId(), "Ога, пиши начало фразы в чат.\nДа, он долгий\nПросто написал - подождал");
        } else {
            if (!isParse) {
                try {
                    String response = getTexty(update.getMessage().getText());
                    sendMsg(update.getMessage().getChatId(), response);
                    isParse = false;
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    public String getTexty(String text) throws Exception{
        isParse = true;
        chromeDriver.manage().window().setSize(new Dimension(1920,1080));
        chromeDriver.get("https://yandex.ru/lab/yalm?style=1");

        String responceText = "";

        try {

            WebElement button = (new WebDriverWait(chromeDriver, 1))
                    .until(ExpectedConditions.presenceOfElementLocated(By.className("Button2")));

            button.click();

            WebElement textField = (new WebDriverWait(chromeDriver, 1))
                    .until(ExpectedConditions.presenceOfElementLocated(By.className("Textarea-Control")));

            WebElement confirmButton = (new WebDriverWait(chromeDriver, 5))
                    .until(ExpectedConditions.presenceOfElementLocated(By.tagName("button")));

            textField.sendKeys(text);
            confirmButton.click();

            WebElement response = (new WebDriverWait(chromeDriver, 10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.className("response__text")));

            responceText = response.getText();
        } catch (Exception e){
            responceText = "Чета нет";
        }

        return responceText;
    }



    public void sendMsg(Long member, String text){

        SendMessage msg = new SendMessage();
        msg.setChatId(member);
        msg.setText(text);

        try {
            execute(msg);
        } catch (TelegramApiException exception) {
            exception.printStackTrace();
        }

    }


    @Override
    public String getBotUsername() {
        return getenv.get("BOT_NAME");
    }

    @Override
    public String getBotToken() {
        return getenv.get("BOT_TOKEN");
    }
}
