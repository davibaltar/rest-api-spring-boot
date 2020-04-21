package com.unisys.api.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unisys.api.entities.Job;
import com.unisys.api.entities.ParentJob;
import com.unisys.api.entities.Task;
import com.unisys.api.repositories.JobRepository;
import com.unisys.api.repositories.TaskRepository;
import com.unisys.api.services.JobService;
import com.unisys.api.services.TaskService;

@Service
public class JobServiceImpl implements JobService {

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private TaskService taskService;

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public List<Job> getJobs(Boolean $sortByWeight) {
		List<Job> jobs = jobRepository.findAll();
		jobs.forEach(job -> {
			if (job._getIdParentJob() != null) {
				Job j = getJobById(job._getIdParentJob()).get();
				job.setParentJob(new ParentJob(j.getId(), j.getName(), j.getActive()));
			}
		});
		if ($sortByWeight) {
			Collections.sort(jobs);
			return jobs;
		}
		return jobs;
	}

	@Override
	public Optional<Job> getJobById(Integer id) {
		try {
			Optional<Job> job = jobRepository.findById(id);
			Integer idParentJob = job.get()._getIdParentJob();
			if (job != null && idParentJob != null) {
				Job j = getJobById(idParentJob).get();
				job.get().setParentJob(new ParentJob(j.getId(), j.getName(), j.getActive()));
			}
			return job;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Job saveJob(Job job) {
		/*
		 * Regras: 1. se 'parentJob' for nulo, 'job' será uma raiz 2. se 'parantJob' for
		 * encontrado e não possuir self-dependency, 'job' será adicionado como filho de
		 * 'parantJob' 3. se 'parentJob' for encontrado e possuir self-dependency),
		 * 'job' não será adicionado
		 */

		Job parentJob = new Job();
		if (job.getParentJob() != null) {
			parentJob = findParentJob(job.getParentJob());
		}

		// Verificando: self-dependency entre Jobs.
		if ((job.getParentJob() == null) || (parentJob.getId() != null && !selfDependency(parentJob, job.getName()))) {
			Job newJob = new Job(job.getId(), job.getName(), job.getActive(), job.getParentJob(), null);
			if (job.getParentJob() != null) { 	// 'job' não será uma raiz
				newJob.setIdParentJob(job.getParentJob().getId());
			}
			jobRepository.save(newJob);
			for (Task task : job.getTasks()) {
				taskRepository.save(new Task(task.getId(), task.getName(), task.getWeight(), task.getCompleted(),
						task.getCreatedAt(), newJob));
			}
			newJob.setTasks(job.getTasks());
			return newJob;
		} else { // possui self-dependency
			return new Job();
		}
	}

	@Override
	public Boolean deleteJobById(Integer id) {
		/*
		 * Esse tipo de exclusão poderia gerar uma instabilidade no BD, mas, para que
		 * seja possível realizar a exclusão de acordo com a documentação, todas as
		 * referências ao Job serão apagadas antes da exclusão do Job em si.
		 */
		if (!jobRepository.findById(id).isEmpty()) {

			// Verificando referencias nos Jobs
			List<Job> jobs = jobRepository.findJobByIdParentJob(id);
			jobs.forEach(job -> {
				job.setIdParentJob(null);
				jobRepository.save(job);
			});

			// Verificando referencias nas Tasks
			List<Task> tasks = taskRepository.findTaskByJobId(id);
			tasks.forEach(task -> {
//				task.setJob(null);							// Apagando referencia do Job
//				taskRepository.save(task);
				taskService.deleteTaskById(task.getId());	// Apagando Task
			});

			jobRepository.deleteById(id);

			Optional<Job> job = jobRepository.findById(id);
			if (!job.isEmpty()) // Verificando se Job foi excluído com sucesso.
				return false;
			return true;
		}
		return false;
	}

	@Override
	public Boolean updateJobById(Integer id, Job newJob) {
		Optional<Job> currentJob = jobRepository.findById(id);
		if (!currentJob.isEmpty()) {
			Job parentJob = new Job();
			if (newJob.getParentJob() != null) {
				parentJob = findParentJob(newJob.getParentJob());
			}
			// Verificando: self-dependency entre Jobs
			if ((newJob.getParentJob() == null)
					|| (parentJob.getId() != null && !selfDependency(parentJob, newJob.getName()))) {
				Job job = new Job(newJob.getId(), newJob.getName(), newJob.getActive(), newJob.getParentJob(), null);
				if (newJob.getParentJob() != null) { // 'job' não será uma raiz
					job.setIdParentJob(newJob.getParentJob().getId());
				}
				jobRepository.save(job);

				// Atualizando Tasks
				if (newJob.getTasks() != null && newJob.getTasks().size() > 0) {
					List<Task> tasks = newJob.getTasks();
					tasks.forEach(task -> {
						taskService.updateTaskById(task.getId(), task);
					});
				}
				return true;
			}
			return false;
		}
		saveJob(newJob);
		return true;
	}

	public Boolean selfDependency(Job job, String name) {
		if (job.getName().equals(name)) {
			return true;
		} else if (job._getIdParentJob() == null) { // Raiz
			return false;
		} else {
			return selfDependency(getJobById(job._getIdParentJob()).get(), name);
		}
	}

	public Job findParentJob(ParentJob job) {
		List<Job> jobs = jobRepository.findAll();
		for (Job j : jobs) {
			if (j.getId() == job.getId() && j.getName().equals(job.getName()) && j.getActive() == job.getActive()) {
				return j;
			}
		}
		return new Job();
	}

}
