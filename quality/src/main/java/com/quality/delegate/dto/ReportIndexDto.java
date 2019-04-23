package com.quality.delegate.dto;

import java.util.List;
import java.util.Map;

public class ReportIndexDto {
    //是否生成
    public boolean isGen;
    //索引编号
    public String indexNumber;
    //索引名称
    public String indexName;

    public Map<String, List<ReportDto>> sheetContent;

    public Map<String, List<ReportDto>> getSheetContent() {
        return sheetContent;
    }

    public void setSheetContent(Map<String, List<ReportDto>> sheetContent) {
        this.sheetContent = sheetContent;
    }

    public boolean isGen() {
        return isGen;
    }

    public void setGen(boolean gen) {
        isGen = gen;
    }

    public String getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(String indexNumber) {
        this.indexNumber = indexNumber;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
}
