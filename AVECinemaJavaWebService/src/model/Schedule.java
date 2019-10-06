package model;


import java.util.Map;

public class Schedule {

	private String date;
	private String startTime;
	private String endTime;
	private Map<Integer,Integer> seats;
	private String roomNo;
	private int scheduleID;


	public Schedule() {
	
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Map<Integer, Integer> getSeats() {
		return seats;
	}

	public void setSeats(Map<Integer, Integer> seats) {
		this.seats = seats;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public int getScheduleID()
	{
		return scheduleID;
	}
	
	public void setScheduleID(int scheduleID)
	{
		this.scheduleID = scheduleID;
	}
	
	
	
}
