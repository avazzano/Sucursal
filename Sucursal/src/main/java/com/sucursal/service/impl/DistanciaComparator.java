package com.sucursal.service.impl;

import java.util.Comparator;

import com.sucursal.model.Sucursal;

public class DistanciaComparator implements Comparator<Sucursal> {
    
    private double earthRadius = 6371.01; //Kilometers
	private double lat;
	private double lng;
	
	DistanciaComparator(double lat, double lng){
		  this.lat=lat;
		  this.lng=lng;
	}
		
	@Override
    public int compare(Sucursal suc1, Sucursal suc2) {
       
        //Punto Origen
        double latDesde = Math.toRadians(lat);
        double lngDesde = Math.toRadians(lng);
        
        //Calcula distancia Origen -> Sucusal1
        double lat1 = Math.toRadians(suc1.getLat());
        double lon1 = Math.toRadians(suc1.getLng());
        Double distanciaA = earthRadius * Math.acos(Math.sin(latDesde)*Math.sin(lat1) + Math.cos(latDesde)*Math.cos(lat1)*Math.cos(lngDesde - lon1));
        
        //Calculo distancia Origen -> Sucusal2    	        
        double lat2 = Math.toRadians(suc2.getLat());
        double lon2 = Math.toRadians(suc2.getLng());
        Double distanciaB = earthRadius * Math.acos(Math.sin(latDesde)*Math.sin(lat2) + Math.cos(latDesde)*Math.cos(lat2)*Math.cos(lngDesde - lon2));
        
    	
    	return  distanciaA.compareTo(distanciaB);
    }
	
	
}