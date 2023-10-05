package com.mobingc.personal.controller;


import com.mobingc.personal.common.Result;
import com.mobingc.personal.common.ResultMap;
import com.mobingc.personal.service.SettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 设置表 前端控制器
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Api(tags = "设置管理")
@RestController
@RequestMapping("/setting")
public class SettingController {

    private final SettingService settingService;

    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @ApiOperation(value = "获取公开设置项", notes = "获取公开设置项")
    @PostMapping("/public")
    public Result<ResultMap> publicSetting(@ApiParam(name = "keys", value = "查询设置key列表") @RequestBody Set<String> keys) {
        Map<String, String> publicSettingMap = settingService.publicSetting(new ArrayList<>(keys));
        ResultMap resultMap = ResultMap.create(publicSettingMap);
        return Result.ok("获取设置项成功!", resultMap);
    }

    @ApiOperation(value = "获取设置项", notes = "获取设置项")
    @PostMapping("/map")
    public Result<ResultMap> mapSetting(@ApiParam(name = "keys", value = "查询设置key列表") @RequestBody Set<String> keys) {
        Map<String, String> settingMap = settingService.mapSetting(new ArrayList<>(keys));
        ResultMap resultMap = ResultMap.create(settingMap);
        return Result.ok("获取设置项成功!", resultMap);
    }

    @ApiOperation(value = "更新设置项", notes = "更新设置项")
    @PostMapping("/update")
    public Result<?> saveSetting(@ApiParam(name = "settingMap", value = "设置项") @RequestBody Map<String, String> settingMap) {
        settingService.update(settingMap);
        return Result.ok("更新设置项成功!");
    }

}

