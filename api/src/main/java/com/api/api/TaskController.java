package com.filas.api.controller;
import com.filas.api.model.Task;
import com.filas.api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    @PostMapping
    public ResponseEntity<Task> criarTarefa(@RequestBody Map<String, Object> body) {
        String queueName = (String) body.get("queue_name");
        Map<String, Object> payload = (Map<String, Object>) body.get("payload");
        Task task = taskService.criarTarefa(queueName, payload);
        return ResponseEntity.ok(task);}
    @GetMapping("/{id}")
    public ResponseEntity<Task> buscarTarefa(@PathVariable UUID id) {
        return taskService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());}
    @GetMapping("/next/{queueName}")
    public ResponseEntity<Task> pegarProxima(@PathVariable String queueName) {
        return taskService.pegarProximaTarefa(queueName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());}
    @PatchMapping("/{id}")
    public ResponseEntity<Task> atualizarStatus(
            @PathVariable UUID id,
            @RequestBody Map<String, String> body) {
        String novoStatus = body.get("status");
        return taskService.atualizarStatus(id, novoStatus)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());}}