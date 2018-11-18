package com.convert.object2excel.domain;

import java.util.Date;

/**
 * @Author: wyy
 * @Date: 2018/11/18 20:24
 */
public class UserDTO {

    private String name;
    private boolean gender;
    private double height;
    private Date birthday;

    public UserDTO() {
    }

    public UserDTO(String name, boolean gender, double height, Date birthday) {
        this.name = name;
        this.gender = gender;
        this.height = height;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                ", height=" + height +
                ", birthday=" + birthday +
                '}';
    }
}
