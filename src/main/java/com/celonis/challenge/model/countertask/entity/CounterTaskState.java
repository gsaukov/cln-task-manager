package com.celonis.challenge.model.countertask.entity;

import javax.persistence.*;
import java.util.Objects;

//TODO reason for this entity is te decouple task's execution state and task itself.

@Entity
@Access(AccessType.FIELD)
@Table(name = "COUNTER_TASK_STATE")
public class CounterTaskState extends BaseEntity {

    @Column(name = "COUNTER_STATE")
    private Integer counterState;

    protected CounterTaskState() {
        //hibernate
    }

    public CounterTaskState(Integer counterState) {
        this.counterState = counterState;
    }

    public Integer getCounterState() {
        return counterState;
    }

    public void setCounterState(Integer counterState) {
        this.counterState = counterState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CounterTaskState counterTaskState = (CounterTaskState) o;
        return this.getId().equals(counterTaskState.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId());
    }

}
