package com.bot;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class Bot extends TelegramLongPollingBot {



    BobaThread thread = null;
    private boolean isParse = false;
    ChromeDriver chromeDriver;

    public Bot(){

        System.setProperty("webdriver.chrome.driver", "src/chromedriver/chromedriver.exe");
        chromeDriver = new ChromeDriver();

    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage().getText().equals("/start")){
            sendMsg(update.getMessage().getChatId(), "Ога, пиши начало фразы в чат.\nДа, он долгий, потому что selenium\nПросто написал - подождал");
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

        chromeDriver.manage().window().maximize();


        chromeDriver.get("https://yandex.ru/lab/yalm?style=6");

        String respon = "";

        try {

            WebElement button = (new WebDriverWait(chromeDriver, 1))
                    .until(ExpectedConditions.presenceOfElementLocated(By.className("Button2")));
            //WebElement button = chromeDriver.findElement(By.className("Button2"));

            button.click();


            //chromeDriver.manage().timeouts().pageLoadTimeout(200, TimeUnit.MILLISECONDS);


            WebElement textField = (new WebDriverWait(chromeDriver, 1))
                    .until(ExpectedConditions.presenceOfElementLocated(By.className("Textarea-Control")));

            //WebElement textField = chromeDriver.findElement(By.className("Textarea-Control"));

            WebElement confirmButton = (new WebDriverWait(chromeDriver, 5))
                    .until(ExpectedConditions.presenceOfElementLocated(By.tagName("button")));
            //WebElement confirmButton = chromeDriver.findElement(By.tagName("button"));


            textField.sendKeys(text);
            confirmButton.click();


            //WebDriverWait wait = new WebDriverWait(chromeDriver, 10);
            //wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("response__text")));

            WebElement response = (new WebDriverWait(chromeDriver, 10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.className("response__text")));
            //WebElement response = chromeDriver.findElement(By.className("response__text"));

            respon = response.getText();
        } catch (Exception e){
            respon = "Чета нет";
        }


        //chromeDriver.close();


        return respon;

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
        return Constants.sysEnv.get("BOT_NAME");
    }

    @Override
    public String getBotToken() {
        return Constants.sysEnv.get("BOT_TOKEN");
    }
}
