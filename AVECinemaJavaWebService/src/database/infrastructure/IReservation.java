package database.infrastructure;

import java.util.ArrayList;

import model.Reservation;

public interface IReservation {
public int makeReservation(Reservation reservation, String email);
public boolean reserveSeat(int seat_no, int reservationID, int scheduleID);
public void commit();
public void rollBack();
public ArrayList<Reservation> getAllReservations();
public ArrayList<Reservation> getAllReservationsByCustomer(String emailCustomer);
public boolean updateReservationStatus(int reservationID, String status);
public boolean deleteReservedSeat(int reservationID);
public boolean updateReservation(Reservation reservation);





}
