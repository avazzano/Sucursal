package com.sucursal.service;

import java.util.List;

import com.sucursal.exception.RecursoNoEncontradoException;
import com.sucursal.model.Sucursal;

public interface SucursalService {

	public void create(Sucursal sucursal);
	
	public List<Sucursal> get();
	
	public Sucursal get(long id) throws RecursoNoEncontradoException;
	
	public Sucursal get(double lat, double lng) throws RecursoNoEncontradoException;
	
}
