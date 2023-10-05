package com.mobingc.personal.model.entity;

import com.mobingc.personal.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 日志表
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
@Table(name = "s_logging")
@ApiModel(value="Logging对象", description="日志表")
@EntityListeners(AuditingEntityListener.class)
public class Logging implements BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "日志ID")
    private Long id;

    @Column(name = "operation", nullable = false)
    @ApiModelProperty(value = "操作描述")
    private String operation;

    @Column(name = "params")
    @ApiModelProperty(value = "参数")
    private String params;

    @Column(name = "result")
    @ApiModelProperty(value = "结果")
    private String result;

    @Column(name = "status", nullable = false)
    @ApiModelProperty(value = "状态（0：失败，1：成功）")
    private Boolean status;

    @CreatedDate
    @Column(name = "create_time", nullable = false)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Logging logging = (Logging) o;
        return id != null && Objects.equals(id, logging.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
