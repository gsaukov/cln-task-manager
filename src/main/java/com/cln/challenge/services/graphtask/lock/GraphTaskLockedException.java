package com.cln.challenge.services.graphtask.lock;

import com.cln.challenge.exceptions.TaskExecutionException;

public class GraphTaskLockedException extends TaskExecutionException {

    public GraphTaskLockedException(String message) {
        super(message);
    }

}
