package com.sucursal.service;

import java.util.List;

import com.sucursal.exception.RecurnoNoEncontradoException;
import com.sucursal.model.Sucursal;

public interface SucursalService {

	public void create(Sucursal sucursal);
	
	public List<Sucursal> get();
	
	public Sucursal get(long id) throws RecurnoNoEncontradoException;
	
	public Sucursal get(double lat, double lng) throws RecurnoNoEncontradoException;
	
}
