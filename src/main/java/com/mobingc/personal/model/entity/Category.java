package com.mobingc.personal.model.entity;

import com.mobingc.personal.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 文章分类表
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
@Table(name = "d_category")
@ApiModel(value="Category对象", description="文章分类表")
@EntityListeners(AuditingEntityListener.class)
public class Category implements BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "分类ID")
    private Long id;

    @Column(name = "name", length = 128, unique = true, nullable = false)
    @ApiModelProperty(value = "分类名")
    private String name;

    @Column(name = "description", nullable = false)
    @ApiModelProperty(value = "分类描述")
    private String description;

    @Column(name = "article_amount", columnDefinition = "int not null default 0")
    @ApiModelProperty(value = "包含文章数量")
    private Integer articleAmount;

    @Column(name = "image")
    @ApiModelProperty(value = "分类图片")
    private String image;

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

    @OneToMany(mappedBy = "category", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @ApiModelProperty(value = "包含文章")
    @ToString.Exclude
    private List<Article> articles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Category category = (Category) o;
        return id != null && Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
