package com.mobingc.personal.controller;

import com.mobingc.personal.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "测试")
@RestController
@RequestMapping("/")
public class TestController {

    @ApiOperation(value = "请求测试", notes = "请求测试")
    @GetMapping("/")
    public Result<?> test() {
        return Result.ok("欢迎使用!");
    }

}
