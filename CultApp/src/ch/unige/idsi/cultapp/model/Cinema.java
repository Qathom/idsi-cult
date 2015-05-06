package ch.unige.idsi.cultapp.model;

import java.util.List;

public class Cinema extends Place {
	
	public Cinema(long id, String name, String contact, String town, String address, String url, long latitude, long longitude) {
		super(id, name, contact, town, address, url, Infrastructure.CINEMA, latitude, longitude);
	}
	
	public Cinema(long id, String name, String contact, String town, String address, String url, long latitude, long longitude, List<Recommendation> recommendations) {
		super(id, name, contact, town, address, url, Infrastructure.CINEMA, latitude, longitude, recommendations);
	}
}
