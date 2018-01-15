package model;

import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import java.util.Collection;

@PersistenceCapable
public class Event {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private long id;
	private String name;
	private String data;
	private String description;
	@Persistent
	private List<String> users;
	
	public Event(){
		
	}
	
	public Event(long id, String name, String data, String description) {
		super();
		this.id = id;
		this.name = name;
		this.data = data;
		this.description = description;
	}
	
	public Event(String name, String data, String description) {
		super();
		this.name = name;
		this.data = data;
		this.description = description;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
}
