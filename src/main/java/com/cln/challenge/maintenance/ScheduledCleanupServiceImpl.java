package com.cln.challenge.maintenance;

import com.cln.challenge.model.countertask.repository.CounterTaskRepository;
import com.cln.challenge.model.projectgenerationtask.ProjectGenerationTaskRepository;
import com.cln.challenge.services.countertask.CounterTaskService;
import com.cln.challenge.services.projectgenerationtask.ProjectGenerationTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@ConditionalOnProperty(value="clnTaskManager.taskCleanup.enabled", havingValue = "true")
public class ScheduledCleanupServiceImpl implements ScheduledCleanupService {


    @Value("${clnTaskManager.taskCleanup.createdDaysAgo}")
    private Integer createdDaysAgo;

    @Autowired
    private CounterTaskRepository counterTaskRepository;

    @Autowired
    private ProjectGenerationTaskRepository projectGenerationTaskRepository;

    @Autowired
    private CounterTaskService counterTaskService;

    @Autowired
    private ProjectGenerationTaskService projectGenerationTaskService;

    @Override
    @Scheduled(cron = "${clnTaskManager.taskCleanup.schedule}")
    public void cleanupTasks() {
        cleanupCounterTasks();
        cleanupProjectGenerationTasks();
    }

    private void cleanupCounterTasks() {
        counterTaskRepository.findAllByCreatedAtIsBefore(LocalDateTime.now().minusDays(createdDaysAgo))
                .stream().forEach(t -> counterTaskService.delete(t.getId()));
    }

    private void cleanupProjectGenerationTasks() {
        Date dateBefore = Date.from(Instant.now().minus(Duration.ofDays(createdDaysAgo)));
        projectGenerationTaskRepository.findAllByCreationDateIsBefore(dateBefore)
                .stream().forEach(t -> projectGenerationTaskService.delete(t.getId()));
    }

}
