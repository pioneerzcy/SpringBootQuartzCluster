package com.jadework.dao.inter;

import com.jadework.model.Task;
import java.util.List;

/**
 * @Copyright      Copyright (c) 2016,2016
 * @Title
 * @Description
 *
 *
 * @author         Jade ZHANG E-mail:pioneer_zcy@163.cpm
 * @date           16/02/21
 * @version        1.0
 */
public interface TaskDaoInter {

    public List<Task> queryAllTask();//查询所有task

    public int insertTask(Task task);//插入一条task

    public int deleteTask(Task task);//删除一条task

    public int updateTask(Task task);//更新一条task



}
