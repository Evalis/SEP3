package database.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.dbcp2.BasicDataSource;

import database.infrastructure.ICustomerDatabase;
import model.Customer;
import model.User;

public class CustomerDatabase implements ICustomerDatabase {

	private Connection connection;

	public CustomerDatabase() {
		BasicDataSource dataSource = DatabaseConnectionPool.getInstance().getBasicSource();
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean registerNewCustomer(Customer customer) throws SQLException {
		String sql = "INSERT INTO cinema.customer("
				+ "firstName, lastName, password, emailCustomer, phoneNo) values(?,?,?,?,?)";
		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, customer.getFirstName());
			pStatement.setString(2, customer.getLastName());
			pStatement.setString(3, customer.getPassword());
			pStatement.setString(4, customer.getEmail());
			pStatement.setString(5, customer.getPhoneNo());
			pStatement.executeUpdate();
			System.out.println("New Customer added to database.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Exception code " + e.getErrorCode());
			System.out.println("Exception state" + e.getSQLState());
			System.out.println("Exception message " + e.getMessage());
			if(e.getSQLState().equals("23505"))
			{
				throw e;
			}
			return false;

		}
		return true;
	}

	@Override
	public Customer logInCustomer(String emailCustomer, String password) {
		Customer c = null;
		String sql = "SELECT * FROM cinema.customer" + " WHERE emailCustomer = ? and password = ?";

		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, emailCustomer);
			pStatement.setString(2, password);

			ResultSet r = pStatement.executeQuery();

			while (r.next()) {
				c = new Customer(r.getString("emailCustomer"), r.getString("password"), User.CUSTOMER);
				c.setFirstName(r.getString("firstName"));
				c.setLastName(r.getString("lastName"));
				c.setPhoneNo(r.getString("phoneno"));
				System.out.println("customer found " + c.getEmail());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	public boolean updateCustomerInfo(Customer customer) {
		String sql = "UPDATE cinema.customer SET " + " firstName = ?," + " lastName = ?," + " password = ?,"
				+ " phoneno = ?" + " WHERE emailCustomer = ? ";
		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, customer.getFirstName());
			pStatement.setString(2, customer.getLastName());
			pStatement.setString(3, customer.getPassword());
			pStatement.setString(4, customer.getPhoneNo());
			pStatement.setString(5, customer.getEmail());
			pStatement.executeUpdate();
			System.out.println("Customer has been edided.");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		}

		return true;
	}

	public Customer getCustomer(String email) {
		Customer c = null;
		String sql = "SELECT * from cinema.customer " + "where emailCustomer = ?";

		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, email);

			ResultSet r = pStatement.executeQuery();

			while (r.next()) {
				c = new Customer();
				c.setPassword(r.getString("password"));
				c.setFirstName(r.getString("firstName"));
				c.setLastName(r.getString("lastName"));
				c.setPhoneNo(r.getString("phoneno"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public ArrayList<Customer> getAllCustomers() {
		ArrayList<Customer> temp = new ArrayList<>();

		String sql = "SELECT * FROM  cinema.customer ";
		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				Customer customer = new Customer();
				customer.setFirstName(resultSet.getString("firstName"));
				customer.setLastName(resultSet.getString("lastName"));
				customer.setEmail(resultSet.getString("emailCustomer"));
				customer.setPhoneNo(resultSet.getString("phoneNo"));
				temp.add(customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return temp;
		}
		return temp;
	}
}
