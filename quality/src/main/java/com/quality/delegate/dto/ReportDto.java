package com.quality.delegate.dto;

import java.util.Map;

/**
 * @Description: 报告类的转换类
 * @Author: yerui
 * @CreateDate : 2019/3/19 4:05
 * @Version: 1.0
 *
 */
public class ReportDto {
    //序号
    public String number;
    //项目内容
    public String projectContent;
    //单位
    public String unit;
    //结果
    public String result;
    //平方
    public String square;
    //是否是平方形式的内容
    public boolean isSquareFlag;
    //不包含平方的内容
    public String disSquareContent;

    public String getDisSquareContent() {
        return disSquareContent;
    }

    public void setDisSquareContent(String disSquareContent) {
        this.disSquareContent = disSquareContent;
    }

    public String getProjectContent() {
        return projectContent;
    }

    public void setProjectContent(String projectContent) {
        this.projectContent = projectContent;
    }

    public String getSquare() {
        return square;
    }

    public void setSquare(String square) {
        this.square = square;
    }

    public boolean getSquareFlag() {
        return isSquareFlag;
    }

    public void setSquareFlag(boolean squareFlag) {
        isSquareFlag = squareFlag;
    }



    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


}
