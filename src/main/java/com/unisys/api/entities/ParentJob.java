package com.unisys.api.entities;

/* 
 * Esta classe foi construída somente para manter o padrão de resposta exigido na documentação
 * */
public class ParentJob {

	private Integer id;

	private String name;

	private Boolean active;

	public ParentJob() {
		// TODO Auto-generated constructor stub
	}

	public ParentJob(Integer id, String name, Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.active = active;
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

}
