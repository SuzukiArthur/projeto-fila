


package com.api.api;

import java.util.Map;
import java.util.UUID;

import com.api.api.Task;
import com.api.api.TaskService;

import org.springframework.web.bind.annotation.*;
<<<<<<< HEAD
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
=======
import java.util.*;
>>>>>>> b79b341a9f3a3a73b51c2cde8b3452cf90fc3e5d

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> criarTarefa(@RequestBody Map<String, Object> body) {
        String queueName = (String) body.get("queue_name");
        Object payloadObj = body.get("payload");
        Map<String, Object> payload;
        if (payloadObj instanceof Map) {
            payload = (Map<String, Object>) payloadObj;
        } else {
            return ResponseEntity.badRequest().build();
        }
        Task task = taskService.criarTarefa(queueName, payload);
        return ResponseEntity.ok(task);
    }
<<<<<<< HEAD

=======
>>>>>>> b79b341a9f3a3a73b51c2cde8b3452cf90fc3e5d
    @GetMapping("/{id}")
    public ResponseEntity<Task> buscarTarefa(@PathVariable UUID id) {
        return taskService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/next/{queueName}")
    public ResponseEntity<Task> pegarProxima(@PathVariable String queueName) {
        return taskService.pegarProximaTarefa(queueName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Task> atualizarStatus(
            @PathVariable UUID id,
            @RequestBody Map<String, String> body) {
        String novoStatus = body.get("status");
        return taskService.atualizarStatus(id, novoStatus)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}