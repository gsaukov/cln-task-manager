package com.cln.challenge.countertask;

import com.cln.challenge.controllers.countertask.CounterTaskController;
import com.cln.challenge.controllers.countertask.CounterTaskModel;
import com.cln.challenge.model.countertask.repository.CounterTaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CounterTaskCRUDTest {

    @Autowired
    CounterTaskRepository repository;

    @Autowired
    CounterTaskController taskController;

    @Test
    public void createNew_List_Get() {
        //given
        var taskName = "counterTestTaskName";
        var X = 100;
        var Y = 200;
        var task = new CounterTaskModel();
        task.setName(taskName);
        task.setX(X);
        task.setY(Y);

        //when
        var createdTask = taskController.createTask(task);

        //then
        assertNotNull(repository.findById(createdTask.getId()).get());
        assertEquals(taskName, createdTask.getName());
        assertEquals(X, createdTask.getX());
        assertEquals(Y, createdTask.getY());

        var taskList = taskController.listTasks();
        assertFalse(taskList.isEmpty());
        assertTrue(taskListHasTask(taskList, createdTask.getId()));

        var getTask = taskController.getTask(createdTask.getId());
        assertNotNull(getTask);
        assertEquals(taskName, getTask.getName());
    }

    @Test
    public void createNew_Delete() {
        //given
        var task = createTask();

        //when
        var createdTask = taskController.getTask(task.getId());

        //then
        assertNotNull(createdTask);

        //when
        taskController.deleteTask(task.getId());

        //then
        assertTrue(repository.findById(createdTask.getId()).isEmpty());
    }

    private CounterTaskModel createTask() {
        var task = new CounterTaskModel();
        task.setName("testTaskName");
        task.setX(0);
        task.setY(100);
        return taskController.createTask(task);
    }


    private boolean taskListHasTask(List<CounterTaskModel> tasks, UUID taskId) {
        for (var task : tasks) {
            if (task.getId().equals(taskId)) {
                return true;
            }
        }
        return false;
    }

}
