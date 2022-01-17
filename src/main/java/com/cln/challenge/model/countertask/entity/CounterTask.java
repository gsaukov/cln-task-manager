package com.cln.challenge.model.countertask.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Access(AccessType.FIELD)
@Table(name = "COUNTER_TASK")
public class CounterTask extends BaseEntity {

    @Column(name = "COUNTER_TASK_NAME")
    private String counterTaskName;

    @Column(name = "X")
    private Integer x;

    @Column(name = "Y")
    private Integer y;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private CounterTaskStatus status;

    protected CounterTask() {
        //hibernate
    }

    public CounterTask(String counterTaskName, Integer x, Integer y) {
        this.counterTaskName = counterTaskName;
        this.x = x;
        this.y = y;
        this.status = CounterTaskStatus.ACTIVE;
    }

    public String getCounterTaskName() {
        return counterTaskName;
    }

    public void setCounterTaskName(String counterTaskName) {
        this.counterTaskName = counterTaskName;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public CounterTaskStatus getStatus() {
        return status;
    }

    public void setStatus(CounterTaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CounterTask counterTask = (CounterTask) o;
        return Objects.equals(getId(), counterTask.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
