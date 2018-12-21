package com.quality;




import com.quality.common.util.DeleteProcessInstanceCaCadeCmd;
import com.quality.common.util.MD5;
import com.quality.system.entity.QualityUser;
import org.apache.commons.io.FileUtils;
import org.flowable.cmmn.engine.impl.process.ProcessInstanceService;
import org.flowable.common.engine.api.management.TableMetaData;
import org.flowable.engine.*;

import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.db.DbIdGenerator;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.DataObject;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.identitylink.api.history.HistoricIdentityLink;
import org.flowable.idm.api.IdmIdentityService;
import org.flowable.idm.api.IdmManagementService;
import org.flowable.idm.api.Privilege;
import org.flowable.idm.api.User;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntityImpl;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.flowable.task.api.Task;
import org.flowable.task.service.delegate.DelegateTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static javafx.scene.input.KeyCode.H;

/**
 * Created by sunzw on 2018/11/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = QualityApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActivitiTest {

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
    DelegateTask delegateTask;





    /**
     * 测试process启动
     */
    @Test
    public void TestStartProcess() {
        System.out.println("Start.........");
        Map<String, Object> variables = new HashMap<>();
        //variables.put("inputUser", "John Doe");

        //ProcessInstance pi = runtimeService.startProcessInstanceByKey("LeaveBill", "2");
       // ProcessInstance pi = runtimeService.startProcessInstanceByKey("LeaveBill", variables);
       // System.out.println("流程启动成功，流程id:"+pi.getId());
        System.out.println("Number of process definitions : "
                + repositoryService.createProcessDefinitionQuery().count());
        System.out.println("Number of tasks : " + taskService.createTaskQuery().count());
        runtimeService.startProcessInstanceByKey("PROCESS_1");
        System.out.println("Number of tasks after process start: "
                + taskService.createTaskQuery().count());

    }

    /**
     * 测试查找个人任务
     */
    @Test
    public void findMyTask(){
        List<Task> list = taskService.createTaskQuery().taskAssignee("李四").list();
        list.forEach(task->{
            System.out.println("任務ID"+task.getId());
            System.out.println("任务名称"+task.getName());
        });


    }

    /**
     * 启动任务
     */
    @Test
    public void  StartTask(){
        System.out.println("Number of process definitions : "
                + repositoryService.createProcessDefinitionQuery());
        System.out.println("Number of tasks : " + taskService.createTaskQuery().count());
       // runtimeService.startProcessInstanceByKey("oneTaskProcess");
       // System.out.println("Number of tasks after process start: "
            //    + taskService.createTaskQuery().count());
    }

    /**
     * 测试部署
     */
    @Test
    public void StartProcess(){
        repositoryService.createDeployment().name("testDeploy")
                .addClasspathResource("processes/test2.bpmn20.xml")
                .deploy();
    }

    /**
     * 完成任务
     */
    @Test
    public void compateTask(){
        //完成这个任务,根据TASKID，就到了历史记录
        taskService.complete("377be2ec-01cd-11e9-a50c-001e64f20cfb");
    }

    /**
     * 测试带有流程变量定义的流程
     */
    @Test
    public void testValib(){
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("holidyName", "张三");
        hashMap.put("approveName", "李四");
        //启动一个实例
        runtimeService.startProcessInstanceByKey("myProcess_1",hashMap);
    }

    /**
     * 测试task的变量，根据taskID 设置变量
     */
    @Test
    public void setTaskByAssinge(){
        String name = "张三";
        //从数据库act_run_task 中的proc_inst_id 这个字段，根据流程ID
        String processId = "42504";
        //返回最近的唯一的结果
        Task  task =  taskService.createTaskQuery().taskAssignee(name).processInstanceId(processId).singleResult();
        String taskId = task.getId();

        Map<String, Object> resultMap = new HashMap<String,Object>();
        resultMap.put("请假人", "张无忌");
        QualityUser user = new QualityUser();
        user.setUserName("测试姓名");
        resultMap.put("user", user);
        //回想 act_ru_valibre 这个表加数据
        taskService.setVariables(taskId,resultMap);

    }

    /**
     * 根据认为ID获取任务中的变量
     */
    @Test
    public void getTaskByAssinge(){
        String name = "张三";
        //从数据库act_run_task 中的proc_inst_id 这个字段，根据流程ID
        String processId = "42504";
        //返回最近的唯一的结果
        Task  task =  taskService.createTaskQuery().taskAssignee(name).processInstanceId(processId).singleResult();
        String taskId = task.getId();


        String vlaue = taskService.getVariable(taskId,"请假人").toString();
        System.out.println(vlaue);

        QualityUser user1 = (QualityUser) taskService.getVariable(taskId,"user");
        System.out.println(user1.getUserName());
    }

    /**
     * 历史流程实例查询
     * act_hi_procinst 这个表是历史流程实例表
     */
    @Test
    public void getHistoty(){
        List<HistoricProcessInstance> list =  historyService.createHistoricProcessInstanceQuery().
                processDefinitionKey("myProcess_1").orderByProcessInstanceStartTime().desc().list();

        list.stream().forEach(e->{
            System.out.println(e.getName());
            System.out.println(e.getProcessDefinitionKey());
            System.out.println(e.getId());
            System.out.println(e.getBusinessKey());
        });

    }

    /**
     * 历史活动查看，查看某一个流程执行经历里的多少步骤，后者现在执行到哪个步骤了
     * 比如我测试测数据 执行了这些行为
     * _4
     * 批准
     * _3
     * 请假
     * _2
     * 开始
     */
    @Test
    public void getHistotyTask(){
        String processId = "42504";
        List<HistoricActivityInstance> list =  historyService.createHistoricActivityInstanceQuery().
                //注意processInstaneId 就是流程ID
                processInstanceId(processId).orderByHistoricActivityInstanceStartTime().desc().list();

        list.stream().forEach(e->{
            System.out.println(e.getActivityId());
            System.out.println(e.getActivityName());
        });

    }


    //排他网关的测试
    @Test
    public void paitaInit(){

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("assigenName","张三");
        map.put("bumen","李四");
        map.put("caiwu","王五");
        map.put("zongjingli", "赵六");
        //启动流程
        ProcessInstance pid = runtimeService.startProcessInstanceByKey("myProcess_2",map);
        System.out.println(pid.getName());
        System.out.println(pid.getId());

    }

    /**
     * 测试排他网管的任务，看看随金额走不同的流程
     */
    @Test
    public void paitaTask(){
        int process =400;
        String taskId = "107514";
        List<Task> tasks =taskService.createTaskQuery().processInstanceId("107506").list();
        System.out.println(tasks.get(0).getId());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("price", process);
        taskService.complete(taskId,map);

    }

    @Test
    public void queryTadkByName() {
        String name = "李四";
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(name).list();
        tasks.stream().forEach(e -> {
            System.out.println(e.getAssignee());
            System.out.println(e.getName());
        });
    }


    @Test
    public void saveIdmUser(){
        //这个是idm的用户,跟security的认证不是一张表
        UserEntityImpl userEntity = new UserEntityImpl();
        userEntity.setId("test1");
        userEntity.setPassword("1");
        userEntity.setEmail("w68335397@163.com");
        //必须设置这个版本，否则会认为空指针异常
        userEntity.setRevision(0);
        idmIdentityService.saveUser(userEntity);
    }

    /**
     *  创建IDM组
     */
    @Test
    public void saveIdmGroup(){
        GroupEntityImpl groupEntity = new GroupEntityImpl();
        groupEntity.setId("1");
        groupEntity.setName("研发部");
        //也必须设置版本号
        groupEntity.setRevision(0);
        idmIdentityService.saveGroup(groupEntity);
    }

    /**
     * 创建组和用户的关联关系
     */
    @Test
    public void saveIdmMermerShip(){
        //userid groupid
        idmIdentityService.createMembership("test1","1");

    }

    /**
     * 查询IDM User
     * 其余的删除组，用户 关联关系等方法 都差不多，不写了
     */
    @Test
    public void QueryIdmUser(){
        //userid groupid
        List<User> list =  idmIdentityService.createUserQuery().list();
        list.stream().forEach(e->{
            System.out.println(e.getEmail());
        });

    }

    /**
     * 保存IDM权限
     */
    public void SaveIdmPerimission(){
        //userid groupid
        List<User> list =  idmIdentityService.createUserQuery().list();
        list.stream().forEach(e->{
            System.out.println(e.getEmail());
        });

    }

    /**
     * 保存那个用户能访问哪个模块的权限
     * 添加组的权限是一样的 这里就不重复了
     */
    @Test
    public void saveUserPrivilegeMapping(){
        //String privilegeId, String userId
        //给用户test1 添加task权限
        idmIdentityService.addUserPrivilegeMapping("040f9def-fa9a-11e8-9374-001e64f20cfb", "test1");

    }

    /**
     * 查询权限
     */
    @Test
    public void queryPrivilager(){
        //String privilegeId, String userId
        //给用户test1 添加task权限
        List<Privilege>  list = idmIdentityService.createPrivilegeQuery().list();
        list.stream().forEach(e->{
            System.out.println(e.getName());
        });
    }

    /**
     * 获取IDM的元数据表 总共66
     */
    @Test
    public void queryIdmTalbe(){
        //String privilegeId, String userId
        //给用户test1 添加task权限
        Map<String,Long>  tableCount = idmManagementService.getTableCount();
        System.out.println("表的梳理=="+tableCount.size());
        tableCount.keySet().stream().forEach(k->{
            System.out.println(k);
            System.out.println(tableCount.get(k));
        });

    }

    /**
     * 根据类获取表名称
     */
    @Test
    public void getTableNames(){
        String tableCount = idmManagementService.getTableName(User.class);
        System.out.println("表的名称=="+tableCount);
    }

    /**
     * 获取表元数据信息 比如表名 列的名称和类型
     * 传入表名获取该表的信息
     */
    @Test
    public void getTableMeta(){
        TableMetaData tableCount = idmManagementService.getTableMetaData("act_id_user");
        System.out.println(tableCount.getColumnNames());
        System.out.println(tableCount.getTableName());


    }

    /**
     * 设置流程的相关配置，比如不生成图片，自动部署方式等
     */
    @Test
    public void getProcessInfo(){
        String name = processEngineConfiguration.getEngineName();
        //不创建图片
        processEngineConfiguration.setCreateDiagramOnDeploy(false);


    }

    /**
     * 查看流程定义的名称
     */
    @Test
    public  void  createProcessDefinitionQuery(){
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .latestVersion() //查询的时候最后的一个版本
                //查看流程定义的名字
               // .processDefinitionName("Complex_compensation")
                //查看分类
                // .processDefinitionCategory("")
                .list();
        for (ProcessDefinition pd:list){
            System.out.println("###################");
            System.out.println(pd.getId());
            System.out.println(pd.getName());
            System.out.println(pd.getCategory());
            System.out.println(pd.getDeploymentId());
        }
    }

    /**
     * 根据部署流程ID删除流程，会级联删除
     * 流程定义相关的 任务 ，图片 ，事件相关表
     */

    @Test
    public  void  deleteDeployment(){
        String deploymentId="42501";
        repositoryService.deleteDeployment(deploymentId);
    }

    /**
     * 级联删除会删除当前流程定义下面所有的流程实例
     */
    @Test
    public  void  deleteDeploymentCaseCade(){
        String deploymentId="40001";
        repositoryService.deleteDeployment(deploymentId,true);
    }


    /**
     * 获取部署的资源
     */
    @Test
    public  void  getDepolyResource() throws IOException {
        String deploymentId="2501";
        List<String> deploymentResourceNames = repositoryService
                .getDeploymentResourceNames(deploymentId);

        System.out.println(deploymentResourceNames);

        String imageName=null;
        for (String name : deploymentResourceNames){
            if (name.indexOf(".png")>0){
                imageName=name;
            }
        }
        System.out.println(imageName);
        if (imageName!=null){
            //制定文件的位置
            File file=new File("D:\\workspace\\idea_workspace\\qc_workspace\\test.png");
            //根据部署ID获取 资源的图片或者流程定义
            InputStream resourceAsStream = repositoryService.getResourceAsStream(deploymentId, imageName);
            //采用common.io把流输出成为一个文件
            FileUtils.copyInputStreamToFile(resourceAsStream,file);
        }
    }

    @Test
    public  void  createDeploymentQuery(){
        List<Deployment> list = repositoryService.createDeploymentQuery()
                //.deploymentCategory("测试分类")
                //.deploymentId("37501")
                .list();
        for(Deployment deployment :list){
            System.out.println("###################");
            System.out.println(deployment.getId());
            System.out.println(deployment.getKey());
        }
    }

    /**
     * 本地SQL查询
     */
    @Test
    public  void  createNativeDeploymentQuery(){
        List<Deployment> list = repositoryService.createNativeDeploymentQuery()
                .sql("select * from ACT_RE_DEPLOYMENT").list();

        for(Deployment deployment :list){
            System.out.println("###################");
            System.out.println(deployment.getId());
            System.out.println(deployment.getKey());
        }
    }

    /**
     * select distinct RES.* , P.KEY_ as ProcessDefinitionKey, P.ID_
     * as ProcessDefinitionId from ACT_RU_EXECUTION RES
     * inner join ACT_RE_PROCDEF P on RES.PROC_DEF_ID_ = P.ID_ order by RES.ID_ asc
     * 查询执行实例
     */
    @Test
    public void createExecutionQuery() {
        //查询该流程下的所有实例
        List<Execution> executions = runtimeService.createExecutionQuery()
                //会查最近的版本的流程下的 执行实例
                .processDefinitionKey("testMyProcess").list();
        for (Execution execution : executions) {
            System.out.println(execution.getId() + "," + execution.getActivityId()
                    +","+execution.getName()+",");
        }
    }

    /**
     * 查询历史流程实例
     * select distinct RES.* , DEF.KEY_ as PROC_DEF_KEY_,
     * DEF.NAME_ as PROC_DEF_NAME_, DEF.VERSION_
     * as PROC_DEF_VERSION_, DEF.DEPLOYMENT_ID_ as DEPLOYMENT_ID_
     * from ACT_HI_PROCINST RES left outer join ACT_RE_PROCDEF DEF
     * on RES.PROC_DEF_ID_ = DEF.ID_ WHERE RES.PROC_INST_ID_ = ? order by RES.ID_ asc
     */
    @Test
    public void createHistoricProcessInstanceQuery() {
        String processInstanceId = "59501";
        HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId).
                        singleResult();
        System.out.println("流程定义ID：" + hpi.getProcessDefinitionId());
        System.out.println("流程实例ID：" + hpi.getId());
        System.out.println(hpi.getStartTime());
        System.out.println(hpi.getStartActivityId());
        System.out.println(hpi.getEndTime());
    }

    /**
     * : select RES.* from ACT_HI_ACTINST RES order by RES.ID_ asc
     * 历史活动表
     */
    @Test
    public void createHistoricActivityInstanceQuery() {
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .list();
        for (HistoricActivityInstance hai : list) {
            System.out.println(hai.getId());
        }
    }

    /**
     * 设置流程实例的启动人
     * 我们一般启动实例是没有启动人的
     */
    @Test
    public void setAuthenticatedUserId1() {
        String authenticatedUserId = "分享牛";
        idmIdentityService.setAuthenticatedUserId(authenticatedUserId);
        String processDefinitionKey = "leave";
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
        System.out.println(processInstance.getId() + "," + processInstance.getActivityId());
    }

    /**
     * 根据执行id 获取执行实例的object
     * select * from ACT_RU_VARIABLE where EXECUTION_ID_ = ? and NAME_= ? and TASK_ID_ is null
     * - ==> Parameters: 77001(String), day(String)
     */
    @Test
    public void getDataObject() {
        String executionId = "77001";
        String dataObject = "天数";
        DataObject dataObject1 = runtimeService.getDataObject(executionId, dataObject);
        if (dataObject1 != null) {
            System.out.println(dataObject1.getDataObjectDefinitionKey());
            System.out.println(dataObject1.getDescription());
            System.out.println(dataObject1.getExecutionId());
            System.out.println(dataObject1.getName());
            System.out.println(dataObject1.getValue());
            System.out.println(dataObject1.getType());
        }
    }

    /**
     * process没有级联删除，只是根据ID删除，没有级联删除
     */
    @Test
    public void deleteProcessInstanceCascade() {
        String processInstanceId = "69501";
        String deleteReason = "删除";

        //调用自定义的方法进行级联删除
       // managementService.executeCommand(new DeleteProcessInstanceCaCadeCmd(processInstanceId, deleteReason));
    }

    /**
     * 判断流程是否被挂起
     * true 表示被挂起
     * false 表示没有被挂起
     *
     */
    @Test
    public void isProcessDefinitionSuspended() {
        String processDefinitionId = "leave:2:67004";
        boolean processDefinitionSuspended = repositoryService.isProcessDefinitionSuspended(processDefinitionId);
        System.out.println(processDefinitionSuspended);

    }

    /**
     * 流程定义表状态是2 表示已经被挂起，1的话是没有被挂起。
     * 流程被挂起，流程实例是不能被启动的，启动会报错
     */
    @Test
    public void suspendProcessDefinitionById() {
        String processDefinitionId = "testMyProcess:1:b11fca53-01c0-11e9-bb33-001e64f20cfb";
        repositoryService
                .suspendProcessDefinitionById(processDefinitionId);

    }

    /**
     *  根据流程id 激活任务
     *  带true，null 参数 就是代表 激活实例 同时激活所属的流程
     */
    @Test
    public void activateProcessDefinitionById() {
        String processDefinitionId = "testMyProcess:1:b11fca53-01c0-11e9-bb33-001e64f20cfb";
        repositoryService.activateProcessDefinitionById(processDefinitionId,true,null);
    }

    /**
     * 通过第二个参数 true 或 false
     * true 表示挂起执行实例
     * false 表示不挂起执行实例
     */
    @Test
    public void suspendProcessDefinitionById2() {
        String processDefinitionId = "leave:2:67004";
        repositoryService.suspendProcessDefinitionById(processDefinitionId,true,null);
    }


    /**
     * ********************************************
     * 测试一个全新的流程 测试以下几个问题：
     * 1：一个流程的激活，他的执行实例有几个？
     * 是否可以获取到当前活动的实例或者说这个流程是由多少个执行实例组成的？
     *      不可以的，不管是任务表还是执行表，都是当前最新的执行实例
     * 2:获取到活动任务的节点，是只当前活动到的节
     * 3:如果执行到一半的任务，这个时候流程升级了，如何处理，那历史记录会有变化吗
     *           互不影响，因为流程定义的版本不一样了，
     * 4:挂起一个流程 执行实例会挂起吗？反之，挂起一个实例，进程会怎么样
     *     挂起了流程，流程不能被激活，但是实例可以完成任务吗？
     *     答：流程被挂起，但是实例任务还是可以完成的
     *  5：测试挂起一个实例，实例是否可以被完成？
     *            不可以完成实例，同时所属的流程也被挂起
     *
     */

     @Test
     public void testProcessWithQuestion(){
            Map<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("holidyName", "张三");
            //自己执行，下一步，到自己，看看可以不
            hashMap.put("approveName", "李四");
            //启动一个实例
            runtimeService.startProcessInstanceByKey("testMyProcess",hashMap);
      }

    /**
     * 挂起实例，同时观察所属流程实例也被挂起。
     */
    @Test
    public void suspendProcessDefinitionById3() {
        String processDefinitionId = "testMyProcess:1:b11fca53-01c0-11e9-bb33-001e64f20cfb";
        //注意 这里方法不一样 是根据流程定义ID
        repositoryService.suspendProcessDefinitionById(processDefinitionId,true,null);


    }


    /**

     select distinct RES.* from ACT_RU_TASK
     RES WHERE RES.ASSIGNEE_ is null
      and exists(
     select LINK.ID_ from ACT_RU_IDENTITYLINK LINK where LINK.TYPE_ = 'candidate'
       and LINK.TASK_ID_ = RES.ID_ and ( LINK.USER_ID_ = ? ) ) order by RES.ID_ asc

     主要是查询 任务表和 ACT_RU_IDENTITYLINK 这个表进行关联，

     查询组任务中的某个人可以执行的任务
     */
    @Test
    public void findGroupTask() {
        String userId="张三";
        List<Task> tasks = taskService.createTaskQuery()
                .taskCandidateUser(userId)
                .list();
        for (Task task : tasks){
            System.out.println(task.getId());
            System.out.println(task.getName());
            System.out.println(task.getCreateTime());

        }
    }

    /**
     * 查询组任务成员列表
     */
    /**
     * select * from ACT_RU_IDENTITYLINK where TASK_ID_ = ?
     * 查询运行任务的处理人
     */
    @Test
    public void findGroupuser() {
        String taskId="40006";
        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(taskId);
        for (IdentityLink identityLink : identityLinksForTask){
            System.out.println("#####################");
            System.out.println(identityLink.getProcessDefinitionId());
            System.out.println(identityLink.getGroupId());
            System.out.println(identityLink.getUserId());
            System.out.println(identityLink.getTaskId());
            System.out.println("#####################");
        }
    }

    /**
     * 查询历史任务的处理人
     * select * from ACT_HI_IDENTITYLINK where TASK_ID_ = ?
     */
    @Test
    public void findHisGroupuser() {
        String taskId="40006";
        List<HistoricIdentityLink> identityLinksForTask = historyService.getHistoricIdentityLinksForTask(taskId);
        for (HistoricIdentityLink identityLink : identityLinksForTask){
            System.out.println("#####################");
            System.out.println(identityLink.getGroupId());
            System.out.println(identityLink.getUserId());
            System.out.println(identityLink.getTaskId());
            System.out.println("#####################");
        }
    }

    /**
     * anceEntityImpl.updateHistoricActivityInstance  - ==>  Preparing: update ACT_HI_ACTINST SET REV_ = ?, PROC_DEF_ID_ = ?, ASSIGNEE_ = ? where ID_ = ? and REV_ = ?
     * 02:32:57,856 [main] DEBUG org.flowable.engine.impl.persistence.entity.HistoricActivityInstanceEntityImpl.updateHistoricActivityInstance  - ==> Parameters: 2(Integer), grouptask:1:27504(String), 分享牛1(String), 30004(String), 1(Integer)
     * 02:32:57,857 [main] DEBUG org.flowable.engine.impl.persistence.entity.HistoricActivityInstanceEntityImpl.updateHistoricActivityInstance  - <==    Updates: 1
     * 02:32:57,859 [main] DEBUG org.flowable.task.service.impl.persistence.entity.HistoricTaskInstanceEntityImpl.updateHistoricTaskInstance  - ==>  Preparing: update ACT_HI_TASKINST SET REV_ = ?, ASSIGNEE_ = ?, CLAIM_TIME_ = ?, LAST_UPDATED_TIME_ = ? where ID_ = ? and REV_ = ?
     * 02:32:57,860 [main] DEBUG org.flowable.task.service.impl.persistence.entity.HistoricTaskInstanceEntityImpl.updateHistoricTaskInstance  - ==> Parameters: 2(Integer), 分享牛1(String), 2018-11-11 14:32:57.777(Timestamp), 2018-11-11 14:32:57.782(Timestamp), 30005(String), 1(Integer)
     * 02:32:57,861 [main] DEBUG org.flowable.task.service.impl.persistence.entity.HistoricTaskInstanceEntityImpl.updateHistoricTaskInstance  - <==    Updates: 1
     * 任务的认领/拾取
     *
     * 任务被认领后，组任务中就无法查询了，而task任务表中多了一条记录，
     * 历史任务表里 多了认领时间
     * 历史活动表 更新了认领人
     */
    @Test
    public void claim() {
        String taskId="62505";
        String userId="分享牛8";
        taskService.claim(taskId,userId);
    }


    @Test
    public void test(){
        //延迟两秒激活任务
        Date date = new Date(new Date().getTime()+2*1000);
        //这个方式是根据时间激活，默认激活后挂起
        repositoryService.createDeployment().activateProcessDefinitionsOn(date);
    }
}
