package com.jadework.controller;

import com.jadework.dao.TaskDao;
import com.jadework.dao.inter.TaskDaoInter;
import com.jadework.model.Task;
import com.jadework.service.TaskService;
import org.codehaus.jackson.map.ObjectMapper;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * @Copyright: Copyright (c) 2016,${year},
 * @Description: ${todo}
 */

@RestController
@RequestMapping("/task")
public class CURDTaskController {
    private static final Logger logger = LoggerFactory.getLogger(CURDTaskController.class);


    @Resource
    HttpServletRequest request;
    @Resource(name = "uniScheduler")
    Scheduler scheduler;
    @Resource
    TaskDaoInter taskDao;
    @Resource
    TaskService taskService;

    @RequestMapping("/query_all")
    public String queryAllTask(){
        logger.info("query_all_tasks from Database, && send JSON to frontend");

        String json = "";
        try {
            json = new ObjectMapper().writeValueAsString(taskDao.queryAllTask());

            //json = new ObjectMapper().writeValueAsString(new User("zhang","pioneer"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    @RequestMapping("/insert")
    public String insertTask(){

        Task task = getTaskByRequest();

        int status = taskDao.insertTask(task);

        try{
            if(scheduler.isStarted())
                taskService.scheduleAddOneJobWithTrigger(scheduler, task);
        }catch (SchedulerException e){
            e.printStackTrace();
        }

        logger.warn("--/task/insert--{}:",status);


        if(status == 1)
            return "Insert Success!";
        else
            return "Insert failure!";
    }

    @RequestMapping("/delete")
    public String deleteTask(){
        Task task = getTaskByRequest();
        int status = taskDao.deleteTask(task);
        try{
            if(scheduler.isStarted())
                taskService.scheduleRemoveOneJob(scheduler, task);
        }catch (SchedulerException e){
            e.printStackTrace();
        }

        logger.warn("--/task/delete--{}:",status);


        if(status == 1)
            return "Delete Success!";
        else
            return "Delete failure!";
    }

    @RequestMapping("/update")
    public String updateTask(){
        Task task = getTaskByRequest();
        int status = taskDao.updateTask(task);
        try{
            if(scheduler.isStarted())
                taskService.scheduleUpdateOneJobWithTrigger(scheduler, task);
        }catch (SchedulerException e){
            e.printStackTrace();
        }

        logger.warn("--/task/update--{}:",status);

        if(status == 1)
            return "Update Success!";
        else
            return "Update failure!";
    }




    public Task getTaskByRequest(){
        return new Task(
                request.getParameter("task_group"),
                request.getParameter("job_name"),
                request.getParameter("trigger_name"),
                request.getParameter("cron_expression"),
                request.getParameter("app_address"));
    }


}
