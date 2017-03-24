package com.github.alebabai.tg2vk.service.impl;

import com.github.alebabai.tg2vk.domain.ChatSettings;
import com.github.alebabai.tg2vk.domain.Role;
import com.github.alebabai.tg2vk.domain.User;
import com.github.alebabai.tg2vk.domain.UserSettings;
import com.github.alebabai.tg2vk.repository.ChatSettingsRepository;
import com.github.alebabai.tg2vk.repository.UserRepository;
import com.github.alebabai.tg2vk.repository.UserSettingsRepository;
import com.github.alebabai.tg2vk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserSettingsRepository settingsRepository;
    private final ChatSettingsRepository chatSettingsRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserSettingsRepository settingsRepository,
                           ChatSettingsRepository chatSettingsRepository) {
        this.userRepository = userRepository;
        this.settingsRepository = settingsRepository;
        this.chatSettingsRepository = chatSettingsRepository;
    }

    @Override
    public List<User> findAllStarted() {
        return userRepository.findAllStarted();
    }

    @Override
    public Optional<User> findOneByVkId(Integer id) {
        return userRepository.findOneByVkId(id);
    }

    @Override
    public Optional<User> findOneByTgId(Integer id) {
        return userRepository.findOneByTgId(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserSettings updateUserSettings(UserSettings settings) {
        Assert.isTrue(!settings.isNew(), "Can't update unlinked user settings!");
        return settingsRepository.save(settings);
    }

    @Transactional
    @Override
    public User createOrUpdate(Integer tgId, Integer vkId, String vkToken) {
        Assert.notNull(tgId, "tgId is required param!");
        Assert.notNull(tgId, "vkId is required param!");
        Assert.notNull(tgId, "vkToken is required param!");
        return userRepository.findOneByTgId(tgId)
                .map(user -> {
                    chatSettingsRepository.delete(user.getChatsSettings());
                    user.setVkId(vkId).setVkToken(vkToken);
                    return userRepository.save(user);
                })
                .orElseGet(() -> userRepository.save(new User()
                        .setVkId(vkId)
                        .setTgId(tgId)
                        .setVkToken(vkToken)
                        .setRoles(Collections.singleton(Role.USER))));
    }

    @Override
    public Optional<ChatSettings> findChatSettings(User user, Integer vkChatId) {
        return user.getChatsSettings().stream()
                .filter(chatSettings -> Objects.equals(vkChatId, chatSettings.getVkChatId()))
                .findFirst();
    }
}
