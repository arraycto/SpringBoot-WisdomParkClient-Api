package com.niit.soft.client.api.service;

import com.niit.soft.client.api.common.ResponseResult;
import com.niit.soft.client.api.domain.dto.FleaUserDto;
import com.niit.soft.client.api.domain.dto.UpdateFleaUserDto;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName FleaGoodsService.java
 * @Description TODO
 * @createTime 2020年06月09日 13:58:00
 */
public interface FleaUserService {

    /**
     * 添加用户数据
     * @param fleaUserDto FleaUserDto
     * @return ResponseResult
     */
    ResponseResult saveFleaUser(FleaUserDto fleaUserDto);

    /**
     * 用户数据修改
     * @param updateFleaUserDto UpdateFleaUserDto
     * @return ResponseResult
     */
    ResponseResult updateFleaUser(UpdateFleaUserDto updateFleaUserDto);
}
