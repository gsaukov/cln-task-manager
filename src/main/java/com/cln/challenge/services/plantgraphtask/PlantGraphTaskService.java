package com.cln.challenge.services.plantgraphtask;

import com.cln.challenge.controllers.plantgraphtask.PlantGraphTaskModel;
import com.cln.challenge.model.plantgraphtask.entity.PlantGraphTask;

import java.util.List;
import java.util.UUID;

public interface PlantGraphTaskService {

    PlantGraphTask getTask(UUID taskId);

    List<PlantGraphTask> listTasks();

    PlantGraphTask createTask(PlantGraphTaskModel model);

    void delete(UUID taskId);

    void executeTask(UUID taskId);

    void stopTask(UUID taskId);

}
