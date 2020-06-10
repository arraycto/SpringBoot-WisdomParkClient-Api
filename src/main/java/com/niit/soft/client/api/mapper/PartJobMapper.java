package com.niit.soft.client.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.niit.soft.client.api.domain.model.PartJob;
import com.niit.soft.client.api.domain.vo.PartJobVo;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Su
 * @className JobMapper
 * @Description TODO
 * @Date 2020/6/9 14:37
 * @Version 1.0
 **/
public interface PartJobMapper extends BaseMapper<PartJob> {


    List<PartJobVo> findPartJob(Page<PartJobVo> partJobVoPage, Object field);
}
