package com.unisys.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.unisys.api.entities.Job;
import com.unisys.api.entities.ParentJob;
import com.unisys.api.entities.Task;
import com.unisys.api.services.JobService;

@SpringBootTest
@Transactional
class UnisysTestApiApplicationTests {

	@Autowired
	private JobService jobService;

	@Test
	void addingAndGettingJob_1() {

		List<Task> tasks = new ArrayList<>();
		tasks.add(new Task(1, "Task 1", 5, true, null, null));
		tasks.add(new Task(2, "Task 2", 3, false, null, null));
		Job job1 = new Job(1, "Job 1", true, null, tasks); // Raiz
		jobService.saveJob(job1);
		Optional<Job> returnedJob = jobService.getJobById(job1.getId());

		assertTrue(!returnedJob.isEmpty() && compareTwoJobs(job1, returnedJob.get()));
	}

	@Test
	void addingAndGettingJob_2() {

		List<Task> tasks = new ArrayList<>();
		tasks.add(new Task(1, "Task 1", 5, true, null, null));
		tasks.add(new Task(2, "Task 2", 3, false, null, null));
		Job job1 = new Job(1, "Job 1", true, null, tasks); // Raiz
		jobService.saveJob(job1);

		List<Task> tasks2 = new ArrayList<>();
		tasks2.add(new Task(3, "Task 3", 14, true, null, null));
		tasks2.add(new Task(4, "Task 4", 18, false, null, null));
		ParentJob parentJob = new ParentJob(1, "Job 1", true);
		Job job2 = new Job(2, "Job 2", true, parentJob, tasks2);
		jobService.saveJob(job2);

		Optional<Job> returnedJob = jobService.getJobById(job2.getId());

		assertTrue(!returnedJob.isEmpty() && compareTwoJobs(job2, returnedJob.get()));
	}

	@Test
	void addingInvalidJob() { // self-dependency

		List<Task> tasks1 = new ArrayList<>();
		tasks1.add(new Task(1, "Task 1", 5, true, null, null));
		tasks1.add(new Task(2, "Task 2", 3, false, null, null));
		Job job1 = new Job(1, "Job 1", true, null, tasks1); // Raiz
		jobService.saveJob(job1);

		List<Task> tasks2 = new ArrayList<>();
		tasks2.add(new Task(3, "Task 3", 14, true, null, null));
		tasks2.add(new Task(4, "Task 4", 18, false, null, null));
		ParentJob parentJob2 = new ParentJob(1, "Job 1", true);
		Job job2 = new Job(2, "Job 2", true, parentJob2, tasks2);
		jobService.saveJob(job2);

		// Adição inválida: self-dependency
		List<Task> tasks3 = new ArrayList<>();
		tasks3.add(new Task(5, "Task 5", 4, true, null, null));
		tasks3.add(new Task(6, "Task 6", 7, false, null, null));
		ParentJob parentJob3 = new ParentJob(2, "Job 2", true);
		Job job3 = new Job(3, "Job 1", true, parentJob3, tasks3);
		Job returnedJob = jobService.saveJob(job3);

		assertTrue(returnedJob.getId() == null);
	}

	@Test
	void addingAndDeletingJob() {

		List<Task> tasks = new ArrayList<>();
		tasks.add(new Task(1, "Task 1", 5, true, null, null));
		tasks.add(new Task(2, "Task 2", 3, false, null, null));
		Job job1 = new Job(1, "Job 1", true, null, tasks); // Raiz
		jobService.saveJob(job1);

		jobService.deleteJobById(job1.getId());
		Optional<Job> returnedJob = jobService.getJobById(job1.getId());

		assertTrue(returnedJob == null);
	}

	@Test
	void addingAndUpdatingJob() {

		List<Task> tasks = new ArrayList<>();
		tasks.add(new Task(1, "Task 1", 5, true, null, null));
		tasks.add(new Task(2, "Task 2", 3, false, null, null));
		Job job1 = new Job(1, "Job 1", true, null, tasks); // Raiz
		jobService.saveJob(job1);

		Job job2 = new Job(1, "Job 1 Updated", false, null, null);
		jobService.updateJobById(job1.getId(), job2);
		Optional<Job> returnedJob = jobService.getJobById(job1.getId());

		assertEquals(returnedJob.get().getName(), "Job 1 Updated");
	}

	Boolean compareTwoJobs(Job a, Job b) {
		if (a == null && b == null) {
			return true;
		}
		if (a != null && b != null && a.getActive() == b.getActive() && a.getId() == b.getId()
				&& a.getName().compareTo(b.getName()) == 0) {
			for (int i = 0; i < a.getTasks().size() - 1; i++) {
				if (!compareTwoTasks(a.getTasks().get(i), a.getTasks().get(i))) {
					return false;
				}
			}
			if (!compareTwoParentJob(a.getParentJob(), b.getParentJob())) {
				return false;
			}
			return true;
		}
		return false;
	}

	Boolean compareTwoTasks(Task a, Task b) {
		if (a == null && b == null) {
			return true;
		}
		if (a.getCompleted() == b.getCompleted() && a.getId() == b.getId() && a.getName().compareTo(b.getName()) == 0
				&& a.getWeight() == b.getWeight()) {
			return true;
		}
		return false;
	}

	Boolean compareTwoParentJob(ParentJob a, ParentJob b) {
		if (a == null && b == null) {
			return true;
		}
		if (a.getActive() == b.getActive() && a.getId() == b.getId() && a.getName().compareTo(b.getName()) == 0) {
			return true;
		}
		return false;
	}

}
