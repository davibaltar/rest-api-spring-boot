package com.unisys.api.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unisys.api.entities.Task;
import com.unisys.api.services.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskResource {

	@Autowired
	private TaskService taskService;

	@GetMapping
	public ResponseEntity<List<Task>> getTasks(@RequestParam(required = false, defaultValue = "") String $createdAt) {
		List<Task> taskList = taskService.getTasks($createdAt);
		return ResponseEntity.ok().body(taskList);
	}

	@PostMapping
	public ResponseEntity<Task> saveTask(@RequestBody Task task) throws URISyntaxException {
		Task newTask = taskService.saveTask(task);
		return ResponseEntity.created(new URI("/jobs/" + newTask.getId())).body(newTask);
	}

	@GetMapping(path = { "/{id}" })
	public ResponseEntity<Optional<Task>> getTaskById(@PathVariable Integer id) {
		Optional<Task> task = taskService.getTaskById(id);
		if (task.isEmpty())
			return ResponseEntity.status(404).body(task);
		return ResponseEntity.ok().body(task);
	}

	@PutMapping(path = { "/{id}" })
	public ResponseEntity<Boolean> updateTaskById(@PathVariable Integer id, @RequestBody Task task)
			throws URISyntaxException {
		Boolean updated = taskService.updateTaskById(id, task);
		return ResponseEntity.ok().body(updated);
	}

	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<Boolean> deleteTaskById(@PathVariable Integer id) {
		Boolean deleted = taskService.deleteTaskById(id);
		return ResponseEntity.ok().body(deleted);
	}
}
