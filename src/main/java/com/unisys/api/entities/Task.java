package com.unisys.api.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Task {

	@Id
	@NotNull
	private Integer id;

	@Column
	@NotNull
	@NotBlank
	private String name;

	@Column
	@NotNull
	private Integer weight;

	@Column
	private Boolean completed;

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate createdAt;

	@PrePersist
	private void prePersist() {
		if (this.createdAt == null) {
			this.createdAt = LocalDate.now();
		}
	}

	@ManyToOne
	@JoinColumn(name = "job_id")
	private Job job;

	public Task() {
		// TODO Auto-generated constructor stub
	}

	public Task(Integer id, String name, Integer weight, Boolean completed, LocalDate createdAt, Job job) {
		super();
		this.id = id;
		this.name = name;
		this.weight = weight;
		this.completed = completed;
		this.createdAt = createdAt;
		this.job = job;
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

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public Job _getJob() {
		return this.job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Integer _getJobId() {
		return this.job.getId();
	}

}
