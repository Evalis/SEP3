package database.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.dbcp2.BasicDataSource;

import database.infrastructure.IManagerDatabase;
import model.Manager;
import model.User;
import model.Voucher;

public class ManagerDatabase implements IManagerDatabase {

	private Connection connection;

	public ManagerDatabase() {
		BasicDataSource dataSource = DatabaseConnectionPool.getInstance().getBasicSource();
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Manager logInManager(String emailManager, String password) {
		Manager m = null;
		String sql = "SELECT * FROM cinema.manager" + " WHERE emailManager = ? and password = ?";

		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, emailManager);
			pStatement.setString(2, password);

			ResultSet r = pStatement.executeQuery();

			while (r.next()) {
				m = new Manager(r.getString("emailManager"), r.getString("password"), User.MANAGER);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}

	@Override
	public Manager getManager(String email) {
		Manager m = null;
		String sql = "SELECT * from cinema.manager " + "where emailManager = ?";

		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, email);

			ResultSet r = pStatement.executeQuery();

			while (r.next()) {
				m = new Manager();
				m.setPassword(r.getString("password"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}

	public boolean updateManagerInfo(Manager manager) {
		String sql = "UPDATE cinema.manager SET " + " password = ?" + " WHERE emailManager = ? ";
		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, manager.getPassword());
			pStatement.setString(2, manager.getEmail());
			pStatement.executeUpdate();
			System.out.println("Manager has been edided.");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		}

		return true;
	}

	public boolean createVoucher(Voucher voucher) {
		String sql = "INSERT INTO cinema.voucher(" + "code, percentage) values(?,?)";
		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, voucher.getCode());
			pStatement.setInt(2, voucher.getPercentage());

			pStatement.executeUpdate();
			System.out.println("New Voucher added to database.");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		}
		return true;
	}

	public ArrayList<Voucher> getAllVouchers() {
		ArrayList<Voucher> temp = new ArrayList<>();

		String sql = "SELECT * FROM cinema.Voucher ";
		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				Voucher v = new Voucher();

				v.setCode(resultSet.getString("code"));
				v.setPercentage(resultSet.getInt("percentage"));

				temp.add(v);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return temp;
		}
		return temp;
	}

}
