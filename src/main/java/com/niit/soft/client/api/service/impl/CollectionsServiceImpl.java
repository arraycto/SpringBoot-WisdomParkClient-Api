package com.niit.soft.client.api.service.impl;

import com.niit.soft.client.api.common.ResponseResult;
import com.niit.soft.client.api.common.ResultCode;
import com.niit.soft.client.api.domain.dto.DynamicCollectionDto;
import com.niit.soft.client.api.domain.dto.DynamicCollectionInDto;
import com.niit.soft.client.api.domain.dto.PageDto;
import com.niit.soft.client.api.domain.model.Collections;
import com.niit.soft.client.api.mapper.CollectionsMapper;
import com.niit.soft.client.api.repository.CollectionsRepository;
import com.niit.soft.client.api.repository.DynamicPhotoRepository;
import com.niit.soft.client.api.service.CollectionsService;
import com.niit.soft.client.api.util.SnowFlake;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yujie_Zhao
 * @ClassName CollectionsServiceImpl
 * @Description 收藏
 * @Date 2020/6/11  9:49
 * @Version 1.0
 **/
@Service
public class CollectionsServiceImpl implements CollectionsService {
    @Resource
    private CollectionsMapper collectionsMapper;

    @Resource
    private CollectionsRepository collectionsRepository;

    @Resource
    private DynamicPhotoRepository dynamicPhotoRepository;


    @Override
    public ResponseResult getCollectionsByUserId(PageDto pageDto) {
        List<DynamicCollectionDto> collectionDtoArrayList = new ArrayList<>();

        Pageable pageable = PageRequest.of(
                pageDto.getCurrentPage(),
                pageDto.getPageSize(),
                Sort.Direction.ASC,
                "pk_collection_id");
        Page<Collections> collectionsPage =
                collectionsRepository.getCollectionsByUserId(Long.parseLong((String) pageDto.getField()),pageable);
        collectionsPage.forEach(collections -> {
            DynamicCollectionDto dynamicCollectionDto = collectionsMapper.findCollectionsByDynamicId(collections.getDynamicId());
            dynamicCollectionDto.setPkCollectionId(collections.getPkCollectionId());
            //得到一个资讯的所有配图
            List<String> dynamicPhotoList = dynamicPhotoRepository.findDistinctByDynamicId(collections.getDynamicId());
            if (dynamicPhotoList != null){
                dynamicCollectionDto.setPicture(dynamicPhotoList);
            }
            collectionDtoArrayList.add(dynamicCollectionDto);
        });
        return ResponseResult.success(collectionDtoArrayList);
    }

    @Override
    public ResponseResult insertCollections(DynamicCollectionInDto dynamicCollectionInDto) {
        Collections collections1 = collectionsRepository.findCollectionsByUserIdAndDynamicIdAndIsDeleted(dynamicCollectionInDto.getUserId(), dynamicCollectionInDto.getDynamicId(),false);
        //判断时候存在，不存在才才能添加
        if (collections1 == null){
            Collections collections=Collections.builder()
                    .pkCollectionId(new SnowFlake(1, 3).nextId())
                    .dynamicId(dynamicCollectionInDto.getDynamicId())
                    .userId(dynamicCollectionInDto.getUserId())
                    .isDeleted(false)
                    .build();
            collectionsRepository.save(collections);
            return ResponseResult.success("添加成功");
        }
        return ResponseResult.success(ResultCode.DATA_ALREADY_EXISTED);
    }

    @Override
    public ResponseResult updateCollectionsIsDelete(Long id) {
        Collections collections = collectionsRepository.findCollectionsByPkCollectionId(id);
        collections.setIsDeleted(true);
        collectionsRepository.saveAndFlush(collections);
        return ResponseResult.success("删除成功");
    }
}
