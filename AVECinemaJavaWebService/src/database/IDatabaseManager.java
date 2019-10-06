package database;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Customer;
import model.Manager;
import model.Movie;
import model.Reservation;
import model.Schedule;
import model.User;
import model.Voucher;

public interface IDatabaseManager {

	public boolean registerNewCustomer(Customer customer)throws SQLException;
	public User logIn(String email, String password);
	public boolean updateCustomerInfo(Customer customer);
	public Customer getCustomer(String email);
	public Manager getManager(String email);
	public boolean updateManagerInfo(Manager manager);
	public boolean saveMovie(Movie movie) throws SQLException;
	public boolean updateMovieInfo(Movie movie);
	public ArrayList<Movie> getAllMovies();
	ArrayList<Movie> getAllMoviesForProgramme();
	public ArrayList<Schedule> getSchedules(String movieID);
	public int createSchedule (Schedule schedule, String movieID);
	public boolean updateScheduleInfo(Schedule schedule);
	public int makeReservation(Reservation reservation, String email);
	public ArrayList<Customer> getAllCustomers();
	public ArrayList<Reservation> getAllReservations();
	public Schedule getScheduleById(int scheduleID);
	public ArrayList<Reservation> getAllReservationsByCustomer(String emailCustomer);
	public boolean updateReservationStatus(int reservationID, String status);
	public boolean createVoucher(Voucher voucher);
	public ArrayList<Voucher> getAllVouchers();
	public boolean updateReservation(Reservation reservation);
	
}
