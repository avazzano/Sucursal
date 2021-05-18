package com.sucursal.service;

import com.sucursal.model.Sucursal;

public interface SucursalService {

	public void create(Sucursal sucursal);
	
	public Sucursal get(long id);
	
	public Sucursal get(double lat, double lng);
	
}
