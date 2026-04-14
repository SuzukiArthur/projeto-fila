package com.filas.api.service;

import com.filas.api.model.Task;
import com.filas.api.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional
    public Task criar(String queueName, String payload) {
        Task task = new Task();
        task.setQueueName(queueName);
        task.setPayload(payload);
        task.setStatus("pending");
        return taskRepository.save(task);
    }

    public Optional<Task> buscarPorId(UUID id) {
        return taskRepository.findById(id); // ajuste se o método no repository for findByid
    }

    @Transactional
    public Optional<Task> pegarProximaTarefa(String queueName) {
        Optional<Task> taskOpt = taskRepository.findByIdPendingTask(queueName);
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