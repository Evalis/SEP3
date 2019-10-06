package model;

import java.util.ArrayList;

public class Movie {
	
	private String movieID;
	private String title;
	private String genre;
	private String director;
	private double price;
	private int lenght;
	private String actor;
	private String posterUrl;
	private String overview;
	private String language;
	private ArrayList<Schedule> schedules;
	
	public Movie()
	{
		schedules = new ArrayList<>();
	}

	public String getMovieID() {
		return movieID;
	}

	public void setMovieID(String movieID) {
		this.movieID = movieID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getLenght() {
		return lenght;
	}

	public void setLenght(int lenght) {
		this.lenght = lenght;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public ArrayList<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(ArrayList<Schedule> schedules) {
		this.schedules = schedules;
	}

	

}
