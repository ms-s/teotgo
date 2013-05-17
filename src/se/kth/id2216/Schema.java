package se.kth.id2216;


import java.util.List;

public class Schema {
	
	private List<Course> courses;
	
	private List<CalEvent> events;
	
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	public List<CalEvent> getEvents() {
		return events;
	}
	public void setEvents(List<CalEvent> events) {
		this.events = events;
	}

}
