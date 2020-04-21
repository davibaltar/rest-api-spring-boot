package com.unisys.api.services.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unisys.api.entities.Task;
import com.unisys.api.repositories.TaskRepository;
import com.unisys.api.services.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public Task saveTask(Task task) {
		return taskRepository.save(task);
	}

	@Override
	public List<Task> getTasks(String $createdAt) {
		if ($createdAt.equals("")) {
			return taskRepository.findAll();
		} else {
			return taskRepository.findAll().stream()
					.filter(task -> task.getCreatedAt()
							.compareTo(LocalDate.parse($createdAt, DateTimeFormatter.ofPattern("yyyy-MM-dd"))) == 0)
					.collect(Collectors.toList());
		}
	}

	@Override
	public Optional<Task> getTaskById(Integer id) {
		try {
			return taskRepository.findById(id);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Boolean updateTaskById(Integer id, Task newTask) {
		Optional<Task> task = taskRepository.findById(id);
		if (task.isEmpty()) { // Verificando se Task já existe
			taskRepository.save(newTask);
		} else {
			newTask.setJob(task.get()._getJob()); // Mantendo referencia ao Job
			taskRepository.save(newTask);
		}
		return true;
	}

	@Override
	public Boolean deleteTaskById(Integer id) {
		taskRepository.deleteById(id);
		Optional<Task> task = taskRepository.findById(id);
		if (!task.isEmpty()) // Verificando se Task foi excluída com sucesso.
			return false;
		return true;
	}

}
