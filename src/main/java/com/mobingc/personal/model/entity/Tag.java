package com.mobingc.personal.model.entity;

import com.mobingc.personal.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * <p>
 * 标签表
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "d_tag")
@ApiModel(value = "Tag对象", description = "标签表")
@EntityListeners(AuditingEntityListener.class)
public class Tag implements BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "标签ID")
    private Long id;

    @Column(name = "name", nullable = false, length = 128)
    @ApiModelProperty(value = "标签名")
    private String name;

    @Column(name = "description")
    @ApiModelProperty(value = "标签描述")
    private String description;

    @Column(name = "article_amount", columnDefinition = "int not null default 0")
    @ApiModelProperty(value = "包含文章数量")
    private Integer articleAmount;

    @Column(name = "status", columnDefinition = "tinyint(1) not null default 1")
    @ApiModelProperty(value = "状态（0：禁用，1：启用）")
    private Boolean status;

    @CreatedDate
    @Column(name = "create_time", nullable = false)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ManyToMany
    @JoinTable(
            name = "r_article_tag",
            joinColumns = {@JoinColumn(name = "tag_id")},
            inverseJoinColumns = {@JoinColumn(name = "article_id")}
    )
    @ApiModelProperty(value = "包含文章")
    @ToString.Exclude
    private Set<Article> articles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Tag tag = (Tag) o;
        return id != null && Objects.equals(id, tag.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
