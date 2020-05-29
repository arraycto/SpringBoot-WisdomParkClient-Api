package com.niit.soft.client.api.controller;

import com.niit.soft.client.api.annotation.ControllerWebLog;
import com.niit.soft.client.api.common.ResponseResult;
import com.niit.soft.client.api.common.ResultCode;
import com.niit.soft.client.api.domain.dto.SmsPhoneDto;
import com.niit.soft.client.api.domain.dto.VerifyPhoneDto;
import com.niit.soft.client.api.service.SendSmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Description 短信服务控制器
 * @Author 涛涛
 * @Date 2020/5/21 10:31
 * @Version 1.0
 **/
@RestController
@Slf4j
@Api(value = "SmsApiController",tags = {"短信服务接口"})
public class SmsApiController {
    @Autowired
    private SendSmsService sendSmsService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @ControllerWebLog(name = "code",isSaved = true)
    @ApiOperation(value = "发送验证码",notes = "参数为 手机号")
    @PostMapping(value = "/sendCode")
    public ResponseResult code(@RequestBody SmsPhoneDto smsPhoneDto) {
        log.info("访问 /sendCode 接口");
        String phoneNumber = smsPhoneDto.getPhoneNumber();
        //调用发送方法
        System.out.println("接受的phoneNumber" + phoneNumber);
        String code = redisTemplate.opsForValue().get(phoneNumber);
        if (!StringUtils.isEmpty(code)) {
            //数据已存在
            return ResponseResult.failure(ResultCode.DATA_ALREADY_EXISTED, phoneNumber + ":" + code + "已存在，还没有过期");
        }
        //生成验证码并存储到redis中
        code = UUID.randomUUID().toString().substring(0, 4);
        HashMap<String, Object> param = new HashMap<>();
        param.put("code", code);
        boolean sms = sendSmsService.send(phoneNumber, "SMS_190277609", param);
        if (sms) {
            redisTemplate.opsForValue().set(phoneNumber, code, 5, TimeUnit.MINUTES);
            return ResponseResult.success(code);
        } else {
            return ResponseResult.failure(ResultCode.RESULT_CODE_DATA_NONE);
        }
    }

    //校验验证码
    @ControllerWebLog(name = "code",isSaved = true)
    @ApiOperation(value = "校验验证码",tags = {"参数为手机号和接收的验证码"})
    @PostMapping(value = "/verifyCode")
    public ResponseResult verifyCode(@RequestBody VerifyPhoneDto verifyPhone) {
        if (sendSmsService.verify(verifyPhone)) {
            log.info("验证码通过");
            return ResponseResult.success(true);
        } else {
            log.info("验证码失效或错误");
            return ResponseResult.failure(ResultCode.USER_VERIFY_CODE_ERROR);
        }
    }
}