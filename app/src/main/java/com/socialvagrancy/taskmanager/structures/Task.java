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
	String description_id;
	String start_time;
	Integer duration;
	TaskStatus status;
	String account_id;
	String contact_id;
	String location_id;
	String project_id;
	String recurrance_id;
	boolean automate;

	//=======================================
	// Getters
	//=======================================

	public String accountId() { return account_id; }
	public boolean automate() { return automate; }
	public String contactId() { return contact_id; }
	public String description() { return description; }
	public String descriptionId() { return description_id; }
	public Integer duration() { return duration; }
	public String id() { return id; }
	public String locationId() { return location_id; }
	public String projectId() { return project_id; }
	public String recurranceId() { return recurrance_id; }
	public String startTime() { return start_time; }
	public TaskStatus status() { return status; }
	public String subject() { return subject; }

	//=======================================
	// Setters
	//=======================================
	
	public Task setAccountId(String s)
	{
		account_id = s;
		return this;
	}

	public Task setAutomate(boolean s)
	{
		automate = s;
		return this;
	}

	public Task setContactId(String s)
	{
		contact_id = s;
		return this;
	}

	public Task setDescription(String s)
	{
		description = s;
		return this;
	}

	public Task setDescriptionId(String s)
	{
		description_id = s;
		description = null;
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

	public Task setLocationId(String s)
	{
		location_id = s;
		return this;
	}

	public Task setProjectId(String s)
	{
		project_id = s;
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
		start_time = s;
		return this;
	}
}
