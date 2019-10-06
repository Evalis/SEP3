package database.infrastructure;

import java.util.ArrayList;
import java.util.Map;

import model.Schedule;

public interface IScheduleDatabase {

	public ArrayList<Schedule> getSchedules(String movieID);
	public int createSchedule (Schedule schedule, String movieID);
	public boolean updateScheduleInfo(Schedule schedule);
	public ArrayList<Schedule> getFutureSchedules(String movieID);
	public Map<Integer, Integer> getSeats(int scheduleID);
	public Schedule getSchedule(int scheduleID);
	
}