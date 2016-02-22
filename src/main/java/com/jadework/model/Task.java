package com.jadework.model;

import java.io.Serializable;

/**
 * @Copyright: Copyright (c) 2016,${year},
 * @Description: ${todo}
 */

public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    private String task_group;
    private String job_name;
    private String trigger_name;
    private String cron_expression;
    private String app_address;

    public String getTask_group() {
        return task_group;
    }

    public void setTask_group(String task_group) {
        this.task_group = task_group;
    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public String getTrigger_name() {
        return trigger_name;
    }

    public void setTrigger_name(String trigger_name) {
        this.trigger_name = trigger_name;
    }

    public String getCron_expression() {
        return cron_expression;
    }

    public void setCron_expression(String cron_expression) {
        this.cron_expression = cron_expression;
    }

    public String getApp_address() {
        return app_address;
    }

    public void setApp_address(String app_address) {
        this.app_address = app_address;
    }

    public Task() {
    }

    public Task(String task_group, String job_name, String trigger_name, String cron_expression, String app_address) {
        this.task_group = task_group;
        this.job_name = job_name;
        this.trigger_name = trigger_name;
        this.cron_expression = cron_expression;
        this.app_address = app_address;
    }

    @Override
    public String toString() {
        return "Task{" +
                "task_group='" + task_group + '\'' +
                ", job_name='" + job_name + '\'' +
                ", trigger_name='" + trigger_name + '\'' +
                ", cron_expression='" + cron_expression + '\'' +
                ", app_address='" + app_address + '\'' +
                '}';
    }
}
