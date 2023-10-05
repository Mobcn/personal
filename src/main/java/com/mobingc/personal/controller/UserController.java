package com.mobingc.personal.controller;


import com.mobingc.personal.common.utils.ConverterUtils;
import com.mobingc.personal.model.dto.UserLoginDTO;
import com.mobingc.personal.model.vo.UserLoginVO;
import com.mobingc.personal.common.Result;
import com.mobingc.personal.model.vo.field.TokenFieldVO;
import com.mobingc.personal.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping("/login")
    public Result<TokenFieldVO<String>> login(@ApiParam(name = "userLoginVO", value = "用户登录数据对象") @RequestBody UserLoginVO userLoginVO) {
        UserLoginDTO userLoginDTO = ConverterUtils.copyConvert(userLoginVO, UserLoginDTO.class);
        String token = userService.login(userLoginDTO);
        TokenFieldVO<String> userTokenFieldVO = new TokenFieldVO<String>().setToken(token);
        return Result.ok("登录成功!", userTokenFieldVO);
    }

}

