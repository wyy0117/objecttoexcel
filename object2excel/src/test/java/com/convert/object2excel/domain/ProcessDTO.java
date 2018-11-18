package com.convert.object2excel.domain;

import java.util.Date;

/**
 * @Author: wyy
 * @Date: 18-11-16 下午3:51
 */
public class ProcessDTO {
    private String name;
    private Date planStartTime;
    private Date planEndTime;
    private Date actualStartTime;
    private Date actualEndTime;

    public ProcessDTO() {
    }

    public ProcessDTO(String name, Date planStartTime, Date planEndTime, Date actualStartTime, Date actualEndTime) {
        this.name = name;
        this.planStartTime = planStartTime;
        this.planEndTime = planEndTime;
        this.actualStartTime = actualStartTime;
        this.actualEndTime = actualEndTime;
    }

    public String getName() {
        return name;
    }

    public ProcessDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Date getPlanStartTime() {
        return planStartTime;
    }

    public ProcessDTO setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
        return this;
    }

    public Date getPlanEndTime() {
        return planEndTime;
    }

    public ProcessDTO setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
        return this;
    }

    public Date getActualStartTime() {
        return actualStartTime;
    }

    public ProcessDTO setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
        return this;
    }

    public Date getActualEndTime() {
        return actualEndTime;
    }

    public ProcessDTO setActualEndTime(Date actualEndTime) {
        this.actualEndTime = actualEndTime;
        return this;
    }

    @Override
    public String toString() {
        return "ProcessDTO{" +
                "name='" + name + '\'' +
                ", planStartTime=" + planStartTime +
                ", planEndTime=" + planEndTime +
                ", actualStartTime=" + actualStartTime +
                ", actualEndTime=" + actualEndTime +
                '}';
    }
}
