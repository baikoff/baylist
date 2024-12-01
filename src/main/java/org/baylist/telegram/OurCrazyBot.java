package org.baylist.telegram;

import lombok.extern.slf4j.Slf4j;
import org.baylist.todoist.service.TodoistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;


@Component
@Slf4j
public class OurCrazyBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final String TOKEN_TG = System.getenv("TOKEN_TG");

    private final TelegramClient telegramClient;
    @Autowired
    private TodoistService todoistService;
//    @Autowired
//    TodoistController todoistController;

    public OurCrazyBot() {
        telegramClient = new OkHttpTelegramClient(getBotToken());
    }

    @Override
    public String getBotToken() {
        return TOKEN_TG;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            log.info("get message - {}", message_text);
//
//            todoistService.fillProjectMap();
//
//            List<String> projectsNames = Storage.projects
//                    .values()
//                    .stream()
//                    .map(Project::getName)
//                    .toList();
//
//            StringBuilder sb = new StringBuilder();
//            sb.append("Есть такие проекты: \n");
//            projectsNames.forEach(m-> sb.append(m).append("\n"));
//
//            SendMessage message;
//
//            if (message_text.equalsIgnoreCase("Проекты")) {
//                message = SendMessage.builder()
//                        .chatId(chat_id)
//                        .text(sb.toString())
//                        .build();
//
//            } else if (projectsNames.contains(message_text)) {
//                Project projectById = todoistService.getProjectById(Storage.projects.entrySet().stream().filter(e -> e.getValue().getName().equals(message_text)).findAny().orElseThrow().getKey());
//                message = SendMessage.builder()
//                        .chatId(chat_id)
//                        .text("подробности по проекту: \n" + projectById.toString())
//                        .build();
//            } else {
//                message = SendMessage.builder()
//                        .chatId(chat_id)
//                        .text("я отвечаю всегда однообразно")
//                        .build();
//            }
//
//            try {
//                telegramClient.execute(message);
//            } catch (TelegramApiException e) {
//                log.error(e.getMessage());
//            }
        }
    }


    @AfterBotRegistration
    public void afterRegistration(BotSession botSession) {
        System.out.println("Registered bot running state is: " + botSession.isRunning());
    }
}
