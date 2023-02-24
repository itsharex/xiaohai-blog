package com.xiaohai.system.service.impl;

import com.xiaohai.common.daomain.PageData;
import com.xiaohai.system.pojo.entity.DictData;
import com.xiaohai.system.dao.DictDataMapper;
import com.xiaohai.system.service.DictDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.ReturnPageData;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaohai.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.xiaohai.system.pojo.query.DictDataQuery;
import com.xiaohai.system.pojo.vo.DictDataVo;
import com.xiaohai.system.pojo.dto.DictDataDto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 字典数据表 服务实现类
 *
 * @author xiaohai
 * @since 2023-02-03
 */
@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements DictDataService {

    @Override
    public Integer add(DictDataVo vo){
        DictData dictData=new DictData();
        BeanUtils.copyProperties(vo,dictData);
        return baseMapper.insert(dictData);
    }

    @Override
    public Integer delete(Long id){
        return baseMapper.deleteById(id);
    }

    @Override
    public Integer updateData(DictDataVo vo){
        DictData dictData=new DictData();
        BeanUtils.copyProperties(vo,dictData);
        return baseMapper.updateById(dictData);
    }

    @Override
    public DictData findById(Long id){
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<DictDataDto> findListByPage(DictDataQuery query){
        DictData dictData=new DictData();
        BeanUtils.copyProperties(query,dictData);
        IPage<DictData> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<DictData> iPage = baseMapper.selectPage(wherePage,Wrappers.query(dictData));
        List<DictDataDto> list=new ArrayList<>();
        for(DictData dictDatas:iPage.getRecords()){
            DictDataDto dictDataDto=new DictDataDto();
            BeanUtils.copyProperties(dictDatas,dictDataDto);
            list.add(dictDataDto);
        }
        PageData pageData=new PageData();
        BeanUtils.copyProperties(iPage,pageData);
        return ReturnPageData.fillingData(pageData,list);
    }
}
