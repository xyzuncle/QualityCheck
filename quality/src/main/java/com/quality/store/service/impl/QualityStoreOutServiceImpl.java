package com.quality.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quality.common.exception.BaseException;
import com.quality.store.entity.QualityInventory;
import com.quality.store.entity.QualityStoreOut;
import com.quality.store.mapper.QualityStoreOutMapper;
import com.quality.store.service.IQualityStoreOutService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yerui
 * @since 2019-02-27
 */
@Service
public class QualityStoreOutServiceImpl extends ServiceImpl<QualityStoreOutMapper, QualityStoreOut> implements IQualityStoreOutService {

    @Autowired
    QualityInventoryServiceImpl qualityInventoryService;

    @Override
    public void customSave(QualityStoreOut qualityStoreOut) {
        String sampleId = qualityStoreOut.getSampleId();
        QualityInventory qualityInventory = null;
        if(StringUtils.isNotBlank(sampleId)){
            QueryWrapper<QualityInventory> qw = new QueryWrapper<>();
            qw.eq("sampleId", sampleId);
            qualityInventory =  qualityInventoryService.getOne(qw);
        }

        //证明新增样品，同时新增库存
        if(qualityInventory==null){
            qualityInventory = new QualityInventory();
            BeanUtils.copyProperties(qualityStoreOut,qualityInventory);
        }else{
            //这里证明是新增，同时处理个数问题
            int tempCount = qualityStoreOut.getSampleCount();
            int storeCount = qualityInventory.getSampleCount();
            int count = storeCount - tempCount;
            if(count <0){
                throw new BaseException("库存数量不足",40101);
            }else{
                //设置减少后的库存
                qualityInventory.setSampleCount(count);
            }
        }
        qualityInventoryService.saveOrUpdate(qualityInventory);
        this.saveOrUpdate(qualityStoreOut);
    }
}
