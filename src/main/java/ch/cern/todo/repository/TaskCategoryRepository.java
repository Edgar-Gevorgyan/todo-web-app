package ch.cern.todo.repository;

import ch.cern.todo.entity.TaskCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskCategoryRepository extends JpaRepository<TaskCategoryEntity, Long> {
}
