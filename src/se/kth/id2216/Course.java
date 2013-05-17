package se.kth.id2216;

import java.util.List;

public class Course {
	/* we need id only as a query string param, not for display to user */
	private int id;
	private String code;
	private String name;
	
	private List<CalEvent> events;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<CalEvent> getEvents() {
		return events;
	}
	public void setEvents(List<CalEvent> events) {
		this.events = events;
	}
	
	public String toString() {
		return code + "\t" + name;
	}
	
}
