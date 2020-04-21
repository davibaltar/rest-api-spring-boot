package com.unisys.api.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Job implements Comparable<Job> {

	@Id
	@NotNull
	private Integer id;

	@Column
	@NotNull
	@NotBlank
	private String name;

	@Column
	@NotNull
	private Boolean active;

	@OneToMany(mappedBy = "job")
	private List<Task> tasks = new ArrayList<>();

	@Transient
	private ParentJob parentJob;

	@Column
	private Integer idParentJob;

	@Transient
	private Integer sumWeights;

	public Job() {
		// TODO Auto-generated constructor stub
	}

	public Job(Integer id, String name, Boolean active, ParentJob parentJob, List<Task> tasks) {
		super();
		this.id = id;
		this.name = name;
		this.active = active;
		this.parentJob = parentJob;
		this.tasks = tasks;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public ParentJob getParentJob() {
		return parentJob;
	}

	public void setParentJob(ParentJob parentJob) {
		this.parentJob = parentJob;
	}

	public void setIdParentJob(Integer idParentJob) {
		this.idParentJob = idParentJob;
	}

	public Integer _getIdParentJob() {
		return idParentJob;
	}

	public void setSumWeights(Integer sumWeights) {
		this.sumWeights = sumWeights;
	}

	private Integer sumWeights(Job job) {
		sumWeights = 0;
		job.getTasks().forEach(task -> sumWeights += task.getWeight());
		return sumWeights;
	}

	@Override
	public int compareTo(Job nextJob) {
		Integer jobWeights = sumWeights(this);
		Integer nextJobWeights = sumWeights(nextJob);
		if (jobWeights > nextJobWeights) {
			return -1;
		} else if (jobWeights < nextJobWeights) {
			return 1;
		}
		return 0;
	}

}
