package database.infrastructure;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Customer;

public interface ICustomerDatabase {
	
	public boolean registerNewCustomer(Customer customer) throws SQLException;
	public Customer logInCustomer(String email, String password);
	public boolean updateCustomerInfo(Customer customer);
	public Customer getCustomer(String email);
	public ArrayList<Customer> getAllCustomers();

}
