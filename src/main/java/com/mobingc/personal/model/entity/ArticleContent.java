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

/**
 * <p>
 * 文章内容表
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
@Table(name = "b_article_content")
@ApiModel(value="ArticleContent对象", description="文章内容表")
@EntityListeners(AuditingEntityListener.class)
public class ArticleContent implements BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "文章内容ID")
    private Long id;

    @Column(name = "content", columnDefinition = "longtext")
    @ApiModelProperty(value = "文章内容")
    private String content;

    @CreatedDate
    @Column(name = "create_time", nullable = false)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @OneToOne(mappedBy = "content", fetch = FetchType.LAZY)
    @ApiModelProperty(value = "文章")
    @ToString.Exclude
    private Article article;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ArticleContent that = (ArticleContent) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
