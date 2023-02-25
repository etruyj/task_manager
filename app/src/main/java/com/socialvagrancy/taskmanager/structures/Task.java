//===================================================================
// Task.java
// 	Description:
// 		A container class for all the information related
// 		to tasks.
//===================================================================

package com.socialvagrancy.taskmanager.structure;

public class Task
{
	String id;
	String subject;
	String description;
	String start_time;
	Integer duration;
	TaskStatus status;
	String account;
	String contact;
	String location;
	String project;
	String recurrance_id;
	boolean automate;

	//=======================================
	// Getters
	//=======================================

	public String account() { return account; }
	public boolean automate() { return automate; }
	public String contact() { return contact; }
	public String description() { return description; }
	public Integer duration() { return duration; }
	public String id() { return id; }
	public String location() { return location; }
	public String project() { return project; }
	public String recurranceId() { return recurrance_id; }
	public String startTime() { return start_time; }
	public TaskStatus status() { return status; }
        public String statusString() { return status.toString(); }
	public String subject() { return subject; }

	//=======================================
	// Setters
	//=======================================
	
	public Task setAccount(String s)
	{
		account = s;
		return this;
	}

	public Task setAutomate(boolean s)
	{
		automate = s;
		return this;
	}

	public Task setContact(String s)
	{
		contact = s;
		return this;
	}

	public Task setDescription(String s)
	{
		description = s;
		return this;
	}

	public Task setDuration(int s)
	{
		duration = s;
		return this;
	}

	public Task setId(String s)
	{
		id = s;
		return this;
	}

	public Task setLocation(String s)
	{
		location = s;
		return this;
	}

	public Task setProject(String s)
	{
		project = s;
		return this;
	}

	public Task setRecurranceId(String s)
	{
		recurrance_id = s;
		return this;
	}

	public Task setStartTime(String s)
	{
		start_time = s;
		return this;
	}

	public Task setStatus(String s)
	{
		status = TaskStatus.valueOf(s);
		return this;
	}

	public Task setStatus(TaskStatus s)
	{
		status = s;
		return this;
	}

	public Task setSubject(String s)
	{
		subject = s;
		return this;
	}
}
