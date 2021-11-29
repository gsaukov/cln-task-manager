package com.celonis.challenge.model.countertask.repository;


import com.celonis.challenge.model.countertask.entity.CounterTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CounterTaskRepository extends JpaRepository<CounterTask, String>, JpaSpecificationExecutor<CounterTask> {

    List<CounterTask> findAllByCreatedAtIsBefore(LocalDateTime date);

}
