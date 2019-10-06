package test;

import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import database.core.CustomerDatabase;
import database.infrastructure.ICustomerDatabase;
import model.Customer;
import model.Reservation;
import model.User;
public class Test {

	public static void main(String[] args) throws AddressException, MessagingException {
		//testRegisterNewCustomer();
	
		
	}
	static void testRegisterNewCustomer() throws SQLException{
		ICustomerDatabase cutomerDatabase = new CustomerDatabase();
		Customer c = new Customer("village@gmail.com", "emil", User.CUSTOMER);
		
		c.setFirstName("Emil");
		c.setLastName("From");
		c.setPhoneNo("11111");
		
		Boolean result = cutomerDatabase.registerNewCustomer(c);
		Reservation r = new Reservation();
		r.setStatus(Reservation.UNPAID);
		
		System.out.println(result);
		
	}
	
	

}
