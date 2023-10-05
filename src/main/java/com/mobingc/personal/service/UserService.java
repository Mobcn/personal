package com.mobingc.personal.service;

import com.mobingc.personal.base.BaseService;
import com.mobingc.personal.model.dto.UserLoginDTO;
import com.mobingc.personal.model.entity.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
public interface UserService extends BaseService<User, Long> {

    /**
     * 用户登录
     *
     * @param userLoginDTO 用户登录数据对象
     * @return 令牌
     */
    String login(UserLoginDTO userLoginDTO);

    /**
     * 生成令牌（有效时间30分钟）
     *
     * @return 令牌
     */
    String generateToken(String userName);

    /**
     * 生成令牌
     *
     * @param timeout 令牌有效时间（单位：秒）
     * @return 令牌
     */
    String generateToken(String userName, long timeout);

    /**
     * 令牌校验
     *
     * @param token 令牌值
     * @return 是否校验成功
     */
    Boolean checkToken(String token);

}
