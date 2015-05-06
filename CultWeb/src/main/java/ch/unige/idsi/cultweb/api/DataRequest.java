package ch.unige.idsi.cultweb.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import ch.unige.idsi.cultweb.model.Location;

public class DataRequest {

	private String MUSEUM_API = "http://ge.ch/ags1/rest/services/Culture/MapServer/1/query?text=Mus%C3%A9e&geometry=&geometryType=esriGeometryPoint&inSR=&spatialRel=esriSpatialRelIntersects&relationParam=&objectIds=&where=&time=&returnCountOnly=false&returnIdsOnly=false&returnGeometry=true&maxAllowableOffset=&outSR=&outFields=*&f=pjson";
	private String CINEMA_API = "http://ge.ch/ags1/rest/services/Culture/MapServer/1/query?text=Cin%C3%A9ma&geometry=&geometryType=esriGeometryPoint&inSR=&spatialRel=esriSpatialRelIntersects&relationParam=&objectIds=&where=&time=&returnCountOnly=false&returnIdsOnly=false&returnGeometry=true&maxAllowableOffset=&outSR=&outFields=*&f=pjson";
	
	private boolean LOCAL_ONLY = true;
	
	public JSONArray getMuseums() throws IOException {
		
		JSONObject json = new JSONObject();
		
		if(this.LOCAL_ONLY) {
			json = this.readFile("museums.json");
		} else {
			json = this.sendGet(MUSEUM_API);
		}
		
		return (JSONArray) json.get("features");
	}
	
	public JSONArray getCinemas() throws IOException {
		
		JSONObject json = new JSONObject();
		
		if(this.LOCAL_ONLY) {
			json = this.readFile("cinemas.json");
		} else {
			json = this.sendGet(CINEMA_API);
		}
		return (JSONArray) json.get("features");
	}
	
	public Location getLocation(String address) throws IOException {
		
		JSONObject json = this.sendGet("http://maps.google.com/maps/api/geocode/json?address="+address+" Genève, Switzerland&sensor=false");
		
		JSONArray results = (JSONArray) json.get("results");
		JSONObject res = (JSONObject) results.get(0);
		JSONObject geometry = (JSONObject) res.get("geometry");
		JSONObject location = (JSONObject) geometry.get("location");

		double lat = (double) location.get("lat");
		double lng = (double) location.get("lng");

		return new Location(lat, lng);
	}
	
	private JSONObject sendGet(String url) throws IOException {
		
		url = url.replace(" ", "%20");
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		JSONObject o = (JSONObject) JSONValue.parse(response.toString());
		return o;
	}
	
	/**
	 * Reads a file from resources folder
	 * By mkyong: http://www.mkyong.com/java/java-read-a-file-from-resources-folder/
	 * @param filename
	 * @return
	 */
	private JSONObject readFile(String fileName) {

		StringBuilder result = new StringBuilder("");
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
	 
		try (Scanner scanner = new Scanner(file)) {
	 
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}
	 
			scanner.close();
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
	 
		return (JSONObject) JSONValue.parse(result.toString());
	}
}