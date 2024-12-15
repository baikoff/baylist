package org.baylist.dto.telegram;

import lombok.Data;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Data
public class ChatState {

    //тут предполагается хранить нить разговора и состояние беседы.
    //пример использования см тут - https://github.com/BadHard101/RemindMe7Bot/blob/master/src/main/java/com/example/remindme7bot/service/TelegramBot.java
    // данную дто нужно будет переделать по ситуации, это просто пример

    private Long chatId;
    private Update update;
    private SendMessage message;

    private boolean commandIsProcess;

    public ChatState(Update update) {
        this.update = update;
        if (update.hasMessage() && update.getMessage().hasText()) {
            this.chatId = update.getMessage().getChatId();
        } else {
            this.chatId = update.getCallbackQuery().getMessage().getChatId();
        }
        this.message = SendMessage.builder().text("").chatId(chatId).build();
    }

}
