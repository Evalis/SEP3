package model;

import java.util.ArrayList;

public class MovieList {
	
	private ArrayList<Movie> movies;
	
	public MovieList() {
		
		movies = new ArrayList<>();
	}

	public ArrayList<Movie> getMovies() {
		return movies;
	}

	public void setMovies(ArrayList<Movie> movies) {
		this.movies = movies;
	}
	
	
	
	
}
