package com.yangxiaochen.examples.activiti;

import com.yangxiaochen.examples.activiti.listener.MyActivitiEventListener;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangxiaochen
 * @date 16/5/19 下午11:24
 */
public class Main {
    private static Logger LOG = LogManager.getLogger(Main.class);
    public static void main(String[] args) throws InterruptedException {

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

        LOG.info("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());


        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employeeName", "Kermit");
        variables.put("numberOfDays", new Integer(4));
        variables.put("vacationMotivation", "I'm really tired!");

        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Process_0b8brnk", variables);

        LOG.info("Number of process instances: " + runtimeService.createProcessInstanceQuery().count());


        // Fetch all tasks for the management group
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
        for (Task task : tasks) {
            LOG.info("Task available: {}, id: {}",task.getName(), task.getId());
        }

        Task task = tasks.get(0);

        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("vacationApproved", "false");
        taskVariables.put("managerMotivation", "We have a tight deadline!");
        taskService.complete(task.getId(), taskVariables);
    }
}
