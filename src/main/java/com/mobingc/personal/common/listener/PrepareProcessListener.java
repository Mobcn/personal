package com.mobingc.personal.common.listener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mobingc.personal.common.utils.JSONUtils;
import com.mobingc.personal.model.entity.Setting;
import com.mobingc.personal.model.entity.User;
import com.mobingc.personal.repository.SettingRepository;
import com.mobingc.personal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据库初始化
 */
@Slf4j
@Component
@Order(0)
public class PrepareProcessListener implements ApplicationListener<ApplicationReadyEvent>, Ordered {

    private final UserRepository userRepository;
    private final SettingRepository settingRepository;

    public PrepareProcessListener(UserRepository userRepository, SettingRepository settingRepository) {
        this.userRepository = userRepository;
        this.settingRepository = settingRepository;
    }

    @Override
    public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {

        log.info("初始化数据库...");

        // 初始化设置表数据
        Resource resource = new ClassPathResource("setting-default.json");
        String settingJSON;
        try (
                InputStream is = resource.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(isr);
        ) {
            StringBuilder sb = new StringBuilder();
            String ls = System.getProperty("line.separator");
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(ls);
            }
            sb.deleteCharAt(sb.length() - 1);
            settingJSON = sb.toString();
        } catch (IOException e) {
            throw new RuntimeException("设置JSON文件读取失败!", e);
        }
        Map<String, Map<String, Object>> settingMap = JSONUtils.parse2Object(settingJSON, new TypeReference<Map<String, Map<String, Object>>>(){});
        List<Setting> existSettingList = settingRepository.findAllByKeyIn(Objects.requireNonNull(settingMap).keySet());
        List<String> existSettingKeyList = existSettingList.stream().map(Setting::getKey).collect(Collectors.toList());
        List<Setting> addSettingList = new ArrayList<>();
        for (Map.Entry<String, Map<String, Object>> settingEntity : Objects.requireNonNull(settingMap).entrySet()) {
            String key = settingEntity.getKey();
            if (!existSettingKeyList.contains(key)) {
                Map<String, Object> entityValue = settingEntity.getValue();
                Setting setting = new Setting();
                setting.setKey(key);
                setting.setDescription((String) entityValue.get("description"));
                setting.setIsPublic((Boolean) entityValue.get("isPublic"));
                Object value = entityValue.get("value");
                if (value instanceof Map || value instanceof List) {
                    setting.setValue(JSONUtils.toJSONString(value));
                } else {
                    setting.setValue((String) value);
                }
                addSettingList.add(setting);
            }
        }
        settingRepository.saveAll(addSettingList);

        // 初始化用户表数据
        long count = userRepository.count();
        if (count <= 0) {
            User user = new User();
            user.setNickname((String) settingMap.get("user").get("value"));
            user.setUsername("admin");
            user.setPassword("admin");
            user.setAvatar((String) settingMap.get("avatarImageURL").get("value"));
            userRepository.save(user);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

}
