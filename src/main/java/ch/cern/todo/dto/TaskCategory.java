package ch.cern.todo.dto;

import java.util.List;

public record TaskCategory(Long id,
                           String name,
                           String description,
                           List<Task> tasks) {
}
