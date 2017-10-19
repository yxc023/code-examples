package com.yangxiaochen.examples.activiti;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

/**
 * @author yangxiaochen
 * @date 2017/10/18 13:48
 */
public class EventTest {

    ProcessEngine processEngine;

    @Before
    public void init() {
        processEngine = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration()
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
                .setJdbcUrl("jdbc:mysql://localhost:3306/activiti3")
                .setJdbcUsername("root")
                .setJdbcPassword("")
//                .setAsyncExecutorEnabled(true)
//                .setAsyncExecutorActivate(false)
                .buildProcessEngine();
    }

    @Test
    public void deployProcess() {

//        processEngine.getRuntimeService().addEventListener(new MyActivitiEventListener());
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment()
                .addClasspathResource("EventTextProcess4.bpmn")
                .deploy();
    }

    @Test
    public void test() throws Exception {
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("EventTestProcess", new HashMap() {{
//            put("startCompany", "A10001");
//            put("publishScope", "nation");
        }});

        List<Task> taskList = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).list();

        while (taskList.size() != 0) {
            for (Task task : taskList) {
                System.out.println("=============" + task.getId() + " " + task.getName());
            }
            for (Task task : taskList) {
                if (task.getName().equalsIgnoreCase("节点3")) {
                    continue;
                }
                processEngine.getTaskService().complete(task.getId());
            }
            taskList = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).list();
        }
    }
}
