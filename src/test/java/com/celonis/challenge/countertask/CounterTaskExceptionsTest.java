package com.celonis.challenge.countertask;

import com.celonis.challenge.controllers.countertask.CounterTaskController;
import com.celonis.challenge.model.countertask.repository.CounterTaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CounterTaskExceptionsTest {

    private static final UUID DO_NOT_EXIST = UUID.randomUUID();

    @Autowired
    CounterTaskRepository repository;

    @Autowired
    CounterTaskController taskController;

    @Test
    public void getTaskNotExists() {
        //when
        assertThrows(NoSuchElementException.class, () -> taskController.getTask(DO_NOT_EXIST));
    }

    @Test
    public void executeTaskNotExists() {
        //when
        assertThrows(NoSuchElementException.class, () -> taskController.executeTask(DO_NOT_EXIST));
    }

    @Test
    public void stopTaskNotExists() {
        //when
        assertThrows(NoSuchElementException.class, () -> taskController.stopTask(DO_NOT_EXIST));
    }

    @Test
    public void deleteTaskNotExists() {
        //when
        assertThrows(NoSuchElementException.class, () -> taskController.deleteTask(DO_NOT_EXIST));
    }

}
