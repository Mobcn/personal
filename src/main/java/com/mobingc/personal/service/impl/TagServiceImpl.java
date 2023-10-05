package com.mobingc.personal.service.impl;

import com.mobingc.personal.base.BaseRepository;
import com.mobingc.personal.common.enums.ResultCode;
import com.mobingc.personal.common.exception.BizException;
import com.mobingc.personal.common.utils.AssertUtils;
import com.mobingc.personal.model.dto.info.TagInfoDTO;
import com.mobingc.personal.model.dto.search.TagSearchDTO;
import com.mobingc.personal.model.entity.Tag;
import com.mobingc.personal.repository.TagRepository;
import com.mobingc.personal.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public BaseRepository<Tag, Long> getRepository() {
        return this.tagRepository;
    }

    @Override
    public Page<TagInfoDTO> pageTag(TagSearchDTO tagSearchDTO, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Tag> tagPage = tagRepository.selectPageByKey(pageable, tagSearchDTO.getKey());
        List<TagInfoDTO> tagInfoDTOList = tagPage.stream()
                .map(tag -> {
                    TagInfoDTO tagInfoDTO = new TagInfoDTO();
                    BeanUtils.copyProperties(tag, tagInfoDTO);
                    return tagInfoDTO;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(tagInfoDTOList, pageable, tagPage.getTotalElements());
    }

    @Override
    public Set<Tag> findTagSetById(Collection<String> ids) {
        List<Long> idList = ids.stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
        List<Tag> tagList = this.selectAllById(idList);
        return new HashSet<>(tagList);
    }

    @Override
    public TagInfoDTO selectTagById(Long tagId) {
        Tag tag = tagRepository.selectById(tagId);
        AssertUtils.isTrue(tag, AssertUtils::isNotNull, () -> new BizException(ResultCode.TAG_IS_NOT_FOUND));
        TagInfoDTO tagInfoDTO = new TagInfoDTO();
        BeanUtils.copyProperties(tag, tagInfoDTO);
        return tagInfoDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean statusTag(Long tagId) {
        // 查询标签
        Tag tag = tagRepository.selectById(tagId);
        AssertUtils.isTrue(tag, AssertUtils::isNotNull, () -> new BizException(ResultCode.TAG_IS_NOT_FOUND));
        // 修改标签状态
        tag.setStatus(!tag.getStatus());
        tagRepository.updateById(tag);
        return tag.getStatus();
    }

}
