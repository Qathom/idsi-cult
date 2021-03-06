package ch.unige.idsi.cultapp.model;

import java.util.List;

public class Museum extends Place {
	
	public Museum(int id, String name, String contact, String town, String address, String url, double latitude, double longitude) {
		super(id, name, contact, town, address, url, Infrastructure.MUSEUM, latitude, longitude);
	}
	
	public Museum(int id, String name, String contact, String town, String address, String url, double latitude, double longitude, List<Recommendation> recommendations) {
		super(id, name, contact, town, address, url, Infrastructure.MUSEUM, latitude, longitude, recommendations);
	}
	
	
}
