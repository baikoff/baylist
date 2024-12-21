package org.baylist.telegram2.hanlder;

import lombok.AllArgsConstructor;
import org.baylist.db.entity.User;
import org.baylist.dto.telegram.ChatValue;
import org.baylist.dto.telegram.State;
import org.baylist.service.UserService;
import org.baylist.telegram2.hanlder.config.DialogHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;

@Component
@AllArgsConstructor
public class FeedbackHandler implements DialogHandler {

	UserService userService;

	@Override
	public void handle(ChatValue chatValue) {
		User fil = userService.getFil();
		chatValue.setReplyText("""
				спасибо за обратную связь.
				ваше сообщение будет передано куда надо
				""");
		ForwardMessage forwardMessage = ForwardMessage.builder()
				.chatId(fil.getUserId())
				.messageId(chatValue.getUpdate().getMessage().getMessageId())
				.fromChatId(chatValue.getUpdate().getMessage().getChatId())
				.build();
		chatValue.setForwardMessage(forwardMessage);
		chatValue.setState(State.DEFAULT);
	}
}