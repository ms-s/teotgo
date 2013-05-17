package se.kth.id2216;

import java.util.Date;

public class CalEvent {
	
	private long start;
	private long end;
	private String code;
	private String room;
	private String moment;
	private String name;
	private String rename;
	
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getMoment() {
		return moment;
	}
	public void setMoment(String moment) {
		this.moment = moment;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRename() {
		return rename;
	}
	public void setRename(String rename) {
		this.rename = rename;
	}
	
	public String toString() {
		Date st = new Date( start );
		Date en = new Date( end );
		return name + " " + code + " " + moment + "\troom:" + room + "\t" + st + "-" + en + " ";
	}
}
