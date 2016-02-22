package com.jadework.service;

import com.jadework.dao.TaskDao;
import com.jadework.dao.inter.TaskDaoInter;
import com.jadework.model.Task;
import com.jadework.model.User;
import com.jadework.quartz.ScheduledJob;
import org.mybatis.spring.SqlSessionTemplate;
import org.quartz.*;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.CronScheduleBuilder.cronSchedule;

/**
 * @Copyright: Copyright (c) 2016,${year},
 * @Description: ${todo}
 */

@Service
public class TaskService {

    @Resource(name = "taskDao")
    TaskDaoInter taskDao; //polymorphism


    public void scheduleAllJobs(Scheduler scheduler){

        try{
            scheduler.start();

            List<Task> list = taskDao.queryAllTask();

            for(Task task : list){

                TriggerKey triggerKey = TriggerKey.triggerKey(
                        task.getTrigger_name(), task.getTask_group()
                );
                CronTrigger trigger = (CronTrigger) scheduler
                        .getTrigger(triggerKey);

                if(null == trigger){//TriggerKey as uniqueKey
                    JobDetail jobDetail = newJob(ScheduledJob.class)
                            .withIdentity(task.getJob_name(), task.getTask_group())
                            .usingJobData("app_address",task.getApp_address())
                            .build();

                    trigger = newTrigger()
                            .withIdentity(task.getTrigger_name(), task.getTask_group())
                            .startNow()
                            .withSchedule(cronSchedule(task.getCron_expression()))
                            .build();
                    scheduler.scheduleJob(jobDetail, trigger);
                }else{//trigger exist, update the cronExpression
                    trigger = trigger.getTriggerBuilder()
                            .withIdentity(task.getTrigger_name(), task.getTask_group())
                            .startNow()
                            .withSchedule(cronSchedule(task.getCron_expression()))
                            .build();
                    scheduler.rescheduleJob(triggerKey, trigger);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //Scheduler中添加一个job_trigger
    public int scheduleAddOneJobWithTrigger(Scheduler scheduler, Task task){ //可能该job已有,添加其它trigger;也可能重复添加;

        try{
            if(scheduler.isStarted()){//如果Scheduler处于Started-Mode,可以添加新的Job
                if(!isContainedTrigger(scheduler, task)){//如果不包含该trigger,则可以添加
                    addTrigger(scheduler, task);
                }

            }
        }catch(SchedulerException e){
            e.printStackTrace();
        }


        return 1;//添加成功
    }
    //Scheduler中删除一个job,会删除all_job_trigger
    public int scheduleRemoveOneJob(Scheduler scheduler, Task task){
        try{
            if(scheduler.isStarted()){
                if(isContainedJob(scheduler, task)){//包含该job,可以删除
                    deleteJob(scheduler, task);
                }
            }
        }catch (SchedulerException e){
            e.printStackTrace();
        }
        return 1;
    }
    //Scheduler中更新一个job,reScheduleJob(),job_trigger
    public int scheduleUpdateOneJobWithTrigger(Scheduler scheduler, Task task){
        try{
            if(scheduler.isStarted()){
                if(isContainedTrigger(scheduler, task)){
                    updateTrigger(scheduler, task);
                }
            }
        }catch (SchedulerException e){
            e.printStackTrace();
        }
        return 1;
    }

    //判断函数: 判断Scheduler中是否有该trigger(由triggerKey可作为唯一主键决定)
    public boolean isContainedTrigger(Scheduler scheduler, Task task){
        boolean result = true;

        try{
            TriggerKey triggerKey = TriggerKey.triggerKey(
                    task.getTrigger_name(), task.getTask_group()
            );//从Task中得到triggerKey
            CronTrigger trigger = (CronTrigger) scheduler
                    .getTrigger(triggerKey);//从Scheduler中获取该trigger,通过triggerKey

            if(null == trigger){
                result = false;
            }

        }catch(SchedulerException e){
            e.printStackTrace();
        }

        return result;//不包含返回false,包含返回true
    }

    //判断函数: 判断Scheduler中是否有该job
    public boolean isContainedJob(Scheduler scheduler, Task task){
        boolean result = true;

        try{
            JobKey jobKey = JobKey.jobKey(task.getJob_name(),task.getTask_group());
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if(null == jobDetail){
                result = false;
            }
        }catch (SchedulerException e){
            e.printStackTrace();
        }
        return result;
    }

    //添加函数: Scheduler中添加job_trigger函数
    public int addTrigger(Scheduler scheduler, Task task){

        try{
            JobDetail jobDetail = newJob(ScheduledJob.class)
                    .withIdentity(task.getJob_name(), task.getTask_group())
                    .usingJobData("app_address",task.getApp_address())
                    .build();

            CronTrigger trigger = newTrigger()
                    .withIdentity(task.getTrigger_name(), task.getTask_group())
                    .startNow()
                    .withSchedule(cronSchedule(task.getCron_expression()))
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
        }catch (SchedulerException e){
            e.printStackTrace();
        }

        return 1;
    }

    //删除函数: Scheduler中删除job
    public int deleteJob(Scheduler scheduler, Task task){
        try{
            JobKey jobKey = JobKey.jobKey(task.getJob_name(),task.getTask_group());
            scheduler.deleteJob(jobKey);

        }catch (SchedulerException e){
            e.printStackTrace();
        }

        return 1;
    }

    //更新函数: Scheduler中更新job_trigger
    public int updateTrigger(Scheduler scheduler, Task task){
        try{
            TriggerKey triggerKey = TriggerKey.triggerKey(
                    task.getTrigger_name(), task.getTask_group()
            );//从Task中得到triggerKey
            CronTrigger trigger = (CronTrigger) scheduler
                    .getTrigger(triggerKey);//从Scheduler中获取该trigger,通过triggerKey

            trigger = trigger.getTriggerBuilder()
                    .withIdentity(task.getTrigger_name(), task.getTask_group())
                    .startNow()
                    .withSchedule(cronSchedule(task.getCron_expression()))
                    .build();
            scheduler.rescheduleJob(triggerKey, trigger);


        }catch (SchedulerException e){
            e.printStackTrace();
        }

        return 1;
    }


}
