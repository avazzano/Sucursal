package com.sucursal.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sucursal.exception.RecursoNoEncontradoException;
import com.sucursal.model.Sucursal;
import com.sucursal.service.SucursalService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/sucursal/")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@Api(tags ="Endpoint Sucursal")
public class SucursalResource {

	@Autowired
	private SucursalService sucursalService;
	
	@GetMapping("{id}")
	@ApiOperation(value = "Obtener Scursal por id")
	public ResponseEntity<Sucursal> getSucursal(@PathVariable long id) throws RecursoNoEncontradoException {
		 
		Sucursal sucursal = sucursalService.get(id);
		
		return ResponseEntity.ok(sucursal);
	}
	
	@GetMapping()
	@ApiOperation(value = "Listar Scursales")
	public ResponseEntity<List<Sucursal>> getSucursal() {
		 
		List<Sucursal> sucursal = sucursalService.get();
		
		return ResponseEntity.ok(sucursal);
	}
	
	
	@GetMapping("{lat}/{lng}")
	@ApiOperation(value = "Sucursal mas cercana")
	public ResponseEntity<Sucursal> getSucursal(@PathVariable double lat, @PathVariable double lng) throws RecursoNoEncontradoException{

		Sucursal sucursal=sucursalService.get(lat, lng);
		
		return ResponseEntity.ok(sucursal);
	
	}
	
	
	@PostMapping
	@ApiOperation(value = "Alta sucursal")
	public ResponseEntity<String> addSucursal(@RequestBody Sucursal sucursal){
		
		sucursalService.create(sucursal);
		
		return  ResponseEntity.ok("OK");
		
	}
	
}
