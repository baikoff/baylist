package org.baylist.telegram;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baylist.service.TodoistService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.baylist.util.log.TgLog.inputLogMessage;

@Component
@Slf4j
@AllArgsConstructor
public class TelegramChat {

    private TodoistService todoist;
    private Command command;

    public SendMessage chat(Update update) {
        String updateMessage = update.getMessage().getText();
        inputLogMessage(update);
        SendMessage message = SendMessage.builder().text("").chatId(update.getMessage().getChatId()).build();


        if (todoist.storageIsEmpty()) {
            todoist.syncBuyListData();
        }

        //todo для обработки чата применить https://refactoring.guru/ru/design-patterns/chain-of-responsibility/java/example
        message = command.commandHandler(updateMessage, message);
        if (message.getText().isEmpty()) {
            message = todoist.sendTaskToTodoist(updateMessage, message);
        }
        return message;
    }


}
