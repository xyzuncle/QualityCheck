package com.quality.delegate.service;

import com.quality.common.dto.PageResult;
import com.quality.delegate.dto.QualityReportDto;
import com.quality.delegate.entity.QualityReport;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
