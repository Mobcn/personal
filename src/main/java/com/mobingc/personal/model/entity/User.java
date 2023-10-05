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
 * 用户表
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
@Table(name = "s_user")
@ApiModel(value="User对象", description="用户表")
@EntityListeners(AuditingEntityListener.class)
public class User implements BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "用户ID")
    private Long id;

    @Column(name = "nickname", length = 128, nullable = false)
    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @Column(name = "username", length = 128, unique = true, nullable = false)
    @ApiModelProperty(value = "用户名")
    private String username;

    @Column(name = "password", length = 16, nullable = false)
    @ApiModelProperty(value = "用户密码")
    private String password;

    @Column(name = "avatar")
    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @Column(name = "last_login_time")
    @ApiModelProperty(value = "上次登录时间")
    private Date lastLoginTime;

    @Column(name = "status", columnDefinition = "tinyint(1) not null default 1")
    @ApiModelProperty(value = "状态（0：禁用，1：启用）")
    private Short status;

    @CreatedDate
    @Column(name = "create_time", nullable = false)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
