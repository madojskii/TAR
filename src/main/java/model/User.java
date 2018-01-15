package model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class User {
	
	@PrimaryKey
	public String email;
	@Persistent
	public Key event;
	
	public User(){
		
	}
	
	public User(String email){
		this.email = email;
	}
	
	public User(String email, Key event){
		this.email = email;
		this.event = event;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Key getEvent() {
		return event;
	}

	public void setEvent(Key event) {
		this.event = event;
	}

	@Override
	public String toString() {
		return "User [email=" + email + "]";
	}
		
}
