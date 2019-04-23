package com.quality;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.quality.common.mapper.CommonTicketMapper;
import com.quality.common.service.ICommonTicketService;
import net.bytebuddy.asm.Advice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DecimalFormat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = QualityApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class QualityApplicationTests {


    @Autowired
    ICommonTicketService commonTicketService;


    @Test
    public void test(){
       String unit= commonTicketService.selectUnitCode();

       String assignCode =  commonTicketService.selectAssignmentCode("HY".concat(unit));

       String sample = commonTicketService.selectSampleCode("HY".concat(unit));

       String task =  commonTicketService.selectTaskCode(assignCode);


        System.out.println(unit);

        System.out.println(assignCode);


        System.out.println(sample);


        System.out.println(task);

    }

}
