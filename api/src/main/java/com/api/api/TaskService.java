package com.api.api;

import com.api.api.Task;
import com.api.api.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional
    public Task criarTarefa(String queueName, Map<String, Object> payload) {
        Task task = new Task();
        task.setQueueName(queueName);
        task.setPayload(payload);
        task.setStatus("pending");
        return taskRepository.save(task);
    }

    public Optional<Task> buscarPorId(UUID id) {
        return taskRepository.findById(id);
    }

    @Transactional
    public Optional<Task> pegarProximaTarefa(String queueName) {
        Optional<Task> taskOpt = taskRepository.findNextPendingTask(queueName);
        taskOpt.ifPresent(task -> {
            task.setStatus("processing");
            task.setAttempts(task.getAttempts() + 1);
            taskRepository.save(task);
        });
        return taskOpt;
    }

    @Transactional
    public Optional<Task> atualizarStatus(UUID id, String novoStatus) {
        return taskRepository.findById(id).map(task -> {
            task.setStatus(novoStatus);
            return taskRepository.save(task);
        });
    }
}