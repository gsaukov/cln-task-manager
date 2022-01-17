package com.cln.challenge.model.countertask.repository;


import com.cln.challenge.model.countertask.entity.CounterTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface CounterTaskRepository extends JpaRepository<CounterTask, UUID>, JpaSpecificationExecutor<CounterTask> {

    List<CounterTask> findAllByCreatedAtIsBefore(LocalDateTime date); //must be indexed

}
