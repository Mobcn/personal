package com.mobingc.personal.model.entity;

import com.mobingc.personal.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Objects;

/**
 * <p>
 * 文章统计数据表
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
@Table(name = "b_statistic")
@ApiModel(value="Statistic对象", description="文章统计数据表")
public class Statistic implements BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "文章统计数据ID")
    private Long id;

    @Column(name = "views", columnDefinition = "int not null default 0")
    @ApiModelProperty(value = "文章浏览次数")
    private Integer views;

    @OneToOne(mappedBy = "statistic", fetch = FetchType.LAZY)
    @ApiModelProperty(value = "文章")
    @ToString.Exclude
    private Article article;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Statistic statistic = (Statistic) o;
        return id != null && Objects.equals(id, statistic.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
