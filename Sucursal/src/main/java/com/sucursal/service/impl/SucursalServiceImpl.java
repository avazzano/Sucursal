package com.sucursal.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sucursal.exception.RecursoNoEncontradoException;
import com.sucursal.model.Sucursal;
import com.sucursal.model.SucursalDistance;
import com.sucursal.repository.SucursalRepository;
import com.sucursal.service.GoogleDistanceServiceClient;
import com.sucursal.service.SucursalService;

@Service
public class SucursalServiceImpl implements SucursalService {

	@Autowired
	private SucursalRepository sucursalRepository;

	@Autowired
	private GoogleDistanceServiceClient googleDistanceServiceClient;

	@Override
	public void create(Sucursal sucursal) {
		sucursalRepository.save(sucursal);

	}

	@Override
	public Sucursal get(long id) throws RecursoNoEncontradoException {
		Sucursal sucursal = sucursalRepository.getOne(id);

		if (sucursal == null)
			throw new RecursoNoEncontradoException("No se encontro sucrusal");

		return sucursal;
	}

	@Override
	public Sucursal get(final double lat, final double lng) throws RecursoNoEncontradoException {

		Sucursal sucursal;
		
		List<Sucursal> sucursales = this.sucursalRepository.findAll();

		//No hay sucursales
		if(sucursales.isEmpty())
			throw new RecursoNoEncontradoException("No se encontro sucrusal cernana");
		
		try {
			sucursal = getSucursalCalculateRemote(lat, lng, sucursales);
		} catch (Exception e) {
			sucursal = this.getSucursalCalculteLocal(lat, lng, sucursales);
		}

		if (sucursal == null)
			throw new RecursoNoEncontradoException("No se encontro sucrusal cernana");

		return sucursal;

	}

	@Override
	public List<Sucursal> get() {
		return sucursalRepository.findAll();
	}

	/**
	 * Call Api de google para calcular distancia.
	 * 
	 * @param lat 
	 * @param lng
	 * @param sucursales
	 * @return
	 */
	private Sucursal getSucursalCalculateRemote(double lat, double lng, List<Sucursal> sucursales) {

		List<SucursalDistance> sucursalDistance = googleDistanceServiceClient.geoDistance(lat, lng, sucursales);

		SucursalDistance sucursalCercana = sucursalDistance.stream().min(Comparator.comparing(item -> item.getDistance())).get();

		return sucursalCercana.getSucursal();
	}

	/**
	 * Calcula distancia localmente con formula.
	 * 
	 * @param lat
	 * @param lng
	 * @param sucursales
	 * @return
	 */
	private Sucursal getSucursalCalculteLocal(double lat, double lng, List<Sucursal> sucursales) {

		Sucursal sucursal = sucursales.stream().sorted(new DistanciaComparator(lat, lng)).collect(Collectors.toList()).get(0);

		return sucursal;
	}

}
