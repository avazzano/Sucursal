package com.sucursal.repository;

import org.springframework.data.geo.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sucursal.model.Sucursal;

public interface SucursalRepository extends JpaRepository<Sucursal, Long>{
	

}
