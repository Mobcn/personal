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
import java.util.Objects;
import java.util.Set;

/**
 * <p>
 * 文章表
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
@Table(name = "b_article")
@ApiModel(value = "Article对象", description = "文章表")
@EntityListeners(AuditingEntityListener.class)
public class Article implements BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "文章ID")
    private Long id;

    @Column(name = "title", length = 128, nullable = false)
    @ApiModelProperty(value = "文章标题")
    private String title;

    @Column(name = "description")
    @ApiModelProperty(value = "文章描述")
    private String description;

    @Column(name = "cover")
    @ApiModelProperty(value = "文章封面")
    private String cover;

    @ManyToMany
    @JoinTable(
            name = "r_article_tag",
            joinColumns = {@JoinColumn(name = "article_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    @ApiModelProperty(value = "文章标签")
    @ToString.Exclude
    private Set<Tag> tags;

    @Column(name = "topping", columnDefinition = "tinyint(1) not null default 0")
    @ApiModelProperty(value = "是否置顶")
    private Boolean topping;

    @Column(name = "status", columnDefinition = "tinyint(1) not null default 1")
    @ApiModelProperty(value = "状态（0：隐藏，1：发布）")
    private Integer status;

    @CreatedDate
    @Column(name = "create_time", nullable = false)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "article_content_id", nullable = false)
    @ApiModelProperty(value = "文章内容")
    @ToString.Exclude
    private ArticleContent content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @ApiModelProperty(value = "文章分类")
    @ToString.Exclude
    private Category category;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "statistic_id", nullable = false)
    @ApiModelProperty(value = "文章统计")
    @ToString.Exclude
    private Statistic statistic;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Article article = (Article) o;
        return id != null && Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
