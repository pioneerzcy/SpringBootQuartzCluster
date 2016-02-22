package com.jadework.model;


import java.io.Serializable;
/**
 * @Copyright: Copyright (c) 2016,${year},
 * @Description: ${todo}
 */


public class User implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer user_id;
    private String user_name;
    private String user_email;

    public User() {
        super();
    }

    public User(String user_name, String user_email) {
        this.user_name = user_name;
        this.user_email = user_email;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_email='" + user_email + '\'' +
                '}';
    }
}

