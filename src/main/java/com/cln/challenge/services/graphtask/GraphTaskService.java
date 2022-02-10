package com.cln.challenge.services.graphtask;

import com.cln.challenge.controllers.graphtask.GraphTaskModel;
import com.cln.challenge.model.graphtask.entity.GraphTask;

import java.util.List;
import java.util.UUID;

public interface GraphTaskService {

    GraphTask getTask(UUID taskId);

    List<GraphTask> listTasks();

    GraphTask createTask(GraphTaskModel ctTask);

    void delete(UUID taskId);

    void executeTask(UUID taskId);

    void stopTask(UUID taskId);

}
