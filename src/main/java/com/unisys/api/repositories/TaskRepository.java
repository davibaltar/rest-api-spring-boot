package com.unisys.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unisys.api.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

	List<Task> findTaskByJobId(Integer id);

}
