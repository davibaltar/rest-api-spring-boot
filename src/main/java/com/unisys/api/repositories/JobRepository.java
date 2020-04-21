package com.unisys.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unisys.api.entities.Job;

public interface JobRepository extends JpaRepository<Job, Integer> {

	List<Job> findJobByIdParentJob(Integer Id);

}
