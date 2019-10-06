package database.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.dbcp2.BasicDataSource;

import database.infrastructure.IMovieDatabase;
import model.Movie;

public class MovieDatabase implements IMovieDatabase {

	private Connection connection;

	public MovieDatabase() {
		BasicDataSource dataSource = DatabaseConnectionPool.getInstance().getBasicSource();
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean saveMovie(Movie movie) throws SQLException{
		String sql = "INSERT INTO cinema.movie("
				+ "movieID, title, genre, director, price, lenght, actor, posterUrl, overview, language) values(?,?,?,?,?,?,?,?,?,?)"
				+ " ON CONFLICT (movieID) "
				+ " DO "
				+ "UPDATE SET  " + " title = ?," + " genre = ?," + " director = ?," + " price = ?,"
				+ " lenght = ?," + " actor = ?," + " posterUrl = ?," + " overview = ?," + " language = ?,"  + " movieID = ?" ;
		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, movie.getMovieID());
			pStatement.setString(2, movie.getTitle());
			pStatement.setString(3, movie.getGenre());
			pStatement.setString(4, movie.getDirector());
			pStatement.setDouble(5, movie.getPrice());
			pStatement.setInt(6, movie.getLenght());
			pStatement.setString(7, movie.getActor());
			pStatement.setString(8, movie.getPosterUrl());
			pStatement.setString(9, movie.getOverview());
			pStatement.setString(10, movie.getLanguage());
			pStatement.setString(11, movie.getTitle());
			pStatement.setString(12, movie.getGenre());
			pStatement.setString(13, movie.getDirector());
			pStatement.setDouble(14, movie.getPrice());
			pStatement.setInt(15, movie.getLenght());
			pStatement.setString(16, movie.getActor());
			pStatement.setString(17, movie.getPosterUrl());
			pStatement.setString(18, movie.getOverview());
			pStatement.setString(19, movie.getLanguage());
			pStatement.setString(20, movie.getMovieID());
			pStatement.executeUpdate();
			System.out.println("New Movie added to database.");
		} catch (SQLException e) {
			e.printStackTrace();
			if(e.getSQLState().equals("23505"))
			{
				throw e;
			}
			return false;

		}
		return true;
	}

	public boolean updateMovieInfo(Movie movie) {
		String sql = "UPDATE cinema.movie SET " + " title = ?," + " genre = ?," + " director = ?," + " price = ?,"
				+ " lenght = ?," + " actor = ?," + " posterUrl = ?," + " overview = ?," + " language = ?"
				+ " WHERE movieID = ? ";
		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setString(11, movie.getTitle());
			pStatement.setString(12, movie.getGenre());
			pStatement.setString(13, movie.getDirector());
			pStatement.setDouble(14, movie.getPrice());
			pStatement.setInt(15, movie.getLenght());
			pStatement.setString(16, movie.getActor());
			pStatement.setString(17, movie.getPosterUrl());
			pStatement.setString(18, movie.getOverview());
			pStatement.setString(19, movie.getLanguage());
			pStatement.setString(20, movie.getMovieID());
			pStatement.executeUpdate();
			System.out.println("Movie has been edided.");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		}

		return true;
	}

	@Override
	public ArrayList<Movie> getAllMovies() {
		ArrayList<Movie> temp = new ArrayList<>();

		String sql = "SELECT * FROM  cinema.movie ";
		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				Movie movie = new Movie();
				movie.setMovieID(resultSet.getString("movieID"));
				movie.setTitle(resultSet.getString("title"));
				movie.setGenre(resultSet.getString("genre"));
				movie.setDirector(resultSet.getString("director"));
				movie.setPrice(resultSet.getDouble("price"));
				movie.setLenght(resultSet.getInt("lenght"));
				movie.setActor(resultSet.getString("actor"));
				movie.setPosterUrl(resultSet.getString("posterUrl"));
				movie.setOverview(resultSet.getString("overview"));
				movie.setLanguage(resultSet.getString("language"));
				

				temp.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return temp;
		}
		return temp;
	}
}
