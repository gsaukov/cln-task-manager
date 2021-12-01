package com.celonis.challenge.countertask;

import com.celonis.challenge.controllers.countertask.CounterTaskController;
import com.celonis.challenge.controllers.countertask.CounterTaskModel;
import com.celonis.challenge.model.countertask.entity.CounterTaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CounterTaskConcurrentExecutionTest {

    @Autowired
    CounterTaskController taskController;

    @Test
    public void run10_stop2_delete2_finish6() throws InterruptedException {
        //given
        var concurrentlyRunningTasks = createConcurrentlyRunningTasks(10);
        Thread.sleep(10l);

        //when
        stopRunningTasks(concurrentlyRunningTasks, 0, 2);
        deleteRunningTasks(concurrentlyRunningTasks, 3, 5);
        Thread.sleep(1000l);

        //then
        assertTaskStatuses(2, CounterTaskStatus.STOPPED);
        assertTaskStatuses(6, CounterTaskStatus.FINISHED);
    }

    private List<CounterTaskModel> createConcurrentlyRunningTasks(int num) {
        List<CounterTaskModel> concurrentlyRunningTasks = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            var task = createTask(0, 100);
            taskController.executeTask(task.getId());
            concurrentlyRunningTasks.add(task);
        }
        return concurrentlyRunningTasks;
    }

    private void stopRunningTasks(List<CounterTaskModel> tasks, int offset, int limit) {
        for (int i = offset; i < limit; i++) {
            taskController.stopTask(tasks.get(i).getId());
        }
    }

    private void deleteRunningTasks(List<CounterTaskModel> tasks, int offset, int limit) {
        for (int i = offset; i < limit; i++) {
            taskController.deleteTask(tasks.get(i).getId());
        }
    }

    private void assertTaskStatuses(int expected, CounterTaskStatus status) {
        var tasks = taskController.listTasks();
        var actualCount = tasks.stream().filter(t -> t.getStatus().equals(status.toString())).count();
        assertEquals(expected, actualCount);
    }

    private CounterTaskModel createTask(int x, int y) {
        var task = new CounterTaskModel();
        task.setName("testTaskName");
        task.setX(x);
        task.setY(y);
        return taskController.createTask(task);
    }

}
