package com.celonis.challenge.model.countertask.repository;

import com.celonis.challenge.model.countertask.entity.CounterTaskState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterTaskStateRepository extends JpaRepository<CounterTaskState, String>, JpaSpecificationExecutor<CounterTaskState> {

}
