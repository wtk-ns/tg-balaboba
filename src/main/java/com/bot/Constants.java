package com.bot;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public final class Constants {
    public static final Map<String, String> sysEnv = System.getenv();


    private static List<ChatMember> members = new ArrayList<>();
    public static Logger logger = Logger.getLogger(Main.class.getName());


    private static void addMember(Long chatID) throws AlreadyChatMemberException{

        logger.info("Adding member");

        boolean hasInMembers = false;

        for (ChatMember member : members){
            hasInMembers = member.getChatID().equals(chatID);
        }

        if (!hasInMembers){
            members.add(new ChatMember(chatID));
            logger.info("New member: " + chatID + " added");
        } else {
            throw new AlreadyChatMemberException();
        }

    }



    @NotNull
    public static ChatMember getMember(Long chatID){

        logger.info("Getting chat member...");

        try {
            addMember(chatID);
        } catch (AlreadyChatMemberException e) {
            logger.info("Member: " + chatID + " is already chat member");
        }

        ChatMember chatMember = null;
        for (ChatMember member : members){
            if (member.getChatID() == chatID) {
                chatMember = member;
            }
        }

        if (chatMember != null){
            logger.info("Member was found");
        }

        return  chatMember;

    }

}

class AlreadyChatMemberException extends Exception{
    public AlreadyChatMemberException() {
        super("This member already exists in com.bot.Constants List<ChatMembers> members");
    }
}
