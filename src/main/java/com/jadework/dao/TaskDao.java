package com.jadework.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.stereotype.Component;

import com.jadework.dao.inter.TaskDaoInter;
import com.jadework.model.Task;

/**
 * @Copyright      Copyright (c) 2016,${year},
 * @Title
 * @Description
 *
 *
 * @author         Jade ZHANG E-mail:pioneer_zcy@163.cpm
 * @date           16/02/21
 * @version        Enter version here...
 */
@Component
public class TaskDao implements TaskDaoInter {
    @Resource(name = "SQL_SESSION")
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<Task> queryAllTask() {
        return sqlSessionTemplate.selectList("com.jadework.model.Task.queryAllTask");
    }

    @Override
    public int insertTask(Task task) {
        return sqlSessionTemplate.insert("com.jadework.model.Task.insertTask", task);
    }

    @Override
    public int deleteTask(Task task) {
        return sqlSessionTemplate.delete("com.jadework.model.Task.deleteTask", task);
    }

    @Override
    public int updateTask(Task task) {
        return sqlSessionTemplate.update("com.jadework.model.Task.updateTask", task);
    }
}


