package com.cln.challenge.model.plantgraphtask.repository;

import com.cln.challenge.model.plantgraphtask.entity.PlantGraphTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlantGraphTaskRepository extends JpaRepository<PlantGraphTask, UUID>, JpaSpecificationExecutor<PlantGraphTask> {

}
