package com.sucursal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sucursal.exception.RecursoNoEncontradoException;
import com.sucursal.model.Sucursal;
import com.sucursal.service.SucursalService;


@SpringBootTest
public class SucursalTest {

	
	@Autowired
	private SucursalService sucursalService;
	
	@Test
	public void testBuscarSucusalCercana() throws RecursoNoEncontradoException {
		
		Sucursal suc1=new Sucursal();
		suc1.setNombre("La Plata");
		suc1.setLat(-34.9176179);
		suc1.setLng(-57.9638704);	
		sucursalService.create(suc1);
		
		Sucursal suc2=new Sucursal();
		suc2.setNombre("Junin");
		suc2.setLat(-34.5908286);
		suc2.setLng(-60.9904505);	
		sucursalService.create(suc2);
		
		Sucursal suc3=new Sucursal();
		suc3.setNombre("Necochea");
		suc3.setLat(-38.5605203);
		suc3.setLng(-58.8263163);	
		sucursalService.create(suc3);
		
		Sucursal suc4=new Sucursal();
		suc4.setNombre("Rosario");
		suc4.setLat(-32.9521898);
		suc4.setLng(-60.7666798);	
		sucursalService.create(suc4);	
		
		
		//Coordenadas de CABA -34.6022703,-58.3915682
		Sucursal sucursalcercana = sucursalService.get(-34.6022703, -58.3915682);
		
		assertEquals(sucursalcercana.getId(), suc1.getId());
		
	
	}
	
}
