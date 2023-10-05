package com.mobingc.personal.service;

import com.mobingc.personal.base.BaseService;
import com.mobingc.personal.model.entity.Setting;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 设置表 服务类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
public interface SettingService extends BaseService<Setting, Long> {

    /**
     * 获取公开设置项
     *
     * @param keys 查询设置key列表
     * @return 设置项
     */
    Map<String, String> publicSetting(List<String> keys);

    /**
     * 获取设置项
     *
     * @param keys 查询设置key列表
     * @return 设置项
     */
    Map<String, String> mapSetting(List<String> keys);

    /**
     * 保存设置项
     *
     * @param settingMap 设置项
     */
    void update(Map<String, String> settingMap);

}
