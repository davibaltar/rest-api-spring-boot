package com.unisys.api.services;

import java.util.List;
import java.util.Optional;

import com.unisys.api.entities.Task;

public interface TaskService {

	public List<Task> getTasks(String $createdAt);

	public Optional<Task> getTaskById(Integer id);

	public Task saveTask(Task task);

	public Boolean deleteTaskById(Integer id);

	public Boolean updateTaskById(Integer id, Task newTask);
}
