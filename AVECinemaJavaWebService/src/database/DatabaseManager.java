package database;

import java.sql.SQLException;
import java.util.ArrayList;

import database.core.CustomerDatabase;
import database.core.ManagerDatabase;
import database.core.MovieDatabase;
import database.core.ReservationDatabase;
import database.core.ScheduleDatabase;
import database.infrastructure.ICustomerDatabase;
import database.infrastructure.IManagerDatabase;
import database.infrastructure.IMovieDatabase;
import database.infrastructure.IReservation;
import database.infrastructure.IScheduleDatabase;
import model.Customer;
import model.Manager;
import model.Movie;
import model.Reservation;
import model.Schedule;
import model.User;
import model.Voucher;

public class DatabaseManager implements IDatabaseManager {

	private ICustomerDatabase customerDatabase;
	private IManagerDatabase managerDatabase;
	private IMovieDatabase movieDatabase;
	private IScheduleDatabase scheduleDatabase;
	private IReservation reservationDatabase;

	public DatabaseManager() {

		customerDatabase = new CustomerDatabase();
		managerDatabase = new ManagerDatabase();
		movieDatabase = new MovieDatabase();
		scheduleDatabase = new ScheduleDatabase();
		reservationDatabase = new ReservationDatabase();
	}

	@Override
	public boolean registerNewCustomer(Customer customer) throws SQLException{
		return customerDatabase.registerNewCustomer(customer);
	}

	@Override
	public User logIn(String email, String password) {
		User user = customerDatabase.logInCustomer(email, password);
		
		if (user == null) {
			System.out.println("looking for manager");
			user = managerDatabase.logInManager(email, password);
		}

		return user;

	}
	@Override
	public boolean updateCustomerInfo(Customer customer) {

		return customerDatabase.updateCustomerInfo(customer);
	}

	@Override
	public Customer getCustomer(String email) {

		return customerDatabase.getCustomer(email);
	}

	@Override
	public Manager getManager(String email) {

		return managerDatabase.getManager(email);
	}

	@Override
	public boolean updateManagerInfo(Manager manager) {

		return managerDatabase.updateManagerInfo(manager);
	}

	@Override
	public boolean saveMovie(Movie movie) throws SQLException {

		return movieDatabase.saveMovie(movie);
	}

	@Override
	public boolean updateMovieInfo(Movie movie) {

		return movieDatabase.updateMovieInfo(movie);
	}

	@Override
	public ArrayList<Movie> getAllMovies() {
		ArrayList<Movie> ms = movieDatabase.getAllMovies();
		for (Movie movie : ms) {
			movie.setSchedules(getSchedules(movie.getMovieID()));
		}

		return ms;
	}

	@Override
	public ArrayList<Movie> getAllMoviesForProgramme() {
		ArrayList<Movie> ms = movieDatabase.getAllMovies();
		for (Movie movie : ms) {
			ArrayList<Schedule> ss = scheduleDatabase.getFutureSchedules(movie.getMovieID());
			for (Schedule s : ss) {
				s.setSeats(scheduleDatabase.getSeats(s.getScheduleID()));
			}
			movie.setSchedules(ss);
		}

		ArrayList<Movie> moviesForProgramme = new ArrayList<>();
		for (Movie movie : ms) {
			if (!movie.getSchedules().isEmpty()) {
				moviesForProgramme.add(movie);
			}
		}

		return moviesForProgramme;
	}

	@Override
	public ArrayList<Schedule> getSchedules(String movieID) {

		return scheduleDatabase.getSchedules(movieID);
	}

	@Override
	public int createSchedule(Schedule schedule, String movieID) {

		return scheduleDatabase.createSchedule(schedule, movieID);
	}

	@Override
	public boolean updateScheduleInfo(Schedule schedule) {

		return scheduleDatabase.updateScheduleInfo(schedule);
	}

	@Override
	public int makeReservation(Reservation reservation, String email) {
		boolean somethingGoesWrong = false;
		int reservationID = reservationDatabase.makeReservation(reservation, email);
		if (reservationID == -1) {
			return reservationID;
		}

		for (int seat : reservation.getSeats()) {
			somethingGoesWrong = !reserveSeat(seat, reservationID, reservation.getScheduleID());

			if (somethingGoesWrong) {
				break;
			}
		}
		if (somethingGoesWrong) {
			reservationDatabase.rollBack();
			reservationID = -5;
		} else {
			reservationDatabase.commit();
		}

		return reservationID;
	}

	public boolean reserveSeat(int seat_no, int reservationID, int scheduleID) {
		return reservationDatabase.reserveSeat(seat_no, reservationID, scheduleID);
	}

	@Override
	public ArrayList<Customer> getAllCustomers() {

		return customerDatabase.getAllCustomers();
	}

	@Override
	public ArrayList<Reservation> getAllReservations() {

		return reservationDatabase.getAllReservations();
	}

	@Override
	public Schedule getScheduleById(int scheduleID) {

		Schedule s = scheduleDatabase.getSchedule(scheduleID);
		if (s != null) {
			s.setSeats(scheduleDatabase.getSeats(scheduleID));
		}
		return s;
	}

	@Override
	public ArrayList<Reservation> getAllReservationsByCustomer(String email) {

		return reservationDatabase.getAllReservationsByCustomer(email);
	}

	@Override
	public boolean updateReservationStatus(int reservationID, String status) {
		boolean result = false;
		result = reservationDatabase.updateReservationStatus(reservationID, status);
		if(result == false)
		{
			reservationDatabase.rollBack();
		}
		else
		{
			if (status.equals(Reservation.CANCELLED)) {
				result = reservationDatabase.deleteReservedSeat(reservationID);
			}
		}

		reservationDatabase.commit();
		return result;
	}
	

	public boolean deleteReservedSeat(int reservationID) {

		return reservationDatabase.deleteReservedSeat(reservationID);
	}

	@Override
	public boolean createVoucher(Voucher voucher) {
		
		return managerDatabase.createVoucher(voucher);
	}

	@Override
	public ArrayList<Voucher> getAllVouchers() {
	
		return managerDatabase.getAllVouchers();
	}

	@Override
	public boolean updateReservation(Reservation reservation) {
		
		return reservationDatabase.updateReservation(reservation);
	}

}
