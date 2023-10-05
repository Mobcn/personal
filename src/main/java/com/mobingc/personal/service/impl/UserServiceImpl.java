package com.mobingc.personal.service.impl;

import com.mobingc.personal.base.BaseRepository;
import com.mobingc.personal.common.consts.UserConst;
import com.mobingc.personal.common.exception.BizException;
import com.mobingc.personal.common.utils.AssertUtils;
import com.mobingc.personal.model.dto.UserLoginDTO;
import com.mobingc.personal.model.entity.User;
import com.mobingc.personal.common.enums.ResultCode;
import com.mobingc.personal.repository.UserRepository;
import com.mobingc.personal.service.UserService;
import com.mobingc.personal.common.Validators;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final NoSqlServiceImpl noSqlService;

    public UserServiceImpl(NoSqlServiceImpl noSqlService, UserRepository userRepository) {
        this.noSqlService = noSqlService;
        this.userRepository = userRepository;
    }

    @Override
    public BaseRepository<User, Long> getRepository() {
        return this.userRepository;
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {

        // 参数校验
        Validators.validate(userLoginDTO);

        // 获取用户
        User user = userRepository.findByUsernameAndPassword(userLoginDTO.getUserName(), userLoginDTO.getPassword());
        // 检查用户是否存在
        AssertUtils.isTrue(user, AssertUtils::isNotNull, () -> new BizException(ResultCode.LOGIN_FAIL));
        // 判断用户是否可用
        AssertUtils.isTrue(user.getStatus(), UserConst.DISABLE, AssertUtils::isNe, () -> new BizException(ResultCode.USER_IS_DISABLE));

        // 设置最后登录时间
        user.setLastLoginTime(new Date());
        userRepository.updateById(user);

        return generateToken(userLoginDTO.getUserName());
    }

    @Override
    public String generateToken(String userName) {
        return this.generateToken(userName, 1800L);
    }

    @Override
    public String generateToken(String userName, long timeout) {
        String token = UUID.randomUUID().toString();
        noSqlService.set(token, userName, timeout);
        return token;
    }

    @Override
    public Boolean checkToken(String token) {
        return Boolean.TRUE.equals(noSqlService.hasKey(token));
    }

}
