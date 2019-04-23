package com.quality.delegate.service;

import com.quality.common.dto.PageResult;
import com.quality.delegate.dto.QualityReportDto;
import com.quality.delegate.entity.QualityReport;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yerui
 * @since 2019-03-02
 */
public interface IQualityReportService extends IService<QualityReport> {

    PageResult<QualityReportDto> converQualityReportDto(PageResult<QualityReport> qualityReportPage);

    /**
     * 根据附件ID来获取计算模板的内容
     * @param attid
     * @return
     */
    boolean customSaveCalculate(String attid, String reportId, HttpServletRequest request, HttpServletResponse response);

    /**
     * 自定义保存
     * @return
     */
    boolean customSaveReport(QualityReport report,HttpServletRequest request,HttpServletResponse response);


    /**
     * 生成报告
     *
     * @param reportId
     */
    public XWPFDocument genReport(String reportId, HttpServletRequest request, HttpServletResponse response);

}
