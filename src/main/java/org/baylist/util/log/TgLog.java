package org.baylist.util.log;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.baylist.util.log.LogUtil.reduceEmptyLines;

@Slf4j
public class TgLog {


    public static void inputLog(Update update) {
        String user_first_name = update.getMessage().getChat().getFirstName();
        String user_last_name = update.getMessage().getChat().getLastName();
        long user_id = update.getMessage().getChat().getId();
        String answer = update.getMessage().getText();

        log.info(" -> Message from {} {} (id = {}) \n Text - {}",
                user_first_name,
                user_last_name,
                user_id,
                answer);
    }

    public static SendMessage outputLog(SendMessage message) {
        int qtyCharToCutMessage = 150;
        String chatId = message.getChatId();
        String text = message.getText();
        text = reduceEmptyLines(text);
        if (text.length() > qtyCharToCutMessage) {
            text = text.substring(0, qtyCharToCutMessage) + "...";
        }
        log.info(" -> Message to chat id - {}, \n Text - {}",
                chatId,
                text);
        return message;
    }


}
