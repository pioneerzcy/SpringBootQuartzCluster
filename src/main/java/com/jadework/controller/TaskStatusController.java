package com.jadework.controller;


import com.jadework.service.TaskService;
import org.quartz.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;

/**
 * @Copyright      Copyright (c) 2016,${year},
 * @Title
 * @Description 控制Scheduler的开启\关闭\暂停...
 *
 *
 * @author         Jade ZHANG E-mail:pioneer_zcy@163.cpm
 * @date           16/02/17
 * @version        Enter version here...
 */
@RestController
public class TaskStatusController {

    @Resource
    HttpServletRequest request;
    @Resource(name = "uniScheduler")
    Scheduler scheduler;
    @Resource
    TaskService taskService;

    /**
     *  开启定时任务
     * @return
     */
    @RequestMapping("/startup")
    public String startup() {

        String message = "startup";
        try {
            System.out.println("scheduler.isInStandbyMode():"+scheduler.isInStandbyMode());
            System.out.println("scheduler.isStarted():"+scheduler.isStarted());
            if (scheduler.isInStandbyMode()) {//Standby Mode
                scheduler.start();
                taskService.scheduleAllJobs(scheduler);
                message = "Success: The Scheduler is in Standby-Mode, is being started...";
            } else{ //Shutdown Mode
                message = "Exception: The Scheduler is in Shutdown-Mode, can't be started!";
            }

        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return message;
    }


    @RequestMapping("/standby")
    public String standby(){
        String message = "standby";
        try{
            if(scheduler.isStarted()){ //Started Mode
                scheduler.standby(); //使处于standby模式
                System.out.println("scheduler.isInStandbyMode():"+scheduler.isInStandbyMode());
                System.out.println("scheduler.isStarted():"+scheduler.isStarted());
                message = "Success: The Scheduler is in Started-Mode, is making it in Standby-Mode...";
            }else if (scheduler.isInStandbyMode()){// Standby Mode
                message = "Exception: The Scheduler is in Standby-Mode, can't be made in Standby-Mode again!";
            }else{//Shutdown Mode
                message = "Exception: The Scheduler is in Shutdown-Mode, can't be made in Standby-Mode!";
            }


        }catch (SchedulerException e){
            e.printStackTrace();
        }

        return message;
    }

//    @RequestMapping("/restart") //该功能是否是累赘? 比如说不通过UI直接在数据中添加任务,可以通过Startup再次开启
//    public String restart(){
//        String message = "restart";
//        try {
//            if (scheduler.isStarted()) { //Started Mode
//                message = "Exception: The Scheduler is in Started-Mode, can't be restart again!";
//            } else if (scheduler.isInStandbyMode()) {//Standby Mode
//                scheduler.start();
//                message = "Success: The Scheduler is in Standby-Mode, is being restart...";
//            } else{ //Shutdown Mode
//                message = "Exception: The Scheduler is in Shutdown-Mode, can't be restart!";
//            }
//
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
//
//        return message;
//    }

    @RequestMapping("/shutdown")
    public String shutdown(){
        String message = "shutdown";
        try{
            if(scheduler.isStarted()){//Started Mode
                scheduler.shutdown(false);//true:等任务结束后停止;default\false:立即停止但等任务执行完;
                message = "Success: The Scheduler is in Started-Mode, is being shutdown...";
            }else if(scheduler.isInStandbyMode()){//Standby-Mode
                scheduler.shutdown(false);
                message = "Success: The Scheduler is in Standby-Mode, is being shutdown...";
            }else{//Shutdown-Mode
                message = "Exception: The Scheduler is in Shutdown-Mode, can't be shutdown again!";
            }

        }catch (SchedulerException e){
            e.printStackTrace();
        }

        return message;

    }


    /** 有必要开pause/resume这个接口吗? 谁会用这种接口? 用户操作不方便,单从Manager角度也不需要该接口:
     * 执行流程:
     * 1\在数据库中配置好
     * 2\从数据库中读取->Startup ->Standby ->ReStart ->Shutdown
     *
     * 3\添加\删除\修改 ->更改数据库
     * 4\添加\删除\修改 ->更改Scheduler
     * or 最狠的方法/3&4 添加\删除\修改 ->更改数据库
     * ->Scheduler.Started-Mode ->Scheduler.clear() ->Scheduler.scheduledJobs
     *
     *
     * 通过scheduler的pauseJob(JobKey)\pauseTrigger(TriggerKey)
     * 第一种:一个Job可能对应多个Trigger,都会停止;
     * 第二种:一个Trigger仅注册到一个Job,停止该Job中该Trigger任务;
     * param - 需要task_group + job_name 停止多个
     * param - 需要task_group + trigger_name 停止单一
     * @return
     */
    @RequestMapping("/task/pause/job")
    public String pauseTaskByJob(){

        return "";
    }
    @RequestMapping("/task/pause/trigger")
    public String pauseTaskByTrigger(){

        return "";
    }
}




