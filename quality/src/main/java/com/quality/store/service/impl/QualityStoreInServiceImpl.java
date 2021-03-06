package com.quality.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quality.store.entity.QualityInventory;
import com.quality.store.entity.QualityStoreIn;
import com.quality.store.mapper.QualityStoreInMapper;
import com.quality.store.service.IQualityStoreInService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.swing.StringUIClientPropertyKey;

import javax.xml.ws.Action;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yerui
 * @since 2019-01-31
 */
@Service
public class QualityStoreInServiceImpl extends ServiceImpl<QualityStoreInMapper, QualityStoreIn> implements IQualityStoreInService {


    @Autowired
    QualityInventoryServiceImpl qualityInventoryService;

    @Override
    @Transactional
    public void customSave(QualityStoreIn qualityStoreIn) {

        this.saveOrUpdate(qualityStoreIn);

        String sampleId = qualityStoreIn.getSampleId();
        QualityInventory qualityInventory = null;
        if(StringUtils.isNotBlank(sampleId)){
            QueryWrapper<QualityInventory> qw = new QueryWrapper<>();
            qw.eq("sampleId", sampleId);
            qualityInventory =  qualityInventoryService.getOne(qw);
        }

        //证明新增样品，同时新增库存
        if(qualityInventory==null){
            qualityInventory = new QualityInventory();
            BeanUtils.copyProperties(qualityStoreIn,qualityInventory);
        }else{
            //这里证明是新增，同时处理个数问题
            int tempCount = qualityStoreIn.getSampleCount();
            int storeCount = qualityInventory.getSampleCount();
            int count = tempCount + storeCount;
            //增加数量
            qualityInventory.setSampleCount(count);
        }
        qualityInventoryService.saveOrUpdate(qualityInventory);



    }
}
