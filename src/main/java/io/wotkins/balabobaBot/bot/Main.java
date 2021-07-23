package io.wotkins.balabobaBot.bot;

import io.wotkins.balabobaBot.utility.WebDriver;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;


public class Main {
    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi api = new TelegramBotsApi();

        try {
            api.registerBot(new Bot(WebDriver.getDriver()));

        } catch (TelegramApiRequestException exception)
        {
            exception.printStackTrace();
        }

    }
}
