package com.material.design.widget.materailactivity.dbdemo.model;

import java.io.Serializable;

/**
 * Created by chawei on 2017/2/21.
 */

public class Student implements Serializable {

    private String uid;
    private String name;
    private String sex;
    private String age;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
