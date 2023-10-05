package com.mobingc.personal.base;

import com.mobingc.personal.common.Validators;
import com.mobingc.personal.common.enums.ResultCode;
import com.mobingc.personal.common.exception.BizException;
import com.mobingc.personal.common.utils.AssertUtils;
import com.mobingc.personal.common.utils.ConverterUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.function.Function;

/**
 * 基础服务接口
 *
 * @author Mo
 * @since 2022-08-25
 */
public interface BaseService<T extends BaseEntity<ID>, ID extends Serializable> {

    /**
     * 获取数据库访问对象
     */
    BaseRepository<T, ID> getRepository();

    /**
     * 获取所有实体对象
     *
     * @return 实体对象列表
     */
    default List<T> selectAll() {
        BaseRepository<T, ID> repository = this.getRepository();
        return repository.findAll();
    }

    /**
     * 通过ID查询实体对象
     *
     * @param id ID
     * @return 实体对象
     */
    default T selectById(ID id) {
        BaseRepository<T, ID> repository = this.getRepository();
        return repository.selectById(id);
    }

    /**
     * 通过ID列表查询实体对象列表
     *
     * @param ids ID列表
     * @return 实体对象列表
     */
    default List<T> selectAllById(Iterable<ID> ids) {
        BaseRepository<T, ID> repository = this.getRepository();
        return repository.findAllById(ids);
    }

    /**
     * 通过DTO添加实体对象
     *
     * @param dto DTO对象
     */
    default <D> ID addByDTO(D dto) {
        return this.saveByDTO(dto);
    }

    /**
     * 通过DTO添加实体对象
     *
     * @param dto     DTO对象
     * @param convert DTO转换Entity
     */
    default <D> ID addByDTO(D dto, Function<D, T> convert) {
        return this.saveByDTO(dto, convert);
    }

    /**
     * 添加实体对象
     *
     * @param entity 实体对象
     */
    default ID add(T entity) {
        return this.save(entity);
    }

    /**
     * 通过DTO更新实体对象
     *
     * @param dto DTO对象
     */
    default <D extends BaseEntity<ID>> ID updateByDTO(D dto) {
        AssertUtils.isTrue(dto.getId(), AssertUtils::isNotNull, () -> new BizException(ResultCode.ID_IS_EMPTY));
        return this.saveByDTO(dto);
    }

    /**
     * 通过DTO更新实体对象
     *
     * @param dto     DTO对象
     * @param convert DTO转换Entity
     */
    default <D extends BaseEntity<ID>> ID updateByDTO(D dto, Function<D, T> convert) {
        AssertUtils.isTrue(dto.getId(), AssertUtils::isNotNull, () -> new BizException(ResultCode.ID_IS_EMPTY));
        return this.saveByDTO(dto, convert);
    }

    /**
     * 更新实体对象
     *
     * @param entity 实体对象
     */
    default ID update(T entity) {
        AssertUtils.isTrue(entity.getId(), AssertUtils::isNotNull, () -> new BizException(ResultCode.ID_IS_EMPTY));
        return this.save(entity);
    }

    /**
     * 通过DTO保存实体对象
     *
     * @param dto DTO对象
     */
    default <D> ID saveByDTO(D dto) {
        Class<?> interfaceClazz = (Class<?>) this.getClass().getGenericInterfaces()[0];
        ParameterizedType pt = (ParameterizedType) interfaceClazz.getGenericInterfaces()[0];
        // noinspection unchecked
        Class<T> entityClass = (Class<T>) pt.getActualTypeArguments()[0];
        return this.saveByDTO(dto, ConverterUtils.copyConverter(entityClass));
    }

    /**
     * 通过DTO保存实体对象
     *
     * @param dto     DTO对象
     * @param convert DTO转换Entity
     */
    default <D> ID saveByDTO(D dto, Function<D, T> convert) {
        // 参数校验
        Validators.validate(dto);
        // DTO转换为Entity
        T entity = convert.apply(dto);
        // 保存
        return this.save(entity);
    }

    /**
     * 保存实体对象
     *
     * @param entity 实体对象
     */
    default ID save(T entity) {
        ID id = entity.getId();
        BaseRepository<T, ID> repository = this.getRepository();
        if (id == null) {
            repository.insert(entity);
        } else {
            repository.updateById(entity);
        }
        return entity.getId();
    }

    /**
     * 通过ID删除实体对象
     *
     * @param id 实体对象ID
     */
    default void remove(ID id) {
        BaseRepository<T, ID> repository = this.getRepository();
        repository.deleteById(id);
    }

    /**
     * 通过ID数组批量删除实体对象
     *
     * @param ids 实体对象ID数组
     */
    default void remove(Iterable<ID> ids) {
        BaseRepository<T, ID> repository = this.getRepository();
        List<T> entityList = repository.findAllById(ids);
        repository.deleteInBatch(entityList);
    }

}
