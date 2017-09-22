package com.yangxiaochen.examples.activiti;

import com.yangxiaochen.examples.activiti.listener.MyActivitiEventListener;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangxiaochen
 * @date 2017/9/19 15:17
 */
public class BpmnTest {
    public static void main(String[] args) {
        ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration()
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
                .setJdbcUrl("jdbc:mysql://localhost:3306/activiti")
                .setJdbcUsername("root")
                .setJdbcPassword("")
                .setAsyncExecutorEnabled(true)
                .setAsyncExecutorActivate(false)
                .buildProcessEngine();

        processEngine.getRuntimeService().addEventListener(new MyActivitiEventListener());

        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("demo.bpmn")
                .deploy();



        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employeeName", "Kermit");
        variables.put("numberOfDays", new Integer(4));
        variables.put("vacationMotivation", "I'm really tired!");
        variables.put("amount", 1500);

        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Process_0b8brnk", variables);

        List<Task> taskList = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).list();

        while (taskList.size() != 0)
        for (Task task : taskList) {
            processEngine.getTaskService().complete(task.getId());
            taskList = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).list();
        }
    }
}
