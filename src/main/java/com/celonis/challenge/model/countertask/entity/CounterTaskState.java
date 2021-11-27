package com.celonis.challenge.model.countertask.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Access(AccessType.FIELD)
@Table(name = "COUNTER_TASK_STATE")
public class CounterTaskState extends BaseEntity implements Comparable {

    @ManyToOne
    @JoinColumn(name = "COUNTER_TASK_ID")
    private CounterTask counterTask;


    @Column(name = "COUNTER_STATE")
    private Integer counterState;

    public CounterTaskState() {
        //hibernate
    }

    public CounterTaskState(CounterTask counterTask) {
        this.counterTask = counterTask;
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

    @Override
    public int compareTo(Object o) {
        CounterTaskState counterTaskState = (CounterTaskState) o;
        return counterState.compareTo(counterTaskState.counterState);
    }

}
