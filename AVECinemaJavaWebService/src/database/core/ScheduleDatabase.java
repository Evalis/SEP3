package database.core;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;

import database.infrastructure.IScheduleDatabase;
import model.Schedule;

public class ScheduleDatabase implements IScheduleDatabase {

	private Connection connection;

	public ScheduleDatabase() {
		BasicDataSource dataSource = DatabaseConnectionPool.getInstance().getBasicSource();
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Schedule> getSchedules(String movieID) {
		System.out.println("looking for schedules for " + movieID);
		ArrayList<Schedule> temp = new ArrayList<>();

		String sql = "SELECT * FROM  cinema.schedule " + " WHERE movieID = ? ";
		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, movieID);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println("Found schedule ");
				Schedule schedule = new Schedule();
				schedule.setDate(resultSet.getDate("date").toString());
				schedule.setStartTime(resultSet.getTime("startTime").toString());
				schedule.setEndTime(resultSet.getTime("endTime").toString());
				schedule.setRoomNo(resultSet.getString("roomNo"));
				schedule.setScheduleID(resultSet.getInt("scheduleID"));
				temp.add(schedule);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return temp;
		}
		return temp;
	}

	@Override
	public ArrayList<Schedule> getFutureSchedules(String movieID) {
		System.out.println("looking for schedules for " + movieID);
		ArrayList<Schedule> temp = new ArrayList<>();

		String sql = "SELECT * FROM  cinema.schedule " + " "
				+ "WHERE movieID = ? and ("
				+ "( date = ? and startTime >= ? ) or "
				+ " date > ? )";
		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, movieID);
			pStatement.setDate(2, Date.valueOf(LocalDate.now()));
			pStatement.setTime(3, Time.valueOf(LocalTime.now()));
			pStatement.setDate(4, Date.valueOf(LocalDate.now()));
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println("Found schedule ");
				Schedule schedule = new Schedule();
				schedule.setDate(resultSet.getDate("date").toString());
				schedule.setStartTime(resultSet.getTime("startTime").toString());
				schedule.setEndTime(resultSet.getTime("endTime").toString());
				schedule.setRoomNo(resultSet.getString("roomNo"));
				schedule.setScheduleID(resultSet.getInt("scheduleID"));
				temp.add(schedule);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return temp;
		}
		return temp;
	}

	@Override
	public int createSchedule(Schedule schedule, String movieID) {
		int scheduleID = -1;
		boolean overlap = checkIfScheduleOverlap(schedule);
		if(overlap)
		{
			return -2;
		}
		
		String sql = "INSERT INTO cinema.schedule(" + " date, startTime, endTime, roomNo, movieID ) values(?,?,?,?,?)"
				+ " returning scheduleID ";

		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setDate(1, Date.valueOf(schedule.getDate()));
			pStatement.setTime(2, Time.valueOf(schedule.getStartTime()));
			pStatement.setTime(3, Time.valueOf(schedule.getEndTime()));
			pStatement.setString(4, schedule.getRoomNo());
			pStatement.setString(5, movieID);

			
			ResultSet r = pStatement.executeQuery();
			while (r.next()) {
				scheduleID = r.getInt("s");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return scheduleID;
		}
		return scheduleID;
	}
	@Override
	public boolean updateScheduleInfo(Schedule schedule) {

		String sql = "UPDATE cinema.schedule SET " + " date = ?," + " startTime = ?," + " endTime = ?," + " roomNo = ?"
				+ "  WHERE scheduleID = ? ";
		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setDate(1, Date.valueOf(schedule.getDate()));
			pStatement.setTime(2, Time.valueOf(schedule.getStartTime()));
			pStatement.setTime(3, Time.valueOf(schedule.getEndTime()));
			pStatement.setString(4, schedule.getRoomNo());
			pStatement.setInt(5, schedule.getScheduleID());
			pStatement.executeUpdate();
			System.out.println("Schudule has been edided.");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		}
		return true;
	}

	@Override
	public Map<Integer, Integer> getSeats(int scheduleID) {
		Map<Integer, Integer>  temp = new HashMap<Integer, Integer> ();

		String sql = "SELECT * FROM  cinema.ReservedSeat "
				+ "WHERE scheduleID = ? ";
		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setInt(1, scheduleID);
			ResultSet resultSet = pStatement.executeQuery();
			
			while (resultSet.next()) {
				System.out.println("Found Seat ");
				temp.put(resultSet.getInt("seat_no"), resultSet.getInt("reservationID"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return temp;
		}
		return temp;
	}

	@Override
	public Schedule getSchedule(int scheduleID) {
		Schedule schedule = null;

		String sql = "SELECT * FROM  cinema.schedule " + " WHERE scheduleID = ? ";
		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setInt(1, scheduleID);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println("Found schedule ");
				schedule = new Schedule();
				schedule.setDate(resultSet.getDate("date").toString());
				schedule.setStartTime(resultSet.getTime("startTime").toString());
				schedule.setEndTime(resultSet.getTime("endTime").toString());
				schedule.setRoomNo(resultSet.getString("roomNo"));
				schedule.setScheduleID(resultSet.getInt("scheduleID"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return schedule;
		}
		return schedule;
	}

	private boolean checkIfScheduleOverlap(Schedule schedule)
	{
		String sql = "SELECT * FROM  cinema.schedule " 
				+ " WHERE roomno = ? " 
				+ " and date = ? "
				+ " and ((endTime >= ? and endTime <= ? ) "
				+ "	or (startTime <= ? and endTime >= ? ) "
				+ " or (startTime >= ? and startTime <= ? ))";		
		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, schedule.getRoomNo());
			pStatement.setDate(2, Date.valueOf(schedule.getDate()));
			pStatement.setTime(3, Time.valueOf(schedule.getStartTime()));
			pStatement.setTime(4, Time.valueOf(schedule.getEndTime()));
			pStatement.setTime(5, Time.valueOf(schedule.getStartTime()));
			pStatement.setTime(6, Time.valueOf(schedule.getEndTime()));
			pStatement.setTime(7, Time.valueOf(schedule.getStartTime()));
			pStatement.setTime(8, Time.valueOf(schedule.getEndTime()));
			
			
			
			ResultSet resultSet = pStatement.executeQuery();
			
			while (resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
		return false;
	}
	
	
	
}
