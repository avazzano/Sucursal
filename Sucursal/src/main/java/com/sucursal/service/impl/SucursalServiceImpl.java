package com.sucursal.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sucursal.exception.RecurnoNoEncontradoException;
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
	public Sucursal get(long id) throws RecurnoNoEncontradoException {
		Sucursal sucursal = sucursalRepository.getOne(id);
		
		if(sucursal == null)
			throw new RecurnoNoEncontradoException();
		
		return sucursal;		
	}

	@Override
	public Sucursal get(final double lat, final double lng) throws RecurnoNoEncontradoException {
		
		Sucursal sucursal= sucursalRepository.findAll().stream().sorted(new DistanciaComparator(lat, lng)).collect(Collectors.toList()).get(0);
		
		if(sucursal == null)
			throw new RecurnoNoEncontradoException();
		
		return sucursal;
		
	}
	
	@Override
	public List<Sucursal> get() {
		
		return sucursalRepository.findAll();
	}
	
	

}
