package com.cln.challenge.services.plantgraphtask.lock;

import com.cln.challenge.exceptions.TaskExecutionException;

public class PlantGraphTaskLockedException extends TaskExecutionException {

    public PlantGraphTaskLockedException(String message) {
        super(message);
    }

}
