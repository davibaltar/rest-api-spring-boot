package com.unisys.api.services;

import java.util.List;
import java.util.Optional;

import com.unisys.api.entities.Job;

public interface JobService {

	public List<Job> getJobs(Boolean $sortByWeight);

	public Optional<Job> getJobById(Integer id);

	public Job saveJob(Job job);

	public Boolean deleteJobById(Integer id);

	public Boolean updateJobById(Integer id, Job newJob);

}
