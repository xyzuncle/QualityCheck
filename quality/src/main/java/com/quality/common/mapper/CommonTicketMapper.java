package com.quality.common.mapper;

/**
 * Created by sunzw on 2019/3/18.
 */

public interface CommonTicketMapper {

    void selectAssignmentCode();

    String selectLastAssignmentId();

    void selectUnitCode();

    String selectLastUnitId();

    void selectSampleCode();

    String selectLastSampleId();

    void selectTaskCode();

    String selectLastTaskId();
}
