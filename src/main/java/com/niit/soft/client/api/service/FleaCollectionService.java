package com.niit.soft.client.api.service;

import com.niit.soft.client.api.common.ResponseResult;
import com.niit.soft.client.api.domain.dto.CancelCollectionDto;
import com.niit.soft.client.api.domain.dto.CollectionDto;
import com.niit.soft.client.api.domain.dto.FleaUserIdDto;
import org.jsoup.Connection;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName FleaGoodsService.java
 * @Description TODO
 * @createTime 2020年06月09日 13:58:00
 */
public interface FleaCollectionService {
    /**
     * 插入收藏
     * @param collectionDto
     * @return
     */
    ResponseResult addCollection(CollectionDto collectionDto);

    /**
     * 获取所有收藏
     * @return
     */
    ResponseResult getCollection(FleaUserIdDto userIdDto);

    /**
     * 根据商品ID以及用户ID删除商品
     * @param collectionDto
     * @return
     */
    ResponseResult logicalDel(CancelCollectionDto collectionDto);
}

