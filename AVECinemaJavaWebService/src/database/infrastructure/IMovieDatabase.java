package database.infrastructure;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Movie;

public interface IMovieDatabase {
	
	public boolean saveMovie(Movie movie) throws SQLException;
	public boolean updateMovieInfo(Movie movie);
	public ArrayList<Movie> getAllMovies();

}
