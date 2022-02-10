package com.cln.challenge.model.graphtask.entity;

import com.cln.challenge.model.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Access(AccessType.FIELD)
@Table(name = "GRAPH_TASK")
public class GraphTask extends BaseEntity {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphTask counterTask = (GraphTask) o;
        return Objects.equals(getId(), counterTask.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
