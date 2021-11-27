package com.celonis.challenge.model.countertask.entity;

import javax.persistence.*;
import java.util.*;

//TODO the idea is to have immutable task, and only increment state object.

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

    @JoinColumn(name = "COUNTER_STATE_ID", referencedColumnName = "ID")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private CounterTaskState counterTaskState;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    protected CounterTask() {
        //hibernate
    }

    public CounterTask(String counterTaskName, Integer x, Integer y) {
        this.counterTaskName = counterTaskName;
        this.x = x;
        this.y = y;
        this.status = Status.ACTIVE;
        this.counterTaskState = new CounterTaskState(y - x);
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

    public Integer getY() {
        return y;
    }

    public CounterTaskState getCounterTaskState() {
        return counterTaskState;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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
        return Objects.hash(getId().hashCode());
    }

    public enum Status {
        ACTIVE,
        RUNNING;
    }

}
