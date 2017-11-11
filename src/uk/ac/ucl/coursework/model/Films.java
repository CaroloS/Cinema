package uk.ac.ucl.coursework.model;

public class Films {
	
	
	private String filmTitle, genre, description;
	private int filmID, startTime, endTime;
	private int[] date;
	
	
	public int getFilmID() {
		return filmID;
	}
	public void setFilmID(int filmID) {
		this.filmID = filmID;
	}
	
	public String getFilmTitle() {
		return filmTitle;
	}
	public void setFilmTitle(String filmTitle) {
		this.filmTitle = filmTitle;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public int getEndTime() {
		return endTime;
	}
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	public int[] getDate() {
		return date;
	}
	public void setDate(int[] date) {
		this.date = date;
	}
	
	

}
