package com.quality.common.service.impl;

import com.quality.common.entity.QualityAttachment;
import com.quality.common.exception.BaseException;
import com.quality.common.fastdfs.FastDFSClient;
import com.quality.common.fastdfs.FileResponseData;
import com.quality.common.mapper.QualityAttachmentMapper;
import com.quality.common.service.IQualityAttachmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yerui
 * @since 2019-02-22
 */
@Service
public class QualityAttachmentServiceImpl extends ServiceImpl<QualityAttachmentMapper, QualityAttachment> implements IQualityAttachmentService {

    private FastDFSClient fastDFSClient = new FastDFSClient();

    /**
     * 文件服务器地址
     */
    @Value("${file_server_addr}")
    private String fileServerAddr;

    /**
     * FastDFS秘钥
     */
    @Value("${fastdfs.http_secret_key}")
    private String fastDFSHttpSecretKey;

    @Value("${fs.useSm}")
    private Boolean useSm;

    @Override
    public String custumSave(QualityAttachment qualityAttachment,MultipartFile file,HttpServletRequest request) {
        FileResponseData fileResponseData = this.uploadSample(file, request);
        String path = fileResponseData.getFilePath();
        String name = fileResponseData.getFileName();
        qualityAttachment.setPath(path);
        if (useSm != null && useSm) {
            String contentType = null;
            try {
                contentType = new Tika().detect(file.getInputStream());
                if (contentType != null && contentType.startsWith("image/")) {
                    File smImg = new File(File.listRoots()[0], "fstdfssm/" + "sm/" + path);
                    if (!smImg.getParentFile().exists()) {
                        smImg.getParentFile().mkdirs();
                    }
                    //可以维护到本地的文件夹，前提是Linux 能不能创建
                    Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.25f).toFile(smImg);
                    qualityAttachment.setSmpath("sm/" + path);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //如果有缩列图
        this.saveOrUpdate(qualityAttachment);
        //返回保存后的id
        return qualityAttachment.getId();
    }


    public FileResponseData uploadFileSample(MultipartFile file, HttpServletRequest request){
        return uploadSample(file, request);
    }

    /**
     * 上传通用方法，只上传到服务器，不保存记录到数据库
     *
     * @param file
     * @param request
     * @return
     */
    public FileResponseData uploadSample(MultipartFile file, HttpServletRequest request){
        FileResponseData responseData = new FileResponseData();
        try {
            // 上传到服务器
            String filepath = fastDFSClient.uploadFileWithMultipart(file);

            responseData.setFileName(file.getOriginalFilename());
            responseData.setFilePath(filepath);
            responseData.setFileType(FastDFSClient.getFilenameSuffix(file.getOriginalFilename()));
            // 设置访文件的Http地址. 有时效性.
            String token = FastDFSClient.getToken(filepath, fastDFSHttpSecretKey);
            responseData.setToken(token);
            responseData.setHttpUrl(fileServerAddr+"/"+filepath+"?"+token);
        } catch (BaseException e) {
            responseData.setSuccess(false);
            responseData.setCode(e.getCode());
            responseData.setMessage(e.getMessage());
        }

        return responseData;
    }
}
