package model;

import java.util.ArrayList;

public class Manager extends User{
	

	private ArrayList<Movie> movies;
	private ArrayList<Customer> customer;
	private ArrayList<Reservation> reservations;
	private ArrayList<Voucher> vouchers;
	
	
	public Manager(String email, String password, String role)
	{
		super(email, password, role);
		movies = new ArrayList<>();
		customer = new ArrayList<>();
		reservations = new ArrayList<>();
		vouchers = new ArrayList<>();
		
	}

	public Manager() {super(User.MANAGER);}

	public ArrayList<Movie> getMovies() {
		return movies;
	}

	public void setMovies(ArrayList<Movie> movies) {
		this.movies = movies;
	}

	public ArrayList<Customer> getCustomer() {
		return customer;
	}

	public void setCustomer(ArrayList<Customer> customer) {
		this.customer = customer;
	}

	public ArrayList<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(ArrayList<Reservation> reservations) {
		this.reservations = reservations;
	}

	public ArrayList<Voucher> getVouchers() {
		return vouchers;
	}

	public void setVouchers(ArrayList<Voucher> vouchers) {
		this.vouchers = vouchers;
	}






	
	

}
