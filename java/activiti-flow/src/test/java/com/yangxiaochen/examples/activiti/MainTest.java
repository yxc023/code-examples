package com.yangxiaochen.examples.activiti;

import com.yangxiaochen.examples.activiti.listener.MyActivitiEventListener;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

/**
 * @author yangxiaochen
 * @date 2017/10/16 17:20
 */
public class MainTest {
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
                .addClasspathResource("newsPublish.bpmn")
                .deploy();
    }


    @Test
    public void testProcess() throws Exception {
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("newsPublish", new HashMap() {{
            put("startCompany", "A10001");
            put("publishScope", "nation");
        }});

        List<Task> taskList = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).list();

        while (taskList.size() != 0) {
            for (Task task : taskList) {
                System.out.println("=============" + task.getId() + " " + task.getName());
            }
            for (Task task : taskList) {
                processEngine.getTaskService().complete(task.getId());
            }
            taskList = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).list();
        }

    }
}
