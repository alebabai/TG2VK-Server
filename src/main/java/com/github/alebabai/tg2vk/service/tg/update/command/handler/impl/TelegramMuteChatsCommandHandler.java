package com.github.alebabai.tg2vk.service.tg.update.command.handler.impl;

import com.github.alebabai.tg2vk.domain.Chat;
import com.github.alebabai.tg2vk.repository.UserRepository;
import com.github.alebabai.tg2vk.service.tg.common.TelegramService;
import com.github.alebabai.tg2vk.service.tg.update.query.callback.handler.TelegramCallbackQueryData;
import com.github.alebabai.tg2vk.service.vk.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

@Service("muteCommandHandler")
public class TelegramMuteChatsCommandHandler extends AbstractTelegramChatsCommandHandler {

    @Autowired
    public TelegramMuteChatsCommandHandler(UserRepository userRepository,
                                           TelegramService tgService,
                                           VkService vkService,
                                           MessageSourceAccessor messages) {
        super(userRepository, tgService, vkService, messages);
    }

    @Override
    protected String getCodePrefix() {
        return "tg.command.mute.msg.";
    }

    @Override
    protected TelegramCallbackQueryData getCallbackData(Integer tgChatId, Chat chat) {
        return TelegramCallbackQueryData.builder()
                .type("mute")
                .tgChatId(tgChatId)
                .vkChatId(chat.getId())
                .build();
    }
}
