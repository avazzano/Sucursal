package com.sucursal.service.impl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sucursal.exception.RecursoNoEncontradoException;
import com.sucursal.model.Sucursal;
import com.sucursal.repository.SucursalRepository;
import com.sucursal.service.SucursalService;

@Service
public class SucursalServiceImpl implements SucursalService {

	@Autowired
	private SucursalRepository sucursalRepository;

	@Autowired
	private GoogleDistanceServiceClient googleDistanceServiceClient;

	
	@Transactional
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

		// No hay sucursales
		if (sucursales.isEmpty())
			throw new RecursoNoEncontradoException("No se encontro sucrusal cernana");

		try {
			sucursal = getSucursalCercanaCalculoRemoto(lat, lng, sucursales);
		} catch (Exception e) {
			sucursal = this.getSucursalCercanaCalculoLocal(lat, lng, sucursales);
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
	 * Sucursal cercana por medio de calculo usando API
	 * 
	 * @param lat
	 * @param lng
	 * @param sucursales
	 * @return
	 */
	private Sucursal getSucursalCercanaCalculoRemoto(double lat, double lng, List<Sucursal> sucursales) {

		List<Double> distancas = googleDistanceServiceClient.getDistanciasDesdeOrigen(lat, lng, sucursales);

		Map<Double, Sucursal> sucursaDistance = new HashMap<Double, Sucursal>();

		IntStream.range(0, distancas.size()).forEach(i -> sucursaDistance.put(distancas.get(i), sucursales.get(i)));

		Double distanciaMenor = distancas.stream().min(Comparator.comparing(item -> item)).get();

		return sucursaDistance.get(distanciaMenor);

	}

	/**
	 * Sucursal cercana por medio de calculo local.
	 * 
	 * @param lat
	 * @param lng
	 * @param sucursales
	 * @return
	 */
	private Sucursal getSucursalCercanaCalculoLocal(double lat, double lng, List<Sucursal> sucursales) {

		Sucursal sucursal = sucursales.stream().sorted(new DistanciaComparator(lat, lng)).collect(Collectors.toList())
				.get(0);

		return sucursal;
	}

}
