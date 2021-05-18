package com.sucursal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sucursal.model.Sucursal;
import com.sucursal.repository.SucursalRepository;
import com.sucursal.service.SucursalService;

@Service
public class SucursalServiceImpl implements SucursalService{

	@Autowired
	private SucursalRepository sucursalRepository;
	
	@Override
	public void create(Sucursal sucursal) {
			sucursalRepository.save(sucursal);
		
	}

	@Override
	public Sucursal get(long id) {
		return sucursalRepository.getOne(id);
	}

	@Override
	public Sucursal get(double lat, double lng) {
		
		//TOOD
		
		
		return null;
	}
	
	

}
