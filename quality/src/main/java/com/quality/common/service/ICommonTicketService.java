package com.quality.common.service;


/**
 * Created by sunzw on 2019/3/19.
 */
public interface ICommonTicketService {

    String selectAssignmentCode(String unitCode);

    String selectUnitCode();

    String selectSampleCode(String unitCode);

    String selectTaskCode(String assignmentCode);
}
