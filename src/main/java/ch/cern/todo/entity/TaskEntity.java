package ch.cern.todo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TASKS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TASK_ID")
    private Long id;
    @Column(name = "TASK_NAME", nullable = false, length = 100)
    private String name;
    @Column(name = "TASK_DESCRIPTION", length = 500)
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DEADLINE", nullable = false)
    private LocalDateTime deadline;
}