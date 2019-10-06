package webService;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Customer;
import model.Manager;
import model.Movie;
import model.Reservation;
import model.Schedule;
import model.User;
import model.Voucher;

public interface ICinemaWebService {

	public boolean registerNewCustomer(Customer customer) throws SQLException;

	public User logIn(String email, String password);

	public boolean updateManagerInfo(Manager manager);

	public Manager getManager(String email);

	public boolean updateCustomerInfo(Customer customer);

	public Customer getCustomer(String email);

	public ArrayList<Customer> getAllCustomers();

	public boolean saveMovie(Movie movie) throws SQLException;

	public boolean updateMovieInfo(Movie movie);

	public ArrayList<Movie> getAllMovies();

	public ArrayList<Movie> getAllMoviesForProgramme();

	public int createSchedule(Schedule schedule, String movieID);

	public boolean updateScheduleInfo(Schedule schedule);

	public Schedule getScheduleByID(int scheduleID);

	public ArrayList<Schedule> getSchedules(String movieID);

	public int makeReservation(Reservation reservation, String email);

	public ArrayList<Reservation> getAllReservations();

	public ArrayList<Reservation> getAllReservationsByCustomer(String email);

	public boolean updateReservationStatus(int reservationID, String status);

	public boolean updateReservation(Reservation reservation);

	public boolean createVoucher(Voucher voucher);

	public ArrayList<Voucher> getAllVouchers();

}
