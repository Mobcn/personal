package com.mobingc.personal.repository;

import com.mobingc.personal.base.BaseRepository;
import com.mobingc.personal.model.entity.Setting;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 设置表 数据访问类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Repository
public interface SettingRepository extends BaseRepository<Setting, Long> {

    /**
     * 通过键名查找设置
     *
     * @param key 键名
     * @return 设置
     */
    Setting findByKey(String key);

    /**
     * 通过键名集合查找所有的公开设置
     *
     * @param keys 设置键名集合
     * @return 设置列表
     */
    List<Setting> findAllByKeyInAndIsPublicTrue(Collection<? extends String> keys);

    /**
     * 通过键名集合查找所有的设置
     *
     * @param keys 设置键名集合
     * @return 设置列表
     */
    List<Setting> findAllByKeyIn(Collection<? extends String> keys);

    /**
     * 通过键名集合查找所有的设置
     *
     * @param keys 设置键名集合
     * @return 设置列表
     */
    default List<Setting> selectAllByKeyIn(Collection<? extends String> keys) {
        return this.findAllByKeyIn(keys);
    }

}