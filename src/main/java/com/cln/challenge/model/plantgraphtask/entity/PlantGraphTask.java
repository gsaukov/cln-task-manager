package com.cln.challenge.model.plantgraphtask.entity;

import com.cln.challenge.model.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Access(AccessType.FIELD)
@Table(name = "PLANT_GRAPH_TASK")
public class PlantGraphTask extends BaseEntity {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlantGraphTask counterTask = (PlantGraphTask) o;
        return Objects.equals(getId(), counterTask.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
