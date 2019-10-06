package model;


import java.util.ArrayList;

public class Customer extends User {
	
	private String firstName;
	private String lastName;
	private String phoneNo;
	private ArrayList<Reservation> reservations;
	
	public Customer(String email, String password, String role)
	{
		super(email, password, role);
		this.reservations = new ArrayList<>();
	}
	public Customer() {super(User.CUSTOMER);}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastNama) {
		this.lastName = lastNama;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public ArrayList<Reservation> getReservations() {
		return reservations;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	

}
