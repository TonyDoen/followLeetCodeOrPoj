//package me.meet.flowable;
//
//import org.flowable.engine.*;
//import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
//import org.flowable.engine.repository.Deployment;
//import org.flowable.engine.repository.ProcessDefinition;
//import org.flowable.engine.runtime.ProcessInstance;
//import org.flowable.engine.task.Task;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class FlowableTest {
//    public static void main(String[] args) {
//        /**
//         * 启动服务
//         */
//        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
//                .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1")
//                .setJdbcUsername("sa")
//                .setJdbcPassword("")
//                .setJdbcDriver("org.h2.Driver")
//                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
//
//        ProcessEngine processEngine = cfg.buildProcessEngine();
//
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        Deployment deployment = repositoryService.createDeployment()
//                .addClasspathResource("holiday-request.bpmn20.xml")
//                .deploy();
//
//        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
//                .deploymentId(deployment.getId())
//                .singleResult();
//        System.out.println("Found process definition : " + processDefinition.getName());
//
//
//        /**
//         * 构建实例
//         */
//        String employee = "tony";
//        Integer nrOfHolidays = 4;
//        String description = "人生需要休假";
//
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//
//        Map<String, Object> variables = new HashMap<String, Object>();
//        variables.put("employee", employee);
//        variables.put("nrOfHolidays", nrOfHolidays);
//        variables.put("description", description);
//        ProcessInstance processInstance =
//                runtimeService.startProcessInstanceByKey("holidayRequest", variables);
//
//        TaskService taskService = processEngine.getTaskService();
//        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
//        System.out.println("You have " + tasks.size() + " tasks:");
//        for (int i=0; i<tasks.size(); i++) {
//            System.out.println((i+1) + ") " + tasks.get(i).getName());
//        }
//
//        int taskIndex = 3;
//        Task task = tasks.get(taskIndex - 1);
//        Map<String, Object> processVariables = taskService.getVariables(task.getId());
//        System.out.println(processVariables.get("employee") + " wants " +
//                processVariables.get("nrOfHolidays") + " of holidays. Do you approve this?");
//    }
//}
