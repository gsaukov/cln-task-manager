package com.cln.challenge.model.graphtask.repository;

import com.cln.challenge.model.graphtask.entity.GraphTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GraphTaskRepository extends JpaRepository<GraphTask, UUID>, JpaSpecificationExecutor<GraphTask> {

}
