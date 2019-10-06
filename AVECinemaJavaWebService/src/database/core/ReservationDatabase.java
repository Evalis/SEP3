package database.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.dbcp2.BasicDataSource;

import database.infrastructure.IReservation;
import model.Reservation;

public class ReservationDatabase implements IReservation {

	private Connection connection;

	public ReservationDatabase() {
		BasicDataSource dataSource = DatabaseConnectionPool.getInstance().getBasicSource();
		try {
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void commit() {
		try {
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void rollBack() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int makeReservation(Reservation reservation, String emailCustomer) {
		int reservationID = -1;

		String sql = "INSERT INTO cinema.reservation("
				+ "totalPrice , numberOfTicket, scheduleID, code, status, emailCustomer ) values(?,?,?,?,?,?)"
				+ " returning reservationID ";
		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setDouble(1, reservation.getTotalPrice());
			pStatement.setInt(2, reservation.getNumberOfTicket());
			pStatement.setInt(3, reservation.getScheduleID());
			pStatement.setString(4, reservation.getVoucherCode());
			pStatement.setString(5, Reservation.UNPAID);
			pStatement.setString(6, emailCustomer);

			ResultSet r = pStatement.executeQuery();
			while (r.next()) {
				reservationID = r.getInt("reservationID");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			commit();
			return reservationID;
		}
		return reservationID;
	}

	public boolean reserveSeat(int seat_no, int reservationID, int scheduleID) {

		String sql = "INSERT INTO  cinema.ReservedSeat (" + "seat_no, reservationID, scheduleID) values (?, ?, ?)";

		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setInt(1, seat_no);
			pStatement.setInt(2, reservationID);
			pStatement.setInt(3, scheduleID);
			pStatement.executeUpdate();
			System.out.println("Seat added to database");

		} catch (SQLException e) {
			e.printStackTrace();
			commit();
			return false;
		}
		return true;
	}

	public ArrayList<Reservation> getAllReservations() {
		ArrayList<Reservation> temp = new ArrayList<>();

		String sql = "SELECT * FROM  cinema.reservation "
				+ " JOIN cinema.schedule ON reservation.scheduleID = schedule.scheduleID "
				+ " JOIN cinema.movie ON schedule.movieID = movie.movieID  ";

		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				Reservation reservation = new Reservation();

				reservation.setReservationID(resultSet.getInt("reservationID"));
				reservation.setScheduleID(resultSet.getInt("scheduleID"));
				reservation.setNumberOfTicket(resultSet.getInt("numberOfTicket"));
				reservation.setTotalPrice(resultSet.getDouble("totalPrice"));
				reservation.setVoucherCode(resultSet.getString("code"));
				reservation.setMovieTitle(resultSet.getString("title"));
				reservation.setStatus(resultSet.getString("status"));

				temp.add(reservation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return temp;
		}
		return temp;
	}

	public ArrayList<Reservation> getAllReservationsByCustomer(String emailCustomer) {
		ArrayList<Reservation> temp = new ArrayList<>();

		String sql = "SELECT * FROM  cinema.reservation "
				+ " JOIN cinema.schedule ON reservation.scheduleID = schedule.scheduleID "
				+ " JOIN cinema.movie ON schedule.movieID = movie.movieID   WHERE emailCustomer = ? ";

		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, emailCustomer);

			ResultSet resultSet = pStatement.executeQuery();

			while (resultSet.next()) {
				Reservation reservation = new Reservation();

				reservation.setReservationID(resultSet.getInt("reservationID"));
				reservation.setScheduleID(resultSet.getInt("scheduleID"));
				reservation.setNumberOfTicket(resultSet.getInt("numberOfTicket"));
				reservation.setTotalPrice(resultSet.getDouble("totalPrice"));
				reservation.setVoucherCode(resultSet.getString("code"));
				reservation.setMovieTitle(resultSet.getString("title"));
				reservation.setStatus(resultSet.getString("status"));
				temp.add(reservation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return temp;
		}
		return temp;
	}

	public boolean updateReservationStatus(int reservationID, String status) {
		String sql = "UPDATE cinema.Reservation SET status = ? WHERE reservationID = ?";

		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, status);
			pStatement.setInt(2, reservationID);
			pStatement.executeUpdate();
			System.out.println("Seat updated into database");

		} catch (SQLException e) {
			e.printStackTrace();
			commit();
			return false;
		}
		return true;
	}

	public boolean deleteReservedSeat(int reservationID) {

		String sql = "DELETE FROM  cinema.ReservedSeat WHERE reservationID = ?";

		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);

			pStatement.setInt(1, reservationID);

			pStatement.executeUpdate();
			System.out.println("Seat deleted from database");

		} catch (SQLException e) {
			e.printStackTrace();
			commit();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateReservation(Reservation reservation) {
		String sql = "UPDATE cinema.reservation SET " + " totalPrice = ?, " + " code = ?" + " WHERE reservationID = ?";
		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setDouble(1, reservation.getTotalPrice());
			pStatement.setString(2, reservation.getVoucherCode());
			pStatement.setInt(3, reservation.getReservationID());
			pStatement.executeUpdate();
			System.out.println("Reservation has been updated.");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		}

		return true;
	}

	
	

}
