package com.quality;

import com.quality.common.config.CustomProcessGenerator;
import com.quality.system.entity.QualityFlow;
import com.quality.system.service.impl.QualityFlowServiceImpl;
import org.apache.commons.io.FileUtils;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.*;
import org.flowable.engine.form.FormProperty;
import org.flowable.engine.form.FormType;
import org.flowable.engine.form.StartFormData;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;

import org.flowable.idm.api.IdmIdentityService;
import org.flowable.idm.api.IdmManagementService;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.flowable.task.api.Task;

import org.flowable.validation.ProcessValidator;
import org.flowable.validation.ProcessValidatorFactory;
import org.flowable.validation.ValidationError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = QualityApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FormTest {

    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    HistoryService historyService;

    @Autowired
    IdmIdentityService idmIdentityService;

    //IDM的元数据信息表
    @Autowired
    IdmManagementService idmManagementService;

    @Autowired
    ProcessEngineConfiguration processEngineConfiguration;

    @Autowired
    FormService formService;

    @Autowired
    CustomProcessGenerator customProcessGenerator;

    @Autowired
    QualityFlowServiceImpl qualityFlowService;


    @Test
    public void TestStartProcess() {
        //根据部署key 启动任务
        runtimeService.startProcessInstanceByKey("testForm");
        System.out.println("Number of tasks after process start: "
                + taskService.createTaskQuery().count());

    }

    /**
     * 获取几点中 form表单中的定义
     */
    @Test
    public void getForm(){
        String processDefinitionId = "testForm:1:3a68fb8c-04d3-11e9-b538-001e64f20cfb";
        StartFormData startFormData =
                formService.getStartFormData(processDefinitionId);
        System.out.println(startFormData.getProcessDefinition());
        System.out.println(startFormData.getDeploymentId());
        System.out.println(startFormData.getFormKey());
        List<FormProperty> formProperties = startFormData.getFormProperties();
        formProperties.forEach(fm->{
            System.out.println("############################");
            System.out.println(fm.getId());
            System.out.println(fm.getName());
            System.out.println(fm.getValue());
            FormType formType = fm.getType();
            System.out.println(formType);
        });
    }


    /**
     * 测试任务
     */
    @Test
    public void findMyTask(){
        List<Task> list = taskService.createTaskQuery().list();
        list.forEach(task->{
            System.out.println("任務ID"+task.getId());
            System.out.println("任务名称"+task.getName());
        });


    }

    /**
     *  <extensionElements>
     *                 <flowable:formProperty id="start_date" name="开始时间" type="date" datePattern="yyyy-MM-dd"
     *                                        required="true"></flowable:formProperty>
     *                 <flowable:formProperty id="end_date" name="结束时间" type="date" datePattern="yyyy-MM-dd"
     *                                        required="true"></flowable:formProperty>
     *                 <flowable:formProperty id="reason" name="请假原因" type="string" required="true"></flowable:formProperty>
     *                 <flowable:formProperty id="days" name="请假的天数" type="long" required="true"></flowable:formProperty>
     *             </extensionElements>
     */
    @Test
    public void submitStartFormData() {
        String processDefinitionId = "testForm:1:3a68fb8c-04d3-11e9-b538-001e64f20cfb";
        Map<String, String> vars=new HashMap<String, String>();
        vars.put("start_date",getDate());
        vars.put("end_date",getDate());
        vars.put("reason","我想出去玩玩");
        vars.put("days","3");
        formService.submitStartFormData(processDefinitionId,vars);
    }

    public  String getDate(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String s = simpleDateFormat.format(new Date());

        return  s;
    }


    //测试外置的表单是否会自动部署
    //结论，只会自动部署XML，外置表单不会被部署
    @Test
    public void testOutSideForm(){
        DeploymentBuilder  builder =  repositoryService.createDeployment()
                .name("handform2")
                .category("testform2")
                .key("testOutSideKey2")
                .addClasspathResource("processes/formkey2.bpmn20.xml")
                .addClasspathResource("processes/form/start.html")
                .addClasspathResource("processes/form/task.html")
                .addClasspathResource("processes/form/task2.html");
        Deployment deployment = builder.deploy();
        //打印部署的ID
        System.out.println(deployment.getId());

    }

    /**
     * 根据流程定义ID获取开始表单的key
     * 这个key是 /process/form/start.html 是这种形式化的key，
     * 就是关联的外部页面
     */
    @Test
    public void getStartFormKey() {
        String processDefinitionId = "formkey2:9:19ee03d7-0852-11e9-b295-001e64f20cfb";
        String startFormKey = formService.getStartFormKey(processDefinitionId);
        //开始节点定义的表单key:/process/form/start.html
        System.out.println("开始节点定义的表单key:" + startFormKey);
    }



    /**
     * 根据流程定义ID获取开始表单的内容
     */
    @Test
    public void getRenderedStartForm() {
        String processDefinitionId="formkey2:9:19ee03d7-0852-11e9-b295-001e64f20cfb";
        Object startFormContent = formService.getRenderedStartForm(processDefinitionId);
        //这个结果就是一个html页面，用流的方式打印出来
        System.out.println("开始节点定义的表单内容:"+startFormContent.toString());
    }



    /**
     * 根据taskId 获取表单内容
     */

    @Test
    public void getRenderedTaskForm() {
        String taskId="95774520-0853-11e9-84d0-001e64f20cfb";
        Object renderedTaskForm = formService.getRenderedTaskForm(taskId);

        System.out.println(renderedTaskForm);
    }


    /**
     * 启动任务并提交表单，
     * 区别：没有表单的任务是根据key或者流程定义ID来启动任务的
     * 而带有外置表单的任务
     */
    @Test
    public void submitStartFormData2() {
        String processDefinitionId="formkey2:9:19ee03d7-0852-11e9-b295-001e64f20cfb";
        Map<String, String> vars=new HashMap<String, String>();
        //这个startDate，endDate,resoan 必须与页面的ID一致
        vars.put("startDate",getDate());
        vars.put("endDate",getDate());
        vars.put("reason","我想出去玩玩2");
        vars.put("days","3");
        formService.submitStartFormData(processDefinitionId,vars);
    }

    /**
     * 带有外置表单的task进行提交
     */
    @Test
    public void submitTaskForm() {
        String taskId="95774520-0853-11e9-84d0-001e64f20cfb";
        Map<String, String> vars=new HashMap<String, String>();
        vars.put("startDate",getDate());
        vars.put("endDate",getDate());
        vars.put("reason","同意你出去玩玩。");
        vars.put("days","3");
        formService.submitTaskFormData(taskId,vars);
    }



    /**
     *  生成常规图片
     *  public InputStream generateDiagram
     *  (BpmnModel bpmnModel, 根据流程ID获取
     *  String imageType, 设置图片类型
     *  List<String> highLightedActivities, 高亮的活动节点 不能为null，可以是空集合
     *  List<String> highLightedFlows,  高亮的连接线 不能null，可以是空集合
     *             String activityFontName, 活动节点的字体
     *             String labelFontName,  标签的名字
     *             String annotationFontName, 注解的名字
     *             ClassLoader customClassLoader, 没有类加载器 所以为null
     *             double scaleFactor,  默认1.0
     *             boolean drawSequenceFlowNameWithNoLabelDI); 6.4.0 的版本才有，修复bug 默认ture
     */
    @Test
    public  void generateDiagram() throws IOException {
        String processDefinitionId="formkey2:9:19ee03d7-0852-11e9-b295-001e64f20cfb";
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        String imageType="PNG";
        List<String> highLightedActivities=new ArrayList<String>();
        List<String> highLightedFlows=new ArrayList<String>();
        String activityFontName="宋体";
        String labelFontName="宋体";
        String annotationFontName="宋体";
        ClassLoader customClassLoader=null;
        double scaleFactor=1.0D;
        boolean drawSequenceFlowNameWithNoLabelDI=true;
        ProcessDiagramGenerator processDiagramGenerator=new DefaultProcessDiagramGenerator();
        InputStream inputStream = processDiagramGenerator.generateDiagram(bpmnModel, imageType, highLightedActivities, highLightedFlows
                , activityFontName, labelFontName, annotationFontName, customClassLoader, scaleFactor, drawSequenceFlowNameWithNoLabelDI);
        //通过common-io 来快速实现流到文件的工具类
        FileUtils.copyInputStreamToFile(inputStream,new File("F:\\/"+"1.png"));
    }

    /**
     * 生成高亮图片
     * @throws IOException
     */
    @Test
    public  void generateHighLightedActivitiesDiagram() throws IOException {
        String processDefinitionId="formkey2:9:19ee03d7-0852-11e9-b295-001e64f20cfb";
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        String imageType="PNG";
        //增加活动节点的高亮 记住只能配置ID，无法配置name
        List<String> highLightedActivities=new ArrayList<String>();
        highLightedActivities.add("sid-4CD86265-1E97-4CC7-99BD-689F65C4C297");
        //增加连线的高亮，只能匹配ID
        List<String> highLightedFlows=new ArrayList<String>();
        highLightedFlows.add("sid-16E5B972-45BF-4B58-A023-688C7EFF1CD8");
        String activityFontName="宋体";
        String labelFontName="宋体";
        String annotationFontName="宋体";
        ClassLoader customClassLoader=null;
        double scaleFactor=1.0D;
        boolean drawSequenceFlowNameWithNoLabelDI=true;
        //用的默认的流程生成器
        ProcessDiagramGenerator processDiagramGenerator=new DefaultProcessDiagramGenerator();
        //这个是自定义的图片生成器
      //  ShareniuDefaultProcessDiagramGenerator processDiagramGenerator=new ShareniuDefaultProcessDiagramGenerator();
        InputStream inputStream = processDiagramGenerator.generateDiagram(bpmnModel, imageType, highLightedActivities, highLightedFlows
                , activityFontName, labelFontName, annotationFontName, customClassLoader, scaleFactor, drawSequenceFlowNameWithNoLabelDI);

        FileUtils.copyInputStreamToFile(inputStream,new File("F:\\/"+"1.png"));
    }

    /**
     * 通过自定义的图片生成器来生成图片，
     * 目前只是集成了父类，没有做任何的行为
     * @throws IOException
     */
    @Test
    public  void generateHighLighteByCustiom() throws IOException {
        String processDefinitionId="formkey2:9:19ee03d7-0852-11e9-b295-001e64f20cfb";
        //通过流程ID获取bpmnModel
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        String imageType="PNG";
        //增加活动节点的高亮 记住只能配置ID，无法配置name
        List<String> highLightedActivities=new ArrayList<String>();
        highLightedActivities.add("sid-4CD86265-1E97-4CC7-99BD-689F65C4C297");
        //增加连线的高亮，只能匹配ID
        List<String> highLightedFlows=new ArrayList<String>();
        highLightedFlows.add("sid-16E5B972-45BF-4B58-A023-688C7EFF1CD8");
        String activityFontName="宋体";
        String labelFontName="宋体";
        String annotationFontName="宋体";
        ClassLoader customClassLoader=null;
        double scaleFactor=1.0D;
        boolean drawSequenceFlowNameWithNoLabelDI=true;
        //用的默认的流程生成器
        //ProcessDiagramGenerator processDiagramGenerator=new DefaultProcessDiagramGenerator();
        //这个是自定义的图片生成器
        InputStream inputStream = customProcessGenerator.generateDiagram(bpmnModel, imageType, highLightedActivities, highLightedFlows
                , activityFontName, labelFontName, annotationFontName, customClassLoader, scaleFactor, drawSequenceFlowNameWithNoLabelDI);
        FileUtils.copyInputStreamToFile(inputStream,new File("F:\\/"+"3.png"));
    }


    /**
     * 收BPMN对象代替XML，并部署流程
     */
    @Test
    public void testBPMN(){
        String resourceName = "/processes/manual.bpmn";
        BpmnModel bpmnModel = getBpmnModel();

        Deployment deployment = repositoryService.createDeployment()
                .name("测试手工bpmn对象")
                .category("model")
                //添加手动创建的bpmnModel对象
                //String resourceName, BpmnModel bpmnModel
                .addBpmnModel(resourceName,bpmnModel)
                .deploy();

    }

    private BpmnModel getBpmnModel() {
        BpmnModel bpmnModel = new BpmnModel();

        StartEvent startEvent = new StartEvent();
       /* startEvent.setId("start");*/
        startEvent.setName("开始事件");

        EndEvent endEvent = new EndEvent();
        endEvent.setId("end");
        endEvent.setName("结束事件");

        //用户的userTask
        UserTask userTask = new UserTask();
        //注意ID不能是一个纯数字，解析不过去
        userTask.setId("a");
        userTask.setName("请假");
        userTask.setAssignee("张三");

        //用户的userTask
        UserTask userTask1 = new UserTask();
        userTask1.setId("b");
        userTask1.setName("审批");
        userTask1.setAssignee("李四");

        //创建连线的BPMN的modle
        SequenceFlow sequenceFlow = new SequenceFlow();
        //设置ID
        sequenceFlow.setId("e");
        //设置源ID
        sequenceFlow.setSourceRef("start");
        //设置目标ID
        sequenceFlow.setTargetRef("a");

        SequenceFlow sequenceFlow2 = new SequenceFlow();
        sequenceFlow2.setId("f");
        sequenceFlow2.setSourceRef("a");
        sequenceFlow2.setTargetRef("b");

        SequenceFlow sequenceFlow3 = new SequenceFlow();
        sequenceFlow3.setId("g");
        sequenceFlow3.setSourceRef("b");
        sequenceFlow3.setTargetRef("end");


        Process process = new Process();
        process.setId("level");
        process.setName("请假流程");
        //process与事件和任务建立关系
        process.addFlowElement(startEvent);
        process.addFlowElement(endEvent);
        process.addFlowElement(userTask);
        process.addFlowElement(userTask1);
        process.addFlowElement(sequenceFlow);
        process.addFlowElement(sequenceFlow2);
        process.addFlowElement(sequenceFlow3);

        //bpmnModel对象与process建立关联关系
        bpmnModel.addProcess(process);
        return bpmnModel;
    }


    /**
     * 效验BPMNmodel是否合法
     */
    @Test
    public void testValid(){
        ProcessValidatorFactory pvf = new ProcessValidatorFactory();
        ProcessValidator defaultProcessValidator = pvf.createDefaultProcessValidator();
        List<ValidationError> validate = defaultProcessValidator.validate(getBpmnModel());
        if(validate.size()>0){
            System.out.println("此文件有错误");
        }else{
            System.out.println("此流程文件没有错误");
        }
    }

    /**
     * 通过流程定义ID来获取BpmnModel对象
     * 这个对象包含整个XML的定义
     * changyi:2:7f8893b6-0f78-11e9-abc8-001e64f20cfb
     * 这个部署ID 指的就是 chuangyi.bpmn20.xml
     */
    @Test
    public void formXMLtoBean(){
        String processDfId = "changyi:2:7f8893b6-0f78-11e9-abc8-001e64f20cfb";
        BpmnModel bpmnModel=  repositoryService.getBpmnModel(processDfId);
        //这个process指的是整个流程文件，也就是XML文件
        Process  process = bpmnModel.getProcesses().get(0);
        List<UserTask> userTasks =  process.findFlowElementsOfType(UserTask.class);
        List<ExclusiveGateway>  gatewayList =  process.findFlowElementsOfType(ExclusiveGateway.class);
        List<SequenceFlow> sequenceFlowList = process.findFlowElementsOfType(SequenceFlow.class);

        userTasks.stream().forEach(e->{
            System.out.println(e.getId());
            System.out.println(e.getName());
            System.out.println(e.getAssignee());
            System.out.println("##################");
        });

        //获取排他网关节点
   /*     gatewayList.stream().forEach(e->{
            List<SequenceFlow> outgoingFlows = e.getOutgoingFlows();

            System.out.println(outgoingFlows);
            System.out.println(outgoingFlows.get(0).getConditionExpression());
        });*/

        //获取所有流水的id,name 与表达式
      /*  sequenceFlowList.stream().forEach(e->{
            if(e.getConditionExpression()!=null){
                System.out.println(e.getId());
                System.out.println(e.getName());
                System.out.println(e.getConditionExpression());
                System.out.println(e.getSourceRef());
                System.out.println(e.getTargetRef());
                System.out.println("########################");
            }
        });*/

    }

    /**
     * 这个方式是为了测试赋值
     */
    @Test
    public void putBmpmModel(){
        //假设我根据一个流程ID得到了BPMN对象
        String processDfId = "changyi:2:7f8893b6-0f78-11e9-abc8-001e64f20cfb";
        BpmnModel bpmnModel=  repositoryService.getBpmnModel(processDfId);
        //这个process指的是整个流程文件，也就是XML文件
        Process  process = bpmnModel.getProcesses().get(0);
        List<UserTask> userTasks =  process.findFlowElementsOfType(UserTask.class);
        List<ExclusiveGateway>  gatewayList =  process.findFlowElementsOfType(ExclusiveGateway.class);
        List<SequenceFlow> sequenceFlowList = process.findFlowElementsOfType(SequenceFlow.class);

        userTasks.stream().forEach(e->{
            //循环这次请求，得到所有的任务，并保存在表里
            QualityFlow qualityFlow = new QualityFlow();
            //保存流程定义ID
            qualityFlow.setProcessId(processDfId);
            qualityFlow.setTaskId(e.getId());
            qualityFlow.setTaskName(e.getName());
            qualityFlow.setType("1");
            qualityFlow.setTaskExpression(e.getAssignee());
            //这里不同前端获取，而是自己设置一个值
           // qualityFlow.setTaskValue();

            System.out.println(e.getId());
            System.out.println(e.getName());
            System.out.println(e.getAssignee());
            System.out.println("##################");
        });

    }



}
