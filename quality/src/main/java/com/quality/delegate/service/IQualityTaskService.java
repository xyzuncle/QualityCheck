package com.quality.delegate.service;

import com.quality.common.dto.PageResult;
import com.quality.delegate.dto.QualityTaskDto;
import com.quality.delegate.dto.QualityTaskListDto;
import com.quality.delegate.entity.QualityTask;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 任务单 服务类
 * </p>
 *
 * @author yerui
 * @since 2019-02-24
 */
public interface IQualityTaskService extends IService<QualityTask> {

    boolean saveOrUpdateTask(QualityTaskDto qualityTaskDto);

    QualityTaskDto converTaskDto(QualityTask qualityTask);

    PageResult<QualityTaskListDto> converTaskPage(PageResult<QualityTask> qualityTaskPage);
}
