package com.quality.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quality.common.entity.QualityAttachment;
import com.quality.common.exception.BaseException;
import com.quality.common.fastdfs.FastDFSClient;
import com.quality.common.fastdfs.FileResponseData;
import com.quality.common.mapper.QualityAttachmentMapper;
import com.quality.common.service.IQualityAttachmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

    @Value("${fs.dir}")
    private String fileDir;

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

        //修改的时候，先删除上一个附件，减少冗余
        String attid = qualityAttachment.getId();
        if(StringUtils.isNotBlank(attid)){
            QualityAttachment oldatt = this.baseMapper.selectById(attid);
            if(oldatt != null){
                String path = oldatt.getPath();
                String smpath = oldatt.getSmpath();
                String type = oldatt.getType();
                if(type.equals("jpg")){
                    if(StringUtils.isNotBlank(path)){
                        this.deleteFile(path);
                    }
                    if(StringUtils.isNotBlank(smpath)){
                        this.del(smpath);
                    }
                }else{
                    if(StringUtils.isNotBlank(path)){
                        this.deleteFile(path);
                    }
                }
                this.baseMapper.deleteById(attid);
            }
        }


        FileResponseData fileResponseData = this.uploadSample(file, request);
        String path = fileResponseData.getFilePath();
        String name = fileResponseData.getFileName();
        QualityAttachment qualityAttachment2 = new QualityAttachment();
        qualityAttachment2.setPath(path);
        qualityAttachment2.setFileName(name);
        if (useSm != null && useSm) {
            String contentType = null;
            try {
                contentType = new Tika().detect(file.getInputStream());

                if (contentType != null && contentType.startsWith("image/")) {
                    File smImg = new File(File.listRoots()[0], fileDir + "/sm/" + path);
                    if (!smImg.getParentFile().exists()) {
                        smImg.getParentFile().mkdirs();
                    }
                    //可以维护到本地的文件夹，前提是Linux 能不能创建
                    Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.25f).toFile(smImg);
                    qualityAttachment2.setSmpath("sm/" + path);
                    qualityAttachment2.setSmImgHeight("110");
                    qualityAttachment2.setSmImgWidth("100");
                }else{
                    String suffix = name.substring(name.lastIndexOf(".") + 1);
                    String type;
                    // 获取文件图标
                    if ("ppt".equalsIgnoreCase(suffix) || "pptx".equalsIgnoreCase(suffix)) {
                        type = "ppt";
                        qualityAttachment2.setSmpath("assets/images/fti/ppt.png");
                    } else if ("doc".equalsIgnoreCase(suffix) || "docx".equalsIgnoreCase(suffix)) {
                        type = "doc";
                        qualityAttachment2.setSmpath("assets/images/fti/doc.png");
                    } else if ("xls".equalsIgnoreCase(suffix) || "xlsx".equalsIgnoreCase(suffix)) {
                        type = "xls";
                        qualityAttachment2.setSmpath("assets/images/fti/xls.png");
                    } else if ("pdf".equalsIgnoreCase(suffix)) {
                        type = "pdf";
                        qualityAttachment2.setSmpath("assets/images/fti/pdf.png");
                    } else if ("html".equalsIgnoreCase(suffix) || "htm".equalsIgnoreCase(suffix)) {
                        type = "htm";
                        qualityAttachment2.setSmpath("assets/images/fti/htm.png");
                    } else if ("txt".equalsIgnoreCase(suffix)) {
                        type = "txt";
                        qualityAttachment2.setSmpath("assets/images/fti/txt.png");
                    } else if ("swf".equalsIgnoreCase(suffix)) {
                        type = "flash";
                        qualityAttachment2.setSmpath("assets/images/fti/flash.png");
                    } else if ("zip".equalsIgnoreCase(suffix) || "rar".equalsIgnoreCase(suffix) || "7z".equalsIgnoreCase(suffix)) {
                        type = "zip";
                        qualityAttachment2.setSmpath("assets/images/fti/zip.png");
                    } else if (contentType != null && contentType.startsWith("audio/")) {
                        type = "mp3";
                        qualityAttachment2.setSmpath("assets/images/fti/mp3.png");
                    } else if (contentType != null && contentType.startsWith("video/")) {
                        type = "mp4";
                        qualityAttachment2.setSmpath("assets/images/fti/mp4.png");
                    } else {
                        type = "file";
                        qualityAttachment2.setSmpath("assets/images/fti/file.png");
                    }
                    qualityAttachment2.setSmImgHeight("56");
                    qualityAttachment2.setSmImgWidth("56");
                    qualityAttachment2.setType(type);

                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //如果有缩列图
        this.saveOrUpdate(qualityAttachment2);
        //返回保存后的id
        return qualityAttachment2.getId();
    }

    /**
     * 自定义删除
     * 如果是jpg类型，则还要处理缩略图
     * 不是jpg类型，则不需要处理缩略图
     * @param attachmentId
     * @return
     */
    @Override
    public boolean custumDel(String attachmentId) {
        boolean delResult = true;
        try {
            QualityAttachment attachment = this.baseMapper.selectById(attachmentId);
            String type = attachment.getType();
            String smpath = attachment.getSmpath();
            String path = attachment.getPath();
            if(StringUtils.isNotBlank(type)){
                if(type.equals("jpg")){
                    //通过fastfds清除数据
                    deleteFile(path);
                    //删除根目录的缩略图
                    del(smpath);
                }else{
                    deleteFile(path);
                }
              int reuslt =   this.baseMapper.deleteById(attachmentId);
              if(reuslt>1){
                  delResult = true;
              }else{
                  delResult = false;
              }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return delResult;
    }

    public Map del(String file) {
        if (fileDir == null) {
            fileDir = "/";
        }
        if (!fileDir.endsWith("/")) {
            fileDir += "/";
        }
        if (file != null && !file.isEmpty()) {
            File f = new File(File.listRoots()[0], fileDir + file);
            if (f.delete()) {
                File smF = new File(File.listRoots()[0], fileDir + "/sm/" + file);
                smF.delete();
                return getRS(200, "删除成功");
            }
        }
        return getRS(500, "删除失败");
    }


    public FileResponseData deleteFile(String filePath) {
        FileResponseData responseData = new FileResponseData();
        try {
            fastDFSClient.deleteFile(filePath);
        } catch (BaseException e) {
            e.printStackTrace();
            responseData.setSuccess(false);
            responseData.setCode(e.getCode());
            responseData.setMessage(e.getMessage());
        }
        return responseData;
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

    // 封装返回结果
    private Map getRS(int code, String msg, String url) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("msg", msg);
        if (url != null) {
            map.put("url", url);
        }
        return map;
    }

    // 封装返回结果
    private Map getRS(int code, String msg) {
        return getRS(code, msg, null);

    }

}
