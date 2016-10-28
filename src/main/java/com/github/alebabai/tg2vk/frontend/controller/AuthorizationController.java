package com.github.alebabai.tg2vk.frontend.controller;

import com.github.alebabai.tg2vk.service.PathResolverService;
import com.github.alebabai.tg2vk.service.VkService;
import com.github.alebabai.tg2vk.util.constants.PathConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.util.Date;
import java.util.Map;

import static com.github.alebabai.tg2vk.util.constants.VkConstants.VK_SCOPE_MESSAGES;
import static com.github.alebabai.tg2vk.util.constants.VkConstants.VK_SCOPE_OFFLINE;
import static com.github.alebabai.tg2vk.util.constants.VkConstants.VK_URL_REDIRECT;

@Controller
public class AuthorizationController {

    @Autowired
    private VkService vkService;

    @Autowired
    private PathResolverService pathResolver;

    @GetMapping(PathConstants.ROOT_PATH)
    public String page(Map<String, Object> model) {
        model.put("time", new Date());
        model.put("message", "Hello");
        return "page.html";
    }

    @RequestMapping(PathConstants.LOGIN_PATH)
    public String login() {
        final String[] scopes = {
                VK_SCOPE_MESSAGES,
                VK_SCOPE_OFFLINE
        };
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + vkService.getAuthorizeUrl(VK_URL_REDIRECT, scopes);
    }

    @RequestMapping(PathConstants.AUTHORIZE_PATH)
    public String authorize(@RequestParam String code) {
        vkService.authorize(code);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + PathConstants.ROOT_PATH;
    }
}