package com.sucursal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sucursal.model.Sucursal;

@Service
public class GoogleDistanceServiceClient {

	private static String API_KYE = "AIzaSyCwUYq4Sgp_gYOj2jg-BW6VUEFZwUOlxlM";

	public List<Double> getDistanciasDesdeOrigen(double latOrigen, double lngOrigen, List<Sucursal> sucursales) {

		String origins = "origins=" + latOrigen + "," + lngOrigen;

		String destinations = "destinations=";
		for (Sucursal sucursal : sucursales) {
			destinations = destinations + sucursal.getLat() + "," + sucursal.getLng() + "|";
		}

		ResponseEntity<String> response = callApi(origins, destinations);

		return parseRta(response);

	}

	private ResponseEntity<String> callApi(String origins, String destinations) {
		RestTemplate restTemplateBuilder = new RestTemplate();
		ResponseEntity<String> response = restTemplateBuilder
				.getForEntity("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&" + origins + "&"
						+ destinations + "&key=" + API_KYE, String.class);
		return response;
	}

	private List<Double> parseRta(ResponseEntity<String> response) {

		List<Double> distancias = new ArrayList<Double>();

		JsonObject jsonObject = JsonParser.parseString(response.getBody()).getAsJsonObject();
		JsonObject rows = jsonObject.get("rows").getAsJsonArray().get(0).getAsJsonObject();
		JsonArray elements = rows.get("elements").getAsJsonArray();

		elements.forEach(item -> {
			JsonObject obj = (JsonObject) item;
			JsonObject distance = obj.get("distance").getAsJsonObject();
			System.out.println(distance.get("text"));

			distancias.add(Double.parseDouble(distance.get("text").getAsString().split(" ")[0].trim()));
		});

		return distancias;

	}
}
