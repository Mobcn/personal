package com.mobingc.personal.service.impl;

import com.mobingc.personal.base.BaseRepository;
import com.mobingc.personal.model.entity.Setting;
import com.mobingc.personal.repository.SettingRepository;
import com.mobingc.personal.service.SettingService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 设置表 服务实现类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Service
public class SettingServiceImpl implements SettingService {

    private final SettingRepository settingRepository;

    public SettingServiceImpl(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    @Override
    public BaseRepository<Setting, Long> getRepository() {
        return this.settingRepository;
    }

    @Override
    public Map<String, String> publicSetting(List<String> keys) {
        Map<String, String> publicSettingMap = new HashMap<>();
        if (keys != null && !keys.isEmpty()) {
            List<Setting> settings = settingRepository.findAllByKeyInAndIsPublicTrue(keys);
            for (Setting setting : settings) {
                publicSettingMap.put(setting.getKey(), setting.getValue());
            }
        }
        return publicSettingMap;
    }

    @Override
    public Map<String, String> mapSetting(List<String> keys) {
        Map<String, String> settingMap = new HashMap<>();
        if (keys != null && !keys.isEmpty()) {
            List<Setting> settings = settingRepository.findAllByKeyIn(keys);
            for (Setting setting : settings) {
                settingMap.put(setting.getKey(), setting.getValue());
            }
        }
        return settingMap;
    }

    @Override
    public void update(Map<String, String> settingMap) {
        Set<String> keys = settingMap.keySet();
        if (!keys.isEmpty()) {
            List<Setting> updateSettingList = settingRepository.findAllByKeyIn(settingMap.keySet());
            Map<String, Integer> indexMap = generateIndexMap(updateSettingList);
            for (Map.Entry<String, String> settingEntry : settingMap.entrySet()) {
                String key = settingEntry.getKey();
                Setting setting = updateSettingList.get(indexMap.get(key));
                setting.setValue(settingEntry.getValue());
            }
            settingRepository.saveAll(updateSettingList);
        }
    }

    /**
     * 生成列表索引Map
     *
     * @param list 列表
     * @return 索引Map
     */
    private Map<String, Integer> generateIndexMap(List<Setting> list) {
        Map<String, Integer> indexMap = new HashMap<>(list.size());
        for (int i = 0; i < list.size(); ++i) {
            indexMap.put(list.get(i).getKey(), i);
        }
        return indexMap;
    }

}
