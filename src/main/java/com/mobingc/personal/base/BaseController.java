package com.mobingc.personal.base;

import com.mobingc.personal.common.Result;
import com.mobingc.personal.model.vo.field.IdFieldVO;
import com.mobingc.personal.model.vo.field.IdsFieldVO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

/**
 * 基础控制器接口
 *
 * @author Mo
 * @since 2022-08-25
 */
public interface BaseController<T extends BaseEntity<ID>, ID extends Serializable> {

    /**
     * 获取服务对象
     */
    BaseService<T, ID> getService();

    /**
     * 通过DTO添加实体对象
     *
     * @param dto DTO对象
     * @return 请求结果
     */
    default <D> Result<IdFieldVO<ID>> add(D dto) {
        BaseService<T, ID> service = getService();
        ID id = service.addByDTO(dto);
        IdFieldVO<ID> idFieldVO = new IdFieldVO<ID>().setId(id);
        return Result.ok("添加成功!", idFieldVO);
    }

    /**
     * 通过DTO更新实体对象
     *
     * @param dto DTO对象
     * @return 请求结果
     */
    default <D extends BaseEntity<ID>> Result<IdFieldVO<ID>> update(D dto) {
        BaseService<T, ID> service = getService();
        ID id = service.updateByDTO(dto);
        IdFieldVO<ID> idFieldVO = new IdFieldVO<ID>().setId(id);
        return Result.ok("更新成功!", idFieldVO);
    }

    /**
     * 通过ID删除实体对象
     *
     * @param id 实体对象ID
     * @return 请求结果
     */
    default Result<IdFieldVO<ID>> remove(ID id) {
        BaseService<T, ID> service = getService();
        service.remove(id);
        IdFieldVO<ID> idFieldVO = new IdFieldVO<ID>().setId(id);
        return Result.ok("删除成功!", idFieldVO);
    }

    /**
     * 通过ID数组批量删除实体对象
     *
     * @param ids 实体对象ID数组
     * @return 请求结果
     */
    default Result<IdsFieldVO<ID>> removeBatch(Set<ID> ids) {
        BaseService<T, ID> service = getService();
        service.remove(ids);
        IdsFieldVO<ID> idsFieldVO = new IdsFieldVO<ID>().setId(new ArrayList<>(ids));
        return Result.ok("批量删除成功!", idsFieldVO);
    }

}
