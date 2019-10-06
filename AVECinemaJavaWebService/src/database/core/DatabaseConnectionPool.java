package database.core;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.dbcp2.BasicDataSource;


public class DatabaseConnectionPool {

	
	private static DatabaseConnectionPool instance;
	private BasicDataSource basicSource = new BasicDataSource();
	private static final String DRIVER = "org.postgresql.Driver";
	private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
	private static final String USER_NAME = "postgres";
	private static final String PASSWORD = "1779";
	private static Lock lock = new ReentrantLock();

	

	private DatabaseConnectionPool()
		{
		 basicSource.setDriverClassName(DRIVER);
		 basicSource.setUrl(URL);
		 basicSource.setUsername(USER_NAME);
		 basicSource.setPassword(PASSWORD);
		 basicSource.setMinIdle(5);
		 basicSource.setMaxIdle(30);
		 
		}

	public static DatabaseConnectionPool getInstance() {
		if (instance == null) {
			synchronized (lock) {
			if(instance == null)
			instance = new DatabaseConnectionPool();
			}
		}
		return instance;
	}

	public BasicDataSource getBasicSource() {
		return basicSource;
	}

	public void setBasicSource(BasicDataSource basicSource) {
		this.basicSource = basicSource;
	}
	
	
}
