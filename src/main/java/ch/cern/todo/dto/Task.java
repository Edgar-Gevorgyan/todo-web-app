package ch.cern.todo.dto;

import java.time.LocalDateTime;

public record Task(Long id,
                   String name,
                   String description,
                   LocalDateTime deadline) {
}
