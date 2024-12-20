package org.baylist.telegram;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baylist.dto.telegram.ChatState;
import org.baylist.service.DictionaryService;
import org.baylist.service.FeedbackService;
import org.baylist.service.TodoistService;
import org.springframework.stereotype.Component;

import static org.baylist.util.log.TgLog.inputLogMessage;

@Component
@Slf4j
@AllArgsConstructor
public class TelegramChat {

    private TodoistService todoist;
	private FeedbackService feedbackService;
    private Command command;
	private DictionaryService dictionaryService;

    public void chat(ChatState chatState) {
        inputLogMessage(chatState.getUpdate());

        if (todoist.storageIsEmpty()) {
            todoist.syncBuyListData();
        }

        //todo для обработки чата применить https://refactoring.guru/ru/design-patterns/chain-of-responsibility/java/example
        command.commandHandler(chatState);

	    if (!chatState.isCommandProcess()) {
		    if (chatState.getUser().getDialog().isReport()) {
			    feedbackService.acceptFeedback(chatState);
		    } else if (chatState.getUser().getDialog().isAddCategory()) {
			    dictionaryService.addDictCategory(chatState);
		    } else {
			    todoist.sendTasksToTodoist(chatState);
		    }
        }
    }


}
