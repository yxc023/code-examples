package com.yangxiaochen.examples.activiti;

import com.yangxiaochen.examples.activiti.listener.MyActivitiEventListener;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangxiaochen
 * @date 16/8/9 上午11:46
 */
public class BpmnTest {
    private static Logger log = LogManager.getLogger(BpmnTest.class);

    ProcessEngine processEngine;

    @Before
    public void init() {
        processEngine = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration()
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
                .setJdbcUrl("jdbc:mysql://localhost:3306/activiti2")
                .setJdbcUsername("root")
                .setJdbcPassword("")
                .setAsyncExecutorEnabled(true)
                .setAsyncExecutorActivate(false)
                .buildProcessEngine();
    }

    @Test
    public void deployProcess() {
        processEngine.getRuntimeService().addEventListener(new MyActivitiEventListener());
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment()
                .addClasspathResource("MyProcess.bpmn")
                .deploy();
        repositoryService.createDeployment()
                .addClasspathResource("CallableSubProcess.bpmn")
                .deploy();
    }

    @Test
    public void startProcess() {
        Map<String, Object> variables = new HashMap<String, Object>();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess", variables);
        log.info("business instance : {}", processInstance.getProcessDefinitionId());
    }

    @Test
    public void testSubProcess() {
        processEngine.getRuntimeService().addEventListener(new MyActivitiEventListener());
        RepositoryService repositoryService = processEngine.getRepositoryService();

        RuntimeService runtimeService = processEngine.getRuntimeService();
        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().processDefinitionKey("myProcess").list();
        ProcessInstance processInstance = processInstances.get(0);

        ProcessInstance subProcessInstance = runtimeService.createProcessInstanceQuery().superProcessInstanceId(processInstance.getProcessInstanceId()).singleResult();

        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list();

        if (taskList.size() > 0) {
            taskService.complete(taskList.get(0).getId());
        }


        if (subProcessInstance != null) {
            taskList = taskService.createTaskQuery().processInstanceId(subProcessInstance.getProcessInstanceId()).list();
            if (taskList.size() > 0) {
                taskService.complete(taskList.get(0).getId());
            }
        }


    }
}
