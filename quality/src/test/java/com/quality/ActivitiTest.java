package com.quality;

import com.quality.activiti.service.IWorkflowService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by sunzw on 2018/11/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = QualityApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActivitiTest {

    @Autowired
    RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Test
    public void TestStartProcess() {
        System.out.println("Start.........");
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("leaveBill", "1");
        System.out.println("流程启动成功，流程id:"+pi.getId());
    }
}
