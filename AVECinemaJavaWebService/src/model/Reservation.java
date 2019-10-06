package model;

public class Reservation {

	public static final String CANCELLED = "Cancelled";
	public static final String PAID = "Paid";
	public static final String UNPAID = "Unpaid";

	private int reservationID;
	private double totalPrice;
	private int numberOfTicket;
	private String voucherCode;
	private int scheduleID;
	private int[] seats;
	private String movieTitle;
	private String status;

	public Reservation() {

	}

	public int getReservationID() {
		return reservationID;
	}

	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getNumberOfTicket() {
		return numberOfTicket;
	}

	public void setNumberOfTicket(int numberOfTicket) {
		this.numberOfTicket = numberOfTicket;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public int getScheduleID() {
		return scheduleID;
	}

	public void setScheduleID(int scheduleID) {
		this.scheduleID = scheduleID;
	}

	public int[] getSeats() {
		return seats;
	}

	public void setSeats(int[] seats) {
		this.seats = seats;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
