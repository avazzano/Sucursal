package com.sucursal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sucursal.exception.RecurnoNoEncontradoException;
import com.sucursal.model.Sucursal;
import com.sucursal.service.SucursalService;

public class SucursalTest {

	
	@Autowired
	private SucursalService sucursalService;
	
	@Test
	public void testBuscarSucusalCercana() throws RecurnoNoEncontradoException {
		
		Sucursal suc1=new Sucursal();
		suc1.setNombre("La Plata");
		suc1.setLat(-32.15);
		suc1.setLng(-38.13);
	
		sucursalService.create(suc1);
		
		Sucursal suc2=new Sucursal();
		suc2.setNombre("Junin");
		suc2.setLat(-36.15);
		suc2.setLng(-28.13);
	
		sucursalService.create(suc2);
		
		Sucursal suc3=new Sucursal();
		suc3.setNombre("Necochea");
		suc3.setLat(-40.55);
		suc3.setLng(-36.84);
	
		sucursalService.create(suc3);
		
		
		Sucursal sucursalcercana = sucursalService.get(-36.16, -32.0001);
		
		assertEquals(sucursalcercana, suc2);
		
	
	}
	
}
