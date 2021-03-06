package com.github.alebabai.tg2vk.service.tg.update.impl;

import com.github.alebabai.tg2vk.service.tg.update.TelegramUpdateHandler;
import com.github.alebabai.tg2vk.service.tg.update.TelegramUpdateListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelegramUpdateListenerImpl implements TelegramUpdateListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramUpdateListenerImpl.class);

    private final List<TelegramUpdateHandler> handlers;

    @Autowired
    public TelegramUpdateListenerImpl(List<TelegramUpdateHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void onUpdate(Update update) {
        handlers.parallelStream().forEach(handler -> destructUpdate(update, handler));
    }

    private void destructUpdate(Update update, TelegramUpdateHandler handler) {
        if (update == null) {
            LOGGER.debug("Can't handle empty update");
            return;
        }

        if (update.inlineQuery() != null) {
            handler.onInlineQueryReceived(update.inlineQuery());
        }

        if (update.chosenInlineResult() != null) {
            handler.onChosenInlineResultReceived(update.chosenInlineResult());
        }

        if (update.callbackQuery() != null) {
            handler.onCallbackQueryReceived(update.callbackQuery());
        }

        if (update.channelPost() != null) {
            handler.onChanelPostReceived(update.channelPost());
        }

        if (update.editedChannelPost() != null) {
            handler.onEditedChanelPostReceived(update.editedChannelPost());
        }

        if (update.message() != null) {
            handler.onMessageReceived(update.message());
        }

        if (update.editedMessage() != null) {
            handler.onEditedMessageReceived(update.editedMessage());
        }
    }
}
