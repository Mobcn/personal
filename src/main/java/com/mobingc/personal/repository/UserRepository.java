package com.mobingc.personal.repository;

import com.mobingc.personal.base.BaseRepository;
import com.mobingc.personal.model.entity.User;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 数据访问类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Repository
public interface UserRepository extends BaseRepository<User, Long> {

    /**
     * 通过用户名和密码获取用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户
     */
    User findByUsernameAndPassword(String username, String password);

}