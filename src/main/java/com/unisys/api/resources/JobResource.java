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

import com.unisys.api.entities.Job;
import com.unisys.api.services.JobService;

@RestController
@RequestMapping("/jobs")
public class JobResource {

	@Autowired
	private JobService jobService;

	@GetMapping
	public ResponseEntity<List<Job>> getJobs(
			@RequestParam(required = false, defaultValue = "false") Boolean $sortByWeight) {
		List<Job> jobList = jobService.getJobs($sortByWeight);
		return ResponseEntity.ok().body(jobList);
	}

	@PostMapping
	public ResponseEntity<Job> saveJob(@RequestBody Job job) throws URISyntaxException {
		Job newJob = jobService.saveJob(job);
		return ResponseEntity.created(new URI("/jobs/" + newJob.getId())).body(newJob);
	}

	@GetMapping(path = { "/{id}" })
	public ResponseEntity<Optional<Job>> getJobById(@PathVariable Integer id) {
		Optional<Job> job = jobService.getJobById(id);
		if (job == null)
			return ResponseEntity.status(404).body(job);
		return ResponseEntity.ok().body(job);
	}

	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<Boolean> deleteJobById(@PathVariable Integer id) {
		Boolean deleted = jobService.deleteJobById(id);
		return ResponseEntity.ok().body(deleted);
	}

	@PutMapping(path = { "/{id}" })
	public ResponseEntity<Boolean> updateJobById(@PathVariable Integer id, @RequestBody Job job)
			throws URISyntaxException {
		Boolean updated = jobService.updateJobById(id, job);
		return ResponseEntity.ok().body(updated);
	}
}
