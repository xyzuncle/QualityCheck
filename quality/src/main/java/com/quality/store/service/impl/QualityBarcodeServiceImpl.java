package com.quality.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quality.common.fastdfs.FastDFSClient;
import com.quality.common.util.CustomerBarcodeUtil;
import com.quality.store.entity.QualityBarcode;
import com.quality.store.entity.QualityFastdfsIndex;
import com.quality.store.mapper.QualityBarcodeMapper;
import com.quality.store.service.IQualityBarcodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.swing.text.html.parser.Entity;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * <p>
 * 条形码样品关系表 服务实现类
 * </p>
 *
 * @author yerui
 * @since 2019-01-23
 */
@Service
public class QualityBarcodeServiceImpl extends ServiceImpl<QualityBarcodeMapper, QualityBarcode> implements IQualityBarcodeService {

    @Autowired
    QualityFastdfsIndexServiceImpl qualityFastdfsIndexService;

    private FastDFSClient fastDFSClient = new FastDFSClient();


     @Override
     @Transactional
     public boolean SaveBarCodeAndImg(QualityBarcode qualityBarcode){

         boolean result=false;
         if(StringUtils.isBlank(qualityBarcode.getId())){
             String path = genBarCode4Fds(qualityBarcode);
             qualityBarcode.setBarCodeImgPath(path);
             //处理索引包的关系
             result = this.save(qualityBarcode);
             saveFastdfsIndex(qualityBarcode);
         }else if(StringUtils.isNotBlank(qualityBarcode.getId())){

             QueryWrapper<QualityFastdfsIndex> ew = new QueryWrapper<>();
             ew.eq("barcode", qualityBarcode.getBarCode());
             QualityFastdfsIndex indexEntity = qualityFastdfsIndexService.getOne(ew);
             //查出来不等null 证明已经保存过，不重复生成图片
             if(indexEntity!=null){
                 result = this.saveOrUpdate(qualityBarcode);
             }else if(indexEntity==null){
                 //证明是新的附件上传，先删除已经存在的。在上传新的
                 String filePath = qualityBarcode.getBarCodeImgPath();
                 fastDFSClient.deleteFile(filePath);
                 //重新生成图片路径放入fastdfs
                 String path = genBarCode4Fds(qualityBarcode);
                 qualityBarcode.setBarCodeImgPath(path);
                 result = this.saveOrUpdate(qualityBarcode);

             }

         }
         return result;
     }



    private void saveFastdfsIndex(QualityBarcode qualityBarcode) {
        //返回获取业务ID
        String businessId = qualityBarcode.getId();
        String imgPath = qualityBarcode.getBarCodeImgPath();
        String barcode = qualityBarcode.getBarCode();
        QualityFastdfsIndex index = new QualityFastdfsIndex();
        index.setBusinessId(businessId);
        index.setFastdfsIndex(imgPath);
        index.setBarcode(barcode);
        qualityFastdfsIndexService.save(index);
    }

    private String genBarCode4Fds(@RequestBody QualityBarcode QualityBarcode) {
        String barCode = QualityBarcode.getBarCode();
        //这个是输出流
        InputStream inputStream = new ByteArrayInputStream(CustomerBarcodeUtil.generate(barCode));
        FastDFSClient dfsClient = new FastDFSClient();
        String barCodePath = dfsClient.upload(inputStream,barCode + "条形码", null);
        return barCodePath;
    }

}
