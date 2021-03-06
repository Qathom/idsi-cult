package ch.unige.idsi.cultweb.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ch.unige.idsi.cultweb.model.Cinema;
import ch.unige.idsi.cultweb.model.Location;
import ch.unige.idsi.cultweb.model.Museum;
import ch.unige.idsi.cultweb.model.Place;
import ch.unige.idsi.cultweb.model.RecommendationManager;
import ch.unige.idsi.cultweb.model.Place.Infrastructure;
import ch.unige.idsi.cultweb.model.Recommendation;

/**
 * 
 * The role of DataAccessObject is to retrieve our model Data like a Place (Museum, Cinema) object
 * or the list of cinemas/museums
 */
public class DataAccessObject {

	private List<Museum> museums = new ArrayList<Museum>();
	private List<Cinema> cinemas = new ArrayList<Cinema>();

	public DataAccessObject() throws IOException {
		this.getMuseums();
		this.getCinemas();
	}
	
	/**
	 * Retrieves museums
	 * @return
	 * @throws IOException
	 */
	public List<Museum> getMuseums() throws IOException {
		
		if(this.museums.isEmpty()) {
			
			DataRequest req = new DataRequest();
			
			JSONObject json = req.getMuseums();
			JSONArray array = (JSONArray) json.get("features");
			
			int i = 0;
			int size = array.size();
			
			for (; i < size; i++) {
	
				JSONObject obj = (JSONObject) array.get(i);
				JSONObject attributes = (JSONObject) obj.get("attributes");
	
				int id = (int) ((long) attributes.get("ID_INFRASTRUCTURE"));
				String name = (String) attributes.get("NOM");
				String contact = (String) attributes.get("CONTACT");
				String town = (String) attributes.get("COMMUNE");
				String address = (String) attributes.get("ADRESSE");
				String url = (String) attributes.get("LIEN_WWW");
				
				Museum m = new Museum(id, name, contact, town, address, url, 0, 0);
	
				this.museums.add(m);
			}
		}
		
		return this.museums;
	}
	
	/**
	 * Retrieves cinemas
	 * @return
	 * @throws IOException
	 */
	public List<Cinema> getCinemas() throws IOException {

		if(this.cinemas.isEmpty()) {
			
			DataRequest req = new DataRequest();
	
			JSONObject json = req.getCinemas();
			JSONArray array = (JSONArray) json.get("features");
			
			int i = 0;
			int size = array.size();
			
			for (; i < size; i++) {
	
				JSONObject obj = (JSONObject) array.get(i);
				JSONObject attributes = (JSONObject) obj.get("attributes");
	
				int id = (int) ((long) attributes.get("ID_INFRASTRUCTURE"));
				String name = (String) attributes.get("NOM");
				String contact = (String) attributes.get("CONTACT");
				String town = (String) attributes.get("COMMUNE");
				String address = (String) attributes.get("ADRESSE");
				String url = (String) attributes.get("LIEN_WWW");
	
				Cinema c = new Cinema(id, name, contact, town, address, url, 0, 0);
	
				this.cinemas.add(c);
			}
		}
		return cinemas;
	}
	
	/**
	 * Retrieves a specific place
	 * @param id
	 * @param infrastructure
	 * @return
	 * @throws IOException
	 */
	public Place getPlace(int id, Infrastructure infrastructure) throws IOException {
		
		Place place = null;
	
		if(infrastructure.equals(Infrastructure.MUSEUM)) {
			for(Museum m : this.museums) {
				if(m.getId() == id) {
					place = m;
				}
			}
		} else if(infrastructure.equals(Infrastructure.CINEMA)) {
			for(Cinema c : this.cinemas) {
				if(c.getId() == id) {
					place = c;
				}
			}
		}
		
		if(place != null) {
			RecommendationManager rm = new RecommendationManager();
			List<Recommendation> recommendations = rm.get(id);
			place.setRecommendations(recommendations);
			
			DataRequest r = new DataRequest();
			Location l = r.getLocation(place.getAddress());
			
			place.setLatitude(l.getLatitude());
			place.setLongitude(l.getLongitude());
		}
		
		return place;
	}

}
