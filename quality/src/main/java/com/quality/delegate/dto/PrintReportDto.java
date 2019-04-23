package com.quality.delegate.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 为了判断是否有特殊符号
 * @Author: yerui
 * @CreateDate : 2019/3/26 17:29
 * @Version: 1.0
 *
 */
public class PrintReportDto implements Serializable {

    private boolean squareFlag;
    private List<ReportDto> reportDtoList;

    public boolean isSquareFlag() {
        return squareFlag;
    }

    public void setSquareFlag(boolean squareFlag) {
        this.squareFlag = squareFlag;
    }

    public List<ReportDto> getReportDtoList() {
        return reportDtoList;
    }

    public void setReportDtoList(List<ReportDto> reportDtoList) {
        this.reportDtoList = reportDtoList;
    }
}
