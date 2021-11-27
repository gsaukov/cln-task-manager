package com.celonis.challenge.model.countertask.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Access(AccessType.FIELD)
@Table(name = "COUNTER_TASK")
public class CounterTask extends BaseEntity {

    @NotNull
    @Size(min = 3, max = 128)
    @Column(name = "COUNTER_TASK_NAME")
    private String counterTaskName;

    @Positive
    @Column(name = "X")
    private Integer x;

    @Positive
    @Column(name = "Y")
    private Integer y;

    @JoinColumn(name = "COUNTER_TASK_ID")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("COUNTER_STATE DESC")
    private SortedSet<CounterTaskState> counterTaskStates;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    public CounterTask(String counterTaskName, Integer x, Integer y) {
        this.counterTaskName = counterTaskName;
        this.x = x;
        this.y = y;
        this.status = Status.ACTIVE;
    }

    public CounterTask() {
        //hibernate
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

    public SortedSet<CounterTaskState> getCounterTaskStates() {
        return counterTaskStates;
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
