package com.quality.common.service.impl;

import com.quality.common.constants.CommonConstants;
import com.quality.common.mapper.CommonTicketMapper;
import com.quality.common.service.ICommonTicketService;
import com.quality.common.service.IQualityAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * Created by sunzw on 2019/3/19.
 */
@Service
public class CommonTicketServiceImpl  implements ICommonTicketService {

    @Autowired
    private CommonTicketMapper ticketMapper;

    private static  Calendar cal = Calendar.getInstance();

    @Override
    public String selectAssignmentCode(String unitCode) {
        ticketMapper.selectAssignmentCode();
        String number = ticketMapper.selectLastAssignmentId();
        String code = CommonConstants.WT.concat("-").concat(unitCode).concat(cal.get(Calendar.YEAR)+"").
                concat(getCode(number,"0000"));
        return code;
    }

    @Override
    public String selectUnitCode() {
        ticketMapper.selectUnitCode();
        String number = ticketMapper.selectLastUnitId();
        String code =  getCode(number,"00");
        return code;
    }

    @Override
    public String selectSampleCode(String unitCode) {
        ticketMapper.selectSampleCode();
        String number = ticketMapper.selectLastSampleId();
        String code = CommonConstants.YP.concat(unitCode).concat(cal.get(Calendar.YEAR)+"").
                concat(getCode(number,"0000"));
        return code;
    }

    @Override
    public String selectTaskCode(String assignmentCode) {
        ticketMapper.selectTaskCode();
        String number = ticketMapper.selectLastTaskId();
        assignmentCode = assignmentCode.substring(assignmentCode.indexOf('-')+1);
        String code = CommonConstants.RW.concat("-").concat(assignmentCode).concat("-").
                concat(getCode(number,"00"));
        return code;
    }

    private String getCode(String num,String reg){
        DecimalFormat df=new DecimalFormat(reg);
        String str2=df.format(Integer.parseInt(num));
        return str2;
    }
}
