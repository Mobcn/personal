package com.mobingc.personal.service;

import com.mobingc.personal.base.BaseService;
import com.mobingc.personal.model.dto.info.TagInfoDTO;
import com.mobingc.personal.model.dto.search.TagSearchDTO;
import com.mobingc.personal.model.entity.Tag;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.Set;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
public interface TagService extends BaseService<Tag, Long> {

    /**
     * 获取标签列表
     *
     * @param tagSearchDTO 标签查询参数对象
     * @param page         查询页数
     * @param size         每页数据条数
     * @return 返回分页数据
     */
    Page<TagInfoDTO> pageTag(TagSearchDTO tagSearchDTO, Integer page, Integer size);

    /**
     * 通过ID列表获取不重复标签集合
     *
     * @param ids ID列表
     * @return 不重复标签集合
     */
    Set<Tag> findTagSetById(Collection<String> ids);

    /**
     * 通过标签ID查询标签
     *
     * @param tagId 标签ID
     * @return 标签信息
     */
    TagInfoDTO selectTagById(Long tagId);

    /**
     * 修改标签状态
     *
     * @param tagId 标签ID
     * @return 当前标签状态
     */
    Boolean statusTag(Long tagId);

}
