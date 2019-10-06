package webService;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;
import database.DatabaseManager;
import database.IDatabaseManager;
import model.Customer;
import model.Manager;
import model.Movie;
import model.Reservation;
import model.Schedule;
import model.User;
import model.Voucher;

@WebService
public class CinemaWebService implements ICinemaWebService {

	private IDatabaseManager databaseManager;

	public CinemaWebService() {
		databaseManager = new DatabaseManager();
	}

	@WebMethod
	@Override
	public boolean registerNewCustomer(Customer customer) throws SQLException {
		System.out.println("register method was called");
		return databaseManager.registerNewCustomer(customer);
	}

	@WebMethod
	@Override
	public User logIn(String email, String password) {
		System.out.println("logincustomer() method called with " + email + password);
		return databaseManager.logIn(email, password);
	}

	@WebMethod
	@Override
	public Manager getManager(String email) {
		Manager m = databaseManager.getManager(email);
		return m;
	}

	@WebMethod
	@Override
	public boolean updateCustomerInfo(Customer customer) {

		return databaseManager.updateCustomerInfo(customer);
	}

	@WebMethod
	@Override
	public Customer getCustomer(String email) {
		System.out.println("getCustomer called with email = " + email);
		Customer c = databaseManager.getCustomer(email);
		if (c == null) {
			System.out.println("customer not found");
		} else {
			System.out.println(c.getEmail());
		}
		return c;
	}

	@WebMethod
	@Override
	public boolean updateManagerInfo(Manager manager) {

		return databaseManager.updateManagerInfo(manager);
	}

	@WebMethod
	@Override
	public boolean saveMovie(Movie movie) throws SQLException {

		return databaseManager.saveMovie(movie);
	}

	@WebMethod
	@Override
	public boolean updateMovieInfo(Movie movie) {

		return databaseManager.updateMovieInfo(movie);
	}

	@WebMethod
	@Override
	public ArrayList<Movie> getAllMovies() {

		return databaseManager.getAllMovies();
	}

	@WebMethod
	@Override
	public ArrayList<Movie> getAllMoviesForProgramme() {

		return databaseManager.getAllMoviesForProgramme();
	}

	@WebMethod
	@Override
	public ArrayList<Schedule> getSchedules(String movieID) {

		return databaseManager.getSchedules(movieID);
	}

	@WebMethod
	@Override
	public int createSchedule(Schedule schedule, String movieID) {
			
		return databaseManager.createSchedule(schedule, movieID);
	}

	@WebMethod
	@Override
	public boolean updateScheduleInfo(Schedule schedule) {

		return databaseManager.updateScheduleInfo(schedule);
	}

	@WebMethod
	@Override
	public int makeReservation(Reservation reservation, String email) {
		return databaseManager.makeReservation(reservation, email);
	}

	@WebMethod
	@Override
	public ArrayList<Customer> getAllCustomers() {

		return databaseManager.getAllCustomers();
	}

	@WebMethod
	@Override
	public ArrayList<Reservation> getAllReservations() {
		return databaseManager.getAllReservations();
	}

	@WebMethod
	@Override
	public Schedule getScheduleByID(int scheduleID) {
		return databaseManager.getScheduleById(scheduleID);
	}

	@WebMethod
	@Override
	public ArrayList<Reservation> getAllReservationsByCustomer(String email) {

		return databaseManager.getAllReservationsByCustomer(email);
	}

	@WebMethod
	@Override
	public boolean updateReservationStatus(int reservationID, String status) {

		return databaseManager.updateReservationStatus(reservationID, status);
	}
	
	@WebMethod
	@Override
	public boolean updateReservation(Reservation reservation) {

		return databaseManager.updateReservation(reservation);
	}

	@WebMethod
	@Override
	public boolean createVoucher(Voucher voucher) {

		return databaseManager.createVoucher(voucher);
	}

	@WebMethod
	@Override
	public ArrayList<Voucher> getAllVouchers() {

		return databaseManager.getAllVouchers();
	}
	
	
}
