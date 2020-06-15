package com.niit.soft.client.api.controller;

import com.niit.soft.client.api.common.ResponseResult;
import com.niit.soft.client.api.domain.dto.JobDto;
import com.niit.soft.client.api.domain.dto.PageDto;
import com.niit.soft.client.api.service.PartJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author su
 * @className JobController
 * @Description TODO
 * @Date 2020/6/9
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/partJob")
@Api(tags = "校园聘的接口")
public class PartJobController {

    @Resource
    private PartJobService jobService;

    @PostMapping("/list")
//    @ControllerWebLog(name = "find")
    @ApiOperation(value = "兼职列表", notes = "请求参数为PageDto，field参数为pay,gmt_create或者兼职类型")
    public ResponseResult findPartJob(@RequestBody PageDto pageDto){
        return ResponseResult.success(jobService.findByPage(pageDto));
    }

    @PostMapping("/byId")
    @ApiOperation(value = "查看兼职详情",  notes = "请求参数为兼职的id")
    public ResponseResult findJob(@RequestBody JobDto jobDto){
        return ResponseResult.success(jobService.findById(jobDto.getId()));
    }



}
