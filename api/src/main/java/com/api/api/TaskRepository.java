package com.filas.api.repository;

import com.filas.api.model.Task;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;

import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = """
        SELECT * FROM tasks
        WHERE queue_name = :queueName
          AND status = 'pending'
        ORDER BY created_at ASC
        LIMIT 1
        FOR UPDATE SKIP LOCKED
        """, nativeQuery = true)
    Optional<Task> findNextPendingTask(String queueName);
}