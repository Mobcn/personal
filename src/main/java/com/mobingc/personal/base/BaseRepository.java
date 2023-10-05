package com.mobingc.personal.base;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 基础数据库访问接口
 *
 * @author Mo
 * @since 2022-08-25
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity<ID>, ID extends Serializable>  extends JpaRepository<T, ID> {

    /**
     * 添加
     */
    default <S extends T> S insert(S entity) {
        return this.save(entity);
    }

    /**
     * 通过ID修改
     */
    default <S extends T> void updateById(S entity) {
        // 获取ID
        ID id = entity.getId();
        if (id == null) {
            throw new RuntimeException("not found id");
        }
        // 查询实体对象
        T managedEntity = this.selectById(id);
        if (managedEntity == null) {
            throw new RuntimeException("not found record by id");
        } else {
            // 获取Bean
            BeanWrapper srcBean = new BeanWrapperImpl(entity);
            // 获取Bean的属性描述
            PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
            // 获取Bean的空属性
            Set<String> properties = new HashSet<>();
            for (PropertyDescriptor propertyDescriptor : pds) {
                String propertyName = propertyDescriptor.getName();
                Object propertyValue = srcBean.getPropertyValue(propertyName);
                // 过滤空字符串条件: || propertyValue instanceof String && StringUtils.isEmpty(String.valueOf(propertyValue))
                if (propertyValue == null) {
                    srcBean.setPropertyValue(propertyName, null);
                    properties.add(propertyName);
                }
            }
            // 复制更新字段
            BeanUtils.copyProperties(entity, managedEntity, properties.toArray(new String[0]));
            this.save(managedEntity);
        }
    }

    /**
     * 通过ID查询
     *
     * @param id ID
     * @return 实体对象
     */
    default T selectById(ID id) {
        return this.findById(id).orElse(null);
    }

}
