package com.sucursal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sucursal.model.Sucursal;
import com.sucursal.model.SucursalDistance;

@Service
public class GoogleDistanceServiceClient {

	private String API_KYE="AIzaSyCwUYq4Sgp_gYOj2jg-BW6VUEFZwUOlxlM";
	
	/**
	 * Call a la API de google
	 * 
	 * @param lat
	 * @param lng
	 * @param sucursal
	 * @return
	 */
	private String callAPI(double lat, double lng, Sucursal sucursal) {

		String origins = "origins=" + lat + "," + lng;

		String destinations = "destinations=";
		destinations = destinations + sucursal.getLat() + "," + sucursal.getLng();

		RestTemplate restTemplateBuilder = new RestTemplate();
		ResponseEntity<String> response = restTemplateBuilder
				.getForEntity("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&" + origins + "&"
						+ destinations + "&key="+API_KYE, String.class);

		
		return response.getBody();
	}
	
	/**
	 * Obtiene distancia de la respuesta de la API  
	 * 
	 * @param json
	 * @return
	 */
	private double getDistanceFromJson(String json) {
		
		JsonParser parser = new JsonParser();

		JsonElement parsedTree = parser.parse(json);

		JsonObject root = parsedTree.getAsJsonObject();

		JsonObject firstRow = root.get("rows").getAsJsonArray().get(0).getAsJsonObject();

		JsonObject firstElement = firstRow.get("elements").getAsJsonArray().get(0).getAsJsonObject();
		JsonObject distance = firstElement.get("distance").getAsJsonObject();

		return Double.parseDouble(distance.get("text").getAsString().split(" ")[0].trim());

	}
	
	private SucursalDistance getSucursalDistance(double lat, double lng, Sucursal sucursal) {
		
		SucursalDistance sucursalDistance=new SucursalDistance();
		sucursalDistance.setSucursal(sucursal);
		sucursalDistance.setDistance(this.getDistanceFromJson( this.callAPI(lat, lng, sucursal)));
		return sucursalDistance;
	}

	public List<SucursalDistance> geoDistance(double lat, double lng, List<Sucursal> sucursales) {

		List<SucursalDistance> sucursalesDistance = new ArrayList<SucursalDistance>();

		sucursales.stream().forEach(s -> sucursalesDistance.add(this.getSucursalDistance(lat, lng, s)));

		return sucursalesDistance;

	}
	
	


//
//
//	public List<SucursalDistance> geoDistance(double lat, double lng, List<Sucursal> sucursales) {
//
//
//		List<SucursalDistance> sucursalesDistance=new ArrayList<SucursalDistance>();
//		for (Sucursal sucursal : sucursales) {
//			sucursalesDistance.add(this.geoDistance(lat, lng, sucursal));
//		}
//		
//        return sucursalesDistance;		
////		String origins = "origins=" + lat + "," + lng;
////
////		String destinations = "destinations=";
////		for (Sucursal sucursal : sucursales) {
////			destinations = destinations + sucursal.getLat() + "," + sucursal.getLng() + "|";
////		}
////		
////		RestTemplate restTemplateBuilder = new RestTemplate();
////		ResponseEntity<String> response = restTemplateBuilder
////				.getForEntity("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&" + origins + "&"
////						+ destinations + "&key=AIzaSyCwUYq4Sgp_gYOj2jg-BW6VUEFZwUOlxlM", String.class);
////
////	      
////		  JsonParser parser = new JsonParser();
////
////	      JsonElement parsedTree = parser.parse(response.getBody());
////	      
////	      JsonObject root = parsedTree.getAsJsonObject();
////	      
////	      JsonObject firstRow = root.get("rows").getAsJsonArray().get(0).getAsJsonObject();
////	      
////	      JsonArray firstElement = firstRow.get("elements").getAsJsonArray();
////	      
////	      firstElement.forEach(item -> {
////	    	   JsonObject obj = (JsonObject) item;
////	    	   JsonObject distance = obj.get("distance").getAsJsonObject();
////	    	  System.out.println( distance.get("text"));
////	    	  
////	    	});
////
//		
//	}
}
