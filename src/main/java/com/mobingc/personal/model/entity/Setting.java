package com.mobingc.personal.model.entity;

import com.mobingc.personal.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

/**
 * <p>
 * 设置表
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
@Table(name = "s_setting")
@ApiModel(value = "Setting对象", description = "设置表")
public class Setting implements BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "设置ID")
    private Long id;

    @Column(name = "`key`", length = 128, unique = true, nullable = false)
    @ApiModelProperty(value = "设置键")
    private String key;

    @Column(name = "description")
    @ApiModelProperty(value = "描述")
    private String description;

    @Column(name = "value", nullable = false, columnDefinition = "varchar(2000) not null")
    @ApiModelProperty(value = "设置值")
    private String value;

    @Column(name = "is_public", columnDefinition = "tinyint(1) not null default 0")
    @ApiModelProperty(value = "是否为公共值")
    private Boolean isPublic;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Setting setting = (Setting) o;
        return id != null && Objects.equals(id, setting.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
