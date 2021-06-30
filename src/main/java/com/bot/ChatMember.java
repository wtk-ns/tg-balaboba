package com.bot;

public class ChatMember {

    private final Long chatID;

    public ChatMember(Long chatID){
        this.chatID = chatID;
    }

    public Long getChatID(){
        return chatID;
    }
}
